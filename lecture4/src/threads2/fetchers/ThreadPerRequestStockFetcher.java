package threads2.fetchers;

import threads2.StockExchange;
import threads2.Stock;
import threads2.StockFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class ThreadPerRequestStockFetcher implements StockFetcher {
  
  @Override
  public List<Stock> fetch(List<String> symbols) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(symbols.size());
    
    final List<GetPriceTask> tasks = new ArrayList<>();
    for (String symbol : symbols) {
      final GetPriceTask task = new GetPriceTask(symbol, latch);
      tasks.add(task);
      new Thread(task).start();
    }

    latch.await();
    
    return tasks.stream()
      .map(task -> new Stock(task.symbol, task.price))
      .collect(Collectors.toList());
  }

  private static class GetPriceTask implements Runnable {

    final String symbol;
    final CountDownLatch latch;
    
    volatile double price;

    GetPriceTask(String symbol, CountDownLatch latch) {
      this.symbol = symbol;
      this.latch = latch;
    }

    @Override
    public void run() {
      price = StockExchange.getPrice(symbol);
      latch.countDown();
    }
  }
}
