package threads2;

public record Stock(String symbol, double price) {
  @Override
  public String toString() {
    return String.format("Symbol: %s, price: %.2f", symbol, price);
  }
}
