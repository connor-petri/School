// scene.rs
// Author: Connor Petri
// CS-116A

use crate::ray::Ray;
use crate::shape::{Hit, Shape};
use crate::light::Light;
use crate::camera::RGBA;

pub struct Scene {
    pub objects: Vec<Box<dyn Shape + Sync>>,
    pub lights: Vec<Light>,
    pub ambient: RGBA,
}

impl Scene {
    pub fn new(ambient: RGBA) -> Self {
        Self { objects: Vec::new(), lights: Vec::new(), ambient }
    }

    pub fn add(&mut self, shape: impl Shape + Sync + 'static) {
        self.objects.push(Box::new(shape));
    }

    pub fn add_light(&mut self, light: Light) {
        self.lights.push(light);
    }

    pub fn len(&self) -> usize {
        self.objects.len()
    }

    /// Returns a reference to the object at `index`, or `None` if out of range.
    pub fn get_object(&self, index: u32) -> Option<&(dyn Shape + Sync)> {
        self.objects.get(index as usize).map(|obj| obj.as_ref())
    }

    /// Returns a mutable reference to the object at `index`, or `None` if out of range.
    pub fn get_object_mut(&mut self, index: u32) -> Option<&mut (dyn Shape + Sync + 'static)> {
        self.objects.get_mut(index as usize).map(|obj| obj.as_mut())
    }

    pub fn hit(&self, ray: &Ray, t_min: f32, t_max: f32) -> Option<Hit> {
        let mut closest: Option<Hit> = None;
        let mut closest_t: f32 = t_max;

        for obj in &self.objects {
            if let Some(h) = obj.hit(ray, t_min, closest_t) {
                closest_t = h.t;
                closest = Some(h);
            }
        }
        return closest;
    }
}