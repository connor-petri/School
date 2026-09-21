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

### Plane Intersection
- A plane can be defined by a point $p_0$ on the plane and a normal vector $\vec{n}$
- The plane equation: $(p - p_0) \cdot \vec{n} = 0$
- Substitute the ray equation $p = e + t\vec{d}$:
  $$(e + t\vec{d} - p_0) \cdot \vec{n} = 0$$
- Solve for $t$:
  $$t = \frac{(p_0 - e) \cdot \vec{n}}{\vec{d} \cdot \vec{n}}$$
- Check if $t$ is within the valid range $[t_{min}, t_{max}]$
- If valid, compute the hit point $p = e + t\vec{d}$ and the normal $\vec{n}$

### Sphere Intersection
- A sphere can be defined by its center $c$ and radius $r$
- The sphere equation: $(p - c) \cdot (p - c) = r^2$
- Substitute the ray equation $p = e + t\vec{d}$:
  $$(e + t\vec{d} - c) \cdot (e + t\vec{d} - c) = r^2$$
- Expand and simplify to get a quadratic equation in $t$:
  $$t^2 (\vec{d} \cdot \vec{d}) + 2t \vec{d} \cdot (e - c) + (e - c) \cdot (e - c) - r^2 = 0$$
- Solve the quadratic equation for $t$:
  $$t = \frac{-b \pm \sqrt{b^2 - 4ac}}{2a}$$
  where $a = \vec{d} \cdot \vec{d}$, $b = 2 \vec{d} \cdot (e - c)$, $c = (e - c) \cdot (e - c) - r^2$
- Check if the solutions for $t$ are within the valid range $[t_{min}, t_{max}]$
- If valid, compute the hit point $p = e + t\vec{d}$ and the normal $\vec{n} = \frac{p - c}{r}$

### Ray-Triangle: A Parametric Surface
- A triangle has 3 vertices, $a, b, c$ - and *two edge vectors* from $a$: $\vec{e_1} = b - a$, $\vec{e_2} = c - a$
- Walk $\beta$ and $\gamma$ along the edges to reach any point on the triangle: $p = a + \beta \vec{e_1} + \gamma \vec{e_2}$
- $(\beta, \gamma)$ are the barycentric coordinates of the point on the triangle, with the constraints $\beta \ge 0$, $\gamma \ge 0$, and $\beta + \gamma \le 1$
- The point $p = a + \beta \vec{e_1} + \gamma \vec{e_2}$ lies inside the triangle if the barycentric coordinates satisfy the constraints.
- $p = a * \alpha + b * \beta + c * \gamma$ is another way to express the point using barycentric coordinates, with the constraints $\alpha \ge 0$, $\beta \ge 0$, $\gamma \ge 0$, and $\alpha + \beta + \gamma = 1$. $a, b, c$ are the triangle's verticies, and $\alpha, \beta, \gamma$ are the corresponding barycentric coordinates, or weights.

#### Set the 2 Descriptions Equal
- The ray point and the triangle point must be equal at the intersection.
    - $e + t\vec{d} = a + \beta \vec{e_1} + \gamma \vec{e_2}$
- Move the unknowns $(\beta, \gamma, t)$ to one side:
    - $e - a = \beta \vec{e_1} + \gamma \vec{e_2} - t\vec{d}$
- That is *one vector equation* = *three scalar equaltions* (for the $x$, $y$, and $z$ components)
- This system of three scalar equations can be solved for the unknowns $(\beta, \gamma, t)$ to find the intersection point, if it exists.

#### Solve the System
- Rewrite the vector equation as a matrix equation:
    - $\begin{bmatrix} x_a - x_b & x_a - x_c & x_d \\ y_a - y_b & y_a - y_c & y_d \\ z_a - z_b & z_a - z_c & z_d \end{bmatrix} \begin{bmatrix} \beta \\ \gamma \\ t \end{bmatrix} = \begin{bmatrix} x_e - x_a \\ y_e - y_a \\ z_e - z_a \end{bmatrix}$
- Solve this system using standard linear algebra techniques (e.g., Cramer's rule, Gaussian elimination) to find the intersection point.
    - $A = \begin{bmatrix} a - b & a - c & d \end{bmatrix}$
    - $\beta = \det{\begin{bmatrix} a - e & a - c & d \end{bmatrix}} / \det{A}$
    - $\gamma = \det{\begin{bmatrix} a - b & a - e & d \end{bmatrix}} / \det{A}$
    - $t = \det{\begin{bmatrix} a - b & a - c & a - e \end{bmatrix}} / \det{A}$

#### The Inside Test - and Bailing Out Early
- Having $\beta$, $\gamma$, and $t$, accept the hit if and only if **all** of these hold:
    - $t \in [t_{min}, t_{max}]$: in front of us and closer than the best so far
    - $\gamma > 0 \land \gamma < 1$
    - $\beta > 0 \land \beta < 1 - \gamma$
- Test in that order and *return as soon as one fails*:
    - each determinant you skip is real time saved, per ray, per triangle. This stacks up quickly with millions of triangles.

### The Triangle Normal
- Two edges span the plane, so their *cross product* is perpendicular to it:
    - $\vec{n} = \vec{b - a} \times \vec{c - a}$, then normalize
- It is the same for every point on the triangle: compute it once and cache it.
- Vertex order sets which way it points (right-hand rule) - *winding matters*
- Bonus: $\beta$ and $\gamma$ also *interpolate* across the face

### Summary
- Every test substitutes $p(t) = e + t\vec{d}$ and solves for t
- **Plane** (implicit): $t = \frac{(a - e) \cdot \vec{n}}{\vec{d} \cdot \vec{n}}$
- **Sphere** (implicit): a quadratic in $t$, the discriminant say miss/graze/through; keep the near root; $\vec{n} = \frac{p(t) - c}{R}$
- **Triangle** (barycentric): solve for $\beta$, $\gamma$, and $t$ as described above, then check the inside test.
- Keep the smallest valid $t$ across all objects and start new rays at $\epsilon$ beyond the intersection point.
- Next: **shading** turning a hit point and its normal into a color
