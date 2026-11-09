// mesh.rs
// Author: Connor Petri
// CS-116A

use std::any::Any;
use std::collections::HashMap;
use std::fs;
use std::path::Path;

use glam::{Vec2, Vec3};

use crate::material::Material;
use crate::ray::Ray;
use crate::shape::{Hit, Shape};
use crate::transform::Transform;


pub struct Mesh {
    pub transform:  Transform,
    pub vertices:   Vec<Vec3>,
    pub triangles:  Vec<[u32; 3]>,
    pub normals:    Vec<Vec3>,
    pub uvs:        Vec<Vec2>,
    pub material:   Material,
    bbox_min:       Vec3,
    bbox_max:       Vec3,
    // Bounding volume hierarchy over `triangles`, stored flat in depth-first
    // order. Node 0 is the root; an interior node's left child is always the
    // next node, so only the right child's index is stored. Built in local
    // space, so moving the mesh with `transform` never invalidates it.
    nodes:          Vec<BvhNode>,
    // Permutation of triangle indices so each leaf owns one contiguous
    // range of `tri_order`. `triangles` itself keeps its original order.
    tri_order:      Vec<u32>,
}

// One node of the mesh's BVH. 32 bytes.
#[derive(Clone, Copy, Debug)]
struct BvhNode {
    bbox_min:      Vec3,
    bbox_max:      Vec3,
    // Leaf: index into `tri_order` of its first triangle.
    // Interior: index of the right child.
    first_or_right: u32,
    // Leaf: number of triangles (> 0). Interior: 0.
    count:          u16,
    // Interior: the axis the children were split on, used to visit the
    // nearer child first.
    axis:           u8,
}

// Leaves hold at most this many triangles.
const BVH_LEAF_SIZE: usize = 4;

// When true, `Mesh::hit_local` walks the BVH and only calls `hit_triangle`
// on triangles whose boxes the ray enters. When false, it calls
// `hit_triangle` on every triangle in the mesh. Same answer either way; the
// BVH is just a faster search order.
pub const USE_BVH: bool = true;

impl Mesh {
    // Builds a mesh from positions and index triples and computes
    // area-weighted smooth vertex normals.
    pub fn new(vertices: Vec<Vec3>, triangles: Vec<[u32; 3]>, material: Material) -> Self {
        let mut m = Self {
            transform: Transform::IDENTITY,
            vertices,
            triangles,
            normals: Vec::new(),
            uvs: Vec::new(),
            material,
            bbox_min: Vec3::ZERO,
            bbox_max: Vec3::ZERO,
            nodes: Vec::new(),
            tri_order: Vec::new(),
        };
        m.compute_vertex_normals();
        m.compute_bounds();
        m
    }

    pub fn with_transform(mut self, transform: Transform) -> Self {
        self.transform = transform;
        self
    }

    pub fn new_flat(vertices: Vec<Vec3>, triangles: Vec<[u32; 3]>, material: Material) -> Self {
        let mut m = Self::new(vertices, triangles, material);
        m.normals.clear();
        m
    }

    pub fn corners(&self, t: usize) -> [Vec3; 3] {
        let [i, j, k] = self.triangles[t];
        [self.vertices[i as usize], self.vertices[j as usize], self.vertices[k as usize]]
    }

    // Un-normalized face normal of triangle `t`. Its length is twice the
    // triangle's area, which is what makes the vertex-normal average
    // area-weighted.
    pub fn face_normal(&self, t: usize) -> Vec3 {
        let [a, b, c] = self.corners(t);
        (b - a).cross(c - a)
    }

    // Cramer's rule. Directly copied from lecture notes
    pub fn hit_triangle(&self, t: usize, ray: &Ray, t_min: f32, t_max: f32)
        -> Option<(f32, f32, f32)>
    {
        let [i, j, k] = self.triangles[t];
        let a = self.vertices[i as usize];
        let b = self.vertices[j as usize];
        let c = self.vertices[k as usize];

        let ab = a - b;
        let ac = a - c;
        let ao = a - ray.origin();

        let det_a = det(ab, ac, ray.direction());
        if det_a.abs() < 1e-8 {
            return None; // Ray is parallel to the triangle
        }

        let t = det(ab, ac, ao) / det_a;
        if t < t_min || t > t_max {
            return None;
        }

        let gamma = det(ab, ao, ray.direction()) / det_a;
        if gamma < 0.0 || gamma > 1.0 {
            return None;
        }

        let beta = det(ao, ac, ray.direction()) / det_a;
        if beta < 0.0 || beta > 1.0 - gamma {
            return None;
        }

        Some((t, beta, gamma))
    }

    pub fn compute_vertex_normals(&mut self) {
        let mut acc = vec![Vec3::ZERO; self.vertices.len()];
        for t in 0..self.triangles.len() {
            let n = self.face_normal(t);
            for &i in &self.triangles[t] {
                acc[i as usize] += n;
            }
        }
        self.normals = acc.into_iter().map(|n| n.normalize_or_zero()).collect();
    }

    pub fn compute_bounds(&mut self) {
        let mut lo = Vec3::splat(f32::INFINITY);
        let mut hi = Vec3::splat(f32::NEG_INFINITY);
        for v in &self.vertices {
            lo = lo.min(*v);
            hi = hi.max(*v);
        }
        if self.vertices.is_empty() {
            lo = Vec3::ZERO;
            hi = Vec3::ZERO;
        }
        self.bbox_min = lo;
        self.bbox_max = hi;
        self.build_bvh();
    }

    pub fn bounds(&self) -> (Vec3, Vec3) {
        (self.bbox_min, self.bbox_max)
    }

    // BVH ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Rebuilds the BVH over all triangles: a median split on the longest
    // axis of the centroid bounds at every level, down to leaves of at most
    // `BVH_LEAF_SIZE` triangles.
    fn build_bvh(&mut self) {
        let n = self.triangles.len();
        self.nodes.clear();
        self.tri_order = (0..n as u32).collect();
        if n == 0 {
            return;
        }

        // Per-triangle bounds and centroids, computed once.
        let boxes: Vec<(Vec3, Vec3)> = (0..n).map(|t| {
            let [a, b, c] = self.corners(t);
            (a.min(b).min(c), a.max(b).max(c))
        }).collect();
        let centroids: Vec<Vec3> = boxes.iter().map(|(lo, hi)| 0.5 * (*lo + *hi)).collect();

        // A balanced tree over n leaves of size LEAF has about 2n/LEAF nodes.
        self.nodes.reserve(2 * n / BVH_LEAF_SIZE + 1);
        self.build_node(0, n, &boxes, &centroids);
    }

    // Builds the subtree covering `tri_order[start..end]`, appending its
    // nodes to `self.nodes`, and returns the index of the subtree's root.
    fn build_node(&mut self, start: usize, end: usize,
                  boxes: &[(Vec3, Vec3)], centroids: &[Vec3]) -> u32 {
        let mut lo = Vec3::splat(f32::INFINITY);
        let mut hi = Vec3::splat(f32::NEG_INFINITY);
        let mut c_lo = lo;
        let mut c_hi = hi;
        for &t in &self.tri_order[start..end] {
            let (b_lo, b_hi) = boxes[t as usize];
            lo = lo.min(b_lo);
            hi = hi.max(b_hi);
            c_lo = c_lo.min(centroids[t as usize]);
            c_hi = c_hi.max(centroids[t as usize]);
        }

        let idx = self.nodes.len() as u32;
        self.nodes.push(BvhNode { bbox_min: lo, bbox_max: hi, first_or_right: 0, count: 0, axis: 0 });

        let extent = c_hi - c_lo;
        let axis = if extent.x >= extent.y && extent.x >= extent.z { 0 }
                   else if extent.y >= extent.z { 1 }
                   else { 2 };

        let count = end - start;
        // Stop at small ranges, and also when every centroid coincides, which
        // a median split could never separate.
        if count <= BVH_LEAF_SIZE || extent[axis] <= 0.0 {
            self.nodes[idx as usize].first_or_right = start as u32;
            self.nodes[idx as usize].count = count.min(u16::MAX as usize) as u16;
            return idx;
        }

        // Median split: O(n) partition, no full sort.
        let half = count / 2;
        self.tri_order[start..end].select_nth_unstable_by(half, |&a, &b| {
            centroids[a as usize][axis].total_cmp(&centroids[b as usize][axis])
        });
        let mid = start + half;

        // Left child is built first so it lands at idx + 1.
        self.build_node(start, mid, boxes, centroids);
        let right = self.build_node(mid, end, boxes, centroids);
        self.nodes[idx as usize].first_or_right = right;
        self.nodes[idx as usize].axis = axis as u8;
        idx
    }

    // OBJ loading ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Loads a Wavefront .obj file.
    //
    // Handles `v`, `vt`, `vn`, and `f` lines, 1-based and negative indices,
    // and polygons with more than three corners (fan triangulated, which
    // preserves winding). Other lines (`o`, `g`, `s`, `usemtl`, `mtllib`,
    // comments) are ignored.
    //
    // Each distinct `position/uv/normal` triple in the file becomes one
    // mesh vertex, so seams with split normals or uvs are preserved. If the
    // file has no `vn` lines, smooth normals are computed instead.
    //
    // Panics with the file name and line number if the file can't be read
    // or contains a malformed line, since a missing model is not something
    // the renderer can carry on without.
    pub fn from_obj(path: impl AsRef<Path>, material: Material) -> Mesh {
        let path = path.as_ref();
        let text = fs::read_to_string(path)
            .unwrap_or_else(|e| panic!("failed to read {}: {e}", path.display()));
        Self::parse_obj(&text, material)
    }

    // Parses OBJ text. See `from_obj`. Panics on malformed input.
    pub fn parse_obj(text: &str, material: Material) -> Mesh {
        // Raw tables as they appear in the file.
        let mut positions: Vec<Vec3> = Vec::new();
        let mut uvs: Vec<Vec2> = Vec::new();
        let mut normals: Vec<Vec3> = Vec::new();

        // Output mesh. Each unique (p, t, n) index triple is one vertex.
        let mut out_vertices: Vec<Vec3> = Vec::new();
        let mut out_uvs: Vec<Vec2> = Vec::new();
        let mut out_normals: Vec<Vec3> = Vec::new();
        let mut triangles: Vec<[u32; 3]> = Vec::new();
        let mut remap: HashMap<(u32, Option<u32>, Option<u32>), u32> = HashMap::new();

        let mut any_missing_normal = false;

        for (line_idx, raw) in text.lines().enumerate() {
            let line_no = line_idx + 1;
            let fail = |msg: String| -> ! { panic!("OBJ line {line_no}: {msg}") };

            // Strip trailing comment and surrounding whitespace.
            let line = raw.split('#').next().unwrap_or("").trim();
            if line.is_empty() {
                continue;
            }

            let mut parts = line.split_whitespace();
            match parts.next() {
                Some("v") => {
                    let v = parse_floats::<3>(&mut parts).unwrap_or_else(|m| fail(m));
                    positions.push(Vec3::from_array(v));
                }
                Some("vt") => {
                    // Only u and v are used; a trailing w is allowed by the spec.
                    let uv = parse_floats::<2>(&mut parts).unwrap_or_else(|m| fail(m));
                    uvs.push(Vec2::from_array(uv));
                }
                Some("vn") => {
                    let n = parse_floats::<3>(&mut parts).unwrap_or_else(|m| fail(m));
                    normals.push(Vec3::from_array(n));
                }
                Some("f") => {
                    let mut corners: Vec<u32> = Vec::new();
                    for corner in parts {
                        let mut fields = corner.split('/');

                        let p_str = fields.next().unwrap_or("");
                        let p = parse_index(p_str, positions.len())
                            .unwrap_or_else(|| fail(format!("bad position index '{corner}'")));

                        let t = match fields.next() {
                            None | Some("") => None,
                            Some(s) => Some(parse_index(s, uvs.len())
                                .unwrap_or_else(|| fail(format!("bad uv index '{corner}'")))),
                        };

                        let n = match fields.next() {
                            None | Some("") => None,
                            Some(s) => Some(parse_index(s, normals.len())
                                .unwrap_or_else(|| fail(format!("bad normal index '{corner}'")))),
                        };

                        if n.is_none() {
                            any_missing_normal = true;
                        }

                        let key = (p, t, n);
                        let idx = match remap.get(&key) {
                            Some(&i) => i,
                            None => {
                                let i = out_vertices.len() as u32;
                                out_vertices.push(positions[p as usize]);
                                out_uvs.push(t.map_or(Vec2::ZERO, |t| uvs[t as usize]));
                                out_normals.push(n.map_or(Vec3::ZERO, |n| normals[n as usize]));
                                remap.insert(key, i);
                                i
                            }
                        };
                        corners.push(idx);
                    }

                    if corners.len() < 3 {
                        fail("face needs at least 3 vertices".into());
                    }

                    // Fan triangulation: (0,1,2), (0,2,3), (0,3,4), ...
                    for k in 1..corners.len() - 1 {
                        triangles.push([corners[0], corners[k], corners[k + 1]]);
                    }
                }
                // o, g, s, usemtl, mtllib, l, p, and anything else: ignored.
                _ => {}
            }
        }

        assert!(!triangles.is_empty(), "OBJ file contains no faces");

        let has_uvs = !uvs.is_empty();

        let mut mesh = Mesh {
            transform: Transform::IDENTITY,
            vertices: out_vertices,
            triangles,
            normals: Vec::new(),
            uvs: if has_uvs { out_uvs } else { Vec::new() },
            material,
            bbox_min: Vec3::ZERO,
            bbox_max: Vec3::ZERO,
            nodes: Vec::new(),
            tri_order: Vec::new(),
        };

        if normals.is_empty() || any_missing_normal {
            // No usable authored normals: build smooth ones ourselves.
            mesh.compute_vertex_normals();
        } else {
            mesh.normals = out_normals.into_iter().map(|n| n.normalize_or_zero()).collect();
        }
        mesh.compute_bounds();

        mesh
    }
}

impl Shape for Mesh {
    fn transform(&self) -> &Transform { &self.transform }
    fn transform_mut(&mut self) -> &mut Transform { &mut self.transform }

    fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let local_ray = ray.transform(self.transform.inverse());
        let mut hit = self.hit_local(&local_ray, t_min, t_max)?;
        hit.point = ray.point(hit.t);
        hit.normal = self.transform.normal_to_world(hit.normal);
        Some(hit)
    }

    fn hit_local(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let mut closest: Option<(usize, f32, f32, f32)> = None;
        let mut closest_t = t_max;

        // Shared per-triangle step for both search orders below. Shrinking
        // closest_t as hits are found culls anything farther away.
        let test = |t: usize, closest: &mut Option<(usize, f32, f32, f32)>, closest_t: &mut f32| {
            if let Some((tt, beta, gamma)) = self.hit_triangle(t, ray, t_min, *closest_t) {
                *closest_t = tt;
                *closest = Some((t, tt, beta, gamma));
            }
        };

        if USE_BVH && !self.nodes.is_empty() {
            // Explicit stack. Median splits give depth ~log2(n / LEAF); even a
            // badly skewed tree on 160k triangles stays far below 64.
            let mut stack = [0u32; 64];
            let mut sp = 0usize;
            stack[sp] = 0;
            sp += 1;

            let dir = ray.direction();

            while sp > 0 {
                sp -= 1;
                let node_idx = stack[sp];
                let node = &self.nodes[node_idx as usize];

                // A box farther than the best hit so far can't improve on it.
                if slab_hit(node.bbox_min, node.bbox_max, ray, t_min, closest_t).is_none() {
                    continue;
                }

                if node.count > 0 {
                    let first = node.first_or_right as usize;
                    for &t in &self.tri_order[first..first + node.count as usize] {
                        test(t as usize, &mut closest, &mut closest_t);
                    }
                } else {
                    let left = node_idx + 1;
                    let right = node.first_or_right;
                    // Push the far child first so the near one is popped next;
                    // a close hit found early lets the slab test prune the far side.
                    let (near, far) = if dir[node.axis as usize] >= 0.0 { (left, right) } else { (right, left) };
                    stack[sp] = far;
                    sp += 1;
                    stack[sp] = near;
                    sp += 1;
                }
            }
        } else {
            // No acceleration: one bounding-box test, then every triangle.
            if slab_hit(self.bbox_min, self.bbox_max, ray, t_min, t_max).is_none() {
                return None;
            }
            for t in 0..self.triangles.len() {
                test(t, &mut closest, &mut closest_t);
            }
        }

        let (tri, t, beta, gamma) = closest?;
        let alpha = 1.0 - beta - gamma;

        let normal = if self.normals.is_empty() {
            // Flat shading: the face normal.
            self.face_normal(tri).normalize()
        } else {
            // Smooth shading: interpolate the vertex normals, then re-normalize.
            let [i, j, k] = self.triangles[tri];
            let n = alpha * self.normals[i as usize]
                + beta * self.normals[j as usize]
                + gamma * self.normals[k as usize];
            n.normalize_or(self.face_normal(tri).normalize())
        };

        Some(Hit::new(t, ray.point(t), normal, self.material))
    }

    fn material(&self) -> Material {
        self.material
    }

    fn as_any(&self) -> &dyn Any {
        self
    }

    fn as_any_mut(&mut self) -> &mut dyn Any {
        self
    }
}

// Scalar triple product, the 3x3 determinant with columns p, q, r.
#[inline]
fn det(p: Vec3, q: Vec3, r: Vec3) -> f32 {
    p.dot(q.cross(r))
}

// Slab test. Returns the ray parameter where it enters the box `[lo, hi]`
// (clamped to `t_min`) if the ray overlaps the box within `[t_min, t_max]`.
//
// Zero direction components give `inv = ±inf`; the products are then ±inf
// (or NaN for an origin exactly on a face), and `max`/`min` discard NaN, so
// the test still rejects correctly without special cases.
#[inline]
fn slab_hit(lo: Vec3, hi: Vec3, ray: &Ray, t_min: f32, t_max: f32) -> Option<f32> {
    let o = ray.origin();
    let d = ray.direction();
    let mut t0 = t_min;
    let mut t1 = t_max;
    for axis in 0..3 {
        let inv = 1.0 / d[axis];
        let mut near = (lo[axis] - o[axis]) * inv;
        let mut far = (hi[axis] - o[axis]) * inv;
        if inv < 0.0 {
            std::mem::swap(&mut near, &mut far);
        }
        t0 = t0.max(near);
        t1 = t1.min(far);
        if t1 < t0 {
            return None;
        }
    }
    Some(t0)
}

// Parses exactly `N` floats from the remaining fields of a line.
fn parse_floats<'a, const N: usize>(parts: &mut impl Iterator<Item = &'a str>) -> Result<[f32; N], String> {
    let mut out = [0.0f32; N];
    for (i, slot) in out.iter_mut().enumerate() {
        let s = parts.next().ok_or_else(|| format!("expected {N} values, found {i}"))?;
        *slot = s.parse::<f32>().map_err(|_| format!("'{s}' is not a number"))?;
    }
    Ok(out)
}

// Converts an OBJ index string to a 0-based index into a table of `len`
// entries. OBJ indices are 1-based; negative ones count back from the end
// of the table as it stands at that point in the file.
fn parse_index(s: &str, len: usize) -> Option<u32> {
    let i: i64 = s.parse().ok()?;
    let idx = if i > 0 {
        i - 1
    } else if i < 0 {
        len as i64 + i
    } else {
        return None;
    };
    if idx >= 0 && (idx as usize) < len {
        Some(idx as u32)
    } else {
        None
    }
}


// Tests ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#[cfg(test)]
mod tests {
    use super::*;
    use crate::camera::RGBA;

    fn mat() -> Material {
        Material::matte(RGBA::new(1.0, 1.0, 1.0, 1.0))
    }

    // Unit cube centered at the origin, 12 CCW triangles.
    const CUBE_OBJ: &str = "\
# cube
v -0.5 -0.5 -0.5
v  0.5 -0.5 -0.5
v  0.5  0.5 -0.5
v -0.5  0.5 -0.5
v -0.5 -0.5  0.5
v  0.5 -0.5  0.5
v  0.5  0.5  0.5
v -0.5  0.5  0.5
f 1 4 3 2
f 5 6 7 8
f 1 2 6 5
f 2 3 7 6
f 3 4 8 7
f 4 1 5 8
";

    // Deterministic xorshift so the tests need no extra crate.
    struct Rng(u64);
    impl Rng {
        fn next_f32(&mut self) -> f32 {
            self.0 ^= self.0 << 13;
            self.0 ^= self.0 >> 7;
            self.0 ^= self.0 << 17;
            (self.0 >> 40) as f32 / (1u64 << 24) as f32
        }
        fn unit(&mut self, lo: Vec3, hi: Vec3) -> Vec3 {
            lo + (hi - lo) * Vec3::new(self.next_f32(), self.next_f32(), self.next_f32())
        }
    }

    // Reference search: `hit_triangle` on every triangle, nearest wins.
    fn brute_force(m: &Mesh, ray: &Ray, t_min: f32, t_max: f32) -> Option<(usize, f32)> {
        let mut best: Option<(usize, f32)> = None;
        let mut best_t = t_max;
        for t in 0..m.triangles.len() {
            if let Some((tt, _, _)) = m.hit_triangle(t, ray, t_min, best_t) {
                best_t = tt;
                best = Some((t, tt));
            }
        }
        best
    }

    // Fires random rays at a mesh from a shell around it toward points inside
    // its box, and checks the BVH search in `hit_local` agrees with brute
    // force on whether and where the ray hits.
    fn bvh_matches_brute_force(m: &Mesh, rays: usize, seed: u64) {
        assert!(USE_BVH, "this test compares the BVH path against brute force");
        let (lo, hi) = m.bounds();
        let pad = (hi - lo).max_element();
        let mut rng = Rng(seed);
        let mut hits = 0;
        for _ in 0..rays {
            let origin = rng.unit(lo - Vec3::splat(pad), hi + Vec3::splat(pad));
            let target = rng.unit(lo, hi);
            let ray = Ray::new(origin, (target - origin).normalize());
            let brute = brute_force(m, &ray, 0.001, f32::MAX);
            let fast = m.hit_local(&ray, 0.001, f32::MAX).map(|h| h.t);
            match (brute, fast) {
                (None, None) => {}
                (Some((bt, bd)), Some(fd)) => {
                    hits += 1;
                    // Coplanar shared edges can legitimately tie on t; accept
                    // either triangle when the distances agree.
                    assert!((bd - fd).abs() <= 1e-5 * bd.max(1.0),
                            "t mismatch: brute {bd} (tri {bt}) vs bvh {fd}");
                }
                (b, f) => panic!("hit/miss mismatch: brute {b:?} vs bvh {f:?}"),
            }
        }
        assert!(hits > rays / 4, "too few hits to be a meaningful test: {hits}/{rays}");
    }

    #[test]
    fn hit_triangle_returns_t_and_barycentrics() {
        // Single triangle in the z = 0 plane.
        let m = Mesh::new(
            vec![Vec3::new(0.0, 0.0, 0.0), Vec3::new(2.0, 0.0, 0.0), Vec3::new(0.0, 2.0, 0.0)],
            vec![[0, 1, 2]],
            mat(),
        );
        // Straight down onto the point (0.5, 1.0): beta = 0.25, gamma = 0.5.
        let r = Ray::new(Vec3::new(0.5, 1.0, 3.0), -Vec3::Z);
        let (t, beta, gamma) = m.hit_triangle(0, &r, 0.0, f32::INFINITY).unwrap();
        assert!((t - 3.0).abs() < 1e-5);
        assert!((beta - 0.25).abs() < 1e-5);
        assert!((gamma - 0.5).abs() < 1e-5);
        // Outside the hypotenuse: beta + gamma > 1.
        let r = Ray::new(Vec3::new(1.5, 1.5, 3.0), -Vec3::Z);
        assert!(m.hit_triangle(0, &r, 0.0, f32::INFINITY).is_none());
        // Parallel to the plane.
        let r = Ray::new(Vec3::new(0.5, 0.5, 1.0), Vec3::X);
        assert!(m.hit_triangle(0, &r, 0.0, f32::INFINITY).is_none());
        // Outside [t_min, t_max].
        let r = Ray::new(Vec3::new(0.5, 1.0, 3.0), -Vec3::Z);
        assert!(m.hit_triangle(0, &r, 0.0, 2.0).is_none());
    }

    #[test]
    fn bvh_matches_brute_force_on_cube() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        assert!(!m.nodes.is_empty());
        bvh_matches_brute_force(&m, 2000, 0x9E3779B97F4A7C15);
    }

    #[test]
    fn bvh_matches_brute_force_on_vase() {
        let m = Mesh::from_obj("assets/vase.obj", mat());
        bvh_matches_brute_force(&m, 3000, 12345);
    }

    #[test]
    fn bvh_matches_brute_force_on_car() {
        let m = Mesh::from_obj("assets/SR17-reduced.obj", mat());
        bvh_matches_brute_force(&m, 300, 777);
    }

    #[test]
    fn bvh_leaves_cover_every_triangle_once() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        let mut seen = vec![0u32; m.triangles.len()];
        for node in &m.nodes {
            if node.count > 0 {
                let f = node.first_or_right as usize;
                for &t in &m.tri_order[f..f + node.count as usize] {
                    seen[t as usize] += 1;
                }
            }
        }
        assert!(seen.iter().all(|&c| c == 1), "{seen:?}");
    }

    #[test]
    fn parses_quads_into_triangles() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        assert_eq!(m.vertices.len(), 8);
        assert_eq!(m.triangles.len(), 12);
        assert_eq!(m.normals.len(), 8);
        assert!(m.uvs.is_empty());
    }

    #[test]
    fn cube_normals_point_outward() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        for t in 0..m.triangles.len() {
            let [a, b, c] = m.corners(t);
            let centroid = (a + b + c) / 3.0;
            assert!(m.face_normal(t).dot(centroid) > 0.0, "triangle {t} is wound inward");
        }
        // Corner vertex normals should point away from the center.
        for (v, n) in m.vertices.iter().zip(&m.normals) {
            assert!(n.dot(*v) > 0.0);
        }
    }

    #[test]
    fn ray_hits_cube_front_face() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        let ray = Ray::new(Vec3::new(0.0, 0.0, -5.0), Vec3::Z);
        let h = m.hit(&ray, 0.0, f32::INFINITY).expect("should hit");
        assert!((h.t - 4.5).abs() < 1e-5);
        assert!(h.point.abs_diff_eq(Vec3::new(0.0, 0.0, -0.5), 1e-5));
        // Smooth normal in the middle of a face still faces the camera.
        assert!(h.normal.z < 0.0);
    }

    #[test]
    fn flat_mesh_uses_face_normal() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        let flat = Mesh::new_flat(m.vertices.clone(), m.triangles.clone(), mat());
        let ray = Ray::new(Vec3::new(0.1, 0.2, -5.0), Vec3::Z);
        let h = flat.hit(&ray, 0.0, f32::INFINITY).unwrap();
        assert!(h.normal.abs_diff_eq(-Vec3::Z, 1e-5));
    }

    #[test]
    fn ray_misses_cube() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        let ray = Ray::new(Vec3::new(3.0, 0.0, -5.0), Vec3::Z);
        assert!(m.hit(&ray, 0.0, f32::INFINITY).is_none());
    }

    #[test]
    fn nearest_hit_wins_and_t_max_is_respected() {
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        let ray = Ray::new(Vec3::new(0.0, 0.0, -5.0), Vec3::Z);
        // Only the back face lies beyond t = 5.
        let h = m.hit(&ray, 5.0, f32::INFINITY).unwrap();
        assert!((h.t - 5.5).abs() < 1e-5);
        // Nothing within t < 4.
        assert!(m.hit(&ray, 0.0, 4.0).is_none());
    }

    #[test]
    fn honors_authored_normals_and_uvs() {
        let obj = "\
v 0 0 0
v 1 0 0
v 0 1 0
vt 0 0
vt 1 0
vt 0 1
vn 0 0 1
f 1/1/1 2/2/1 3/3/1
";
        let m = Mesh::parse_obj(obj, mat());
        assert_eq!(m.vertices.len(), 3);
        assert_eq!(m.uvs.len(), 3);
        assert_eq!(m.uvs[1], Vec2::new(1.0, 0.0));
        for n in &m.normals {
            assert!(n.abs_diff_eq(Vec3::Z, 1e-6));
        }
    }

    #[test]
    fn split_normals_duplicate_vertices() {
        // One position shared by two corners with different normals
        // must become two mesh vertices.
        let obj = "\
v 0 0 0
v 1 0 0
v 0 1 0
v 0 0 1
vn 0 0 1
vn 0 1 0
f 1//1 2//1 3//1
f 1//2 4//2 2//2
";
        let m = Mesh::parse_obj(obj, mat());
        assert_eq!(m.triangles.len(), 2);
        assert_eq!(m.vertices.len(), 6);
    }

    #[test]
    fn negative_indices() {
        let obj = "\
v 0 0 0
v 1 0 0
v 0 1 0
f -3 -2 -1
";
        let m = Mesh::parse_obj(obj, mat());
        assert_eq!(m.triangles, vec![[0, 1, 2]]);
    }

    #[test]
    fn position_only_faces_ignore_slashes() {
        let obj = "v 0 0 0\nv 1 0 0\nv 0 1 0\nf 1// 2// 3//\n";
        let m = Mesh::parse_obj(obj, mat());
        assert_eq!(m.triangles.len(), 1);
    }

    #[test]
    #[should_panic(expected = "OBJ line 3")]
    fn panics_on_bad_index_with_line_number() {
        let obj = "v 0 0 0\nv 1 0 0\nf 1 2 7\n";
        Mesh::parse_obj(obj, mat());
    }

    #[test]
    #[should_panic(expected = "OBJ line 1")]
    fn panics_on_bad_float() {
        Mesh::parse_obj("v 0 abc 0\n", mat());
    }

    #[test]
    #[should_panic(expected = "no faces")]
    fn panics_on_empty_file() {
        Mesh::parse_obj("# nothing here\n", mat());
    }

    #[test]
    fn transform_places_mesh_in_world() {
        // Unit cube scaled 10x and raised so it sits on y = 0.
        let m = Mesh::parse_obj(CUBE_OBJ, mat())
            .with_transform(Transform::from_position(Vec3::new(0.0, 5.0, 0.0)).with_uniform_scale(10.0));
        // Local bounds are untouched.
        let (lo, hi) = m.bounds();
        assert!(lo.abs_diff_eq(Vec3::splat(-0.5), 1e-5));
        assert!(hi.abs_diff_eq(Vec3::splat(0.5), 1e-5));
        // World-space ray from above hits the top face at y = 10.
        let ray = Ray::new(Vec3::new(1.0, 50.0, 1.0), -Vec3::Y);
        let h = m.hit(&ray, 0.0, f32::INFINITY).unwrap();
        assert!((h.t - 40.0).abs() < 1e-3);
        assert!(h.point.abs_diff_eq(Vec3::new(1.0, 10.0, 1.0), 1e-3));
        // A ray passing below the cube (which now spans y in [0, 10]) misses.
        let ray2 = Ray::new(Vec3::new(0.0, -1.0, -50.0), Vec3::Z);
        assert!(m.hit(&ray2, 0.0, f32::INFINITY).is_none());
    }

    #[test]
    fn rotated_flat_mesh_rotates_face_normal() {
        use std::f32::consts::FRAC_PI_4;
        let m = Mesh::parse_obj(CUBE_OBJ, mat());
        let mut flat = Mesh::new_flat(m.vertices.clone(), m.triangles.clone(), mat());
        flat.transform.set_rotation(glam::Quat::from_rotation_y(FRAC_PI_4));
        // Looking down -Z from in front, we now see an edge; the face hit
        // on the right side of center is the one whose normal was -Z,
        // now rotated 45 degrees toward +X... check it's unit and tilted.
        let ray = Ray::new(Vec3::new(0.3, 0.0, -5.0), Vec3::Z);
        let h = flat.hit(&ray, 0.0, f32::INFINITY).unwrap();
        assert!((h.normal.length() - 1.0).abs() < 1e-5);
        assert!((h.normal.z.abs() - std::f32::consts::FRAC_1_SQRT_2).abs() < 1e-4);
        assert!(h.normal.z < 0.0);
    }
}
