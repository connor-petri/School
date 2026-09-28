# Shadows 
---
## What is a Shadow
- A point is lit by a light if there is a clear line of sight between them
- It is in shadow if some object sits in between
    - The light is still there it just cannot reach $p$
- Shading is local: it only knows $P$, $\vec{n}$, and where the light is
- So we have to ask the scene: "is there anything between $p$ and the light?"

### The Shadow Ray
- Build a ray from $p$ toward the light:
    - $p + t\vec{l}$ where $\vec{l}$ is the unit vector toward the light
- Intersect it with the scene, just like a viewing ray
    - any hit before the light $\implies$ $p$ is in shadow, skip it.
    - no hit $\implies$ the light is visible: shade as usual
- Same intersection code
- A shadow ray is a visibility query, not a color query.

#### Which hit counts?
- A viewing ray wants the nearest hit with $t > 0$
- A shadow ray  wants to know if there is any hit with
    - $\varepsilon < t < t_{light}$
- $t_{light}$ is the distance to the light
    - an object beyond the light cannot block it, do not count it
    - with unit $\vec{l}$, t is a distance, so this comparison works

#### Shadow Acne and the $\varepsilon$ fix
- The computed hit point is never exactly on the surface, floating point puts it a hair off
- $\varepsilon$ is tiny, like $10^{-3}$.
- Ignore hits with $t < \varepsilon$

### Shadows with Many Lights
- Every light gets its own shadow ray - one per light, per shad point
- Inside the shading loop, the shadow test comes first
- The ambient term is added regardless, it is the stand-in for bounced light
- Cost: with N lights, every hit now costs 1 + N ray casts