# Refraction

---

## Previous class summary

### Snell's Law
- $n \sin\theta = n_t \sin\varphi$ ... [ Finish later ]

---

## Algorithm so far...
- Find nearest hit
- Shade
- cast one more reflection ray
- Every surface so far is opaque, but most materials refract light
- To solve this, we cast a refraction ray through the material

### Refraction
- A ray of light bends when it passes from one medium to another
- Light bends at a boundary because it changes speed: $v = c / n$
- It bends at the boundary only, travels in a straight line within the material

#### Why Does Light Slow Down?
- In a vacuum, light travels at $c$
- Inside a transparent material it is slower:
    - $v = c / n$
    - $n$ is the index of refraction (IOR), a number $\geq 1$
- The frequency stays the same, so the wavelength shrinks to $\lambda / n_t$
- A wavefront arriving at an angle slows down one end first

#### Index of Refraction
- $n = c / v$: how many times slower light is in the material than in a vacuum
- Typical values
    - Vacuum: 1
    - Air: 1.0003
    - Water: 1.33
    - Ice: 1.31
    - Window Glass: 1.5
    - Crown/Flint glass: 1.52-1.65
    - Sapphire: 1.77
    - Diamond: 2.42
- Higher n $\implies$ slower light $\implies$ more bending at the boundary
- $n$ varies slightly with wavelength (dispersion), which is where rainbows and prism colors come from
- We use one $n$ per material, next to $k_d, k_s, k_m$

### Snell's Law
- At the hit point $p$: incoming direction $\vec{d}$, and normal $\vec{n}$
    - $\theta$: angle between the incident ray and the normal
    - $\varphi$: angle between the refracted ray $\vec{t}$ and the nromal on the other size
- $n\sin \theta = n_t \sin \varphi$
    - $n$ IOR of medium before transition
    - $n_t$ IOR of medium after transition
- $\vec{d}, \vec{t}, \vec{n}$ all lie on the same plane, no sideways twist
- Reflected ray is still present
- Two refraction events per solid object: at the entry point $p_1$ and the exit point $p_2$

#### Entering vs. Exiting
- A viewing ray enters the glass ball at $p_1$ and exits at $p_2$
- At $p_1$, (air -> glass) the ray arrives against the outward normal
- Inside it goes straight to the next hit, the same sphere but from the inside
- At $p_2$, (glass -> air) the ray arrives along the outward normal
- The sign of $\vec{d} \cdot \vec{n}$ tells the tracer which side it is on

#### Computing a Refracted Ray
- Snell gives the angle; we need the direction
- Split $\vec{d}$ into tangent and normal parts, as for reflection
- Marschner and Shirley equation:
    - $\eta = n / n_t$
    - $\vec{t} = \eta[\vec{d} - \vec{n} (\vec{d} \cdot \vec{n})] - \vec{n} \sqrt{1 - \eta^2(1-(\vec{d} \cdot \vec{n})^2)}$

#### Some of the LIght Reflects
- Glass is not only transparent- you can see your reflection in a window
- At a dielectric boundary the light split:
    - a fraction $R$ reflects along $\vec{r} = \vec{d} - 2(\vec{d} \cdot \vec{n})\vec{n}$
    - The rest $1 - R$ refracts along $\vec{t}$
- $R$ depends on the viewing angle
- This is the Fresnel effect

#### Schlick's Approximation
- $R(\theta) = R_0 + (1 - R_0)(1-\cos\theta)^5$
    - $R_0 = ((n_t - 1) / (n_t + 1))^2$
- Leaving the medium: use the air side angle $\varphi$

#### Which Way Does It Bend?
- Into a denser material (air -> glass), the ray bends towards the normal
- Back out, it bends away from the normal
- Through a flat pane, the two bends cancel each other out

#### Total Internal Reflection
- TIR: light meeting a boundary into a less dense menium beyond the critical angle is not transmitted at all - 100% of it reflects
- $\eta^2(1 - \cos^2\theta) > 1 \implies \sin \varphi > 1 \implies $ no such $\varphi$

### Material Struct
```rust
struct Material {
    k_d: Vec3,
    k_s: Vec3,
    p:   f32,
    k_m: Vec3,
    k_t: Vec3,
    ior: f32,
}
```

### Things That Can Go Wrong
- Nudging the refracted ray along $\vec{n}$: it starts back inside the medium it just left: speckles and black holes
- Forgetting to **flip the normal** when exiting - the ray bends the wrong way
- Using $\eta = n_t/n$ instead of $\eta = n / n_t$
- Check for negatives in square root
- Depth limit too low
- Un-normalized $\vec{d}$
- Shadow rays stop at glass, so glass casts a solid shadow

