package workQueue;

import java.util.concurrent.*;

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(
                2, 4,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5) // 固定長度的佇列
        );

        for (int i = 1; i <= 8; i++) {
            int taskId = i;
            executor.execute(() -> {
                System.out.println("執行任務 " + taskId + " by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}

