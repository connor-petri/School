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