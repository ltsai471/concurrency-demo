package createThreadPool;

import java.util.concurrent.*;

public class ScheduledThreadPoolExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Runnable task = () -> System.out.println("延遲任務執行於 " + System.currentTimeMillis());

        scheduler.schedule(task, 2, TimeUnit.SECONDS); // 延遲 2 秒執行

        scheduler.shutdown();
    }
}

