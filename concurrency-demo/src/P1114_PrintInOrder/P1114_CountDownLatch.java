package P1114_PrintInOrder;

import java.util.concurrent.CountDownLatch;

public class P1114_CountDownLatch {
    //CountDownLatch 適合「等待某個事件發生之後再繼續」
    private CountDownLatch latch1 = new CountDownLatch(1);
    private CountDownLatch latch2 = new CountDownLatch(1);

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        //將 latch1 的計數器減 1，如果減到 0，就喚醒所有等待的執行緒。
        latch1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        //latch1「等待」直到 count == 0。
        latch1.await();
        printSecond.run();
        latch2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        latch2.await();
        printThird.run();
    }
}
