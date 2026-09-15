// camera.rs
// Author: Connor Petri
// CS-116A

use glam::{Vec3, Mat4};
use std::fmt;
use ray::Ray;

pub struct RGBA {
    r: u8,
    g: u8,
    b: u8,
    a: u8
}

impl RGBA {
    fn new(r: u8, g: u8, b: u8, a: u8) -> Self {
        Self { r, g, b, a }
    }
}

pub struct Basis {
    forward: Vec3,
    right:   Vec3,
    up:      Vec3, 
}

pub struct ViewPlane {
    fov_deg:        f32,
    aspect_ratio:   f32,
}

impl ViewPlane {
    pub fn new(fov_deg: f32, aspect_ratio: f32) -> Self {
        Self { fov_deg, aspect_ratio }
    }

    pub fn from_dimensions(width_px: u32, height_px: u32, fov_deg: f32) -> Self {
        Self::new(fov_deg, width_px as f32, height_px as f32)
    }

    
}

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Camera {
    position:       Vec3,
    target:         Vec3,
    up:             Vec3,
    view_plane:     ViewPlane,
}

impl Camera {
    fn new(position: Vec3, target: Vec3, up: Vec3, fov_deg: f32, view_plane: ViewPlane) -> Self {
        Self {
            position,
            target,
            up,
            view_plane
        }
    }

    
}
