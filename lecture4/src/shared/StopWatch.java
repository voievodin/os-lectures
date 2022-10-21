package shared;

import java.time.Duration;

public class StopWatch {
  
  public static StopWatch create() {
    return new StopWatch();
  }

  private final long startedAt;

  private StopWatch() {
    this.startedAt = System.currentTimeMillis();
  }
  
  public Duration sinceCreated() {
    return Duration.ofMillis(System.currentTimeMillis() - startedAt);
  }
}
