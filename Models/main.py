# Importing flask module in the project is mandatory
# An object of Flask class is our WSGI application.
from flask import Flask, request
from sklearn import svm
from sklearn.ensemble import RandomForestClassifier
import numpy as np
import pandas as pd
from sklearn.datasets import load_digits
import os
from collections import defaultdict
import time
import requests
import datetime

ar = 0
pathType = './TrainType/'
pathTouch = './TrainTouch/'
x = 0
val = 0
vl = 0
state = 0

svcType = None
svcTouch = None
involvementModel_RF = None

def trainType():
    global svcType
    for files in os.listdir(pathType):

        if files.endswith('7.csv') and files.startswith("train"):
            csv_file = pathType + files
            data = pd.read_csv(csv_file, sep=',', na_values=".")

            train = list(zip(data['typing_speed'], data["max_text"], data["touch_count"], data["shake_count"]))
            train_labels = list(data['arousal'])
            svcType = svm.SVC(kernel='linear', probability=True)
            svcType = svcType.fit(train, train_labels)
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
    predicted = svcType.predict([(float(typingSpeed), float(maxText), float(touchCount), float(shakeCount))])[0]
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
    # rq = requests.post('http://localhost/AndroidImageUpload/pyphp.php', params=phpdata)
    # print(rq.url)
    print(roll)
    print(time.time() - stime)

    return state


def trainTouch():
    global svcTouch
    for files in os.listdir(pathTouch):

        if files.endswith('5.csv') and files.startswith("train"):
            csv_file = pathTouch + files
            print(csv_file)

            data = pd.read_csv(csv_file, sep=',', na_values=".")

            train = list(zip(data['no_of_events'], data['avg_pressure']))
            train_labels = list(data['label'])
            svcTouch = svm.SVC(kernel='linear', probability=True)
            svcTouch = svcTouch.fit(train, train_labels)
            print(train, train_labels)
            print(csv_file)
    print("Training over")


def touchModelOut(noOfevents, touchPressure, roll):
    stime = time.time()

    # Positive valence=1
    # Negative valence=0

    # State   3--> Low arousal - Negative valence
    # State   2--> Low arousal - Positive valence
    # State   4--> High arousal- Negative valence
    # State   1--> High arousal- Positive valence`

    # statex = ""
    # state = 0
    # predictedState = svcTouch.predict(list(zip([noOfevents], [touchPressure])))[0]
    predictedState = svcTouch.predict([(float(noOfevents), float(touchPressure))])[0]
    print(predictedState)

    phpdata = {"roll": roll, "state": predictedState,
               "ts": datetime.datetime.now()}
    print(phpdata)
    # rq = requests.post('http://localhost/AndroidImageUpload/pyphp.php', params=phpdata)
    # print(rq.url)
    print(roll)
    print(time.time() - stime)

    return predictedState

def involvementModelInitialization():
    global involvementModel_RF
    for x in range(1,35):
        data_train  =  pd.read_csv('./Involvement/Train/WindowSize7/Train_7_'+str(x)+'.csv')
        data_test  =  pd.read_csv('./Involvement/Train/WindowSize7/Test_7_'+str(x)+'.csv')
        X_train, X_test, y_train, y_test = data_train[['mem','temp','acc','rot']], data_test[['mem','temp','acc','rot']], \
                                            data_train['class'],data_test['class']
        involvementModel_RF = RandomForestClassifier(n_estimators=40)
        involvementModel_RF.fit(X_train, y_train) 
        print('Iteration '+str(x)+' completed') 

def involvementModelOut(batteryTemp, avgMemory, acceleration, rotationSpeed, roll):
    predicted = involvementModel_RF.predict([(float(batteryTemp), float(avgMemory), float(acceleration), float(rotationSpeed))])[0]
    print(predicted)

    phpdata = {"roll": roll, "state": predicted,
               "ts": datetime.datetime.now()}
    print(phpdata)
    rq = requests.post('http://localhost/AndroidImageUpload/pyphp.php', params=phpdata)
    # print(rq.url)
    print(roll)
    return predicted

# Flask constructor takes the name of
# current module (__name__) as argument.
app = Flask(__name__)

# The route() function of the Flask class is a decorator,
# which tells the application which URL should call
# the associated function.
@app.route('/typingModel')
# ‘/’ URL is bound with hello_world() function.
def typingModel():
    roll = request.args.get("roll", None)
    typingSpeed = request.args.get("typingSpeed", None)
    maxText = request.args.get("maxText", None)
    touchCount = request.args.get("touchCount", None)
    shakeCount = request.args.get("shakeCount", None)

    result = typingModelOut(typingSpeed, maxText, touchCount, shakeCount, roll)
    return str(result)


@app.route('/touchModel')
# ‘/’ URL is bound with hello_world() function.
def touchModel():
    roll = request.args.get("roll", None)
    noOfevents = request.args.get("noOfevents", None)
    touchPressure = request.args.get("touchPressure", None)

    result = touchModelOut(noOfevents, touchPressure, roll)
    return str(result)

@app.route('/involvementModel')
# http://localhost:5000/involvementModel?roll=160101071&batteryTemp=30&avgMemory=500&acceleration=62&rotationSpeed=23
def involvementmodel():
    roll = request.args.get("roll", None)
    batteryTemp = request.args.get("batteryTemp", None)
    avgMemory = request.args.get("avgMemory", None)
    acceleration = request.args.get("acceleration", None)
    rotationSpeed = request.args.get("rotationSpeed", None)
    result = involvementModelOut(batteryTemp, avgMemory, acceleration, rotationSpeed, roll)
    return str(result)

# main driver function
if __name__ == '__main__':
    trainTouch()
    trainType()
    involvementModelInitialization()
    app.run(host='0.0.0.0')
