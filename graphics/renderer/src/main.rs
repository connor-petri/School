mod ray;
mod camera;
mod shape;
mod scene;
mod light;
mod material;
mod mesh;
mod transform;
mod texture;

use glam::{Quat, Vec3};
use camera::{Camera, RGBA};
use scene::Scene;
use shape::{Sphere, Plane};

use crate::material::Material;
use crate::light::Light;
use crate::mesh::Mesh;
use crate::transform::Transform;
use crate::texture::Texture;

fn car_scene() -> Scene {
    let red = RGBA::new(0.75, 0.0, 0.0, 1.0);
    let green = RGBA::new(0.0, 0.75, 0.0, 1.0);
    let blue = RGBA::new(0.0, 0.0, 0.75, 1.0);
    let background = Texture::Solid(RGBA::new(0.53, 0.80, 0.92, 1.0));

    // Head-on shot: the car's nose points down -X, so sit out in front of it
    // on the -X axis, a little above hood height, looking back at the car.
    let mut camera1 = Camera::new(1920, 
                                    1080, 
                                    45.0, 
                                    Vec3::new(-40.0, 20.0, 0.0),
                                    Vec3::new(0.0, 15.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.0));
                                    

    let mut camera2 = Camera::new(1920, 
                                    1080, 
                                    45.0, 
                                    Vec3::new(-80.0, 25.0, 0.0),
                                    Vec3::new(0.0, 8.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.0));

    let mut camera3 = Camera::new(1920, 
                                    1080, 
                                    45.0, 
                                    Vec3::new(-60.0, 20.0, 60.0),
                                    Vec3::new(0.0, 15.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.0));

    let mut camera4 = Camera::new(1920, 
                                    1080, 
                                    45.0, 
                                    Vec3::new(0.0, 20.0, 60.0),
                                    Vec3::new(0.0, 15.0, 0.0),
                                    Vec3::new(0.0, 1.0, 0.0));

    let light = Light::new(Vec3::new(0.0, 100.0, 0.0), RGBA::new(0.05, 0.1, 0.95, 1.0));
    let light2 = Light::new(Vec3::new(100.0, 50.0, 0.0), RGBA::new(0.75, 0.0, 0.75, 1.0));
    let light3 = Light::new(Vec3::new(-100.0, 50.0, 50.0), RGBA::new(0.00, 0.75, 0.25, 1.0));
    let light4 = Light::new(Vec3::new(0.0, 50.0, 0.0), RGBA::new(1.0, 1.0, 1.0, 1.0));
    // Key light out in front of and above the car so the nose and wing catch
    // highlights in the head-on view instead of reading as a black silhouette.
    let light5 = Light::new(Vec3::new(-70.0, 45.0, 35.0), RGBA::new(0.9, 0.9, 0.9, 1.0));

    let mut sr17 = Mesh::from_obj("assets/SR17.obj", Material::carbon_fiber());

  
    sr17.transform = Transform::from_position(Vec3::new(-56.19136, -0.0045016, 0.0))
        .with_uniform_scale(23.81807);

    let mut vase = Mesh::from_obj("assets/vase.obj", Material::ceramic());
    vase.transform = Transform::from_position(Vec3::new(5.7356186, 7.58, -0.0134946))
        .with_uniform_scale(71.97085);

    // SJSU brand colors: blue #0055A2, gold #E5A823.
    let sjsu_blue = RGBA::new(0.0, 85.0 / 255.0, 162.0 / 255.0, 1.0);
    let sjsu_gold = RGBA::new(229.0 / 255.0, 168.0 / 255.0, 35.0 / 255.0, 1.0);

    let plane = Plane::new(Vec3::new(0.0, 0.0, 0.0),
                           Vec3::new(0.0, 1.0, 0.0),
                           Material::polished(sjsu_gold));


    let backdrop = Plane::new(Vec3::new(50.0, 0.0, -50.0),
                              Vec3::new(-1.0, 0.0, 1.0),
                              Material::matte(sjsu_blue));

    let mut scene = Scene::new(RGBA::new(0.15, 0.15, 0.15, 1.0), background);

    scene.add_light(light4);
    scene.add_light(light5);

    scene.add(plane);
    scene.add(backdrop);

    scene.add(sr17);
    scene.add(vase);

    camera1.render(&scene);
    camera1.save_png("./img/camera-1.png");

    camera3.render(&scene);
    camera3.save_png("./img/camera-3.png");

    camera4.render(&scene);
    camera4.save_png("./img/camera-4.png");

    scene
}

fn earth_scene() -> Scene {
    let background = Texture::image("assets/stars_milky_way_4k.png");
    // Space is dark: only a faint ambient so the night side is not pure black.
    let mut scene = Scene::new(RGBA::new(0.03, 0.03, 0.04, 1.0), background);

    // The sun: one white point light, far enough away that its rays are nearly parallel.
    let sun = Light::new(Vec3::new(1000.0, 300.0, 1500.0), RGBA::new(1.0, 1.0, 1.0, 1.0));
    scene.add_light(sun);

    // Earth at the origin. The map's center (0 longitude) faces local +X.
    let mut earth = Sphere::new(Vec3::ZERO, 20.0,
                                Material::matte(Texture::image("assets/earth_daymap_4k.png")));
    // Axial tilt, then a spin so Europe and Africa face the camera.
    earth.transform.rotate_axis_angle(Vec3::Z, 23.4f32.to_radians());
    earth.transform.rotate_axis_angle(Vec3::Y, -0.3);
    scene.add(earth);


    let moon_pos = Vec3::new(-40.0, 42.0, -47.0);
    let mut moon = Sphere::new(moon_pos, 5.4, Material::matte(Texture::image("assets/moon_4k.png")));

    moon.transform.set_rotation(Quat::from_rotation_arc(Vec3::X, (-moon_pos).normalize()));
    scene.add(moon);

    // From orbit, slightly above the equator
    let mut orbit = Camera::new(1920,
                                1080,
                                35.0,
                                Vec3::new(75.0, 18.0, -25.0),
                                Vec3::ZERO,
                                Vec3::Y);

    // Looking straight down on the north pole
    let mut polar = Camera::new(1920,
                                1080,
                                35.0,
                                Vec3::new(5.0, 90.0, 5.0),
                                Vec3::ZERO,
                                Vec3::X);

    // From the night side
    let mut crescent = Camera::new(1920,
                                   1080,
                                   35.0,
                                   Vec3::new(-70.0, 15.0, -25.0),
                                   Vec3::ZERO,
                                   Vec3::Y);

    // Low orbit, wide lens, looking toward the horizon.
    let mut low_orbit = Camera::new(1920,
                                    1080,
                                    70.0,
                                    Vec3::new(30.0, 12.0, -8.0),
                                    Vec3::new(0.0, 16.0, -14.0),
                                    Vec3::Y);

    orbit.render(&scene);
    orbit.save_png("./img/earth-1.png");

    polar.render(&scene);
    polar.save_png("./img/earth-2.png");

    crescent.render(&scene);
    crescent.save_png("./img/earth-3.png");

    low_orbit.render(&scene);
    low_orbit.save_png("./img/earth-4.png");

    scene
}

fn main() {
    let _car = car_scene();
    let _earth = earth_scene();
}
