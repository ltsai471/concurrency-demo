package waitForAllDone;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            System.out.println("Worker 1 running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Worker 1 done");
            latch.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("Worker 2 running...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Worker 2 done");
            latch.countDown();
        }).start();

        // 主線程等待兩個工作線程完成
        latch.await();
        System.out.println("All workers done, main thread resumes.");
    }

}
