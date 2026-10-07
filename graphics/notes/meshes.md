# Meshes

---

- So far every object has been primitive
- Real scenes are not made of spheres: faces, cars, etc.
- The **triangle mesh** is the workhorse of 3D graphics

### What is a Triangle Mesh?
- A set of vertices joined by edges into triangles that tile a surface
- Some of the vertices are shared among multiple triangles
- **Vertex**: a point in space - the only geometry the mesh stores
- **Edge**: a straight segment between two vertices
- **Face**: a triangle - 3V + 3E
- A triangle is **flat**; the mesh is **piecewise flat** -- it only bends at the edges
- For a large closed mesh: $F \approx 2V$ and $ E \approx 3V$ - about twice as many triangles as vertices

### Smooth Shading
- The geometry is flat ,but the normals do not have to be
- Store a normal per-vertes: the smooth shape's normal at that point
- At a hit inside a triangle, interpolate the three vertex normals
- Shade with that normal -> lighting varies smoothly across surface
- Flat shading uses the face normal -> every faces shows
- Smooth shading cannot fix the silhouette - only more triangles can

### Resolution Trade Offs
- Coarse mesh: few triangles, fast, small, but loss of detail
- Fine mesh: smooth, detailed, but memory, bandwidth, and intersection cost more
- Triangle density should follow curvature: flat regions need few, tight curves need many
- Level of detail (LOD): keep several resolutions, pick by distance to the camera
- Typical counts: game character 10k - 100k, film character millions, 3D scan hundreds of thousands
- Tools: subdivision adds triangles smothly; decimation removes them

### Mesh Topology
- how the triangles connect independent of where the vertices are
- Manifold edge
- Boundary edge
- bow tie
- non-manifold edge (bad)

#### Manifold, Closed, Boundary
- **Manifold**: locally looks like a flat sheet
- **Closed**: every edge has exactly two trangles - no holes

#### Orientation
- Each triangle lists its vertices in an order: counter clockwise as seen from the outside
- Orientation must be consistant or surface normals are weird

---

## Storage

### Separate Triangles
- A list of triangles, each holding it's vertex positions
- Simple, but shared vertices are copied into every triangle that uses them

#### Separate Triangles: The Cost
- Each triangle is 36 bytes
- An interior vertex is stores about six times
- Neighbours are not recorded
- STL files are exactly this "triangle soup"
- fine for drawing once, bad for editing or any adjacency question

### Indexed Mesh
- Store every vertex once and make each triangle three indices into the vertex table
- O(1) retrieval

### Strips and Fans
- Indexed meshes still spend 12 bytes per triangle on indices: can we do better?
- **Triangle Strip**: a swquence of vertices where every three consecutive ones form a triangle
    - One new index per triangle instead of three
- **Triangle fan**: all triangles share the first vertes
- Orientation alternates along a strip
- Hardware pipelines use them; a ray tracer just wants the triangle list

### Mesh Struct
```rust
use glam::{Vec2, Vec3};

#[derive(Default)]
struct Mesh {
    vertices:   Vec<Vec3>,
    triangles:  Vec<u32; 3>,
    normals:    Vec<Vec3>,
    uvs:        Vec<Vec2>,
}

impl Mesh {
    fn corners(&self, t: usize) -> [Vec3; 3] {
        let [i, j, k] = self.triangles[t];
        [self.vertices[i as usize], self.vertices[j as usize], self.vertices[k as usize]]
    }
}
```