# -*- coding: utf-8 -*-

import csv
import matplotlib.pyplot as plt

'''Reliable'''

sr_ton30_request_file = 'success_rate_file_path'
sr_ton30_response_file = 'success_rate_file_path'
sr_ton60_request_file = 'success_rate_file_path'
sr_ton60_response_file = 'success_rate_file_path'


'''Unreliable'''
'''
sr_ton30_request_file = 'success_rate_file_path'
sr_ton30_response_file = 'success_rate_file_path'
sr_ton60_request_file = 'success_rate_file_path'
sr_ton60_response_file = 'success_rate_file_path'
'''
srton30request = []
srton30response = []
srton60request = []
srton60response = []

with open(sr_ton30_request_file) as file:
    reader = csv.reader(file)
    next(reader, None)
    for row in reader:
        # srton30request.append(float(row[3]))
        srton30request.append(round(float(row[3]), 2))
        
with open(sr_ton30_response_file) as file:
    reader = csv.reader(file)
    next(reader, None)
    for row in reader:
        # srton30response.append(float(row[3]))    
        srton30response.append(round(float(row[3]), 2))
             
with open(sr_ton60_request_file) as file:
    reader = csv.reader(file)
    next(reader, None)
    for row in reader:
        # srton60request.append(float(row[3]))   
        srton60request.append(round(float(row[3]), 2))
        
        
with open(sr_ton60_response_file) as file:
    reader = csv.reader(file)
    next(reader, None)
    for row in reader:
        # srton60response.append(float(row[3]))  
        srton60response.append(round(float(row[3]), 2))

        
xlabels = ['1', '2', '3', 'infinte']
plt.plot(xlabels, srton30request, marker='+', markersize=10, color='black', label='$T_{on}$=30, $T_{off}$=5, request')
plt.plot(xlabels, srton30response, marker='.', markersize=10, color='brown', label='$T_{on}$=30, $T_{off}$=5, response')
plt.plot(xlabels, srton60request, marker='p', markersize=10, color='red', label='$T_{on}$=60, $T_{off}$=5, request')
plt.plot(xlabels, srton60response, marker='D', markersize=10, color='orange', label='$T_{on}$=60, $T_{off}$=5, response')
plt.legend(loc='lower right', fontsize=16)
plt.xticks(fontsize=18)
plt.yticks(fontsize=18)
plt.xlabel('Lifetime', fontsize=18)
plt.ylabel('Success Rate', fontsize=18)
plt.ylim([0.7,1.01])

plt.legend(loc=4, prop={'size': 12})
plt.rcParams["figure.figsize"] = [10, 4]
plt.show()
