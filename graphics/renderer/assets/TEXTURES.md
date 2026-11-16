# Textures

All maps are equirectangular (2:1) 4096x2048 PNG, downscaled from the 8k JPEG downloads, from Solar System Scope
(https://www.solarsystemscope.com/textures/), licensed CC BY 4.0.
Attribution: "Solar System Scope".

| File                      | Use                | Layout                                                       |
|---------------------------|--------------------|--------------------------------------------------------------|
| `earth_daymap_4k.png`     | planet albedo      | north pole at row 0, 180 W at left edge, 0 longitude at center |
| `stars_milky_way_4k.png`  | sky / environment  | galactic coordinates: Milky Way band runs horizontally through the middle |
| `moon_4k.png`             | moon albedo        | near side (0 longitude) at center |

Sampling: `u = 0.5 + atan2(-n.z, n.x) / 2pi`, `v = 0.5 - asin(n.y) / pi`
(u wraps, v clamps). The star map is authored for viewing from inside;
the Earth map for viewing from outside. In reality the galactic plane is
tilted about 60 degrees from Earth's equator, so rotate the lookup
direction if you want the Milky Way at a realistic angle in surface shots.
