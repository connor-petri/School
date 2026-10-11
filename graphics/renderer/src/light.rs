use crate::camera::RGBA;
use glam::Vec3;

#[derive(Debug, Clone, Copy)]
pub struct Light {
    pub position:   Vec3,
    pub intensity:  RGBA,
}

impl Light {
    pub fn new(position: Vec3, intensity: RGBA) -> Self {
        Self { position, intensity }
    }
}