# Vectors for 3D Graphics

---

## What is a vector?
- A **vector** has a magnitude (length) and a direction.
- It has no fixed position: Same length and direction means the same vector
- A **scalar** has magnitude only
- Vectors are *everywhere* in 3D graphics
    - Directions: view, light, reflection

### Cartesian Representation
- $\vec{v} = (v_x, v_y, v_z)$
- $\vec{v} = v_x\hat{x} + v_y\hat{y} + v_z\hat{z}$
- $|\vec{v}| = \sqrt{v_x^2 + v_y^2 + v_z^2}$

### Vector Operations
#### Normalization
- Normalization makes the vector of magnitude one.
- Used to isolate direction of a vector
- $\hat{v} = \frac{\vec{v}}{|\vec{v}|}$
- $\vec{0}$ cannot be normalized.

#### Vector Addition
$\vec{a}, \vec{b} \in \mathbb{R}^3$
- $\vec{a} + \vec{b} = (a_x + b_x, a_y + b_y, a_z + b_z)$
- $\vec{a} - \vec{b} = (a_x - b_x, a_y - b_y, a_z - b_z)$

#### Scalar Multiplication
$c \in \mathbb{R}, \vec{a} \in \mathbb{R}^3$
- $c\vec{a} = (ca_x, ca_y, ca_z)$

##### Parametric Equation
- $p(t) = O + t\hat{d}$
Where $O$ is a point (origin of the ray), $\hat{d}$ is a direction, and $t \in \mathbb{R}$
- Each value of $t$ gives a point on the line

##### Rays
- Rays contain a point and a direction
- Ray:$(O, \hat{d})$ - Origin and Direction

#### Dot Product
$\vec{a}, \vec{b} \in \mathbb{R}^3$
- $\vec{a} \cdot \vec{b} = |\vec{a}||\vec{b}|cos(\theta)$
- $\vec{a} \cdot \vec{b} = a_xb_x + a_yb_y + a_zb_z$
- Dot product = 0 $\implies$ $\vec{a}$ and $\vec{b}$ are *orthogonal*
- $\theta = cos^{-1}(\frac{\vec{a}\cdot\vec{b}}{|\vec{a}||\vec{b}|})$

##### Projection
- The projection is the "shadow" of $\vec{a}$ on the line of $\vec{b}$
- Scalar projection (a length): $|\vec{a}|cos(\theta) = \frac{\vec{a} \cdot \vec{b}}{|\vec{b}|}$
- Vector projection: $proj_{\vec{b}} \vec{a} = \frac{\vec{a} \cdot \vec{b}}{|\vec{b}|^2}$

#### Cross Product
- Defined only in $\mathbb{R}^3$
- $\vec{a} \times \vec{b} = (a_yb_z - a_zb_y, a_zb_x - a_xb_z, a_xb_y - a_yb_x)$
- Produces vector orthogonal to both $\vec{a}$ and $\vec{b}$
- $|\vec{a} \times \vec{b}| = |\vec{a}||\vec{b}|sin(\theta)$

##### Uses
- Surface normal of a triangle (A, B, C):
    - $\vec{n} = (B-A) \times (C - A)$, then normalize
- Normals drive lighting, shading, and backface tests

#### Orthonormal Basis
- Forming and *orthonormal basis* from one vector (used for camera)
- Given a vector $\vec{a}$:
    1. $\vec{w} = \frac{\vec{a}}{|\vec{a}|}$
    2. Pick any $\vec{t}$ not parallel to $\vec{w}$, e.g. $\vec{t} = (0, 1, 0)$
    3. $\vec{u} = \frac{\vec{t} \times \vec{w}}{|\vec{t} \times \vec{w}|}$
    4. $\vec{v} = \vec{w} \times \vec{u}$

##### Orthonormal Basis from Two Vectors
- Given two non-parallel vectors $\vec{a}$ and $\vec{b}$:
    1. $\vec{w} = \frac{\vec{a}}{|\vec{a}|}$
    2. $\vec{u} = \frac{\vec{b} \times \vec{w}}{|\vec{b} \times \vec{w}|}$
    3. $\vec{v} = \vec{w} \times \vec{u}$
-  $\vec{w}$ points along $\vec{a}$, $\vec{v}$ ends up in the plane of $\vec{a}$ and $\vec{b}$
- No abritrary helper needed: $\vec{b}$ pins down the orientation
- Classic use: building a camera frame from a gaze direction and an "up" vector

---
## Rust GLAM Vectors
```rust
// Cargo.toml
use glam::Vec3;

let v = Vec3::new(4.0, 3.0, 0.0);
let len = v.length();
let unit = v.normalize();

let a = Vec3::new(1.0, 2.0, 0.0);
let b = Vec3::new(3.0, -1.0, 2.0);
let sum = a + b;
let diff = a - b;
let scaled = 2.0 * a;
let dot = a.dot(b);
let cross = a.cross(b);
let proj = a.project_onto(b);
```

### Glam Cross Product, basis and rays
```rust
let n = (b - a).cross(c - a).normalize(); // normal of triangle ABC
let w = a.normalize()
let (u, v) = w.any_orthonormal_pair(); // orthonormal basis from one vector

// Or from 2 vectors
let w = gaze.normalize();
let u = up.cross(w).normalize();
let v = w.cross(u);

// Parametric line
let p = o + 2.0 * d; // point on ray (o, d)
```