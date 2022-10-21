package threads2;

import java.util.List;

public interface StockFetcher {
  List<Stock> fetch(List<String> symbols) throws Exception;
}
