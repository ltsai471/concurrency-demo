package rejectedHandler;

import java.util.concurrent.*;

public class AbortPolicyExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 2, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.AbortPolicy() // 預設：超過就拋異常
        );

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("執行任務 " + taskId + ": " + System.currentTimeMillis());
                //超過第 4 個任務後會拋出 RejectedExecutionException
            });
        }

        executor.shutdown();
    }
}

