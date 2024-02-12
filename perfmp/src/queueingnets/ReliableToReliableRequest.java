package queueingnets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import examples.AnalyticalModelsONOFF;
import examples.OnOffRQN;
import extensions.LftLsesBranch;
import extensions.SinkLftLses;
import extensions.SinkOvrlNet;
import icsa2024.Params;
import network.Delay;
import network.Link;
import network.Network;
import network.Node;
import network.QueueingNode;
import network.Source;
import tools.Deterministic;
import tools.Exp;
import tools.Sim;


public class ReliableToReliableRequest extends Sim {
	
	public static double duration = Params.SIM_DURATION;
	public static Exp processingServiceTime;
	public static Exp transmissionServiceTime;
	public static double averageLifetime = 0;
	
	public static double averageOnEndToEndLink1 = 0;
	public static double averageOffEndToEndLink1 = 0;
	public static double averageOnEndToEndLink2 = 0;
	public static double averageOffEndToEndLink2 = 0;

	
	public static double noOfCust = 0;
	public static double avgTimeinQueue = 0;

	
	// Example termination function
	public boolean stop() {
		return now() > duration;
	}

	// Here, the constructor starts the simulation.
	public ReliableToReliableRequest(double d) {

		duration = d;

		Network.initialise();

		Deterministic lifetime = new Deterministic(Params.LIFETIME);
		Source src = new Source("Source", new Exp(Params.SRC_RATE), lifetime, "lifetime");		
		
		processingServiceTime = new Exp(Params.PR_MSG_RATE);
		Delay prMsg = new Delay(processingServiceTime);
		Delay trMsg_app = new Delay(new Exp(Params.TR_APP_RATE));
		Delay trMsg_broker = new Delay(new Exp(Params.TR_BROKER_RATE));
		Delay trMsg_mediator = new Delay(new Exp(Params.TR_MEDIATOR_RATE));
		
		
		Exp ONOvrlDriver = new Exp(Params.ON_OVRL_DRIVER_RATE);
		Exp OFFOvrlDriver = new Exp(Params.OFF_OVRL_DRIVER_RATE);

		//Driver
		OnOffRQN driver_app = new OnOffRQN("DRIVER-APP", prMsg, 1, ONOvrlDriver, OFFOvrlDriver, duration);
		QueueingNode driver_mdw = new QueueingNode("DRIVER-MDW", trMsg_app, 1);
		
		//Broker
		QueueingNode broker_in = new QueueingNode("BROKER-IN", prMsg, 1);
		QueueingNode broker_out = new QueueingNode("BROKER-OUT", trMsg_broker, 1);
		
		//Mediator
		QueueingNode mediator_in = new QueueingNode("MEDIATOR-IN", prMsg, 1);
		QueueingNode mediator_out = new QueueingNode("MEDIATOR_OUT", trMsg_mediator, 1);
		
		//Service
		QueueingNode service_mdw = new QueueingNode("SERVICE-MDW", prMsg, 1);
		QueueingNode service_app = new QueueingNode("SERVICE-APP", prMsg, 1);
		
		
		SinkLftLses sinkDriverApp = new SinkLftLses("SINK-DRIVER-APP");		
		SinkLftLses sinkBrokerIn = new SinkLftLses("SINK-BROKER-IN");
		SinkLftLses sinkMediatorIn = new SinkLftLses("SINK-MEDIATOR-IN");
		SinkOvrlNet sinkServiceEnd = new SinkOvrlNet("SINK-SERVICE-APP");
	
		LftLsesBranch branchDriverApp = new LftLsesBranch(new Node[] {sinkDriverApp, driver_mdw});
		LftLsesBranch branchBrokerIn = new LftLsesBranch(new Node[] {sinkBrokerIn, broker_out});
		LftLsesBranch branchMediatorIn = new LftLsesBranch(new Node[] {sinkMediatorIn, mediator_out});
		
		src.setLink(new Link(driver_app));
		driver_app.setLink(branchDriverApp);
		driver_mdw.setLink(new Link(broker_in));
		
		broker_in.setLink(branchBrokerIn);
		broker_out.setLink(new Link(mediator_in));
		
		mediator_in.setLink(branchMediatorIn);
		mediator_out.setLink(new Link(service_mdw));
		
		service_mdw.setLink(new Link(service_app));
		service_app.setLink(new Link(sinkServiceEnd));

		simulate();
		
		averageOnEndToEndLink1 = ONOvrlDriver.average();
		averageOffEndToEndLink1 = OFFOvrlDriver.average();
		System.err.println("ON average End-to-end Driver : " + averageOnEndToEndLink1);
		System.err.println("OFF average End-to-end Driver : " + averageOffEndToEndLink1);
	
		System.out.println("Processing Avg Service Time: " + processingServiceTime.average());
				
		Network.logResult("CompletionsExpired", Network.completionsExpired);
		Network.logResult("ResponseTimeExpired", Network.responseTimeExpired.mean());
		
		Network.logResult("Completions", Network.completions);
		Network.logResult("ResponseTime", Network.responseTime.mean());
		
		System.out.println("SuccessRate: " + ((double)(Network.completions) / (double) (Network.completions + Network.completionsExpired)));
		
	}

	public void runSimulation(String responseTimesOutputFile, String successRateOutputFile) {
		
		
		ReliableToReliableRequest simulation = new ReliableToReliableRequest(duration);
		
		
		Network.displayResults( 0.01 ) ;
		Network.responseTime.saveResponseTimesCsv(responseTimesOutputFile);
		Network.logResults();
		
		simulation.writeResultsToCsv(successRateOutputFile, Params.LIFETIME, 1/Params.ON_OVRL_DRIVER_RATE, 1/Params.OFF_OVRL_DRIVER_RATE);
		
		try {

			AnalyticalModelsONOFF an = new AnalyticalModelsONOFF(Network.responseTime.mean(), Network.responseTimeON.mean(),
					Network.responseTimeOFF.mean(), Network.completions, Network.completionsON, Network.completionsOFF, duration,
					processingServiceTime.average(), Network.virtualServiceTime.mean(), Network.virtualServiceTime.variance(),
					Network.serviceTimeON.mean(), Network.serviceTimeOFF.mean(), 10.0, 20.0, averageOnEndToEndLink1, averageOffEndToEndLink1);

			String data = "Lsim: " + an.computeL() + " -- S-sim: " + processingServiceTime.average() + " -- avgON: " + averageOnEndToEndLink1
					+ " -- avgOFF: " + averageOffEndToEndLink1 + " -- Arrivals: " + Network.completions + " -- Duration: " + duration;

			String simulator = "R-sim (mean resp time in system): " + Network.responseTime.mean() + data +" -- R-model (mean resp time in system): " + an.computeR()
					+ " -- R-sim-queue (mean resp time in queue): " + avgTimeinQueue + " -- R-model-mosxolios (mean resp time in system): " + an.computeR_mosxolios();
			String model = " Q-sim (num of cust in queue): " + noOfCust + " -- Q-sim (num of cust in system): " + an.computeQsim();
			
			String R_paper = " -- R_paper: " + an.computeR_paper();
			
			File file = new File("results_onoff.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Data\n");
			bw.write(data);
			bw.write("\n");
			bw.write("Simulator\n");
			bw.write(simulator);
			bw.write("\n");
			bw.write("Model\n");
			bw.write(model);
			bw.write("\n");
			bw.write("R_paper");
			bw.write(R_paper);
			bw.write("\n");
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void writeResultsToCsv(String outputFile, double lifetime, double driverON, double driverOFF) {
		System.out.println("Writing Results to CSV....");
		File file = new File(outputFile);
		try {
        	if (!file.exists()) {
        		FileWriter output = new FileWriter(file);
                CSVWriter write = new CSVWriter(output);
                // Header column value
	            String[] header = { "Lifetime", "DriverON", "DriverOFF", "SuccessRate"};
	            write.writeNext(header);
	            write.close();
            }
		} catch (Exception e) {
            e.printStackTrace();
        }
        
		try {
        	FileWriter output = new FileWriter(file, true);
            CSVWriter write = new CSVWriter(output);
            double successRate = (double)(Network.completions) / (double) (Network.completions + Network.completionsExpired);
            String[] data = { Double.toString(lifetime), Double.toString(driverON), Double.toString(driverOFF), Double.toString(successRate)};
            write.writeNext(data);
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("End.");
		
	}
}
