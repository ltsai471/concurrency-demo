package createThreadPool;

import java.util.concurrent.*;

public class CachedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool(); // 自動增減線程數

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("執行任務 " + taskId + " by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}

