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

    pub fn transform(&self, matrix: &Mat4) -> Self {
        // Reconstruct and return self
        Self {
            origin:     matrix.transform_vector3(self.origin),
            direction:  matrix.transform_vector3(self.direction)
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
    fn display() {
        let r = Ray::new(Vec3::new(1.0, 2.0, 3.0), Vec3::new(0.0, 0.0, -1.0));
        assert_eq!(r.to_string(), "Ray[origin: [1, 2, 3], direction: [0, 0, -1]]");
    }
}
