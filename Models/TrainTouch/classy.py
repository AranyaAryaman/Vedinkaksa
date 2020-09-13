import pandas
from sklearn import svm
import os
import csv
from collections import defaultdict
import time
import requests
import datetime

ar = 0
path = '../TrainTouch/'
print("Counting all .csv files in: " + path)
x = 0
val = 0
vl = 0
state = 0

svc = None


def trainTouch():
    for files in os.listdir(path):

        if files.endswith('5.csv') and files.startswith("train"):
            csv_file = files
            data = pandas.read_csv(csv_file, sep=',', na_values=".")

            train = list(zip(data['no_of_events'], data['avg_pressure']))
            train_labels = list(data['label'])
            svc = svm.SVC(kernel='linear', probability=True)
            svc = svc.fit(train, train_labels)
            print(csv_file)
    print("Training over")


def touchModelOut(noOfevents, touchPressure, roll):
    stime = time.time()

    # Positive valence=1
    # Negative valence=0

    # State   3--> Low arousal - Negative valence
    # State   2--> Low arousal - Positive valence
    # State   4--> High arousal- Negative valence
    # State   1--> High arousal- Positive valence

    # statex = ""
    # state = 0
    predictedState = svc.predict([noOfevents, touchPressure])[0]

    phpdata = {"roll": roll, "state": predictedState,
               "ts": datetime.datetime.now()}
    rq = requests.post('http://localhost/AndroidImageUpload/pyphp.php', params=phpdata)
    print(rq.url)
    print(roll)
    print(time.time() - stime)

    return phpdata
