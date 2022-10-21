package shared;

public final class Threads {
  // intentionally avoid discussion around interruption, not a good time yet!
  public static void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException x) {
      throw new RuntimeException(x.getMessage(), x);
    }
  }

  @FunctionalInterface
  public interface REx extends Runnable {
    @Override
    default void run() {
      try {
        rex();
      } catch (Exception x) {
        throw new RuntimeException(x.getMessage(), x);
      }
    }

    void rex() throws Exception;
  }
}
