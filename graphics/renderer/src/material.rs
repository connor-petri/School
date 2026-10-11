// light.rs
// Author: Connor Petri
// CS-116A

use glam::Vec3;
use crate::camera::RGBA;

#[derive(Debug, Clone, Copy)]
pub struct Material {
    pub k_d:    RGBA,
    pub k_s:    RGBA,
    pub k_m:    RGBA,
    pub k_t:    RGBA,
    pub p:      f32,
    pub ior:    f32,
}

impl Material {
    fn new(k_d: RGBA, k_s: RGBA, p: f32) -> Self {
        Self {
            k_d,
            k_s,
            k_m: RGBA::new(0.0, 0.0, 0.0, 1.0),
            k_t: RGBA::new(0.0, 0.0, 0.0, 1.0),
            p,
            ior: 1.0,
        }
    }

    pub fn matte(k_d: RGBA) -> Self {
        Self::new(k_d, RGBA::new(0.0, 0.0, 0.0, 1.0), 1.0)
    }

    pub fn plastic(k_d: RGBA) -> Self {
        Self::new(k_d, RGBA::new(0.5, 0.5, 0.5, 1.0), 32.0)
    }

    pub fn polished(k_d: RGBA) -> Self {
        Self::new(k_d, RGBA::new(0.9, 0.9, 0.9, 1.0), 256.0)
    }
}