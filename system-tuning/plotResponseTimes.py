# -*- coding: utf-8 -*-

# defining the libraries
import numpy as np
import matplotlib.pyplot as plt
import csv

responseTimes = []

files = []
paths = 'responsetimes_reliable.txt'

f = open(paths, 'r')
lines = f.readlines()

for line in lines:
   files.append(line.replace('\n', ''))
'''Reliable'''

#ton=30
file_rtlt1 = files[0]
file_rtlt2 = files[1]
file_rtlt3 = files[2]
# file_rtlt1000 = 'results/reliable_ton30_lt1000.csv'

#ton=60
file_ton60_rtlt1 = files[3]
file_ton60_rtlt2 = files[4]
file_ton60_rtlt3 = files[5]
# file_rtlt1000 = 'results/reliablet_ton60_lt1000.csv'

'''Unreliable'''

# file_rtlt1 = 'results/unreliable_ton30_lt1.csv'
# file_rtlt2 = 'results/unreliable_ton30_lt2.csv'
# file_rtlt3 = 'results/unreliable_ton30_lt3.csv'
file_rtlt1000 = 'results/unreliable_ton30_lt1000.csv'

#ton=60
# file_ton60_rtlt1 = 'results/unreliable_ton60_lt1.csv'
# file_ton60_rtlt2 = 'results/unreliable_ton60_lt2.csv'
# file_ton60_rtlt3 = 'results/unreliable_ton60_lt.csv'

def getResponseTimes(inputFile):
    responseTimes = []
    with open (inputFile, 'r') as file:
        reader = csv.DictReader(file)
        for row in reader:
            resptime = float(row['ResponseTime'])
            responseTimes.append(resptime)
        file.close()
    return responseTimes


# normal distribution

# sort the data in ascending order
rt_lt1 = getResponseTimes(file_rtlt1)
rt_lt2 = getResponseTimes(file_rtlt2)
rt_lt3 = getResponseTimes(file_rtlt3)

rt_ton60_lt1 = getResponseTimes(file_ton60_rtlt1)
rt_ton60_lt2 = getResponseTimes(file_ton60_rtlt2)
rt_ton60_lt3 = getResponseTimes(file_ton60_rtlt3)


N_lt1 = len(rt_lt1)
N_lt2 = len(rt_lt2)
N_lt3 = len(rt_lt3)


N_ton60_lt1 = len(rt_ton60_lt1)
N_ton60_lt2 = len(rt_ton60_lt2)
N_ton60_lt3 = len(rt_ton60_lt3)


x_lt1 = np.sort(rt_lt1)
x_lt2 = np.sort(rt_lt2)
x_lt3 = np.sort(rt_lt3)



x_ton60_lt1 = np.sort(rt_ton60_lt1)
x_ton60_lt2 = np.sort(rt_ton60_lt2)
x_ton60_lt3 = np.sort(rt_ton60_lt3)

# get the cdf values of y
y_lt1 = np.arange(N_lt1) / float(N_lt1)
y_lt2 = np.arange(N_lt2) / float(N_lt2)
y_lt3 = np.arange(N_lt3) / float(N_lt3)

y_ton60_lt1 = np.arange(N_ton60_lt1) / float(N_ton60_lt1)
y_ton60_lt2 = np.arange(N_ton60_lt2) / float(N_ton60_lt2)
y_ton60_lt3 = np.arange(N_ton60_lt3) / float(N_ton60_lt3)


# y_lt1 = y_lt1.tolist()

# plotting
plt.xlabel('Response Time (sec)', fontsize=20)
plt.ylabel('Cumulative Density', fontsize=20)
plt.xticks(fontsize=20)
plt.yticks(fontsize=20)
plt.title('')

indices1 = [x for x in range(0, len(x_lt1), 100000)]
indices2 = [x for x in range(0, len(x_lt2), 100000)]
indices3 = [x for x in range(0, len(x_lt3), 100000)]
indices4 = [x for x in range(0, len(x_ton60_lt1), 100000)]
indices5 = [x for x in range(0, len(x_ton60_lt2), 100000)]
indices6 = [x for x in range(0, len(x_ton60_lt3), 100000)]


plt.rcParams["figure.figsize"] = [14, 12]

plt.plot(x_lt1, y_lt1, marker='D', markersize=8, color='red', label='$T_{ON}$=30, $T_{OFF}$=5, Lifetime=1', markevery=indices1)
plt.plot(x_lt2, y_lt2, marker='o', markersize=8, color='green', label='$T_{ON}n$=30, $T_{OFF}$=5, Lifetime=2', markevery=indices2)
plt.plot(x_lt3, y_lt3, marker='p', markersize=8, color='blue', label='$T_{ON}$=30, $T_{OFF}$=5, Lifetime=3', markevery=indices3)
# plt.plot([100], [0.1], color='brown', label = '$T_{ON}$=30, $T_{OFF}$=5, Lifetime=infinite')


plt.plot(x_ton60_lt1, y_ton60_lt1, marker='^', markersize=8, color='red', label='$T_{ON}$=60, $T_{OFF}$=5, Lifetime=1', markevery=indices4)
plt.plot(x_ton60_lt2, y_ton60_lt2, marker='x', markersize=8, color='green', label='$T_{ON}$=60, $T_{OFF}$=5, Lifetime=2', markevery=indices5)
plt.plot(x_ton60_lt3, y_ton60_lt3, marker='|', markersize=8, color='blue', label='$T_{ON}=6$0, $T_{OFF}$=5, Lifetime=3', markevery=indices6)
plt.legend(loc=4, prop={'size': 8})
plt.xlim([0,3.5])
