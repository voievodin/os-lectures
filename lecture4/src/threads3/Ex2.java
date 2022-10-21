package threads3;

import threads3.mylang.Interpreter;

import java.nio.file.Paths;

public class Ex2 {
  public static void main(String[] args) throws Exception {
    new Interpreter(Paths.get("lecture4/src/threads3/ex2.mylang")).interpret();
  }
}
