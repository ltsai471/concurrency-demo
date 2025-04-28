package parallelAndSequentialTasks;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 并行执行 func1, func2, func3
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> func1());
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> func2());
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> func3());

        // 等待所有并行任务完成
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);
        allFutures.get(); // 阻塞直到完成

        // 顺序执行 func4 和 func5
        func4();
        func5();
    }

    private static void func1() {
        System.out.println("执行 func1");
    }

    private static void func2() {
        System.out.println("执行 func2");
    }

    private static void func3() {
        System.out.println("执行 func3");
    }

    private static void func4() {
        System.out.println("执行 func4");
    }

    private static void func5() {
        System.out.println("执行 func5");
    }
}
