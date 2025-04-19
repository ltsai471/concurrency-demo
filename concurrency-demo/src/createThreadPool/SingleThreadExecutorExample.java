package createThreadPool;

import java.util.concurrent.*;

public class SingleThreadExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor(); // 只有一條線程
        System.out.println("主線程開始");
        for (int i = 1; i <= 3; i++) {
            int taskId = i;
            //背景執行
            executor.submit(() -> {
                System.out.println("執行任務 " + taskId + " by " + Thread.currentThread().getName());
            });
        }
        System.out.println("主線程可以繼續跑其他事");
        executor.shutdown();
    }
}

