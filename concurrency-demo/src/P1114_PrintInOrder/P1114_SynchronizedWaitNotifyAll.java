package P1114_PrintInOrder;

//在 Java 中，synchronized 有兩個作用：
//1. 互斥（Mutual Exclusion）：同一時間只能有一個執行緒進入同步區塊。
//2. 記憶體可見性（Memory Visibility）：進入 synchronized 時會讀主記憶體，退出時會寫回主記憶體，等同於 volatile 的效果。
public class P1114_SynchronizedWaitNotifyAll {
    //Q. state 需要用 volatile 嗎?
    //A. 不需要，因為你已經用 synchronized 正確保證了可見性與原子性。
    private int state = 0;

    public synchronized void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        state = 1;
        // notify() 可能叫錯人, 所以要用 notifyAll()
        notifyAll();  // 通知等待 second() 的執行緒
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
        while (state < 1) {
            wait();  // 等 first() 執行完
        }
        printSecond.run();
        state = 2;
        notifyAll();  // 通知等待 third() 的執行緒
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
        while (state < 2) {
            wait();  // 等 second() 執行完
        }
        printThird.run();
    }
}

