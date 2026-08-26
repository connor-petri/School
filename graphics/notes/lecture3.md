# Matricies

---

- A **matrix** is a rectangular array of numbers arranged in rows and columns
- An $m \times n$ matrix has $m$ rows and $n$ columns
- Entry $a_{ij}$ is the entry in the $i^{th}$ row and $j^{th}$ column
- A **square matrix** has the same number of rows and columns
- In graphics, a matrix packages a transformation: rotation, scale, translation, projection.
    - A 4x4 matrix is the workhorse of the 3D pipeline

## Symmetric and Orthogonal Matrices
- A **symmetric** matrix equals its owm transpose: $A = A^T$
- An **orthogonal** matrix has orthonormal columns: $A^TA = I$
- Both are everywhere in graphics: rotation matricies are orthogonal and many matrices are symmetric.

### Matrix Operations
#### Matrix Addition/Subtraction
- $A + B = C$ where $c_{ij} = a_{ij} + b_{ij}$
- Communitive: $A + B = B + A$
- Associative: $A + (B + C) = (A + B) + C$
- Identity: $A + 0 = A$

#### Vector-Matrix Multiplication
- $A\vec{v} = \vec{w}$ where $\vec{w} = (w_1, w_2, ..., w_m)$ and $w_i = \sum_{j=1}^{n} a_{ij}v_j$
- Each entry of the resulting vector is a linear combination of the columns of $A$ weighted by the entries of $\vec{v}$

#### Matrix-Matrix Multiplication
- $AB = C$ where $c_{ij} = \sum_{k=1}^{n} a_{ik}b_{kj}$
- Each entry of the resulting matrix is a linear combination of the columns of $A$ weighted by the entries of the rows of $B$