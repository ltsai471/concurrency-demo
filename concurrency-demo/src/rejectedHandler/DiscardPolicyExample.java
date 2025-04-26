package rejectedHandler;

import java.util.concurrent.*;

public class DiscardPolicyExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 2, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("執行任務 " + taskId);
                //任務 5 和 6 被靜默丟棄，不會報錯也不會執行
            });
        }

        executor.shutdown();
    }
}

