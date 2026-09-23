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
- Lambert's Cosine Law states the closer the cosine is to 1, the brighter the light at that point will be

### Lambertian Shading
- An ideal diffuse surface scatters what it receives equally in all directions
- So its brightness is just the cosine law scaled
    - $L = k_d I\max(0, \vec{n} \cdot \vec{l})$
        - $L$ the pixel color
        - $k_d$ - the **diffuse coefficient**: the surface color
        - $I$ - the intensity
        - $\vec{l}$ - light vector
        - $\vec{n}$ - the surface normal
- $\vec{v}$ is missing, meaning this is view-independent

### Color - Do It 3 Times
- $k_d$ and $\vec{I}$ are both RGB triples; $L$ is an RGB color
- The equation runs **per channel** - multiply component-wise
    - $L = k_d I\max(0, \vec{n} \cdot \vec{l})$
- The geometry term $max(0, \vec{n} \cdot \vec{l})$ is shared by all three components
- Finally, clamp $L$ to $[0,1]$ - RGB values should be $[0, 1.0]$

### More Than One Light
- Light is **additive** two lamps deliver the sum of what each delivers alone
- Each light has it's own direction $\vec{l}$ and intesity $I$
- Shade once per light and **add up** the results
- $\vec{n}$ and $k_d$ belong to the surface, they do not change inside the sum
- The clamp now really matters:
    - a light *behind* the suface bust contribute 0, not cancel the others
- $L = \sum k_d I\max(0, \vec{n} \cdot \vec{l})$

### Ambient Light
- With only direct light, anything facing away from every light is pure black
- Real rooms are not like that - light bounces off walls and fills in shadows
- Tracing those bounces is expensive, so we fake it
    - $L = k_a I_a + \sum k_d I\max(0, \vec{n} \cdot \vec{l})$
        - $k_a$ ambient coefficient, usually the surface color ($k_a = k_d$)
        - $I_a$ - a small constant "light from everywhere"
- It is added *once* not once per light and it is flat so keep it small

### The Shading Algorithm
- For each pixel:
    1. Generate the viewing ray and find the **nearest hit**
    2. No hit -> the pixel gets the *bbackground* color. Done
    3. From the hit, get the point $p$, the unit normal $\hat{n}$, and the material $k_d$
    4. Start the color with the **ambient term** $L = k_a I_a$
    5. For *each light* in the scene
        - compute the unit vector $\hat{l}$ from $p$ towards the target
        - compute $\max{0, \hat{n} \cdot \hat{l}}$
        - **Add** $k_d I\max(0, \hat{n} \cdot \hat{l})$ to $L$
    6. Clamp $L$ to $[0, 1]$ and store it in the pixel

### Limitations of Lambert
- Independent of camera position
- No lighlights, everything looks matte


---

## Blinn-Phoong
### The Half Vector
- $\vec{h}$ is the unit vector halfway betweel $\vec{l}$ and $\vec{v}$
- Add two unit vectors and you get their **bisector**, then normalize
- The key fact: a perfect mirror sends the light to the eye exactly when $\vec{h} = \vec{n}$
- So the angle $\alpha$ between $\vec{n}$ and $\vec{h}$ measures how far we are from that. Small $\alpha$ means brighter
- Once more, the cosine is a dot product: $\cos \alpha = \vec{n} \cdot \vec{h}$
- $\vec{h}$ changes as the viewing angle changes, creating highlights

### Blinn-Phoong Shading
- Keep the diffuse term, *add* a specular term
    - $L = k_d I \max(0, \vec{n} \cdot \vec{l}) + k_s I_s max(0, \vec{n} \cdot \vec{h})^p$
        - $p$ is called the **Phoong Exponent**, a constant that determines how wider the highlights are. Smaller $p$ -> wider highlight

### Algorithm with highlights
- Same as above but add specular term