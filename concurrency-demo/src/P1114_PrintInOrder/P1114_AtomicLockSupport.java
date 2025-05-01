package P1114_PrintInOrder;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class P1114_AtomicLockSupport {
    private AtomicInteger state = new AtomicInteger(0);

    Thread t2;
    Thread t3;

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        state.incrementAndGet();
        LockSupport.unpark(t2); //喚醒指定線程
    }

    public void second(Runnable printSecond) throws InterruptedException {
        t2 = Thread.currentThread();
        while (state.get() < 1) {
            LockSupport.park(); //掛起等待
        }
        printSecond.run();
        state.incrementAndGet();
        LockSupport.unpark(t3);
    }

    public void third(Runnable printThird) throws InterruptedException {
        t3 = Thread.currentThread();
        while (state.get() < 2) {
            LockSupport.park();
        }
        printThird.run();
    }
}
