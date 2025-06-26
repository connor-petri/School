import json
from os import getcwd

if __name__ == "__main__":
    with open(getcwd() + "/Lab 3/data.json") as f:
        data = json.load(f)

    data["delta-r-cm"] = []
    for num in data["y-final-cm"]:
        data["delta-r-cm"].append(round(num - data["y-initial-cm"], 3))

    
    with open(getcwd() + "/Lab 3/data.json", "w") as f:
        json.dump(data, f, indent=4)

    