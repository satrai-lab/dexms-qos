package extensions ;

import java.util.ArrayList;

import network.Customer;
import network.Link;
import network.Node;

public class PrioBranch extends Link {
  Node[] nodes ;

  /*
   * @nodes: Node1: the node the expired messages will be sent
   * 		 Node2: node that the non-expired messages continue to the network
   * 
   */
  public PrioBranch(Node[] nodes) {
    this.nodes = nodes ;
  }
 
  public void move(Customer c) {
	  send( c, nodes[c.getPriority()]) ;  
	  
  }
}

