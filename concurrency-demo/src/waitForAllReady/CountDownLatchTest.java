package waitForAllReady;

import java.util.concurrent.*;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        int numWorkers = 3;
        CountDownLatch readySignal = new CountDownLatch(numWorkers);

        for (int i = 1; i <= numWorkers; i++) {
            final int id = i;
            new Thread(() -> {
                System.out.println("Worker " + id + " 正在初始化...");
                try { Thread.sleep(500 * id); } catch (InterruptedException ignored) {}
                System.out.println("Worker " + id + " 初始化完成");
                readySignal.countDown(); // 發出 "我準備好了" 的訊號
            }).start();
        }

        System.out.println("主線程等待所有 worker 初始化完畢...");
        readySignal.await(); // 等待所有 worker 就緒
        System.out.println("== 所有 worker 就緒，主線程開始任務 ==");
    }
}
