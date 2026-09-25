// scene.rs
// Author: Connor Petri
// CS-116A

use crate::ray::Ray;
use crate::shape::{Hit, Shape};

pub struct Scene {
    pub objects: Vec<Box<dyn Shape>>,
}

impl Scene {
    pub fn new() -> Self {
        Self { objects: Vec::new() }
    }

    pub fn add(&mut self, shape: impl Shape + 'static) {
        self.objects.push(Box::new(shape));
    }

    pub fn len(&self) -> usize {
        self.objects.len()
    }

    pub fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let mut closest: Option<Hit> = None;
        let mut closest_t: f32 = t_max;

        for obj in &self.objects {
            if let Some(h) = obj.hit(ray, t_min, t_max) {
                closest_t = h.t;
                closest = Some(h);
            }
        }
        return closest;
    }
}