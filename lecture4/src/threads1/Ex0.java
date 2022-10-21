package threads1;

public class Ex0 {
  
  public static void main(String[] args) {
    Thread t1 = new Thread(() -> System.out.println("Hello from thread " + Thread.currentThread().getId()));
    Thread t2 = new Thread(new MyTask());
    
    System.out.println("Hello from main thread " + Thread.currentThread().getId());
//    t1.start();
//    t2.start();
  }
}

class MyTask implements Runnable {
  @Override
  public void run() {
    System.out.println("Hello from thread " + Thread.currentThread().getId());
  }
}
