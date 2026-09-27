mod ray;
mod camera;
mod shape;
mod scene;

use glam::Vec3;
use camera::{Camera, RGBA};
use scene::Scene;
use shape::{Sphere, Plane};

fn main() {
    let red = RGBA::new(0.75, 0.0, 0.0, 1.0);
    let green = RGBA::new(0.0, 0.75, 0.0, 1.0);
    let blue = RGBA::new(0.0, 0.0, 0.75, 1.0);

    let mut camera1 = Camera::new(1920, 
                                    1080, 
                                    100.0, 
                                    Vec3::new(100.0, -5.0, -25.0),
                                    Vec3::ZERO,
                                    Vec3::new(0.0, 1.0, 0.0));

    let mut camera2 = Camera::new(1920, 
                                    1080, 
                                    100.0, 
                                    Vec3::new(0.0, 20.0, -75.0),
                                    Vec3::new(-50.0, 0.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.15));

    let mut camera3 = Camera::new(1920, 
                                    1080, 
                                    100.0, 
                                    Vec3::new(-100.0, 0.0, 0.0),
                                    Vec3::ZERO,
                                    Vec3::new(0.0, 1.0, 0.5));

    let sphere = Sphere::new(Vec3::ZERO, 15.0, red);
    let sphere1 = Sphere::new(Vec3::new(-50.0, 20.0, 40.0), 15.0, green);
    let sphere2 = Sphere::new(Vec3::new(-25.0, 25.0, -50.0), 15.0, blue);

    let plane = Plane::new(Vec3::new(0.0, -15.0, 0.0),
                           Vec3::new(0.0, 1.0, 0.0),
                           RGBA::new(0.5, 0.5, 0.5, 1.0))
                        .checkered(RGBA::new(0.15, 0.15, 0.15, 1.0), 10.0);

    let mut scene: Scene = Scene::new();

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
