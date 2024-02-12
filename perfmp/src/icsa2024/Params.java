package icsa2024;

public class Params {
	
	/*
	 * Select one of 4 available patterns:
	 * - R2R-REQ: Reliable to reliable - request
	 * - R2R-RES: Reliable to reliable - response
	 * - U2R-REQ: Unreliable to reliable - request
	 * - U2R-RES: Unreliable to reliable - response
	 */
	public static String PATTERN = "R2R-REQ";
	
	//Add the path to where you want to save the file containing the response times for all messages
	public static String RESPONSE_TIMES_FILE = "responseTimes_on60_1.csv";
	
	//Add the path to where you want to save the file containing the success rate of message delivery
	public static String SUCCESS_RATES_FILE = "successRates.csv";
	
	
	//general params
	public static double LIFETIME = 1000;		//lifetime in seconds
	public static double TIMEOUT = 10;
	public static double SRC_RATE = 250;		//total number of requests
	public static double PR_MSG_RATE = 1000;	
	public static double TR_MSG_RATE = 1;		
	public static double ON_OVRL_RATE = 0.1;
	public static double OFF_OVRL_RATE = 0.1;
	public static int SIM_DURATION = 20000;		//change the duration of the simulation here
	
	
	//icsa2024 parameters
	public static double ON_OVRL_DRIVER_RATE = 1.0/60.0;	//publisher is on for 60 seconds
	public static double OFF_OVRL_DRIVER_RATE = 1.0/5.0;	//publisher is off for 5 seconds
	public static double TR_APP_RATE = 282;			//3 Mbps upload x 47 cars: total bandwidth between cars and broker
	public static double TR_BROKER_RATE = 1000;		//1 Gbps
	public static double TR_MEDIATOR_RATE = 1000;	//1 Gbps
	
	


}
