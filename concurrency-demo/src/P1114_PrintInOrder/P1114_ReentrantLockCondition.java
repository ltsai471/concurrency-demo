package P1114_PrintInOrder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/// 特性
/// 1. 支援多條 Condition: 比 wait/notify 更靈活，能針對不同條件設立「信號量」
/// 2. 精準喚醒: signal() 只喚醒在該 Condition 上等待的執行緒，避免誤喚醒其他
/// 3. 不會發生「虛假喚醒未重試」問題: 因為搭配 while 判斷條件重試，保證正確性
/// 4. 更適合可擴充場景: 可輕鬆擴展成 1→2→3→...→n 的流程控制
///
///
/// 屬於 synchronized + wait/notify 的進階寫法，具備更細緻控制能力。
/// ReentrantLock + Condition 提供了可重入鎖 + 精準阻塞/喚醒能力，是構建複雜併發流程的最佳方案之一。
public class P1114_ReentrantLockCondition {
    private final Lock lock = new ReentrantLock();
    private final Condition secondCanRun = lock.newCondition();
    private final Condition thirdCanRun = lock.newCondition();

    private int state = 0;

    public void first(Runnable printFirst) throws InterruptedException {
        //lock.lock() 和 lock.unlock() 包住的是整個臨界區
        // 保證對 state 和 await/signal 操作的原子性與可見性
        lock.lock();
        try {
            printFirst.run();
            state = 1;
            secondCanRun.signal();
        } finally {
            lock.unlock();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        lock.lock();
        try {
            while (state < 1) {
                secondCanRun.await();
            }
            printSecond.run();
            state = 2;
            //喚醒一個正在 thirdCanRun.await() 的執行緒，讓它繼續執行。
            //作用等同於 notify()，但針對的是某一組特定條件下等待的執行緒。
            thirdCanRun.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

    }

    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        try {
            while (state < 2) {
                thirdCanRun.await();
            }
            printThird.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    /// notify()（Object）
    /// 1. 喚醒的是在該Object上 wait() 的執行緒
    /// 2. 一個鎖只能有一個 wait/notify
    /// 3. 無法控制喚醒範圍
    ///
    /// Condition.signal()
    /// 1. 喚醒的是該 Condition 上 await() 的執行緒
    /// 2. 一個 ReentrantLock 可以建立多個 Condition，更靈活
    /// 3. 可以控制哪一組等待者被喚醒

}
