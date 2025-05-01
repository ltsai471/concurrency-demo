package P1114_PrintInOrder;

import java.util.concurrent.Semaphore;

public class P1114_Semaphore {
    //Semaphore 可以用來控制資源訪問順序
    private Semaphore s2 = new Semaphore(0); //初始為 0, 表示沒有「通行證」
    private Semaphore s3 = new Semaphore(0);

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        //讓 s2 的計數器加 1，並喚醒一個正在 s2.acquire() 的執行緒
        s2.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        //s2嘗試獲得一個「許可證」，沒有就阻塞等。
        s2.acquire();
        printSecond.run();
        s3.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        s3.acquire();
        printThird.run();
    }
}
