import json


if __name__ == "__main__":
    with open("data.json") as f:
        data = json.load(f)

    
    with open("data.json") as f:
        json.dump(data, f, indent=4)