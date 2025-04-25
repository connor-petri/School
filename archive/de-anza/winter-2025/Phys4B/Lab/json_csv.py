# This is a tool to convert "key": [list] JSON format to CSV format.
# This is only designed to be used for this lab and will not work for all JSON formats.
import json
import csv
import sys


if __name__ == "__main__":
    try:
        json_path: str = sys.argv[1]

        if json_path == "-h" or json_path == "--help":
            print("Usage: python json_csv.py <json_file_path> <csv_file_path>")
            print("Json format must be a dictionary with lists as values.")
            print('i.e {"key1": [1, 2, 3], "key2": ["a", "b", "c"]}')
            exit(0)

        csv_path: str = sys.argv[2]

    except IndexError:
        print("Please provide a JSON file path and a CSV file path.")
        exit(1)

    if not json_path.endswith(".json"):
        print("Invalid file type. Please provide a JSON file.")
        exit(1)
        
    if not csv_path.endswith(".csv"):
        print("Invalid file type. Please provide a CSV file.")
        exit(1)

    try:
        with open(json_path, "r") as f:
            data = json.load(f)
    except FileNotFoundError:
        print("File not found. Please provide a valid file.")
        exit(1)
    

    with open("./data.csv", "w") as f:
        writer = csv.writer(f)
        writer.writerow(data.keys())

        
        max_length = 0
        for key in data.keys():
            if len(data[key]) > max_length:
                max_length = len(data[key])

        
        for i in range(max_length):
            row = []
            for key in data.keys():
                try:
                    row.append(data[key][i])
                except IndexError:
                    row.append("")
            writer.writerow(row)
                

