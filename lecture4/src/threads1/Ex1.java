package threads1;

import shared.Threads.REx;

import static java.lang.Thread.sleep;

public class Ex1 {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("My id: " + Thread.currentThread().getId());
    
    // Create a thread
    Thread t1 = new Thread((REx) () -> {
      System.out.println("My id: " + Thread.currentThread().getId());
      
      for (int i = 0; i < 5; i++) {
        sleep(2000);
        System.out.println("T1 iteration: " + i);
      }
    });
    
    // Wait a bit before starting the created thread
    sleep(5000);
    
    // Start the thread
    t1.start();

    System.out.printf("Thread %d is waiting for thread %d to finish\n", Thread.currentThread().getId(), t1.getId());
    
    // Wait for it to finish
    t1.join();
    
    System.out.printf("Thread %d may exit now\n", Thread.currentThread().getId());
  }
}
