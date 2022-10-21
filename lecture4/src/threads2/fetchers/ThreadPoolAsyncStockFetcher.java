package threads2.fetchers;

import threads2.AsyncStockFetcher;
import threads2.Stock;
import threads2.StockExchange;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolAsyncStockFetcher implements AsyncStockFetcher {

  final ExecutorService executor;

  public ThreadPoolAsyncStockFetcher(ExecutorService executor) {
    this.executor = executor;
  }

  @Override
  public CompletableFuture<List<Stock>> fetch(List<String> symbols) {
    CopyOnWriteArrayList<Stock> result = new CopyOnWriteArrayList<>();
    CompletableFuture<List<Stock>> f = new CompletableFuture<>();
    AtomicInteger done = new AtomicInteger(symbols.size());
    for (String symbol : symbols) {
      executor.submit(() -> {
        final double price = StockExchange.getPrice(symbol);
        result.add(new Stock(symbol, price));
        if (done.decrementAndGet() == 0) {
          f.complete(result);
        }
      });
    }
    return f;
  }
}
