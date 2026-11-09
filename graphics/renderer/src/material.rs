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
    fn new(k_d: RGBA, k_s: RGBA, k_m: RGBA, p: f32) -> Self {
        Self {
            k_d,
            k_s,
            k_m,
            k_t: RGBA::new(0.0, 0.0, 0.0, 1.0),
            p,
            ior: 1.0,
        }
    }

    pub fn matte(k_d: RGBA) -> Self {
        Self::new(k_d, RGBA::new(0.0, 0.0, 0.0, 1.0), RGBA::new(0.0, 0.0, 0.0, 1.0), 1.0)
    }

    pub fn plastic(k_d: RGBA) -> Self {
        Self::new(k_d, RGBA::new(0.5, 0.5, 0.5, 1.0), RGBA::new(0.05, 0.05, 0.05, 1.0), 32.0)
    }

    pub fn polished(k_d: RGBA) -> Self {
        Self::new(k_d, RGBA::new(0.9, 0.9, 0.9, 1.0), RGBA::new(0.1, 0.1, 0.1, 1.0), 256.0)
    }

    pub fn mirror(k_d: RGBA, k_m: RGBA) -> Self {
        Self::new(k_d, RGBA::new(0.9, 0.9, 0.9, 1.0), k_m, 256.0)
    }

    pub fn carbon_fiber() -> Self {
        Self::new(RGBA::new(0.03, 0.03, 0.035, 1.0),
                  RGBA::new(0.85, 0.85, 0.85, 1.0),
                  RGBA::new(0.2, 0.2, 0.22, 1.0),
                  96.0)
    }

    pub fn ceramic() -> Self {
        Self::new(RGBA::new(0.85, 0.82, 0.75, 1.0),
                  RGBA::new(0.8, 0.8, 0.8, 1.0),
                  RGBA::new(0.08, 0.08, 0.08, 1.0),
                  128.0)
    }
}