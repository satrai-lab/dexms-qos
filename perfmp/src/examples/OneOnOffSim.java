package examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import extensions.SinkLftLses;
import extensions.SinkOvrlNet;
import network.*;
import tools.*;


class OneOnOffSim extends Sim {
	
	public static double duration = 0;
	public static Exp serviceTime;
	public static Exp serviceTime2;
	public static double averageOn = 0;
	public static double averageLifetime = 0;
	public static double averageOff = 0;
	public static double durationOn = 0;
	public static double durationOff = 0;
	public static double noOfCust = 0;
	public static double avgTimeinQueue = 0;

	// Example termination function
	public boolean stop() {
		return now() > duration;
	}


	// Here, the constructor starts the simulation.
	public OneOnOffSim(double d) {

		duration = d;

		Network.initialise();

		
		serviceTime = new Exp(8);
		Delay serveTime = new Delay(serviceTime);
		
		Exp onlineTime1 = new Exp(0.05);
//		Deterministic onlineTime1 = new Deterministic(30);
		Exp offlineTime1 = new Exp(0.05);
		
//		10 = 0.1
//		20 = 0.05
//		30 = 0.033
		
//		timeouts40 = [10, 15, 20, 25, 30, 35];
		
//		Exp lifetime = new Exp(0.0769230769230769);
//		Deterministic lifetime = new Deterministic(43);
		double L = 3.8;
		
//		buffer capacity 
		int k = 34;

		Source source = new Source("Source", new Exp(L));
//		
		
//		Source source = new Source("Source", new Exp(L), lifetime, "lifetime");
		
		FIFOQueue fqueue = new FIFOQueue(k);
		OnOffRQN on0ff_buf = new OnOffRQN("ON-OFF-1", serveTime, 1, fqueue, onlineTime1, offlineTime1, duration);
		
//		OnOffRQN on0ff_inf = new OnOffRQN("ON-OFF-1", serveTime, 1, onlineTime1, offlineTime1, duration);
		
		
//		Sink sink = new Sink("Sink");
		
		SinkOvrlNet sink = new SinkOvrlNet("Sink");


//		source.setLink(new Link(on0ff_inf));
//		on0ff_inf.setLink(new Link(sink));
		
		source.setLink(new Link(on0ff_buf));
		on0ff_buf.setLink(new Link(sink));
		
		simulate();
		
		averageOn = onlineTime1.average();
		averageOff = offlineTime1.average();
		
//		averageLifetime = lifetime.average();
		
		System.err.println("ON average 1: " + averageOn);
		System.err.println("OFF average 1 : " + averageOff);
		
	
		System.out.println("Avg Service Time: " + serviceTime.average());
		
//		System.out.println("Avg Lifetime: " + lifetime.average());
		
//		System.out.println("Customers at the End of ON - ON-OFF-1: " + on0ff_1.getCustomersEndOn());
		
		Network.logResult("CompletionsExpired", Network.completionsExpired);
		Network.logResult("ResponseTimeExpired", Network.responseTimeExpired.mean());
		
		Network.logResult("Completions", Network.completions);
		Network.logResult("ResponseTime", Network.responseTime.mean());
		
//		System.out.println("SuccesRate Lifetime: " + ((double)(Network.completions) / (double) (Network.completions + Network.completionsExpired)));
		System.out.println("SuccesRate buffer: " + ((double)(Network.completions) / (double) (Network.completions + on0ff_buf.getLosses())));
		System.out.println("Losses ON: " + on0ff_buf.lossesON);
		System.out.println("FailureRate buffer ON: " + (1-((double)(Network.completions) / (double) (Network.completions + on0ff_buf.lossesON))));
		System.out.println("Losses OFF: " + on0ff_buf.lossesOFF);
		System.out.println("FailureRate buffer OFF: " + (1-((double)(Network.completions) / (double) (Network.completions + on0ff_buf.lossesOFF))));
				
//		System.out.println("Mean Time in ON-OFF queue: " + on0ff_inf.meanTimeInQueue());
		System.out.println("Mean Time in ON-OFF queue: " + on0ff_buf.meanTimeInQueue());
//		System.out.println("Queue Length: " + Network.responseTime.mean()*L);
		
//		Network.responseTime.saveResponseMeasures();
	}

	public static void main(String args[]) {
		new OneOnOffSim(1000000);
		
		Network.displayResults( 0.01 ) ;
		
		Network.logResults();
		
		
		
//		try {
//
//			AnalyticalModelsONOFF an = new AnalyticalModelsONOFF(Network.responseTime.mean(), Network.responseTimeON.mean(),
//					Network.responseTimeOFF.mean(), Network.completions, Network.completionsON, Network.completionsOFF, duration,
//					serviceTime.average(), Network.virtualServiceTime.mean(), Network.virtualServiceTime.variance(),
//					Network.serviceTimeON.mean(), Network.serviceTimeOFF.mean(), durationOn, durationOff, averageOn, averageOff);
//
//			String data = "Lsim: " + an.computeL() + " -- S-sim: " + serviceTime.average() + " -- avgON: " + averageOn
//					+ " -- avgOFF: " + averageOff + " -- Arrivals: " + Network.completions + " -- Duration: " + duration;
//
//			String simulator = "R-sim (mean resp time in system): " + Network.responseTime.mean() + " -- R-model (mean resp time in system): " + an.computeR()
//					+ " -- R-sim-queue (mean resp time in queue): " + avgTimeinQueue + " -- R-model-mosxolios (mean resp time in system): " + an.computeR_mosxolios();
//			String model = " Q-moscholios_paper: " + an.computeEN_mosxolios() + " Q-sim: " + Network.responseTime.mean() * an.computeL();
//			
//			String R_paper = " -- R_paper: " + an.computeR_paper();
//			
//			File file = new File("results_onoff.txt");
//
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(data);
//			bw.write("\n");
//			bw.write(simulator);
//			bw.write("\n");
//			bw.write(model);
//			bw.write("\n");
//			bw.write(R_paper);
//			bw.write("\n");
//			bw.close();
//
//			System.out.println("Done");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
