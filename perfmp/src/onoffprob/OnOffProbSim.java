package onoffprob;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import network.*;
import tools.*;


class OnOffProbSim extends Sim {
	
	public static double duration = 0;
	public static Exp serviceTime;
	public static double averageOn = 0;
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
	public OnOffProbSim(double d) {

		duration = d;

		Network.initialise();

		double mu = 8;
		double thOn = 0.05;
		double thOff = 0.05;
		
		serviceTime = new Exp(mu);

		Exp onlineTime1 = new Exp(thOn);
//		Weibull onlineTime1 = new Weibull(20.9807, 1.1571);

		Exp offlineTime1 = new Exp(thOff);
		Delay serveTime = new Delay(serviceTime);
		
		double L = 3.8;
		double prob = 0.252;
		
		Source source = new Source("Source", new Exp(L));
		OnOffProbQN on0ff_1 = new OnOffProbQN("ON-OFF-1", serveTime, 1, onlineTime1, offlineTime1, prob, duration);
				
		Sink sink = new Sink("Sink");

		source.setLink(new Link(on0ff_1));
		on0ff_1.setLink(new Link(sink));
		
		AnalyticalModel an = new AnalyticalModel();
		System.out.println("EFF LAMBDA: " + an.computeEffLam(L, thOn, thOff, prob));
		System.out.println("QUEUE SIZE AN MODEL: " + an.numMessSys(L, L/mu, thOn, thOff, prob, mu));
		System.out.println("DELTA AN MODEL: " + (an.numMessSys(L, L/mu, thOn, thOff, prob, mu)/an.computeEffLam(L, thOn, thOff, prob)));
		
		simulate();
		
		averageOn = onlineTime1.average();
		averageOff = offlineTime1.average();
		
		
		System.err.println("ON average 1: " + averageOn);
		System.err.println("OFF average 1 : " + averageOff);
		

		System.out.println("Avg Service Time: " + serviceTime.average());
		
		
		System.out.println("Customers at the End of ON - ON-OFF-1: " + on0ff_1.getCustomersEndOn());
		System.out.println("losses: "+ on0ff_1.getLosses());
		Network.logResult("Completions", Network.completions);
		Network.logResult("Response Time", Network.responseTime.mean());
		Network.logResults();
		
		
		System.out.println("SuccesRate: " + ((double)(Network.completions) / (double) (Network.completions + on0ff_1.getLosses())));
		
		
//		System.err.println("A: "+an.computeCubicA(L, L/mu, thOn));
//		System.err.println("B: "+an.computeCubicB(L, L/mu, thOn, thOff, an.numMessSys(L, L/mu, thOn, thOff, prob)));
//		System.err.println("C: "+an.computeCubicC(L, L/mu, thOn, thOff, an.numMessSys(L, L/mu, thOn, thOff, prob)));
//		System.err.println("D: "+an.computeCubicD(L, L/mu, thOn, thOff, an.numMessSys(L, L/mu, thOn, thOff, prob)));
//		
//		double zeta = an.computeCubicFormula(an.computeCubicA(L, L/mu, thOn), an.computeCubicB(L, L/mu, thOn, thOff, an.numMessSys(L, L/mu, thOn, thOff, prob)),
//				an.computeCubicC(L, L/mu, thOn, thOff, an.numMessSys(L, L/mu, thOn, thOff, prob)), an.computeCubicD(L, L/mu, thOn, thOff, an.numMessSys(L, L/mu, thOn, thOff, prob)));
		
//		System.err.println("Zeta prob: " + zeta);

	}

	public static void main(String args[]) {
		new OnOffProbSim(4000000);
//		new OnOffSim(200);
		
		Network.displayResults( 0.01 ) ;
		
//		Network.logResults();
		
//		try {
//
//			AnalyticalModelsONOFF an = new AnalyticalModelsONOFF(Network.responseTime.mean(), Network.responseTimeON.mean(),
//					Network.responseTimeOFF.mean(), Network.completions, Network.completionsON, Network.completionsOFF, duration,
//					serviceTime.average(), Network.virtualServiceTime.mean(), Network.virtualServiceTime.variance(),
//					Network.serviceTimeON.mean(), Network.serviceTimeOFF.mean(), durationOn, durationOff, averageOn, averageOff);
//
//			String data = "Lsim: " + an.computeL() + " -- S-sim: " + serviceTime.average() + " -- avgON: " + averageOn
//					+ " -- avgOFF: " + averageOff + " -- Arrivals: " + Network.completions + " -- Duration: " + duration;
//			// String data = "Lsim: " + an.computeL() + " -- Lon-sim: " +
//			// an.computeLon() + " -- Loff-sim: " + an.computeLoff() + " -- S-sim: " +
//			// serviceTime.average() + " -- Arrivals: " + Network.completions +
//			// " -- Duration: " + duration;
//
//			// String new_metrics = "Lon-model: " + an.computeLOnModel() +
//			// " -- Loff-model: " + an.computeLOffModel();
//			// String simulator = "R-sim: " + Network.responseTime.mean() +
//			// " -- Ns (queue): " + noOfCust;
//			String simulator = "R-sim (mean resp time in system): " + Network.responseTime.mean() + " -- R-model (mean resp time in system): " + an.computeR()
//					+ " -- R-sim-queue (mean resp time in queue): " + avgTimeinQueue + " -- R-model-mosxolios (mean resp time in system): " + an.computeR_mosxolios();
//			String model = " Q-sim (num of cust in queue): " + noOfCust + " -- Q-sim (num of cust in system): " + an.computeQsim();
//			// String onoff = "Ron-sim " + Network.responseTimeON.mean() +
//			// " -- Son-sim: " + Network.serviceTimeON.mean() + " -- Roff-sim " +
//			// Network.responseTimeOFF.mean() + " -- Soff-sim: " +
//			// Network.serviceTimeOFF.mean();
//			// String model = "Ron-model: " + an.computeRon() + " -- Roff-model: " +
//			// an.computeRoff() + " -- R-model: " + an.computeR();
////			String prob = "Psim (server utilization) : " + util;
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
//			// bw.write(new_metrics);
//			// bw.write("\n");
//			bw.write(simulator);
//			bw.write("\n");
//			// bw.write(onoff);
//			// bw.write("\n");
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
