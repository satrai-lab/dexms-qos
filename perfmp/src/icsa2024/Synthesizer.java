package icsa2024;

import queueingnets.*;


public class Synthesizer {

	public static void main(String[] args) {
		String pattern = Params.PATTERN;
		String responseTimesFile = Params.RESPONSE_TIMES_FILE;
		String successRatesFile = Params.SUCCESS_RATES_FILE;
		int duration = Params.SIM_DURATION;
		
		if (pattern.equals("R2R-REQ")) {
			ReliableToReliableRequest network = new ReliableToReliableRequest(duration);
			network.runSimulation(responseTimesFile, successRatesFile);
		}
		else if (pattern.equals("R2R-RES")) {
			ReliableToReliableResponse network = new ReliableToReliableResponse(duration);
			network.runSimulation(responseTimesFile, successRatesFile);
		}
		else if (pattern.equals("U2R-REQ")) {
			UnreliableToReliableRequest network = new UnreliableToReliableRequest(duration);
			network.runSimulation(responseTimesFile, successRatesFile);
		}
		else if (pattern.equals("U2R-RES")) {
			UnreliableToReliableResponse network = new UnreliableToReliableResponse(duration);
			network.runSimulation(responseTimesFile, successRatesFile);
		}

	}

}
