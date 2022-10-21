package threads1;

import shared.Threads;

import java.util.concurrent.atomic.AtomicInteger;

public class Ex2 {
  
  public static void main(String[] args) throws InterruptedException {
    DownloadContentJob job = new DownloadContentJob();
    new Thread(job).start();

    ProgressBar progressBar = new ProgressBar();
    
    while (job.percent.get() != 100) {
      progressBar.percent = job.percent.get();
      progressBar.display();
      Thread.sleep(500);
    }
    
    if (progressBar.percent != 100) {
      progressBar.percent = 100;
      progressBar.display();
    }

    System.out.println("Downloading finished");
  }
  
  static class DownloadContentJob implements Runnable {

    // 0 - 100
    final AtomicInteger percent = new AtomicInteger(0);
    
    @Override
    public void run() {
      // pretend downloading
      for (int i = 0; i < 100; i++) {
        percent.incrementAndGet();
        Threads.sleep(100);
      }
    }
  }
  
  // 40% [############                 ]
  static class ProgressBar {
    int percent = 0;
    
    void display() {
      System.out.printf("%d%% [", percent);
      for (int i = 0; i < percent; i++) {
        System.out.print("#");
      }
      for (int i = percent; i < 100; i++) {
        System.out.print(" ");
      }
      System.out.println("]");
    }
  }
}
