// ray.rs
// Author: Connor Petri
// CS-116A

use glam::{Mat4, Vec3};
use std::fmt;
use std::f32::consts::FRAC_PI_2;

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Ray {
    origin:     Vec3,
    direction:  Vec3
}

impl Ray {
    pub fn new (origin: Vec3, direction: Vec3) -> Self {
        Self { origin, direction }
    }

    pub fn origin(&self) -> Vec3 { self.origin }

    pub fn direction(&self) -> Vec3 { self.direction }

    pub fn point(&self, t: f32) -> Vec3 { self.origin + self.direction * t }

    /// Applies `matrix` to the ray. The origin is a point (translation
    /// applies); the direction is a vector (translation does not). The
    /// direction is deliberately *not* re-normalized so that `t` means the
    /// same thing before and after the transform.
    pub fn transform(&self, matrix: &Mat4) -> Self {
        Self {
            origin:     matrix.transform_point3(self.origin),
            direction:  matrix.transform_vector3(self.direction)
        }
    }

    pub fn reflect(&self, hit_point: Vec3, normal: Vec3) -> Self {
        Self { 
            origin: hit_point, 
            direction: self.direction - 2.0 * (self.direction.dot(normal)) * normal,
        }
    }
}

impl fmt::Display for Ray {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(formatter, "Ray[origin: {}, direction: {}]", self.origin, self.direction)
    }
}


#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn accessors() {
        let r = Ray::new(Vec3::new(1.0, 1.0, 1.0), Vec3::new(2.0, 1.0, 1.0));
        assert_eq!(r.origin(), Vec3::new(1.0, 1.0, 1.0));
        assert_eq!(r.direction(), Vec3::new(2.0, 1.0, 1.0));
    }

    #[test]
    fn point() {
        let r = Ray::new(Vec3::new(1.0, 2.0, 3.0), Vec3::new(0.0, 0.0, -1.0));
        assert_eq!(r.point(0.0), r.origin());
        assert_eq!(r.point(2.5), Vec3::new(1.0, 2.0, 0.5));
    }

    #[test]
    fn transform_rotation() {
        let r = Ray::new(Vec3::new(1.0, 0.0, 0.0), Vec3::new(1.0, 0.0, 0.0));
        let t = r.transform(&Mat4::from_rotation_z(FRAC_PI_2));
        assert!(t.origin().abs_diff_eq(Vec3::new(0.0, 1.0, 0.0), 1e-5));
        assert!(t.direction().abs_diff_eq(Vec3::new(0.0, 1.0, 0.0), 1e-5));
    }

    #[test]
    fn transform_translation_moves_origin_only() {
        let r = Ray::new(Vec3::ZERO, Vec3::X);
        let t = r.transform(&Mat4::from_translation(Vec3::new(0.0, 5.0, 0.0)));
        assert_eq!(t.origin(), Vec3::new(0.0, 5.0, 0.0));
        assert_eq!(t.direction(), Vec3::X);
    }

    #[test]
    fn transform_preserves_t() {
        // After transforming by M, point(t) on the new ray equals M * point(t) on the old one.
        let r = Ray::new(Vec3::new(1.0, 2.0, 3.0), Vec3::new(0.0, 0.0, -1.0));
        let m = Mat4::from_scale_rotation_translation(
            Vec3::new(2.0, 3.0, 4.0),
            glam::Quat::from_rotation_y(0.4),
            Vec3::new(1.0, 1.0, 1.0),
        );
        let t = r.transform(&m);
        assert!(t.point(2.5).abs_diff_eq(m.transform_point3(r.point(2.5)), 1e-4));
    }

    #[test]
    fn display() {
        let r = Ray::new(Vec3::new(1.0, 2.0, 3.0), Vec3::new(0.0, 0.0, -1.0));
        assert_eq!(r.to_string(), "Ray[origin: [1, 2, 3], direction: [0, 0, -1]]");
    }
}
