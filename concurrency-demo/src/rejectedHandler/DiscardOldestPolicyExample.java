package rejectedHandler;

import java.util.concurrent.*;

public class DiscardOldestPolicyExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 2, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("執行任務 " + taskId);
                //任務 5 和 6 加進來前會丟掉最舊佇列任務，並嘗試加入新任務
            });
        }

        executor.shutdown();
    }
}
