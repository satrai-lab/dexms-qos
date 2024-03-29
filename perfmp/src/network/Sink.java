package network ;
import tools.* ;

/**
 * A Sink node absrobs customers from a queueing network.
 * Departing customers are registered with the {@link Network}
 * class, which records the customer's sojourn time.
 * @param name The name of the source node
 * @param d The {@link DistributionSampler} used to generate the
 *          inter-arrival times
 * @param b The {@link DistributionSampler} used to generate the
            batch sizes
*/
public class Sink extends Node {
	private StringBuilder departures = null;
	private double previous = 0;
	
  public Sink() {
    super( "Sink" ) ;
    this.departures = new StringBuilder();
  }

  public Sink( String name ) {
    super( name ) ;
    this.departures = new StringBuilder();
  }
  
  public StringBuilder getDepartures() {
	  return this.departures;
  }
  
public void setPrevious(double previous) {
	this.previous = previous;
}



public double getPrevious() {
	return previous;
}

//
// Do nothing here - customer is absorbed...
//
  protected void accept( Customer c ) {
//  	System.out.println("hello");
    Network.completions++ ;
    Network.registerCompletion( Sim.now() - c.getArrivalTime() ) ;
    
    //departures.append(Sim.now());
    //departures.append(",");
    //departures.append(Sim.now() - this.getPrevious());
    //departures.append(",");
    //departures.append("\n");
    
//    this.setPrevious(Sim.now());
    
//    if (c.isOff()) {
//    	Network.completionsOFF++ ;
//		Network.registerCompletionOFF(Sim.now() - c.getArrivalTime());
//	} else {
//		Network.completionsON++ ;
//		Network.registerCompletionON(Sim.now() - c.getArrivalTime());
//	}
    
  }

}

