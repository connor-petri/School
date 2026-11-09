// transform.rs
// Author: Connor Petri
// CS-116A

use glam::{Mat3, Mat4, Quat, Vec3};

// Position, rotation, and scale of an object in the world.
//
// Every shape is modelled once in its own local coordinates and carries one
// of these to place it in the scene. The matrices are cached, so the fields
// are private and changed through the setters.
//
// `matrix()` maps local to world as `T * R * S` (scale first, then rotate,
// then translate). `inverse()` maps world to local, and `normal_matrix()`
// is the inverse transpose used to carry normals from local to world.
#[derive(Debug, Clone, Copy)]
pub struct Transform {
    position:       Vec3,
    rotation:       Quat,
    scale:          Vec3,

    matrix:         Mat4,
    inverse:        Mat4,
    normal_matrix:  Mat3,
}

impl Default for Transform {
    fn default() -> Self {
        Self::IDENTITY
    }
}

impl Transform {
    pub const IDENTITY: Transform = Transform {
        position: Vec3::ZERO,
        rotation: Quat::IDENTITY,
        scale: Vec3::ONE,
        matrix: Mat4::IDENTITY,
        inverse: Mat4::IDENTITY,
        normal_matrix: Mat3::IDENTITY,
    };

    pub fn new(position: Vec3, rotation: Quat, scale: Vec3) -> Self {
        let mut t = Self { position, rotation, scale, ..Self::IDENTITY };
        t.rebuild();
        t
    }

    pub fn from_position(position: Vec3) -> Self {
        Self::new(position, Quat::IDENTITY, Vec3::ONE)
    }

    pub fn from_position_rotation(position: Vec3, rotation: Quat) -> Self {
        Self::new(position, rotation, Vec3::ONE)
    }

    // Builder-style helpers for one-liners in scene setup.
    pub fn with_position(mut self, position: Vec3) -> Self {
        self.set_position(position);
        self
    }

    pub fn with_rotation(mut self, rotation: Quat) -> Self {
        self.set_rotation(rotation);
        self
    }

    pub fn with_scale(mut self, scale: Vec3) -> Self {
        self.set_scale(scale);
        self
    }

    pub fn with_uniform_scale(self, s: f32) -> Self {
        self.with_scale(Vec3::splat(s))
    }

    // Getters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    pub fn position(&self) -> Vec3 { self.position }
    pub fn rotation(&self) -> Quat { self.rotation }
    pub fn scale(&self) -> Vec3 { self.scale }

    // Local -> world.
    pub fn matrix(&self) -> &Mat4 { &self.matrix }

    // World -> local.
    pub fn inverse(&self) -> &Mat4 { &self.inverse }

    // Inverse transpose of the upper 3x3, for transforming normals.
    pub fn normal_matrix(&self) -> &Mat3 { &self.normal_matrix }

    // Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    pub fn set_position(&mut self, position: Vec3) {
        self.position = position;
        self.rebuild();
    }

    pub fn set_rotation(&mut self, rotation: Quat) {
        self.rotation = rotation.normalize();
        self.rebuild();
    }

    pub fn set_scale(&mut self, scale: Vec3) {
        self.scale = scale;
        self.rebuild();
    }

    // Moves by `delta` in world space.
    pub fn translate(&mut self, delta: Vec3) {
        self.set_position(self.position + delta);
    }

    // Applies `rotation` after the current one, in world space.
    pub fn rotate(&mut self, rotation: Quat) {
        self.set_rotation(rotation * self.rotation);
    }

    // Rotates about a unit `axis` by `angle` radians, in world space.
    pub fn rotate_axis_angle(&mut self, axis: Vec3, angle: f32) {
        self.rotate(Quat::from_axis_angle(axis.normalize(), angle));
    }

    // Point / vector helpers ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    pub fn point_to_world(&self, p: Vec3) -> Vec3 {
        self.matrix.transform_point3(p)
    }

    pub fn point_to_local(&self, p: Vec3) -> Vec3 {
        self.inverse.transform_point3(p)
    }

    pub fn vector_to_world(&self, v: Vec3) -> Vec3 {
        self.matrix.transform_vector3(v)
    }

    pub fn vector_to_local(&self, v: Vec3) -> Vec3 {
        self.inverse.transform_vector3(v)
    }

    // Carries a local-space normal to world space (inverse transpose), re-normalized.
    pub fn normal_to_world(&self, n: Vec3) -> Vec3 {
        (self.normal_matrix * n).normalize()
    }

    fn rebuild(&mut self) {
        self.matrix = Mat4::from_scale_rotation_translation(self.scale, self.rotation, self.position);
        self.inverse = self.matrix.inverse();
        self.normal_matrix = Mat3::from_mat4(self.inverse).transpose();
    }
}


#[cfg(test)]
mod tests {
    use super::*;
    use std::f32::consts::FRAC_PI_2;

    #[test]
    fn identity_is_noop() {
        let t = Transform::IDENTITY;
        let p = Vec3::new(1.0, 2.0, 3.0);
        assert_eq!(t.point_to_world(p), p);
        assert_eq!(t.point_to_local(p), p);
    }

    #[test]
    fn translation_moves_points_not_vectors() {
        let t = Transform::from_position(Vec3::new(10.0, 0.0, 0.0));
        assert_eq!(t.point_to_world(Vec3::ZERO), Vec3::new(10.0, 0.0, 0.0));
        assert_eq!(t.vector_to_world(Vec3::X), Vec3::X);
        assert_eq!(t.point_to_local(Vec3::new(10.0, 0.0, 0.0)), Vec3::ZERO);
    }

    #[test]
    fn trs_order_is_scale_then_rotate_then_translate() {
        // Scale x by 2, rotate 90 deg about z, then move +5 in y.
        let t = Transform::new(
            Vec3::new(0.0, 5.0, 0.0),
            Quat::from_rotation_z(FRAC_PI_2),
            Vec3::new(2.0, 1.0, 1.0),
        );
        // (1,0,0) -> scale (2,0,0) -> rotate (0,2,0) -> translate (0,7,0)
        assert!(t.point_to_world(Vec3::X).abs_diff_eq(Vec3::new(0.0, 7.0, 0.0), 1e-5));
    }

    #[test]
    fn inverse_round_trips() {
        let t = Transform::new(
            Vec3::new(1.0, -2.0, 3.0),
            Quat::from_axis_angle(Vec3::new(1.0, 1.0, 0.0).normalize(), 0.7),
            Vec3::new(2.0, 3.0, 0.5),
        );
        let p = Vec3::new(4.0, 5.0, 6.0);
        assert!(t.point_to_local(t.point_to_world(p)).abs_diff_eq(p, 1e-4));
    }

    #[test]
    fn normals_stay_perpendicular_under_nonuniform_scale() {
        // A plane with local normal +Y, squashed in y. A naively scaled
        // normal would still be +Y; the correct one is too, but check a
        // tilted case where the difference shows.
        let t = Transform::IDENTITY.with_scale(Vec3::new(1.0, 4.0, 1.0));
        let local_n = Vec3::new(1.0, 1.0, 0.0).normalize();
        let local_tangent = Vec3::new(1.0, -1.0, 0.0); // perpendicular to local_n
        let world_n = t.normal_to_world(local_n);
        let world_tangent = t.vector_to_world(local_tangent);
        assert!(world_n.dot(world_tangent).abs() < 1e-5);
        assert!((world_n.length() - 1.0).abs() < 1e-5);
    }

    #[test]
    fn setters_rebuild_matrices() {
        let mut t = Transform::IDENTITY;
        t.translate(Vec3::Y * 3.0);
        t.translate(Vec3::Y * 3.0);
        assert_eq!(t.position(), Vec3::new(0.0, 6.0, 0.0));
        assert_eq!(t.point_to_world(Vec3::ZERO), Vec3::new(0.0, 6.0, 0.0));
        t.rotate_axis_angle(Vec3::Z, FRAC_PI_2);
        assert!(t.vector_to_world(Vec3::X).abs_diff_eq(Vec3::Y, 1e-5));
    }
}
