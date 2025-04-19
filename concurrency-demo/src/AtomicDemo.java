import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    private static final int THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 100000;
    private static AtomicInteger atomicCounter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        long start, end;

        atomicCounter.set(0);
        start = System.nanoTime();
        runTest(() -> atomicCounter.incrementAndGet());
        end = System.nanoTime();
        System.out.println("Atomic 計數: " + atomicCounter.get() + "，耗時: " + (end - start) / 1_000_000 + " ms");
    }

    private static void runTest(Runnable task) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    task.run();
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
