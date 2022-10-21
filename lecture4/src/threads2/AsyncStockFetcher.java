package threads2;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AsyncStockFetcher {
  CompletableFuture<List<Stock>> fetch(List<String> symbols);
}
