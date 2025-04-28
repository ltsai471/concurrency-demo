package threadJoin;

import java.util.concurrent.TimeUnit;

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        Thread prev = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domino(prev), String.valueOf(i)); //new Thread(task, name);
            thread.start();
            prev = thread;
        }
        //doSomething
        TimeUnit.SECONDS.sleep(5);
        System.out.println("main: terminate");
    }

    static class Domino implements Runnable {
        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " join");
                thread.join();
            } catch (InterruptedException e) {

            }
            System.out.println(Thread.currentThread().getName() + " terminate");
        }
    }

}
