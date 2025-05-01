package P1114_PrintInOrder;

import java.util.concurrent.atomic.AtomicInteger;

///
/// 優點：
/// 1. 無鎖實作: 不需要 synchronized、wait、notify，效能好
/// 2. 寫法簡潔: AtomicInteger 搭配自旋控制邏輯清楚
///
/// 缺點（重要）：
/// 1. 忙等耗 CPU: 如果任務很短，可能沒問題；但如果等待時間長，這種寫法會白白浪費 CPU
/// 2. 不適合高延遲場景: 若 second/third 執行時間需等待 I/O 或資料，大量忙等是資源浪費
///
public class P1114_Atomic {
    private AtomicInteger state = new AtomicInteger(0);

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        state.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (state.get() < 1) {
            // busy-wait 等 first 執行完
        }
        printSecond.run();
        state.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (state.get() < 2) {
            // busy-wait 等 first 執行完
        }
        printThird.run();
    }
}
