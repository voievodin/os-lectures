package threads3.mylang;

import java.util.HashMap;
import java.util.Map;

class Memory {
  final Map<String, Integer> variables;

  Memory() {
    this.variables = new HashMap<>();
  }

  Memory(Map<String, Integer> variables) {
    this.variables = new HashMap<>(variables);
  }

  void dump() {
    System.out.println(variables);
  }

  Memory copy() {
    return new Memory(variables);
  }
}
