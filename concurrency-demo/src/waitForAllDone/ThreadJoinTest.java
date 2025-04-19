package waitForAllDone;

public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            System.out.println("t1 done");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 running...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }
            System.out.println("t2 done");
        });

        t1.start();
        t2.start();

        // 等待 t1 和 t2 都執行完
        t1.join();
        t2.join();

        System.out.println("All threads finished, main thread continues.");
    }
}
