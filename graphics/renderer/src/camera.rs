// camera.rs
// Author: Connor Petri
// CS-116A
use glam::Vec3;
use crate::ray::Ray;
use crate::scene::Scene;
use crate::shape::Hit;
use image::{ImageBuffer, Rgba};

#[derive(Debug, Clone, Copy, PartialEq, Default)]
pub struct RGBA {
    pub r: f32,
    pub g: f32,
    pub b: f32,
    pub a: f32
}

impl RGBA {
    pub fn new(r: f32, g: f32, b: f32, a: f32) -> Self {
        Self { r, g, b, a }
    }

    pub fn to_bytes(&self) -> [u8; 4] {
        let q = |c: f32| (c.clamp(0.0, 1.0) * 255.0).round() as u8;
        [q(self.r), q(self.g), q(self.b), q(self.a)]
    }
}

#[derive(Debug, Clone)]
pub struct ViewPlane {
    width_px:          u32,
    height_px:         u32,
    fov_deg:           f32,
    buffer:            Vec<RGBA>,
}

impl ViewPlane {
    pub fn new(width_px: u32, height_px: u32, fov_deg: f32) -> Self {
        let len = (width_px * height_px) as usize;
        Self { 
            width_px, 
            height_px, 
            fov_deg, 
            buffer: vec![RGBA::default(); len],
        }
    }

    pub fn fov_deg(&self) -> f32 {
        self.fov_deg
    }

    pub fn fov_rad(&self) -> f32 {
        self.fov_deg.to_radians()
    }

    pub fn aspect_ratio(&self) -> f32 {
        self.width_px as f32 / self.height_px as f32
    }
    
    pub fn index(&self, x: u32, y: u32) -> usize {
        debug_assert!(x < self.width_px && y < self.height_px);
        (y * self.width_px + x) as usize
    }

    pub fn pixel(&self, x: u32, y: u32) -> RGBA {
        self.buffer[self.index(x, y)]
    }

    pub fn set_pixel(&mut self, x: u32, y: u32, color: RGBA) {
        let i = self.index(x, y);
        self.buffer[i] = color;
    }

    pub fn save_png(&self, path: &str) -> image::ImageResult<()> {
        let bytes: Vec<u8> = self.buffer.iter().flat_map(|c| c.to_bytes()).collect();
        let img: ImageBuffer<Rgba<u8>, Vec<u8>> = 
        ImageBuffer::from_raw(self.width_px, self.height_px, bytes).expect("buffer length matches width * height * 4");
        img.save(path)
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
    pub fn new(width_px: u32, height_px: u32, fov_deg: f32, position: Vec3, target: Vec3, up: Vec3) -> Self {
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

    pub fn ray_for_pixel(&self, x: u32, y: u32) -> Ray {
        let vp = &self.view_plane;

        let plane_h = 2.0 * (vp.fov_rad() / 2.0).tan();
        let plane_w = plane_h * vp.aspect_ratio();

        // Pixel Center -> normalized plane coordinates (s, t)
        let s = (x as f32 + 0.5) / vp.width_px as f32;
        let t = 1.0 - (y as f32 + 0.5) / vp.height_px as f32;

        let center = self.position - self.w;
        let lower_left = center - self.u * (plane_w / 2.0) - self.v * (plane_h / 2.0);

        // Worldspace position of ray target
        let point = lower_left + self.u * (s * plane_w) + self.v * (t * plane_h);
        Ray::new(self.position, (point - self.position).normalize())
    }

    pub fn shade(&self, hit: &Hit) -> RGBA {
        let c = (hit.normal + Vec3::ONE) * 0.5;
        RGBA::new(c.x, c.y, c.z, 1.0)
    }

    pub fn render(&mut self, scene: &Scene) {
        for y in 0..self.view_plane.height_px {
            for x in 0..self.view_plane.width_px {
                let ray = self.ray_for_pixel(x, y);
                let color = match scene.hit(&ray, 0.001, f32::MAX) {
                    Some(hit) => self.shade(&hit),
                    None => RGBA::new(0.5, 0.5, 0.5, 1.0),
                };
                self.view_plane.set_pixel(x, y, color);
            }
        }
    }

    pub fn save_png(&self, path: &str) {
        self.view_plane.save_png(path);
    }
}