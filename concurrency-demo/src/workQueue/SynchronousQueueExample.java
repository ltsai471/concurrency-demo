package workQueue;

import java.util.concurrent.*;

public class SynchronousQueueExample {
    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(
                0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.execute(() -> {
                System.out.println("執行任務 " + taskId + " by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
