// shape.rs
// Author: Connor Petri
// CS-116A

use std::any::Any;
use glam::Vec3;
use crate::{ray::Ray, material::Material};

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

pub trait Shape {
    fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit>;
    fn material(&self) -> Material;
    /// Allows downcasting a `dyn Shape` back to its concrete type.
    fn as_any(&self) -> &dyn Any;
    fn as_any_mut(&mut self) -> &mut dyn Any;
}


// Plane ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
pub struct Plane {
    pub point:      Vec3,
    pub normal:     Vec3,
    pub material:   Material,
}

impl Plane {
    pub fn new(point: Vec3, normal: Vec3, material: Material) -> Self {
        Self { point, normal: normal.normalize(), material }
    }
}

impl Shape for Plane {
    fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let denom = ray.direction().dot(self.normal);
        if denom.abs() < 1e-6 {
            return None;
        }

        let t: f32 = ((self.point - ray.origin()).dot(self.normal)) / denom;

        if t < t_min || t > t_max {
            return None;
        }

        Some(Hit::new(t, ray.point(t), self.normal, self.material))
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

#[derive(Copy, Clone)]
pub struct Sphere {
    pub center:     Vec3,
    pub radius:     f32,
    pub material:   Material,
}

impl Sphere {
    pub fn new(center: Vec3, radius: f32, material: Material) -> Self {
        Self { center, radius, material }
    }
}

impl Shape for Sphere {
    fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let a = ray.direction().dot(ray.direction());
        let b = 2.0 * ray.direction().dot(ray.origin() - self.center);
        let c = (ray.origin() - self.center).dot(ray.origin() - self.center) - self.radius * self.radius;

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
        Some(Hit::new(t, p, (p - self.center) / self.radius, self.material))
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


// Triangle ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
pub struct Triangle {
    pub a:          Vec3,
    pub b:          Vec3,
    pub c:          Vec3,
    pub normal:     Vec3,
    pub material:   Material,
}

impl Triangle {
    pub fn new(a: Vec3, b: Vec3, c: Vec3, material: Material) -> Self {
        let normal = (b - a).cross(c - a).normalize();
        Self { a, b, c, normal, material }
    }
}

impl Shape for Triangle {
    fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let ab = self.a - self.b;
        let ac = self.a - self.c;
        let ao = self.a - ray.origin();

        let det_a = det(ab, ac, ray.direction());
        if det_a.abs() < 1e-8 {
            return None; // Ray is parallel
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

        Some(Hit::new(t, ray.point(t), self.normal, self.material))
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

fn det(p: Vec3, q: Vec3, r: Vec3) -> f32 {
    p.dot(q.cross(r))
}
