mod ray;
mod camera;
mod shape;
mod scene;
mod light;
mod material;
mod mesh;

use glam::Vec3;
use camera::{Camera, RGBA};
use scene::Scene;
use shape::{Sphere, Plane};

use crate::material::Material;
use crate::light::Light;

use minifb::{Key, Window, WindowOptions};

fn main() {
    let red = RGBA::new(0.75, 0.0, 0.0, 1.0);
    let green = RGBA::new(0.0, 0.75, 0.0, 1.0);
    let blue = RGBA::new(0.0, 0.0, 0.75, 1.0);

    let mut camera1 = Camera::new(960, 
                                    540, 
                                    45.0, 
                                    Vec3::new(100.0, 25.0, -25.0),
                                    Vec3::new(0.0, 20.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.0));

    let mut camera2 = Camera::new(1920, 
                                    1080, 
                                    45.0, 
                                    Vec3::new(50.0, 20.0, -100.0),
                                    Vec3::new(-50.0, 25.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.15));

    let mut camera3 = Camera::new(1920, 
                                    1080, 
                                    45.0, 
                                    Vec3::new(-150.0, 0.0, -50.0),
                                    Vec3::new(0.0, 40.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.0));

    let light = Light::new(Vec3::new(0.0, 100.0, 0.0), RGBA::new(1.0, 0.85, 0.65, 1.0));
    let light2 = Light::new(Vec3::new(100.0, 50.0, 0.0), RGBA::new(0.75, 0.1, 0.75, 1.0));

    let sphere = Sphere::new(Vec3::new(0.0, 20.0, 0.0), 15.0, Material::plastic(red));
    let sphere1 = Sphere::new(Vec3::new(-50.0, 50.0, 40.0), 15.0, Material::mirror(RGBA::new(0.05, 0.05, 0.05, 1.0),
                                                                                                                    RGBA::new(0.9, 0.9, 0.9, 1.0)));
    let sphere3 = Sphere::new(Vec3::new(0.0, 50.0, 40.0), 15.0, Material::mirror(RGBA::new(0.05, 0.05, 0.05, 1.0),
                                                                                                                    RGBA::new(0.9, 0.9, 0.9, 1.0)));
    let sphere2 = Sphere::new(Vec3::new(-25.0, 25.0, -50.0), 15.0, Material::polished(blue));
    let plane = Plane::new(Vec3::new(0.0, 0.0, 0.0),
                           Vec3::new(0.0, 1.0, 0.0),
                           Material::polished(RGBA::new(0.5, 0.5, 0.5, 1.0)));

    let mut scene = Scene::new(RGBA::new(0.15, 0.15, 0.15, 1.0));

    scene.add_light(light);
    scene.add_light(light2);

    scene.add(plane);
    scene.add(sphere);
    scene.add(sphere1);
    scene.add(sphere2);
    scene.add(sphere3);

    camera1.render(&scene);
    camera1.save_png("./img/camera-1.png");

    camera2.render(&scene);
    camera2.save_png("./img/camera-2.png");

    camera3.render(&scene);
    camera3.save_png("./img/camera-3.png");


    // Real time shit ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    let (w, h) = (960, 540);
    let mut camera = Camera::new(w as u32, h as u32, 45.0, Vec3::new(100.0, 20.0, -25.0), Vec3::ZERO, Vec3::Y);
    let mut window = Window::new("renderer", w, h, WindowOptions::default()).unwrap();

    // Indices of every sphere in the scene, so each one can be animated.
    let sphere_indices: Vec<u32> = (0..scene.len() as u32)
        .filter(|&i| {
            scene
                .get_object(i)
                .map_or(false, |obj| obj.as_any().is::<Sphere>())
        })
        .collect();

    // Per-sphere vertical velocity. Each sphere launches at a different speed
    // so their bounces are offset from one another.
    const GRAVITY: f32 = 0.05;
    let mut velocities: Vec<f32> = sphere_indices
        .iter()
        .enumerate()
        .map(|(i, _)| 1.0 + 0.75 * i as f32)
        .collect();

    let mut angle: f32 = 0.0;
    while window.is_open() && !window.is_key_down(Key::Escape) {
        angle += 0.01;
        let pos = Vec3::new(200.0 * angle.cos(), 20.0, 200.0 * angle.sin());
        camera.look_at(pos, Vec3::ZERO);

        for (i, &idx) in sphere_indices.iter().enumerate() {
            if let Some(s) = scene
                .get_object_mut(idx)
                .and_then(|obj| obj.as_any_mut().downcast_mut::<Sphere>())
            {
                velocities[i] -= GRAVITY;
                s.center.y += velocities[i];

                // Bounce when the bottom of the sphere touches the floor at y = 0.
                if s.center.y < s.radius {
                    s.center.y = s.radius;
                    velocities[i] = -velocities[i];
                }
            }
        }

        camera.render(&scene);
        window.update_with_buffer(&camera.to_u32_buffer(), w, h).unwrap();
    }
}
