package threads2.fetchers;

import threads2.StockExchange;
import threads2.Stock;
import threads2.StockFetcher;

import java.util.ArrayList;
import java.util.List;

public class SyncStockFetcher implements StockFetcher {
  @Override
  public List<Stock> fetch(List<String> symbols) {
    List<Stock> result = new ArrayList<>(symbols.size());
    for (String symbol : symbols) {
      result.add(new Stock(symbol, StockExchange.getPrice(symbol)));
    }
    return result;
  }
}
