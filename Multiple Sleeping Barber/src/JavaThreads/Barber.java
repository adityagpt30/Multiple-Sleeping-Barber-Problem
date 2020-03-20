package JavaThreads;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Barber implements Runnable {

  private BlockingDeque WaitingArea;
  private Semaphore BarbersAvailable;
  private Semaphore CustomersAvailable;
  private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public Barber(BlockingDeque WaitingArea,
                Semaphore BarbersAvailable, Semaphore CustomersAvailable) {
    this.WaitingArea = WaitingArea;
    this.BarbersAvailable = BarbersAvailable;
    this.CustomersAvailable = CustomersAvailable;
  }

 
  public void run() {
    while (true) {
      try {
        CustomersAvailable.acquire();
        getHaircut(WaitingArea);
        BarbersAvailable.release();

      } catch (InterruptedException e) {
        log.log(Level.SEVERE, "Exception in Process in Barber class");
      }
    }
  }

  public void getHaircut(BlockingDeque<String> WaitingArea) throws InterruptedException {
    //giving 2-5 seconds for the haircut
    String customer = WaitingArea.take();
    System.out.println(customer +" is getting hair cut.");
    long haircutTime = Math.round(2 + 3 * Math.random());
    Thread.sleep(haircutTime * 3000);
    System.out.println(customer+" exits after getting hair cut.");

  }
}
