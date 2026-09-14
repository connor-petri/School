// camera.rs
// Author: Connor Petri
// CS-116A

use glam::{Vec3, Mat4};
use std::fmt;

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Camera {
    position:       Vec3,
    target:         Vec3,
    fov_deg:        f32,
    aspect_ratio:   f32
}

