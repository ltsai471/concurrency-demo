package createSingleThread;

public class SingleThreadExample {
    //stop flag
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        //create thread
        Thread thread = new Thread(() -> {
            while (running) {
                System.out.println("running...");
                try {
                    Thread.sleep(500);// 模擬工作
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();// 重新設定中斷標誌
                    break;
                }
            }
            System.out.println("thread stop");
        });

        //run
        thread.start();
        Thread.sleep(2000); // 主線程等2秒
        running = false;
        thread.join(); // 等待子線程結束
        System.out.println("main thread end");

    }

}

