# Ray Tracing

---

### Viewing Rays from the Camera Basis
- From eye / target / up we built the camera basis
    - $u$ (right), $v$ (up), $w$ (backward)
- Lay a **view plane** in front of the eye, spanned by $u$ and $v$
- Each pixel maps to a point on that plane at normalized $(s, t)$
- Shoot a ray from the eye *through* that point into the scene

#### Bottom Left Corner of View Plane
- Center point: $p = eye - \vec{w} \quad (d = 1)$
- Bottom Left:  $L = (\frac{1}{2}h)(-\vec{v}) + (\frac{1}{2}w) (-\vec{u})$
- Is the *origin* of the view plane

### View Plane
- Coordinate system $(s, t)$, where $s$ is the horizontal component and $t$ is the verticle component
- $s, t \in [0, 1]$ - Normalized, Center is at $(0.5, 0.5)$

## Rendering Loop
- Given pixel $(x, y)$, map to $(s,t)$, then shoot a camera ray through that point

### From Pixels to $(s, t)$
- Camera: ray wants normalized coordinates, not raw pixels
- Sample the **center** of pixel $(x, y)$, hence the + 0.5
    - $s = (x + 0.5) / width$
    - $t = 1 - (y + 0.5) / height$
- The flip on $t$: the film stores row $y = 0$ at the top, but the camera puts $t = 0$ at the bottom, hence the $1 - ...$

#### Direction Vector for a Pixel
- $d_y = t \cdot h$
- $d_x = s \cdot w$
- $x = L + d_x\vec{u}$
- $y = L + d_y\vec{v}$
- $z = eye - \vec{w}$
- Ray r = $(x, y, z) - eye$

---

## Object Intersections
### Questions
- Did this ray hit an object
- Where on the object did it hit
- What is the surface normal at that point (later)

### Paremetric Rays
- A ray is a **starting point** and a **direction**
    - $p(t) = e + t\vec{d}$
- t is the parameter that extends the ray in the direction $\vec{d}$ as shot from eye $e$
- if $\vec{d}$ is a unit vector, then $t$ is a distance

### What an Intersection Test Returns
- Miss-> nothing.
- Hit
    - $t$ - where along the ray the collision occurs
    - $p$ - the hit point (can also be calculated with $t$)
    - $\vec{n}$ - the surface normal
- We also pass a valid range $[t_{min}, t_{max}]$ into every test

```rust
pub struct Hit {
    pub t: f32,
    pub p: Vec3,
    pub n: Vec3,
}

// every shape answers the same question , the same way
// None == Miss; Some(hit) == Hit
fn hit (&self, e: Vec3, d: Vec3, t_min: f32, t_max: f32, t_max: f32) -> Option<Hit>;
```

#### Nearest Hit
- A ray may cross *many* objects, we want the *first* surface it meets
- Test every object; keep the hit with the *smallest* valid $t$
- A neat trick: once you have a hit at $t$, pass it as the new $t_{max}$
- This loop is the whole scene traversal