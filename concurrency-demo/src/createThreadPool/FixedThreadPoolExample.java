package createThreadPool;

import java.util.concurrent.*;

public class FixedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3); // 固定 3 條線程

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("執行任務 " + taskId + " by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown(); // 不再接受新任務，等待完成後關閉
    }
}
