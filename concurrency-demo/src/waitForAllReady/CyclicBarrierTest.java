package waitForAllReady;

import java.util.concurrent.*;

public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        int numWorkers = 3;

        // 當所有 worker 都到達 barrier，會執行這個 Runnable（只會跑一次）
        CyclicBarrier barrier = new CyclicBarrier(numWorkers, () -> {
            System.out.println("== 所有 worker 都就緒了，主流程開始 ==");
        });

        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);

        for (int i = 1; i <= numWorkers; i++) {
            final int id = i;
            executor.execute(() -> {
                try {
                    System.out.println("Worker " + id + " 正在準備...");
                    Thread.sleep(1000 * id); // 模擬不同準備時間
                    System.out.println("Worker " + id + " 就緒");
                    barrier.await(); // 等待其他人就緒
                    System.out.println("Worker " + id + " 開始執行任務");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}
