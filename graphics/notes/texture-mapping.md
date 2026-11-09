# Texture Mapping
---

### What a Texture Does?
- Shading needs surface properties at the hit point $p$, albedo $k_d$, specular, normal, etc
- A texture makes a property a function of position on the surface
- Detail costs texels, not triangles
- Anything can be textured
- Two ingredients: a map from the surface to texture space $(u,v) = m(p)$ and the texture $T(u,v)$ itself

### Two Types of Textures
- **Image Texture**: T is a 2D array of texels: a photo, image, etc.
- **Procedural Texture:** T is a funciton. Code computes the color given u,v or p itself

### UVs Live on the Vertices
- A mesh stores u,v per vertex beside position and normal.
- At a hit, interpolate them with the barycentrics - exactly like the normals
- The texture is pinned at the vertices
- Analytic shapes compute u,v directly from $p$, no vertices needed
- Convention: ray, glTF, nd image files put v=0 at the top  row