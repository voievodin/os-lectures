package threads3.mylang;

import java.util.concurrent.atomic.AtomicInteger;

class MyLangThread {

  private static final AtomicInteger ID_SEQ = new AtomicInteger(0);

  final int id;
  final String[] instructions;
  final Memory memory;
  
  int ip;
  Runtime runtime;

  MyLangThread(String[] instructions, int ip, Memory memory) {
    this.id = ID_SEQ.incrementAndGet();
    this.instructions = instructions;
    this.ip = ip;
    this.memory = memory;
  }

  void setRuntime(Runtime runtime) {
    this.runtime = runtime;
  }

  boolean executeNextInstruction() {
    if (ip >= instructions.length) {
      return false;
    }
    String instruction = instructions[ip];
    System.out.printf("Thread %d: Executing statement %d =>     %s\n", id, ip, instruction);
    executeInstruction(instruction);
    ip++;
    return true;
  }

  private void executeInstruction(String instruction) {
    if (instruction.contains("?")) {
      handleIfStatement(instruction);
    } else if (instruction.contains("tfork")) {
      handleTFork(instruction);
    } else if (instruction.contains("sleep")) {
      handleSleep(instruction);
    } else {
      handleAssignment(instruction);
    }
  }
  
  private void handleSleep(String instruction) {
    int seconds = Integer.parseInt(instruction.trim().split(" ")[1]);
    runtime.sleep(this, seconds);
  }

  private void handleAssignment(String statement) {
    // a = b + 1
    String[] declaration = statement.split("=");
    // a
    String varName = declaration[0].trim();

    // b + 1
    if (declaration[1].contains("+")) {
      int sum = 0;
      for (String expression : declaration[1].split("\\+")) {
        sum += asNumber(expression);
      }
      memory.variables.put(varName, sum);
      // b or 100
    } else {
      memory.variables.put(varName, asNumber(declaration[1]));
    }
  }

  private void handleIfStatement(String statement) {
    // tid == 0 ? b = 100 : b = 200
    String[] parts = statement.split("\\?");
    // tid == 0
    String[] conditionParts = parts[0].split("==");
    // b = 100 : b = 200
    String[] actionParts = parts[1].split(":");
    int left = asNumber(conditionParts[0]);
    int right = asNumber(conditionParts[1]);
    if (left == right) {
      executeInstruction(actionParts[0].trim());
    } else {
      executeInstruction(actionParts[1].trim());
    }
  }

  private void handleTFork(String statement) {
    // tid = tfork
    String varName = statement.split("=")[0].trim();

    Memory copy = memory.copy();
    MyLangThread newThread = new MyLangThread(instructions, ip + 1, copy);
    memory.variables.put(varName, newThread.id);
    copy.variables.put(varName, 0);
    runtime.enqueue(newThread);
  }

  private int asNumber(String value) {
    final String valueTrimmed = value.trim();
    try {
      return Integer.parseInt(valueTrimmed);
    } catch (Exception x) {
      return memory.variables.get(valueTrimmed);
    }
  }
}
