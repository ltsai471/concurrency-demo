import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();
    private final AtomicInteger atomicBalance = new AtomicInteger(0);

    public BankAccount(int balance) {
        this.balance = balance;
        this.atomicBalance.set(balance);
    }

    public synchronized void transferSync(BankAccount target, int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            target.balance += amount;
        }
    }

    public void transferLock(BankAccount target, int amount) {
        lock.lock();
        try {
            if (this.balance >= amount) {
                this.balance -= amount;
                target.balance += amount;
            }
        } finally {
            lock.unlock();
        }
    }

    public void transferAtomic(BankAccount target, int amount) {
        while (true) {
            int current = this.atomicBalance.get();
            int targetCurrent = target.atomicBalance.get();
            if (current >= amount) {
                if (this.atomicBalance.compareAndSet(current, current - amount)) {
                    target.atomicBalance.addAndGet(amount);
                    break;
                }
            } else {
                break;
            }
        }
    }

    public int getBalance() {
        return balance;
    }

    public int getAtomicBalance() {
        return atomicBalance.get();
    }
}

public class BankSimulation {
    private static final int THREADS = 10;
    private static final int TRANSFERS_PER_THREAD = 100000;
    private static final int TRANSFER_AMOUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        BankAccount accountA = new BankAccount(1000000);
        BankAccount accountB = new BankAccount(1000000);

        long start, end;

        start = System.nanoTime();
        BankAccount finalAccountA = accountA;
        BankAccount finalAccountB = accountB;
        runTest(() -> finalAccountA.transferSync(finalAccountB, TRANSFER_AMOUNT));
        end = System.nanoTime();
        System.out.println("Synchronized 轉帳後: " + accountA.getBalance() + "，耗時: " + (end - start) / 1_000_000 + " ms");

        accountA = new BankAccount(1000000);
        accountB = new BankAccount(1000000);
        start = System.nanoTime();
        BankAccount finalAccountA1 = accountA;
        BankAccount finalAccountB1 = accountB;
        runTest(() -> finalAccountA1.transferLock(finalAccountB1, TRANSFER_AMOUNT));
        end = System.nanoTime();
        System.out.println("ReentrantLock 轉帳後: " + accountA.getBalance() + "，耗時: " + (end - start) / 1_000_000 + " ms");

        accountA = new BankAccount(1000000);
        accountB = new BankAccount(1000000);
        start = System.nanoTime();
        BankAccount finalAccountA2 = accountA;
        BankAccount finalAccountB2 = accountB;
        runTest(() -> finalAccountA2.transferAtomic(finalAccountB2, TRANSFER_AMOUNT));
        end = System.nanoTime();
        System.out.println("Atomic 轉帳後: " + accountA.getAtomicBalance() + "，耗時: " + (end - start) / 1_000_000 + " ms");
    }

    private static void runTest(Runnable task) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < TRANSFERS_PER_THREAD; j++) {
                    task.run();
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
