package JavaThreads;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer implements Runnable {

  private BlockingDeque<String> WaitingArea;
  private Semaphore BarbersAvailable;
  private Semaphore CustomersAvailable;
  private String CustomerName;
  private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public Customer(BlockingDeque WaitingArea,
                  Semaphore BarbersAvailable, Semaphore CustomersAvailable, String CustomerName) {
    this.WaitingArea = WaitingArea;
    this.BarbersAvailable = BarbersAvailable;
    this.CustomersAvailable = CustomersAvailable;
    this.CustomerName = CustomerName;
  }

 
  public void run() {

    try {
      CustomersAvailable.release();
      WaitingArea.put(CustomerName);
      if (BarbersAvailable.hasQueuedThreads()) {
        System.out.println(this.CustomerName + " is in waiting area.");
      }
      BarbersAvailable.acquire();

    } catch (InterruptedException e) {
      log.log(Level.SEVERE, "Exception in customer class");
    }
  }

  public String getCustomerName() {
    return CustomerName;
  }

  public void setCustomerName(String CustomerName) {
    this.CustomerName = CustomerName;
  }
}

