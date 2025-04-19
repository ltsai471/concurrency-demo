public class SynchronizedTest {
    public synchronized void instanceMethod() {
        System.out.println(Thread.currentThread().getName() + " 進入 instanceMethod");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println(Thread.currentThread().getName() + " 離開 instanceMethod");
    }

    public static synchronized void staticMethod() {
        System.out.println(Thread.currentThread().getName() + " 進入 staticMethod");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println(Thread.currentThread().getName() + " 離開 staticMethod");
    }

    public static void main(String[] args) {
        SynchronizedTest obj1 = new SynchronizedTest();
        SynchronizedTest obj2 = new SynchronizedTest();

        // 情境一：兩個執行緒呼叫不同對象的 instanceMethod（不會互鎖）
        new Thread(() -> obj1.instanceMethod(), "T1").start();
        new Thread(() -> obj2.instanceMethod(), "T2").start();

        // 情境二：兩個執行緒呼叫 staticMethod（會互鎖）
//         new Thread(() -> SynchronizedTest.staticMethod(), "T1").start();
//         new Thread(() -> SynchronizedTest.staticMethod(), "T2").start();
    }
}
