package threads2;

import java.util.concurrent.ThreadLocalRandom;

import static shared.Threads.sleep;

public class StockExchange {
  
  public static double getPrice(String symbol) {
    System.out.printf("Request a price for '%s'\n", symbol);
    sleep(2000);
    return ThreadLocalRandom.current().nextDouble(1000) + 10;
  }
}
