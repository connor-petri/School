// shape.rs
// Author: Connor Petri
// CS-116A

use std::any::Any;
use glam::{Quat, Vec3};
use crate::{ray::Ray, material::Material, transform::Transform};

pub struct Hit {
    pub t:          f32,
    pub point:      Vec3,
    pub normal:     Vec3,
    pub material:   Material,
}

impl Hit {
    pub fn new(t: f32, point: Vec3, normal: Vec3, material: Material) -> Self {
        Self { t, point, normal, material }
    }
}

/// Anything a ray can hit.
///
/// Every shape is modelled once in its own local coordinates and owns a
/// `Transform` that places it in the world. Implementors write `hit_local`
/// against local coordinates; the provided `hit` moves the ray into local
/// space, runs `hit_local`, and carries the result back out to world space.
pub trait Shape {
    fn transform(&self) -> &Transform;
    fn transform_mut(&mut self) -> &mut Transform;

    /// Ray / shape test in the shape's local coordinates.
    fn hit_local(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit>;

    fn material(&self) -> Material;

    /// Allows downcasting a `dyn Shape` back to its concrete type.
    fn as_any(&self) -> &dyn Any;
    fn as_any_mut(&mut self) -> &mut dyn Any;

    /// Ray / shape test in world coordinates.
    ///
    /// The ray direction is not re-normalized when moved into local space,
    /// so `t` is the same number in both spaces and can be compared across
    /// objects directly. The world hit point is therefore just `ray.point(t)`.
    /// Normals go through the inverse transpose so they stay perpendicular
    /// under non-uniform scale.
    fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let xf = self.transform();
        let local_ray = ray.transform(xf.inverse());
        let mut hit = self.hit_local(&local_ray, t_min, t_max)?;
        hit.point = ray.point(hit.t);
        hit.normal = xf.normal_to_world(hit.normal);
        Some(hit)
    }
}


// Plane ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/// The local plane is `y = 0` with normal `+Y`. Its transform orients and
/// places it; scale has no visible effect on an infinite plane.
pub struct Plane {
    pub transform:  Transform,
    pub material:   Material,
}

impl Plane {
    /// A plane through `point` facing `normal`.
    pub fn new(point: Vec3, normal: Vec3, material: Material) -> Self {
        let rotation = Quat::from_rotation_arc(Vec3::Y, normal.normalize());
        Self {
            transform: Transform::from_position_rotation(point, rotation),
            material,
        }
    }
}

impl Shape for Plane {
    fn transform(&self) -> &Transform { &self.transform }
    fn transform_mut(&mut self) -> &mut Transform { &mut self.transform }

    fn hit_local(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let denom = ray.direction().y;
        if denom.abs() < 1e-6 {
            return None;
        }

        let t = -ray.origin().y / denom;
        if t < t_min || t > t_max {
            return None;
        }

        Some(Hit::new(t, ray.point(t), Vec3::Y, self.material))
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


// Sphere ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/// The local sphere is centered at the origin with the given radius. Its
/// position lives in the transform; a non-uniform scale makes an ellipsoid.
#[derive(Copy, Clone)]
pub struct Sphere {
    pub transform:  Transform,
    pub radius:     f32,
    pub material:   Material,
}

impl Sphere {
    pub fn new(center: Vec3, radius: f32, material: Material) -> Self {
        Self { transform: Transform::from_position(center), radius, material }
    }

    /// World-space center, for convenience.
    pub fn center(&self) -> Vec3 {
        self.transform.position()
    }
}

impl Shape for Sphere {
    fn transform(&self) -> &Transform { &self.transform }
    fn transform_mut(&mut self) -> &mut Transform { &mut self.transform }

    fn hit_local(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let o = ray.origin();
        let d = ray.direction();

        let a = d.dot(d);
        let b = 2.0 * d.dot(o);
        let c = o.dot(o) - self.radius * self.radius;

        let disc = b*b - 4.0*a*c;
        if disc < 0.0 {
            return None;
        }

        let sqrt_disc = disc.sqrt();

        // Try near root first
        let mut t = (-b - sqrt_disc) / (2.0*a);
        if t < t_min || t > t_max {
            t = (-b + sqrt_disc) / (2.0*a);
            if t < t_min || t > t_max {
                return None;
            }
        }
        let p = ray.point(t);
        Some(Hit::new(t, p, p / self.radius, self.material))
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


#[cfg(test)]
mod tests {
    use super::*;
    use crate::camera::RGBA;

    fn mat() -> Material {
        Material::matte(RGBA::new(1.0, 1.0, 1.0, 1.0))
    }

    #[test]
    fn sphere_hit_in_world_space() {
        let s = Sphere::new(Vec3::new(0.0, 0.0, 10.0), 2.0, mat());
        let r = Ray::new(Vec3::ZERO, Vec3::Z);
        let h = s.hit(&r, 0.0, f32::INFINITY).unwrap();
        assert!((h.t - 8.0).abs() < 1e-5);
        assert!(h.point.abs_diff_eq(Vec3::new(0.0, 0.0, 8.0), 1e-5));
        assert!(h.normal.abs_diff_eq(-Vec3::Z, 1e-5));
    }

    #[test]
    fn sphere_moves_with_its_transform() {
        let mut s = Sphere::new(Vec3::ZERO, 1.0, mat());
        s.transform.translate(Vec3::new(0.0, 5.0, 0.0));
        let r = Ray::new(Vec3::new(0.0, 5.0, -10.0), Vec3::Z);
        assert!(s.hit(&r, 0.0, f32::INFINITY).is_some());
        let miss = Ray::new(Vec3::new(0.0, 0.0, -10.0), Vec3::Z);
        assert!(s.hit(&miss, 0.0, f32::INFINITY).is_none());
    }

    #[test]
    fn scaled_sphere_is_an_ellipsoid_with_correct_normal() {
        // Unit sphere stretched 3x along x. A ray along -x should hit at x = 3.
        let mut s = Sphere::new(Vec3::ZERO, 1.0, mat());
        s.transform.set_scale(Vec3::new(3.0, 1.0, 1.0));
        let r = Ray::new(Vec3::new(10.0, 0.0, 0.0), -Vec3::X);
        let h = s.hit(&r, 0.0, f32::INFINITY).unwrap();
        assert!((h.t - 7.0).abs() < 1e-4);
        assert!(h.point.abs_diff_eq(Vec3::new(3.0, 0.0, 0.0), 1e-4));
        assert!(h.normal.abs_diff_eq(Vec3::X, 1e-4));

        // Off-axis: the normal must be perpendicular to the ellipsoid surface,
        // which for x^2/9 + y^2 = 1 at (0, 1, 0) is +Y, and at an angle is
        // NOT just the scaled position vector.
        let r2 = Ray::new(Vec3::new(0.0, 10.0, 0.0), -Vec3::Y);
        let h2 = s.hit(&r2, 0.0, f32::INFINITY).unwrap();
        assert!(h2.normal.abs_diff_eq(Vec3::Y, 1e-4));
    }

    #[test]
    fn plane_through_point_with_normal() {
        let p = Plane::new(Vec3::new(0.0, 3.0, 0.0), Vec3::Y, mat());
        let r = Ray::new(Vec3::new(1.0, 10.0, 1.0), -Vec3::Y);
        let h = p.hit(&r, 0.0, f32::INFINITY).unwrap();
        assert!((h.t - 7.0).abs() < 1e-5);
        assert!(h.point.abs_diff_eq(Vec3::new(1.0, 3.0, 1.0), 1e-5));
        assert!(h.normal.abs_diff_eq(Vec3::Y, 1e-5));
    }

    #[test]
    fn plane_with_arbitrary_normal() {
        // Wall at z = 5 facing -Z.
        let p = Plane::new(Vec3::new(0.0, 0.0, 5.0), -Vec3::Z, mat());
        let r = Ray::new(Vec3::ZERO, Vec3::Z);
        let h = p.hit(&r, 0.0, f32::INFINITY).unwrap();
        assert!((h.t - 5.0).abs() < 1e-5);
        assert!(h.normal.abs_diff_eq(-Vec3::Z, 1e-5));
    }

    #[test]
    fn plane_facing_down_is_not_degenerate() {
        // from_rotation_arc(Y, -Y) is the 180-degree case.
        let p = Plane::new(Vec3::new(0.0, 2.0, 0.0), -Vec3::Y, mat());
        let r = Ray::new(Vec3::ZERO, Vec3::Y);
        let h = p.hit(&r, 0.0, f32::INFINITY).unwrap();
        assert!((h.t - 2.0).abs() < 1e-5);
        assert!(h.normal.abs_diff_eq(-Vec3::Y, 1e-5));
    }

    #[test]
    fn t_is_comparable_across_differently_scaled_objects() {
        // Two unit spheres, one scaled 5x. Nearest-hit logic in Scene
        // relies on t meaning world distance for both.
        let near = Sphere::new(Vec3::new(0.0, 0.0, 4.0), 1.0, mat());
        let mut far = Sphere::new(Vec3::new(0.0, 0.0, 20.0), 1.0, mat());
        far.transform.set_scale(Vec3::splat(5.0));
        let r = Ray::new(Vec3::ZERO, Vec3::Z);
        let hn = near.hit(&r, 0.0, f32::INFINITY).unwrap();
        let hf = far.hit(&r, 0.0, f32::INFINITY).unwrap();
        assert!((hn.t - 3.0).abs() < 1e-5);
        assert!((hf.t - 15.0).abs() < 1e-4);
        assert!(hn.t < hf.t);
    }
}
