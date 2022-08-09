import csv
import sys
from dataProcess.DataProcessor import DataProcessor
from anytree import Node, RenderTree
import pickle
if len(sys.argv) != 3:
    print('usage: python3 main.py <input_path> <output_path>')
    sys.exit(0)
input_csv = sys.argv[1]
vec = ''
static = DataProcessor()
with open(input_csv) as file:
    csv_reader = csv.reader(file)
    for row in csv_reader:
        while '' in row:
            row.remove('')
        if len(row) == 1:
            vec = row[0]
            continue
        static.process_line(vec, row)

for root in static.trees:
    print(RenderTree(root))

with open(sys.argv[2], 'wb') as file:
    pickle.dump(static, file, pickle.HIGHEST_PROTOCOL)