// texture.rs
// Author: Connor Petri
// CS-116A

use std::f32::consts::PI;
use std::sync::Arc;

use glam::{Vec2, Vec3};

use crate::camera::RGBA;

// Texture: T(u, v) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// T(u, v): a surface property as a function of texture coordinates.
#[derive(Clone)]
pub enum Texture {
    // T(u, v) = c everywhere. An untextured material.
    Solid(RGBA),
    // Image texture: T is a 2D array of texels. Shared, since Material is cloned.
    Image(Arc<Image>),
    // Procedural texture: T is a function of (u, v).
    Procedural(fn(Vec2) -> RGBA),
}

impl Texture {
    pub fn image(path: &str) -> Self {
        Texture::Image(Arc::new(Image::load(path)))
    }

    // Evaluates T(u, v).
    pub fn sample(&self, uv: Vec2) -> RGBA {
        match self {
            Texture::Solid(c) => *c,
            Texture::Image(img) => img.sample(uv),
            Texture::Procedural(f) => f(uv),
        }
    }
}

// A plain color is a texture, so `Material::matte(red)` keeps working.
impl From<RGBA> for Texture {
    fn from(c: RGBA) -> Self {
        Texture::Solid(c)
    }
}

pub fn checker(uv: Vec2) -> RGBA {
    let (i, j) = ((uv.x * 8.0).floor() as i64, (uv.y * 8.0).floor() as i64);
    if (i + j).rem_euclid(2) == 0 {
        RGBA::new(0.9, 0.9, 0.9, 1.0)
    } else {
        RGBA::new(0.1, 0.1, 0.1, 1.0)
    }
}

// Image texture ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// A 2D array of texels. Row 0 is the top of the image (v = 0).
pub struct Image {
    width:  u32,
    height: u32,
    texels: Vec<RGBA>,
}

impl Image {
    // Loads a PNG or JPEG. Texels are kept as stored (sRGB scaled to [0, 1]).
    pub fn load(path: &str) -> Self {
        let img = image::open(path)
            .unwrap_or_else(|e| panic!("could not load texture '{path}': {e}"))
            .to_rgb8();
        let (width, height) = img.dimensions();
        let texels = img.pixels()
            .map(|p| RGBA::new(p[0] as f32 / 255.0, p[1] as f32 / 255.0, p[2] as f32 / 255.0, 1.0))
            .collect();
        Self { width, height, texels }
    }

    pub fn from_texels(width: u32, height: u32, texels: Vec<RGBA>) -> Self {
        assert_eq!(texels.len(), (width * height) as usize);
        Self { width, height, texels }
    }

    pub fn width(&self) -> u32 { self.width }
    pub fn height(&self) -> u32 { self.height }

    // Texel at column x, row y. x wraps, y clamps (poles).
    pub fn texel(&self, x: i64, y: i64) -> RGBA {
        let x = x.rem_euclid(self.width as i64) as u32;
        let y = y.clamp(0, self.height as i64 - 1) as u32;
        self.texels[(y * self.width + x) as usize]
    }

    // T(u, v). Texel (x, y) is centered at ((x + 0.5)/w, (y + 0.5)/h).
    pub fn sample(&self, uv: Vec2) -> RGBA {
        let fx = uv.x * self.width as f32 - 0.5;
        let fy = uv.y * self.height as f32 - 0.5;
        let (x0, y0) = (fx.floor(), fy.floor());
        let (sx, sy) = (fx - x0, fy - y0);
        let (x0, y0) = (x0 as i64, y0 as i64);

        let top = self.texel(x0, y0) * (1.0 - sx) + self.texel(x0 + 1, y0) * sx;
        let bot = self.texel(x0, y0 + 1) * (1.0 - sx) + self.texel(x0 + 1, y0 + 1) * sx;
        top * (1.0 - sy) + bot * sy
    }
}

// m(p) for spheres ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// (u, v) = m(p) for a sphere, equirectangular, with n = (p - c) / R in local space.
pub fn sphere_uv(n: Vec3) -> Vec2 {
    let u = 0.5 + (-n.z).atan2(n.x) / (2.0 * PI);
    let v = 0.5 - n.y.clamp(-1.0, 1.0).asin() / PI;
    Vec2::new(u, v)
}


#[cfg(test)]
mod tests {
    use super::*;

    fn close(a: Vec2, b: Vec2) -> bool {
        a.abs_diff_eq(b, 1e-5)
    }

    #[test]
    fn sphere_uv_landmarks() {
        assert!(close(sphere_uv(Vec3::X), Vec2::new(0.5, 0.5)));
        // Longitude is undefined at the poles, so only v is checked there.
        assert!((sphere_uv(Vec3::Y).y - 0.0).abs() < 1e-5);
        assert!((sphere_uv(-Vec3::Y).y - 1.0).abs() < 1e-5);
        assert!(close(sphere_uv(-Vec3::Z), Vec2::new(0.75, 0.5)));
        assert!(close(sphere_uv(Vec3::Z), Vec2::new(0.25, 0.5)));
        // -X is the seam: u = 0 and u = 1 are the same column.
        let u = sphere_uv(-Vec3::X).x;
        assert!(u.abs() < 1e-5 || (u - 1.0).abs() < 1e-5);
    }

    #[test]
    fn solid_texture_ignores_uv() {
        let t = Texture::from(RGBA::new(0.2, 0.4, 0.6, 1.0));
        assert_eq!(t.sample(Vec2::ZERO), t.sample(Vec2::new(7.0, -3.0)));
    }

    #[test]
    fn image_sample_hits_texel_centers_and_blends_between() {
        // 2 x 1 image: black texel, then white.
        let img = Image::from_texels(2, 1, vec![RGBA::new(0.0, 0.0, 0.0, 1.0), RGBA::new(1.0, 1.0, 1.0, 1.0)]);
        assert!((img.sample(Vec2::new(0.25, 0.5)).r - 0.0).abs() < 1e-6);
        assert!((img.sample(Vec2::new(0.75, 0.5)).r - 1.0).abs() < 1e-6);
        assert!((img.sample(Vec2::new(0.5, 0.5)).r - 0.5).abs() < 1e-6);
        // u wraps: the right edge blends back into the left texel.
        assert!((img.sample(Vec2::new(1.0, 0.5)).r - 0.5).abs() < 1e-6);
    }

    #[test]
    fn image_rows_clamp_at_poles() {
        // 1 x 2 image: top row white, bottom row black.
        let img = Image::from_texels(1, 2, vec![RGBA::new(1.0, 1.0, 1.0, 1.0), RGBA::new(0.0, 0.0, 0.0, 1.0)]);
        assert!((img.sample(Vec2::new(0.5, 0.0)).r - 1.0).abs() < 1e-6);
        assert!((img.sample(Vec2::new(0.5, 1.0)).r - 0.0).abs() < 1e-6);
    }

    #[test]
    fn checker_alternates() {
        assert_ne!(checker(Vec2::new(0.05, 0.05)).r, checker(Vec2::new(0.2, 0.05)).r);
        assert_eq!(checker(Vec2::new(0.05, 0.05)).r, checker(Vec2::new(0.2, 0.2)).r);
    }
}
