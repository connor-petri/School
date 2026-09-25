// camera.rs
// Author: Connor Petri
// CS-116A


use glam::{Vec3, Mat4};
use std::fmt;
use crate::ray::Ray;

#[derive(Debug, Clone, Copy, PartialEq, Default)]
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

#[derive(Debug, Clone)]
pub struct ViewPlane {
    width_px:          u32,
    height_px:         u32,
    fov_deg:        f32,
    buffer:         Vec<RGBA>,
}

impl ViewPlane {
    pub fn new(width_px: u32, height_px: u32, fov_deg: f32) -> Self {
        let len = (width_px / height_px) as usize;
        Self { 
            width_px, 
            height_px, 
            fov_deg, 
            buffer: vec![RGBA::default(); len],
        }
    }
    
    pub fn index(&self, x: u32, y: u32) -> usize {
        debug_assert!(x < self.width_px && y < self.height_px);
        (y * self.width_px + x) as usize
    }

    pub fn pixel(&self, x: u32, y: u32) -> RGBA {
        self.buffer[self.index(x, y)]
    }

    pub fn set_pixel(&mut self, x: u32, y: u32, color: RGBA) {
        self.buffer[self.index(x, y)] = color;
    }
}

#[derive(Debug, Clone)]
pub struct Camera {
    position:       Vec3,
    target:         Vec3,
    up:             Vec3,

    // Basis
    u:              Vec3,
    v:              Vec3,
    w:              Vec3,

    view_plane:     ViewPlane,
}

impl Camera {
    fn new(width_px: u32, height_px: u32, fov_deg: f32, position: Vec3, target: Vec3, up: Vec3) -> Self {
        let w: Vec3 = (position - target).normalize();
        let u: Vec3 = up.cross(w).normalize();
        let v: Vec3 = w.cross(u);

        Self {
            position,
            target,
            up,
            // Basis
            u,
            v,
            w,
            view_plane: ViewPlane::new(width_px, height_px, fov_deg),
        }
    }

    pub fn 
}