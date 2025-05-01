package P1114_PrintInOrder;

///
/// 给你一个类：
///
/// public class Foo {
///   public void first() { print("first"); }
///   public void second() { print("second"); }
///   public void third() { print("third"); }
/// }
/// 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
///
/// 线程 A 将会调用 first() 方法
/// 线程 B 将会调用 second() 方法
/// 线程 C 将会调用 third() 方法
/// 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
///
/// 提示：
///
/// 尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
/// 你看到的输入格式主要是为了确保测试的全面性。
///
///
/// 示例 1：
///
/// 输入：nums = [1,2,3]
/// 输出："firstsecondthird"
/// 解释：
/// 有三个线程会被异步启动。输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。正确的输出是 "firstsecondthird"。
/// 示例 2：
///
/// 输入：nums = [1,3,2]
/// 输出："firstsecondthird"
/// 解释：
/// 输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。正确的输出是 "firstsecondthird"。
///
/// 用syncronized的作法
///
public class P1114_Test {
    public static void main(String[] args) {
//        Foo foo = new Foo(); //original

//        SynchronizedWaitNotifyAll foo = new SynchronizedWaitNotifyAll();
//        P1114_CountDownLatch foo = new P1114_CountDownLatch();
//        P1114_Semaphore foo = new P1114_Semaphore();
//        P1114_Atomic foo = new P1114_Atomic();
        P1114_ReentrantLockCondition foo = new P1114_ReentrantLockCondition();

        Runnable printFirst = () -> System.out.print("first");
        Runnable printSecond = () -> System.out.print("second");
        Runnable printThird = () -> System.out.print("third");

        Thread t1 = new Thread(() -> {
            try {
                foo.first(printFirst);
            } catch (InterruptedException ignored) {}
        });

        Thread t2 = new Thread(() -> {
            try {
                foo.second(printSecond);
            } catch (InterruptedException ignored) {}
        });

        Thread t3 = new Thread(() -> {
            try {
                foo.third(printThird);
            } catch (InterruptedException ignored) {}
        });

        // 隨機啟動順序，模擬 OS 調度不確定性
        t2.start();
        t3.start();
        t1.start();
    }
}





