package ParallelAndSequentialTasks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3); // 3 个并行任务

        // 并行执行 func1, func2, func3
        executor.execute(() -> {
            func1();
            latch.countDown();
        });
        executor.execute(() -> {
            func2();
            latch.countDown();
        });
        executor.execute(() -> {
            func3();
            latch.countDown();
        });

        // 等待所有并行任务完成
        latch.await();

        // 顺序执行 func4 和 func5
        func4();
        func5();

        executor.shutdown();
    }

    private static void func1() {
        System.out.println("执行 func1");
    }

    private static void func2() {
        System.out.println("执行 func2");
    }

    private static void func3() {
        System.out.println("执行 func3");
    }

    private static void func4() {
        System.out.println("执行 func4");
    }

    private static void func5() {
        System.out.println("执行 func5");
    }
}
