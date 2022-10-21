package threads2.fetchers;

import threads2.Stock;
import threads2.StockExchange;
import threads2.StockFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ThreadPoolStockFetcher implements StockFetcher  {
  final ExecutorService executor;

  public ThreadPoolStockFetcher(ExecutorService executor) {
    this.executor = executor;
  }

  @Override
  public List<Stock> fetch(List<String> symbols) throws Exception {
    List<Future<Double>> tasks = new ArrayList<>(symbols.size());
    for (String symbol : symbols) {
      Future<Double> f = executor.submit(() -> StockExchange.getPrice(symbol));
      tasks.add(f);
    }

    List<Stock> result = new ArrayList<>(symbols.size());
    for (int i = 0; i < symbols.size(); i++) {
      double price = tasks.get(i).get();
      result.add(new Stock(symbols.get(i), price));
    }
    return result;
  }
}
