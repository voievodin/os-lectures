package threads3.mylang;

import shared.Threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class Runtime {

  final Map<Integer, MyLangThread> readyThreads = new HashMap<>();
  final List<WaitingThread> waitingQueue = new ArrayList<>();
  final SchedStrategy strategy = new PickRandomThreadSchedStrategy();

  void enqueue(MyLangThread t) {
    t.setRuntime(this);
    if (readyThreads.put(t.id, t) != null) {
      throw new IllegalArgumentException("Thread spawned twice " + t.id);
    }
  }

  void sched() {
    while (readyThreads.size() > 0 || waitingQueue.size() > 0) {
      if (!readyThreads.isEmpty()) {
        MyLangThread thread = strategy.pick(readyThreads);
        if (!thread.executeNextInstruction()) {
          readyThreads.remove(thread.id);
          System.out.printf("\nThread %d finished\n", thread.id);
          System.out.printf("Memory dump for thread %d\n", thread.id);
          thread.memory.dump();
          System.out.println();
        }
      }
      wakeUpThreads();
      Threads.sleep(1000);
    }
  }

  public void sleep(MyLangThread thread, int seconds) {
    waitingQueue.add(new WaitingThread(thread, seconds * 1000L + System.currentTimeMillis()));
    readyThreads.remove(thread.id);
  }
  
  private void wakeUpThreads() {
    long nowMs = System.currentTimeMillis();
    for (Iterator<WaitingThread> it = waitingQueue.iterator(); it.hasNext(); ) {
      final WaitingThread waiting = it.next();
      if (nowMs > waiting.until) {
        it.remove();
        readyThreads.put(waiting.thread.id, waiting.thread);
      }
    }
  }

  private interface SchedStrategy {
    MyLangThread pick(Map<Integer, MyLangThread> threads);
  }

  private static class PickRandomThreadSchedStrategy implements SchedStrategy {
    @Override
    public MyLangThread pick(Map<Integer, MyLangThread> threads) {
      int rnd = ThreadLocalRandom.current().nextInt(threads.size());
      Iterator<MyLangThread> it = threads.values().iterator();
      while (rnd-- > 0) {
        it.next();
      }
      return it.next();
    }
  }
  
  private static record WaitingThread(MyLangThread thread, long until) { }
}
