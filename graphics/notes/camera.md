# The 3D Camera

---

The **Camera** has the following components:
- Position/Eye of the camera
- FOV
- Axis of observation / gaze
- View plane (plane where 2d display image is projected on to)
- Near and Far clipping planes (adjustable distance from camera)
    - Camera cannot see outside of the clipping planes



## Film: Recording the Image
- In a real camera, the image is recorded on **film** at the film plane
- The view plane plays exactly that role - it is our **digital film**
- The Film has an **resolution**, a width x height grid of **pixels** carried by the camera
    - Each pixel stores 1 rgb color
- Rendering is the process of computing a color for *every pixel* on the film
- When the frame is done, the film is **saved / archived** to disk

### Frame Buffer
- An array holding the color data for each pixel on a screen

### FOV and Aspect Ratio
- The **Vertical field of view** $\theta$ is the angle othe camera sees top to bottom
- $d$ is the distance from the eye to the view plane along the view direction
- Siilar triangles give the view plane *height*: $2d\tan(\frac{\theta}{2})$
- There is **no second angle for width**, aspect ratio determines that
    - width = aspect $\times$ height

## Aiming the Camera: Eye and Target
- Position: the **eye** point - where the camera sits in the world
- Orientation: a **target point** - where the camera is looking

## Local Camera Coordinates
- $u,v,w$ instead of $x,y,z$
    - $-w$ is in the direction the camera is facing

### The Camera Basis
- From eye/target/up, build the cameras own frame of reference
- $w$ = (eye - target) / ||eye - target|| - points backwards from target
- $u$ = (up x w) / ||up x w||
- $v = w \times u$
- $u,v,w$ are an orthonormal basis

#### Needed to Construct a Camera
- FOV
- Target Point
- Aspect Ratio
- Position/Eye