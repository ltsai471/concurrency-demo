package waitAndNotify;

public class WaitNotifyExample {
    //lock
    private static final Object lock = new Object();
    private static boolean ready = false;

    public static void main(String[] args) {
        //create consumer
        Thread consumer = new Thread(() -> {
            synchronized (lock) {
                while (!ready) {
                    try {
                        System.out.println("consumer is waiting...");
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("consumer run...");
            }
        });

        //create producer
        Thread producer = new Thread(() -> {
            synchronized (lock) {
                System.out.println("producer finish");
                ready = true;
                lock.notify();
            }
        });

        //run
        consumer.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        producer.start();

    }
}
