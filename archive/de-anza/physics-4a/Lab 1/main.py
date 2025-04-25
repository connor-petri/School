import json
from resources.stat_tools import rand_uncertainty, average

def load_json_data(filename: str = "data.json"):
    with open("./" + filename) as file:
        return json.load(file)


if __name__ == "__main__":
    data = load_json_data()

    distances_cm = data["ruler-distances"]
    masses_g = data["scale-masses"]
    diameters_cm = data["caliper-diameters"]

    ruler_ru = round(rand_uncertainty(distances_cm), 3)
    masses_ru = round(rand_uncertainty(masses_g, 3))
    diameters_ru = round(rand_uncertainty(diameters_cm), 3)
