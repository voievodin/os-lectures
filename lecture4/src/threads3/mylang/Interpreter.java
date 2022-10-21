package threads3.mylang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Interpreter {

  private final String[] lines;

  public Interpreter(Path path) throws IOException {
    lines = Files.readAllLines(path).toArray(String[]::new);
  }

  public void interpret() {
    Runtime runtime = new Runtime();
    runtime.enqueue(new MyLangThread(lines, 0, new Memory()));
    runtime.sched();
  }
}
