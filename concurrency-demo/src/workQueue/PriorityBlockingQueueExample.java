package workQueue;

import java.util.concurrent.*;

public class PriorityBlockingQueueExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1,
                60L, TimeUnit.SECONDS,
                new PriorityBlockingQueue<>()
        );

        executor.execute(new PriorityTask("Med", 2));
        executor.execute(new PriorityTask("High", 1));
        executor.execute(new PriorityTask("Low", 3));

        executor.shutdown();
    }

    static class PriorityTask implements Runnable, Comparable<PriorityTask> {
        private final String name;
        private final int priority;

        public PriorityTask(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        public int compareTo(PriorityTask o) {
            return Integer.compare(this.priority, o.priority); // 越小越高
        }

        public void run() {
            System.out.println("執行 " + name + " 優先級任務 by " + Thread.currentThread().getName());
        }
    }
}

