# Shading
---

## Two Kinds of Surfaces
- Diffuse
    - Lambertian reflection
    - Scatters light evenly in all directions
- Specular
    - Blinn-Phong reflection model
    - Reflects light in a specific direction, creating highlights

### What to look for
- **Diffuse** (matte) surface
    - brightness changes **smoothly** - brightness depends on the angle between the light and the surface normal
    - Look the *same from every viewpoint*
    - show the color of the surface
- **Specular** (shiny) surface
    - A small, bright spot - a blurry reflection of the light itself
    - It moves when you move the viewpoint
    - it shows the color of the light, not of the surface

## Lambertian
### The Setup at a Shade Point
- Everything happens at one point $p$ with three *unit* vectors:
    - $\mathbf{n}$: the surface normal at $p$
    - $\mathbf{l}$: the direction to the light source
    - $\mathbf{v}$: the direction to the viewer (camera)
    - All vectors are assumed to be normalized (unit length)
- All threee point **away from the surface**
- $\theta$ is the angle between the surface normal $\mathbf{n}$ and the light direction $\mathbf{l}$
- Unit length matters: it turns every dot product into a cosine
    - $\mathbf{n} \cdot \mathbf{l} = \cos\theta$