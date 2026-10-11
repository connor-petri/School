mod ray;
mod camera;
mod shape;
mod scene;
mod light;
mod material;

use glam::Vec3;
use camera::{Camera, RGBA};
use scene::Scene;
use shape::{Sphere, Plane};

use crate::material::Material;
use crate::light::Light;

fn main() {
    let red = RGBA::new(0.75, 0.0, 0.0, 1.0);
    let green = RGBA::new(0.0, 0.75, 0.0, 1.0);
    let blue = RGBA::new(0.0, 0.0, 0.75, 1.0);

    let mut camera1 = Camera::new(1920, 
                                    1080, 
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

    let sphere = Sphere::new(Vec3::ZERO, 15.0, Material::plastic(red));
    let sphere1 = Sphere::new(Vec3::new(-50.0, 50.0, 40.0), 15.0, Material::polished(green));
    let sphere2 = Sphere::new(Vec3::new(-25.0, 25.0, -50.0), 15.0, Material::plastic(blue));

    let plane = Plane::new(Vec3::new(0.0, -15.0, 0.0),
                           Vec3::new(0.0, 1.0, 0.0),
                           Material::matte(RGBA::new(0.5, 0.5, 0.5, 1.0)));

    let mut scene = Scene::new(RGBA::new(0.15, 0.15, 0.15, 1.0));

    scene.add_light(light);
    scene.add_light(light2);

    scene.add(plane);
    scene.add(sphere);
    scene.add(sphere1);
    scene.add(sphere2);

    camera1.render(&scene);
    camera1.save_png("./img/camera-1.png");

    camera2.render(&scene);
    camera2.save_png("./img/camera-2.png");

    camera3.render(&scene);
    camera3.save_png("./img/camera-3.png");
}
