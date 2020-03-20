package JavaThreads;

import java.util.Scanner;
import java.util.concurrent.*;


class Shop {
	private static final int WaitingSeats = 15;
	  public static void main(String[] args) {
	    System.out.println("Please Enter Number of Barbers in Shop here!");
	    Scanner sc = new Scanner(System.in);
	    int BarbersCount = sc.nextInt();
//	    int BarbersCount = Runtime.getRuntime().availableProcessors();
	    System.out.println("Barbers available are "+BarbersCount);
	    BlockingDeque<String> waitingAreaCustomers = new LinkedBlockingDeque<>(WaitingSeats);

	    final Semaphore barbers = new Semaphore(BarbersCount, true);	
	    final Semaphore customers = new Semaphore(0, true);
	    ExecutorService openShop = Executors.newWorkStealingPool();

	    Barber[] barbersGroup = new Barber[BarbersCount];

	    System.out.println("Shop Opened");
	    for (int i = 0; i < BarbersCount; i++) {
	      barbersGroup[i] = new Barber(waitingAreaCustomers, barbers, customers);
	      openShop.submit(barbersGroup[i]);
	    }
	    int customerNumber = 1;
	    while (true) {
	      try {
	        Thread.sleep(1000 * (Math.round(1 + 2 * Math.random()))); // Sleep until next customer come
	      } catch (InterruptedException e) {
	      }
	      String customerName = "Customer" + customerNumber;
	      customerNumber++;
	      System.out.println(customerName+" enters shop");

	      if (waitingAreaCustomers.size() < WaitingSeats) {
	        new Thread(new Customer(waitingAreaCustomers, barbers, customers, customerName)).start();
	      } else {
	        System.out.println("Customer exits as no seats are available");
	      }
	    }
	  }
	}
