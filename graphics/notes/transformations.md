# 2D and 3D Transformations

---

## Why Transformations?
- We bodel each object *once*, in its own local coordinates
- A **transformation** repositions, reorients, or resizes geometry
- Cameras, animation, and object hierarchies are all build from them
- From the matrices lecture: a linear transform is a matrix product $M\vec{v}$
    - The columns of $M$ are where the axes land
- Plan: 2D first (easy to draw), then the same machinery in 3D

## 2D
### Scaling
- $S$ multiplies each coordinate: $x' = s_{x'}x_s ...$
- In matrix form $S$ is **diagonal**
#### Reflection is a Negative Scale
- A scale factor of -1 flips an axis
- $S(-1, 1)$ reflects across the y-axis; $S(1, -1)$ across the x-axis
-

### Rotation
- $R(\theta)$ spins points about the origin counterclockwise for $\theta > 0$
- The matrix is built from $cos(\theta)$ and $sin(\theta)$
    - $R(\theta) = \begin{bmatrix} cos(\theta) & -sin(\theta) \\ sin(\theta) & cos(\theta) \end{bmatrix}$
    - $R^T = R^{-1}$
- Lengths and angles are preserved $\implies$ orthogonal

## Composing Transformations
- Each transform is a matrix, so a *sequence* is a matrix *product*
    - $M\vec{v} = RS\vec{v}$


## 3D
### Translation
- TODO finish this part

### Rotation
- A 3D rotation about a coordinate axis leaves that axis *fixed*
- The other two coordinates rotate exactly as in 2D
- $R_x(\theta), R_y(\theta), R_z(\theta)$ Note the cyclic sign pattern 
- Positive $\theta$ is counterclockwise looking down the axis *towards the origin*

#### About an Arbitrary Axis
- Any **unit axis** $\hat{n}$ plus an angle $\theta$ defines a rotation $R(\hat{n}, \theta)$
- Strategy - build it out of rotations we already have
    1. rotate the scene so $\hat{n}$ lines up with the z-axis - Call that rotation $A$
    2. rotate by $\theta$ about z
    3. undo step 1: $R(\hat{n}, \theta) = A^{-1}R_z(\theta)A$
- In practice: a library axis-angle call, or **quaternions** (later lecture)

## Translation is different
- Translation moves every point by the same offset: $p' = p + t$
- However it is not linear - no 3x3 matrix can do it
    - $M\cdot 0 = 0$ for every matix, yet translation must move the origin
- The fix: go up one dimension - **homogeneous coordinates**