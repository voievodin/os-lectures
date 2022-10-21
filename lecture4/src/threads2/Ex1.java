package threads2;

import shared.StopWatch;
import threads2.fetchers.SyncStockFetcher;

import java.util.List;

public class Ex1 {
  public static void main(String[] args) throws Exception {
    List<String> symbols = List.of(
      "WISE",
      "APPL",
      "META",
      "VOO",
      "VEA"
    );
    
    StockFetcher fetcher = new SyncStockFetcher();
    
    StopWatch stopWatch = StopWatch.create();
    List<Stock> stocks = fetcher.fetch(symbols);
    System.out.printf("Took %dms\n", stopWatch.sinceCreated().toMillis());
    
    System.out.println("\nSummary:");
    for (Stock stock : stocks) {
      System.out.println(stock);
    }
  }
}
