# Automating the Evaluation of Interoperability Effectiveness in Heterogeneous IoT Systems

This repository contains the necessary code for the automated synthesis and composition of queueing models for evaluating the interoperability effectiveness of heterogeneous IoT systems.

## Getting Started
This repository contains the following directories:
`perfmp`: contains the code needed for generating end-to-end *Queueing Models* for representing and evaluating interconnections of clients using heterogeneous protocols.
`system_tuning`: contains scripts for tuning the system by analyzing the Cumulative Density Function (CDF) of response times distributions and average success rates.

### Installation Requirements
We assume that the host machine is running [Java 17](https://www.oracle.com/fr/java/technologies/downloads/) and using [Eclipse](https://www.eclipse.org/downloads/) for running the artifact. You also need to download the [JavaFX SDK 17](https://gluonhq.com/products/javafx/) and the following JAR files:
- [opencsv-3.8.jar](https://mvnrepository.com/artifact/com.opencsv/opencsv/3.8)
- [json-simple-1.1.1.jar](https://code.google.com/archive/p/json-simple/downloads)
- [json-20210307.jar](https://mvnrepository.com/artifact/org.json/json/20210307)

Once you have downloaded the above JAR files, you can include them use Eclipse to include them into your Classpath as follows:
- In Eclipse, right click on the project name (perfmp)
- Click on *Properties*
- Select *Java Build Path* from the left menu
- Select *Classpath*, then click on *Add External JARs* and select the JARs that you need to add

You should then add JavaFX as the JRE System Library. You can do that in the same window by click on *Add Library...*, then selecting *JRE System Library*, and finally selecting the *javafx-jre-17* as an *Alternate JRE*.
After doing the above steps, you should be able to run the artifact.

To run the scripts used in [System Tuning](#system-tuning), you need to install [Python3](https://www.python.org/downloads/), [numpy](https://numpy.org/), and [matplotlib](https://matplotlib.org/).

### Using the Artifact
We rely on the [JINQS](https://wp.doc.ic.ac.uk/ajf/jinqs/) library for the evaluation of Performance Modelling Patterns (PerfMP). This is done by composing and simulating end-to-end Queueing Networks using JINQS for modelling and evaluating the performance of middleware protocols. 
For this purpose, the *icsa2024* package contains two java files:
- `Synthesizer.java`: this file contains the main class for generating end-to-end Queueing Models for representing and evaluating interconnections of clients using heterogeneous protocols. 
- `Params.java`: this file contains the parameters used by `Synthesizer.java`. These parameters can be changed according to the scenario to be evaluated. In particular, the following parameters can be set:
	- `PATTERN`: this variable is used to select interaction pattern. You can select one of four possible patterns to evaluate:
		- `R2R-REQ`: represents a request from a reliable publisher to a reliable subscriber (e.g., both using MQTT)
		- `R2R-RES`: represents a response from a reliable subscriber to a reliable publisher
		- `U2R-REQ`: represents a request from an unreliable publisher (e.g., using MQTT-SN) to a reliable subscriber.
		- `U2R-RES`: represents a response from a reliable subscriber to an unreliable publisher.
	- `REPONSE_TIME_FILE`: this variable is used to determine the file in which to save the response time values of messages sent during the simulation.
	- `SUCCESS_RATES_FILE`: this variable is used to determine the file in which to save the success rates of messages sent during the simulation.
	- `LIFETIME`: this variable is used to determine the lifetime of messages (in milliseconds)
	- `ON_OVRL_DRIVER_RATE`: this variable is used to determine the time (in seconds) the publisher is available
	- `OFF_OVRL_DRIVER_RATE`: this variable is used to determine the time (in seconds) the publisher is unavailable
	- `SIM_DURATION`: this variable is used to determine the duration of the simulation (in simulation steps)

After setting the above parameters, you can compose a queueing network and run the simulation by running the `Synthesizer.java` file. Note that the simulation may take a few minutes to complete. If the simulation is taking too long, you can change the duration of the simulation using the `SIM_DURATION` variable in the `Params.java` file.
After the simulation is done, two files will be generated: one containing the response times of messages, and one containing the success rates. These files can be used for tuning the system according to the use case requirements.

### System Tuning
As an example, we provide a script for plotting the cumulative density function (CDF) plot for the scenarios presented in [1]. For this purpose, we include in the `data` directory the files containing response times for the simulations of a reliable publisher to a reliable subscriber in different settings (lifetime from 1s to 3s, T_ON=30s, and T_ON=60s). 
To generate the CDF plot, you should first include in the `responsetimes_reliable.txt` file the paths to the files in the `data` directory.
Then, you can run the plotResponseTimes.py script using any Python IDE, or by running the command: `python plotResponseTimes.py`

[1] G. Bouloukakis, N. Georgantas, A. Kattepur, H. Hajj Hassan, V. Issarny. Automating the Evaluation of Interoperability Effectiveness in Heterogeneous IoT Systems.  21st IEEE International Conference on Software Architecture (ICSA 2024).

