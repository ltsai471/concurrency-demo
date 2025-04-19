import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private static int counter = 0;
    private static final int THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 100000;
    private static ReentrantLock lock = new ReentrantLock();

    private static void incrementLock() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start, end;

        counter = 0;
        start = System.nanoTime();
        runTest(ReentrantLockDemo::incrementLock);
        end = System.nanoTime();
        System.out.println("ReentrantLock 計數: " + counter + "，耗時: " + (end - start) / 1_000_000 + " ms");
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
