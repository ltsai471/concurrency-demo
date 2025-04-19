package waitForAllDone;

import java.util.List;
import java.util.concurrent.*;

public class CallableTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 建立多個 Callable 任務
        List<Callable<String>> tasks = List.of(
                () -> {
                    Thread.sleep(1000);
                    return "Task A done";
                },
                () -> {
                    Thread.sleep(1500);
                    return "Task B done";
                },
                () -> {
                    Thread.sleep(500);
                    return "Task C done";
                }
        );

        // 提交任務並取得 Future 列表
        List<Future<String>> futures = executor.invokeAll(tasks);

        // 等待所有任務完成，並收集結果
        for (Future<String> future : futures) {
            String result = future.get(); // 阻塞直到任務完成
            System.out.println(result);
        }

        // 關閉執行器
        executor.shutdown();

        System.out.println("All tasks completed, main thread continues.");
    }
}
