import java.io.*;
import java.nio.file.*;

public class Main {
  public static void main(String[] args) throws IOException {
    final Path path = Paths.get("output.txt");
    Files.writeString(path, "Hey!");
  }
}
