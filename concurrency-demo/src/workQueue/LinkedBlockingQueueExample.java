package workQueue;

import java.util.concurrent.*;

public class LinkedBlockingQueueExample {
    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(
                2, 4,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10) // 有界佇列
        );

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            executor.execute(() -> {
                System.out.println("執行任務 " + taskId + " by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}

