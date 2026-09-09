# Spin vs. Orbit — a transformation exercise

An interactive demo (Rust + [macroquad](https://macroquad.rs) +
[glam](https://crates.io/crates/glam)) for the order-of-multiplication lesson:
a **spin in place** and an **orbit** are the *same* rotation `R` and
translation `T`, just multiplied in the opposite order.

## Run it

```sh
cargo run
```

A window opens with a cube out at radius 3, **spinning in place** on the
ground plane. The live 4×4 model matrix is shown top-left. A faint green ring
and marker show the target orbit path.

Controls: **Space** pause/resume · **G** toggle the orbit guide.

## Your task

Everything happens in one function, `model_matrix()`, in `src/main.rs`. You
have three building blocks already defined:

- `t` — translate out along +x by the radius
- `spin_rot` — a *fast* rotation about Y (the cube's own axis)
- `orbit_rot` — a *slow* rotation about Y (swings the offset around the center)

Matrices apply **right-to-left** to a point (`p' = M * p`).

| Stage | Goal | Model matrix |
| --- | --- | --- |
| 1 | Spin in place *(given)* | `t * spin_rot` |
| 2 | Orbit the center | `orbit_rot * t` |
| 3 | Orbit **and** spin on its own axis | `orbit_rot * t * spin_rot` |

**Stage 2** is the whole point: it's the *same two matrices* as Stage 1, in the
other order. Push the cube out *first*, then rotate that offset about the
center. When you get it right, the cube rides the green ring.

**Stage 3** adds the local spin back on the right (right-most = applied first).
Watch the little R/G/B local axes on the cube: they now turn on their own while
the cube travels around the ring — a moon rotating as it orbits.

### Extension

To orbit a point other than the origin, sandwich the orbit between
translations to and from that center:

```
Mat4::from_translation(c) * orbit_rot * t * Mat4::from_translation(-c)
```

Try changing `ORBIT_CENTER` (top of `src/main.rs`) and see why the plain
`orbit_rot * t` no longer orbits the point you expect.

## Convention

Right-handed, **y-up** (x right, y up, z toward the viewer) — same as the
lecture decks. We rotate about **Y**, so the orbit sweeps the x–z ground plane.
