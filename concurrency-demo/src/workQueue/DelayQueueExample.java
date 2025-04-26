package workQueue;

import java.util.concurrent.*;

public class DelayQueueExample {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedTask> queue = new DelayQueue<>();

        queue.put(new DelayedTask("任務 A", 3000));
        queue.put(new DelayedTask("任務 B", 1000));
        queue.put(new DelayedTask("任務 C", 2000));

        for (int i = 0; i < 3; i++) {
            DelayedTask task = queue.take(); // 等待延遲到期
            System.out.println("執行：" + task.name + " 時間: " + System.currentTimeMillis());
        }
    }

    static class DelayedTask implements Delayed {
        String name;
        long startTime;

        public DelayedTask(String name, long delayMillis) {
            this.name = name;
            this.startTime = System.currentTimeMillis() + delayMillis;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        public int compareTo(Delayed other) {
            return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), other.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}

