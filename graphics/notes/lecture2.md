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