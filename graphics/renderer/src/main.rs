mod ray;
mod camera;
mod shape;
mod scene;
mod light;
mod material;
mod mesh;
mod transform;

use glam::Vec3;
use camera::{Camera, RGBA};
use scene::Scene;
use shape::{Sphere, Plane};

use crate::material::Material;
use crate::light::Light;
use crate::mesh::Mesh;
use crate::transform::Transform;

// use minifb::{Key, Window, WindowOptions};

fn main() {
    let red = RGBA::new(0.75, 0.0, 0.0, 1.0);
    let green = RGBA::new(0.0, 0.75, 0.0, 1.0);
    let blue = RGBA::new(0.0, 0.0, 0.75, 1.0);

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

    // The OBJ is only ~3 units long with its nose pointing down -X. This
    // transform scales it to 70 units and centers it on the origin, resting
    // on the floor plane. The numbers were computed once from the mesh
    // bounds (s = 70 / longest extent; position = -center * s, with y = -min.y * s)
    // and hardcoded; redo that if the model file changes.
    sr17.transform = Transform::from_position(Vec3::new(-56.19136, -0.0045016, 0.0))
        .with_uniform_scale(23.81807);

    // Vase in the driver's seat: in the car's local (OBJ) space the cockpit
    // opening runs x 2.15..2.75 and is ~0.4 wide, with a seat shelf at
    // y ≈ 0.32 toward the back and the rim at y ≈ 0.58. The vase is sized to
    // 0.37 car-units tall so its bulb clears the opening, and placed on the
    // shelf at car-local (2.6, 0.32, 0). Computed once through the car's
    // transform and hardcoded; redo if the car transform or model changes.
    let mut vase = Mesh::from_obj("assets/vase.obj", Material::ceramic());
    vase.transform = Transform::from_position(Vec3::new(5.7356186, 7.58, -0.0134946))
        .with_uniform_scale(71.97085);

    // let sphere = Sphere::new(Vec3::new(0.0, 20.0, 0.0), 15.0, Material::plastic(red));
    // let sphere1 = Sphere::new(Vec3::new(-50.0, 50.0, 40.0), 15.0, Material::mirror(RGBA::new(0.05, 0.05, 0.05, 1.0),
    //                                                                                                                 RGBA::new(0.9, 0.9, 0.9, 1.0)));
    // let sphere3 = Sphere::new(Vec3::new(0.0, 50.0, 40.0), 15.0, Material::mirror(RGBA::new(0.05, 0.05, 0.05, 1.0),
    //                                                                                                                 RGBA::new(0.9, 0.9, 0.9, 1.0)));
    // let sphere2 = Sphere::new(Vec3::new(-25.0, 25.0, -50.0), 15.0, Material::polished(blue));
    // SJSU brand colors: blue #0055A2, gold #E5A823.
    let sjsu_blue = RGBA::new(0.0, 85.0 / 255.0, 162.0 / 255.0, 1.0);
    let sjsu_gold = RGBA::new(229.0 / 255.0, 168.0 / 255.0, 35.0 / 255.0, 1.0);

    let plane = Plane::new(Vec3::new(0.0, 0.0, 0.0),
                           Vec3::new(0.0, 1.0, 0.0),
                           Material::polished(sjsu_gold));

    // Vertical backdrop behind the car. camera1 and camera3 look along +X and
    // camera4 looks along -Z, so a single wall on the diagonal x - z = 100
    // (normal toward -X/+Z) sits behind the car from all three viewpoints and
    // every ray in their frames lands on it. The rear wing tops out near
    // x - z ≈ 47, so the wall is ~37 units behind the car's farthest corner.
    let backdrop = Plane::new(Vec3::new(50.0, 0.0, -50.0),
                              Vec3::new(-1.0, 0.0, 1.0),
                              Material::matte(sjsu_blue));

    // let plane2 = Plane::new(Vec3::new(0.0, 0.0, 0.0),
    //                        Vec3::new(0.0, 1.0, 0.0),
    //                        Material::polished(RGBA::new(0.5, 0.5, 0.5, 1.0)));

    // let sphere4 = Sphere::new(Vec3::new(0.0, 15.0, -60.0), 15.0, Material::matte(blue));
    // let sphere5 = Sphere::new(Vec3::new(0.0, 15.0, -20.0), 15.0, Material::plastic(blue));
    // let sphere6 = Sphere::new(Vec3::new(0.0, 15.0, 20.0), 15.0, Material::polished(blue));
    // let sphere7 = Sphere::new(Vec3::new(0.0, 15.0, 60.0), 15.0, Material::mirror(RGBA::new(0.05, 0.05, 0.05, 1.0), RGBA::new(0.9, 0.9, 0.9, 1.0)));

    let mut scene = Scene::new(RGBA::new(0.15, 0.15, 0.15, 1.0));
    // let mut scene2 = Scene::new(RGBA::new(0.15, 0.15, 0.15, 1.0));

    scene.add_light(light4);
    scene.add_light(light5);
    // scene.add_light(light2);
    // scene.add_light(light3);
    // scene2.add_light(light4);

    scene.add(plane);
    scene.add(backdrop);

    scene.add(sr17);
    scene.add(vase);

    // scene.add(sphere);
    // scene.add(sphere1);
    // scene.add(sphere2);
    // scene.add(sphere3);
    // scene2.add(sphere4);
    // scene2.add(sphere5);
    // scene2.add(sphere6);
    // scene2.add(sphere7);
    // scene2.add(plane2);

    camera1.render(&scene);
    camera1.save_png("./img/camera-1.png");

    // camera2.render(&scene);
    // camera2.save_png("./img/camera-2.png");

    camera3.render(&scene);
    camera3.save_png("./img/camera-3.png");

    camera4.render(&scene);
    camera4.save_png("./img/camera-4.png");

    

    

    // Real time shit ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // let (w, h) = (1200, 720);
    // let mut camera = Camera::new(w as u32, h as u32, 45.0, Vec3::new(100.0, 20.0, -25.0), Vec3::ZERO, Vec3::Y);
    // let mut window = Window::new("renderer", w, h, WindowOptions::default()).unwrap();

    // // Indices of every sphere in the scene, so each one can be animated.
    // let sphere_indices: Vec<u32> = (0..scene.len() as u32)
    //     .filter(|&i| {
    //         scene
    //             .get_object(i)
    //             .map_or(false, |obj| obj.as_any().is::<Sphere>())
    //     })
    //     .collect();

    // // Per-sphere vertical velocity. Each sphere launches at a different speed
    // // so their bounces are offset from one another.
    // const GRAVITY: f32 = 0.05;
    // let mut velocities: Vec<f32> = sphere_indices
    //     .iter()
    //     .enumerate()
    //     .map(|(i, _)| 0.5 + 0.65 * i as f32)
    //     .collect();

    // let mut angle: f32 = 0.0;
    // while window.is_open() && !window.is_key_down(Key::Escape) {
    //     angle += 0.01;
    //     let pos = Vec3::new(200.0 * angle.cos(), 20.0, 200.0 * angle.sin());
    //     camera.look_at(pos, Vec3::ZERO);

    //     for (i, &idx) in sphere_indices.iter().enumerate() {
    //         if let Some(s) = scene
    //             .get_object_mut(idx)
    //             .and_then(|obj| obj.as_any_mut().downcast_mut::<Sphere>())
    //         {
    //             velocities[i] -= GRAVITY;
    //             s.transform.translate(Vec3::new(0.0, velocities[i], 0.0));

    //             // Bounce when the bottom of the sphere touches the floor at y = 0.
    //             let mut pos = s.transform.position();
    //             if pos.y < s.radius {
    //                 pos.y = s.radius;
    //                 s.transform.set_position(pos);
    //                 velocities[i] = -velocities[i];
    //             }
    //         }
    //     }

    //     camera.render(&scene);
    //     window.update_with_buffer(&camera.to_u32_buffer(), w, h).unwrap();
    // }
}
