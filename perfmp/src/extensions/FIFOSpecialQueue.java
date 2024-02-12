package extensions ;

import network.Customer;
import network.Queue;
import tools.* ;

public class FIFOSpecialQueue extends Queue {
  private List q = new List( "FIFO Queue" ) ;

  public FIFOSpecialQueue() {
    super() ;
  }

  public FIFOSpecialQueue( int cap ) {
    super( cap ) ;
  }
  
  protected void insertIntoQueue( Customer e ) {
    q.insertAtBack( e ) ;
    
    Customer c = headOfQueue();
  }

  protected void insertAtHeadOfQueue( Customer e ) {
    q.insertAtFront( e ) ;
  }

  protected Customer headOfQueue() {
    return (Customer)q.first() ;
  }

  protected Customer removeFromQueue() {
    return (Customer)q.removeFromFront() ;
  }
  
}



