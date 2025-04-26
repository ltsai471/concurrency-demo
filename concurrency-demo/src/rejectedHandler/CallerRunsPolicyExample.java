package rejectedHandler;

import java.util.concurrent.*;

public class CallerRunsPolicyExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 2, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("執行任務 " + taskId + " by " + Thread.currentThread().getName());
                //超過 4 個任務後，第 5 和第 6 會由主線程自己執行，不進入線程池
            });
        }

        executor.shutdown();
    }
}

