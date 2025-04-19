import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicInteger;

class SharedResource {
    private int value = 0;
    private final AtomicInteger contentionCount = new AtomicInteger(0); // 記錄衝突發生次數

    public synchronized void increment() {
        boolean lockAcquired = Thread.holdsLock(this);

        if (!lockAcquired) {
            contentionCount.incrementAndGet(); // 記錄無法立即獲取鎖的次數
        }

        value++;
        System.out.println(Thread.currentThread().getName() + " - Value: " + value);
    }

    public int getContentionCount() {
        return contentionCount.get();
    }

    public int getValue() {
        return value;
    }
}

public class SynchronizedDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedResource resource = new SharedResource();

        int NUM_THREADS = 4;
        int ITERATIONS = 10000;
        long startTime = System.nanoTime();

        Runnable task = () -> {
            for (int i = 0; i < ITERATIONS; i++) {
                resource.increment();
            }
        };

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        Thread t3 = new Thread(task, "Thread 3");
        Thread t4 = new Thread(task, "Thread 4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1_000_000; // 轉換為毫秒

        // 計算吞吐量 ops/sec
        int totalOperations = NUM_THREADS * ITERATIONS;
        double throughput = (totalOperations * 1_000.0) / executionTime;

        // 獲取 CPU 使用率
        double cpuUsage = getCpuUsage(startTime, endTime);

        System.out.println("\n====== 結果 ======");
        System.out.println("最終數值: " + resource.getValue());
        System.out.println("執行時間 (ms): " + executionTime);
        System.out.println("吞吐量 (ops/sec): " + throughput);
        System.out.println("鎖衝突發生次數: " + resource.getContentionCount());
        System.out.println("CPU 使用率 (%): " + cpuUsage);

    }

    // 取得 CPU 使用率
    private static double getCpuUsage(long startTime, long endTime) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long cpuTimeStart = threadMXBean.getCurrentThreadCpuTime();

        long cpuTimeEnd = threadMXBean.getCurrentThreadCpuTime();
        long totalCpuTime = (cpuTimeEnd - cpuTimeStart) / 1_000_000; // 轉換為毫秒
        long elapsedTime = (endTime - startTime) / 1_000_000; // 總運行時間毫秒

        return (totalCpuTime * 100.0) / elapsedTime; // 計算 CPU 使用率
    }
}
