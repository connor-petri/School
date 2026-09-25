mod ray;
mod camera;
mod shape;
mod scene;
use ray::Ray;
use camera::{Camera, RGBA};
use scene::Scene;
use shape::Sphere;
use glam::Vec3;

fn main() {
    let red = RGBA::new(1.0, 0.0, 0.0, 1.0);
    let green = RGBA::new(0.0, 1.0, 0.0, 1.0);
    let blue = RGBA::new(0.0, 0.0, 1.0, 1.0);
    let mut camera: Camera = Camera::new(1920, 
                                    1080, 
                                    80.0, 
                                    Vec3::new(-100.0, 0.0, 0.0),
                                    Vec3::new(100.0, 0.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.0));

    let sphere= Sphere::new(Vec3::ZERO, 15.0, red);
    let sphere1 = Sphere::new(Vec3::new(-50.0, 20.0, 20.0), 10.0, green);

    let mut scene: Scene = Scene::new();
    scene.add(sphere);
    scene.add(sphere1);

    camera.render(&scene);
    camera.save_png("/home/cpetri/School/graphics/renderer/img/image.png");
}
