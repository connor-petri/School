// CS116A -- Transformation exercise: spin-in-place vs. orbit
// =========================================================================
// The whole exercise lives in ONE function: `model_matrix()` (below).
// Everything else just draws the scene and a helpful guide.
//
// The lesson: a "spin in place" and an "orbit" use the SAME building blocks --
// a rotation and a translation -- but the ORDER you multiply them in changes
// the result. Matrices apply RIGHT-TO-LEFT to a point (p' = M * p), so the
// right-most matrix acts on the cube first.
//
//   Stage 1 (given): the cube spins on its own axis, out at radius r.
//   Stage 2 (TODO):  make the cube ORBIT the center point instead.
//   Stage 3 (TODO):  make it orbit AND spin on its own axis (like a moon).
//
// You have three building blocks in model_matrix() -- a translation and two
// rotations. Your job is to figure out how to combine them for each stage.
//
// Coordinate convention (matches the lecture decks): right-handed, y-up.
// We rotate about the Y axis, so the orbit sweeps the ground (x-z) plane.
// =========================================================================

use macroquad::prelude::*;
// NOTE: Vec3 / Mat4 / vec3() above are the `glam` types, re-exported by
// macroquad. Same API as the `glam` examples in the lecture slides.

const ORBIT_RADIUS: f32 = 3.0; // how far the cube sits from the center
const ORBIT_CENTER: Vec3 = vec3(0.0, 0.0, 0.0); // the point we orbit

// -------------------------------------------------------------------------
// THE EXERCISE.  `spin` and `orbit` are the current rotation angles (radians)
// stored in main() and nudged a bit larger every frame -- spin fast, orbit
// slow.  Return the 4x4 model matrix that places/orients the unit cube for the
// current stage.
// -------------------------------------------------------------------------
// (Stage 1, the starter, doesn't use every building block yet -- that's fine,
//  the later stages do. This keeps the starter warning-free.)
#[allow(unused_variables)]
fn model_matrix(spin: f32, orbit: f32) -> Mat4 {
    // --- Building blocks (each is a glam Mat4) ---------------------------
    // Push the cube out along +x by the orbit radius:
    let trans = Mat4::from_translation(vec3(ORBIT_RADIUS, 0.0, 0.0));
    // Fast rotation about Y -- used as the cube's OWN-AXIS spin:
    let spin_rot = Mat4::from_rotation_y(spin);
    // Slow rotation about Y -- used to swing the whole offset around:
    let orbit_rot = Mat4::from_rotation_y(orbit);

    // ================= STAGE 1 (given): SPIN IN PLACE ===================
    // Rotate the cube about its own center, THEN push it out to radius r
    // (right-to-left: spin_rot acts first, then trans). It spins in place.
    trans * spin_rot

    // ================= STAGE 2 (TODO): ORBIT THE CENTER =================
    // Right now the cube only spins in place. Make it travel AROUND the
    // center instead, riding the green guide ring.
    // Hint: it's the SAME two matrices as Stage 1 -- what has to change?
    // Replace the `trans * spin_rot` line above with your own combination.

    // ============ STAGE 3 (TODO): ORBIT *AND* SPIN LOCALLY ==============
    // Once it orbits, also make it spin on its own axis as it travels --
    // watch the little R/G/B local axes on the cube. You'll need all three
    // building blocks for this one.
    //
    // Extension: to orbit a point OTHER than the origin, you'll have to
    // account for ORBIT_CENTER. Change ORBIT_CENTER, see what breaks, fix it.
}

// -------------------------------------------------------------------------
// Everything below is scene/drawing plumbing -- no need to edit it.
// -------------------------------------------------------------------------

fn window_conf() -> Conf {
    Conf {
        window_title: "CS116A -- Spin vs. Orbit".to_owned(),
        window_width: 1000,
        window_height: 700,
        ..Default::default()
    }
}

#[macroquad::main(window_conf)]
async fn main() {
    // These two angles ARE the animation. Each is a rotation angle in radians
    // that we store and grow a little every frame -- one for the cube's own
    // spin, one for its trip around the center. They start at zero.
    let mut spin_angle: f32 = 0.0;
    let mut orbit_angle: f32 = 0.0;

    // How fast each angle grows, in radians per second. The bigger the number,
    // the more we add each frame, the faster it turns. Spin is much larger than
    // orbit, so the cube whirls quickly while drifting slowly around the ring.
    let spin_speed = 2.0_f32; // rad/s -- fast
    let orbit_speed = 0.6_f32; // rad/s -- slow

    let mut paused = false;
    let mut show_guide = true;

    loop {
        // --- input ------------------------------------------------------
        if is_key_pressed(KeyCode::Space) {
            paused = !paused;
        }
        if is_key_pressed(KeyCode::G) {
            show_guide = !show_guide;
        }
        // Grow each angle by (speed * time-since-last-frame). Multiplying by
        // the frame time keeps the turn rate steady regardless of frame rate;
        // a larger step per frame means a faster spin/orbit.
        if !paused {
            let dt = get_frame_time();
            spin_angle += spin_speed * dt;
            orbit_angle += orbit_speed * dt;
        }

        // --- the matrix under study ------------------------------------
        let model = model_matrix(spin_angle, orbit_angle);

        // --- 3D scene ---------------------------------------------------
        clear_background(Color::from_rgba(24, 26, 32, 255));
        set_camera(&Camera3D {
            position: vec3(0.0, 7.0, 11.0),
            up: vec3(0.0, 1.0, 0.0),
            target: ORBIT_CENTER,
            ..Default::default()
        });

        // Ground grid + world axes (x=red, y=green, z=blue).
        draw_grid(
            20,
            0.5,
            Color::from_rgba(60, 64, 74, 255),
            Color::from_rgba(45, 48, 56, 255),
        );
        draw_line_3d(Vec3::ZERO, vec3(2.0, 0.0, 0.0), RED);
        draw_line_3d(Vec3::ZERO, vec3(0.0, 2.0, 0.0), GREEN);
        draw_line_3d(Vec3::ZERO, vec3(0.0, 0.0, 2.0), BLUE);

        // Guide: the target orbit ring + a marker where the cube SHOULD be
        // if it were orbiting. Computed straight from cos/sin (not from the
        // answer matrix) so it hints at the path, not the formula.
        if show_guide {
            draw_orbit_ring(ORBIT_CENTER, ORBIT_RADIUS);
            // Rotation about +Y sends (r,0,0) to (r*cos, 0, -r*sin).
            let target = ORBIT_CENTER
                + vec3(
                    ORBIT_RADIUS * orbit_angle.cos(),
                    0.0,
                    -ORBIT_RADIUS * orbit_angle.sin(),
                );
            draw_cube_wires(
                target,
                vec3(1.05, 1.05, 1.05),
                Color::from_rgba(120, 200, 120, 120),
            );
        }

        // The cube itself, transformed by `model`. We push the matrix onto
        // macroquad's model stack, draw a unit cube at the origin, and let
        // the matrix do the work -- this is literally the matrix in action.
        {
            let gl = unsafe { get_internal_gl() };
            gl.quad_gl.push_model_matrix(model);
        }
        draw_cube(
            Vec3::ZERO,
            vec3(1.0, 1.0, 1.0),
            None,
            Color::from_rgba(90, 140, 230, 255),
        );
        draw_cube_wires(
            Vec3::ZERO,
            vec3(1.0, 1.0, 1.0),
            Color::from_rgba(200, 220, 255, 255),
        );
        // The cube's LOCAL axes (transformed by the same matrix): watch these
        // spin on their own in Stage 3.
        draw_line_3d(Vec3::ZERO, vec3(0.9, 0.0, 0.0), RED);
        draw_line_3d(Vec3::ZERO, vec3(0.0, 0.9, 0.0), GREEN);
        draw_line_3d(Vec3::ZERO, vec3(0.0, 0.0, 0.9), BLUE);
        {
            let gl = unsafe { get_internal_gl() };
            gl.quad_gl.pop_model_matrix();
        }

        // --- 2D overlay (screen space) ---------------------------------
        set_default_camera();
        draw_hud(&model, paused, show_guide);

        next_frame().await;
    }
}

/// Draw the target orbit path as a ring of short segments in the x-z plane.
fn draw_orbit_ring(center: Vec3, radius: f32) {
    let seg = 64;
    let col = Color::from_rgba(120, 200, 120, 90);
    for i in 0..seg {
        let a0 = (i as f32) / (seg as f32) * std::f32::consts::TAU;
        let a1 = ((i + 1) as f32) / (seg as f32) * std::f32::consts::TAU;
        let p0 = center + vec3(radius * a0.cos(), 0.0, -radius * a0.sin());
        let p1 = center + vec3(radius * a1.cos(), 0.0, -radius * a1.sin());
        draw_line_3d(p0, p1, col);
    }
}

/// On-screen instructions + the live 4x4 model matrix.
fn draw_hud(model: &Mat4, paused: bool, show_guide: bool) {
    let white = Color::from_rgba(235, 238, 245, 255);
    let dim = Color::from_rgba(150, 156, 168, 255);

    draw_text("CS116A -- Spin in place vs. Orbit", 16.0, 28.0, 26.0, white);
    draw_text(
        "Edit model_matrix() in src/main.rs:  Stage 1 = spin (given) -> Stage 2 = orbit -> Stage 3 = orbit + local spin",
        16.0,
        52.0,
        18.0,
        dim,
    );

    // Live model matrix, row by row (glam stores column-major; .row() reads a row).
    draw_text("model matrix =", 16.0, 92.0, 20.0, white);
    for i in 0..4 {
        let r = model.row(i);
        let line = format!("[ {:7.3}  {:7.3}  {:7.3}  {:7.3} ]", r.x, r.y, r.z, r.w);
        draw_text(&line, 32.0, 118.0 + i as f32 * 22.0, 20.0, white);
    }

    // Controls, bottom-left.
    let h = screen_height();
    draw_text(
        &format!(
            "[Space] {}   [G] guide: {}",
            if paused { "resume" } else { "pause" },
            if show_guide { "on" } else { "off" }
        ),
        16.0,
        h - 20.0,
        20.0,
        dim,
    );
}
