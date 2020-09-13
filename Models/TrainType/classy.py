import pandas
from sklearn import svm
import os
import csv
from collections import defaultdict
import time
import requests
import datetime

ar = 0
path = '../TrainType/'
print("Counting all .csv files in: " + path)
x = 0
val = 0
vl = 0
state = 0

svc = None


def trainType():
    for files in os.listdir(path):

        if files.endswith('7.csv') and files.startswith("train"):
            csv_file = files
            data = pandas.read_csv(csv_file, sep=',', na_values=".")

            train = list(zip(data['typing_speed'], data["max_text"], data["touch_count"], data["shake_count"]))
            train_labels = list(data['arousal'])
            svc = svm.SVC(kernel='linear', probability=True)
            svc = svc.fit(train, train_labels)
            print(csv_file)
    print("Training over")

dict = defaultdict(int) # rollNo : lastPrediction

def typingModelOut(typingSpeed, maxText, touchCount, shakeCount, roll):
    stime = time.time()

    # Positive valence=1
    # Negative valence=0

    # State   3--> Low arousal - Negative valence
    # State   2--> Low arousal - Positive valence
    # State   4--> High arousal- Negative valence
    # State   1--> High arousal- Positive velance

    # for index in range(len(test)):

    statex = ""
    state = 0
    predicted = svc.predict([typingSpeed, maxText, touchCount, shakeCount])[0]
    val = dict[roll]

    vl = 1 if val == predicted else -1
    dict[roll] = predicted
    print(val)

    statex = str(predicted) + "" + str(vl)
    print(statex)
    if (statex == '0-1'):
        state = 3
    elif (statex == '01'):
        state = 2
    elif (statex == '1-1'):
        state = 4
    elif (statex == '11'):
        state = 1

    phpdata = {"roll": roll, "state": state, "arousal": predicted, "val": vl,
               "ts": datetime.datetime.now()}
    rq = requests.post('http://localhost/AndroidImageUpload/pyphp.php', params=phpdata)
    print(rq.url)
    print(roll)
    print(time.time() - stime)

    return phpdata
