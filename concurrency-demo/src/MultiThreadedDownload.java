/// 題目描述
/// 你正在設計一個多執行緒的下載管理器，最多允許 N 個下載執行緒同時下載檔案。
/// 每個檔案下載需要花費 T 毫秒。當執行緒數量超過 N 時，必須等待有其他下載完成後才能開始新的下載。
///
/// 請實作一個類 DownloadManager，並提供方法：
/// void download(String fileName)：模擬下載檔案（使用 Thread.sleep(T) 模擬下載時間），同時確保最多只有 N 個下載執行緒在運行。
/// (簡單版) 請實作 download() 方法，確保不超過 N 個執行緒同時下載。
/// (進階版) 增加 公平性，確保請求先到的檔案先下載 (Semaphore(true))。
/// (挑戰版) 記錄每個檔案的下載時間，並輸出吞吐量（下載完成的檔案數量 / 總執行時間）。

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

class DownloadManager {
    private final Semaphore semaphore;
    private final int downloadTime;
    private final ConcurrentLinkedQueue<Long> completionTimes = new ConcurrentLinkedQueue<>();

    public DownloadManager(int maxConcurrentDownloads, int downloadTime) {
        this.semaphore = new Semaphore(maxConcurrentDownloads, true);
        this.downloadTime = downloadTime;
    }

    public void download(String fileName) {
        long startTime = System.nanoTime();
        try {
            semaphore.acquire();
            System.out.println("Downloading file: " + fileName);
            Thread.sleep(downloadTime);
            System.out.println("Finished downloading file: " + fileName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            long endTime = System.nanoTime();
            completionTimes.add((endTime - startTime) / 1_000_000);
        }
    }

    public double getThroughput(long totalExecutionTime) {
        return completionTimes.size() * 1000 / totalExecutionTime;
    }

    public ConcurrentLinkedQueue<Long> getCompletionTimes() {
        return completionTimes;
    }
}

public class MultiThreadedDownload {
    public static void main(String[] args) throws InterruptedException {
        int N = 2;  // 最大同時下載數
        int T = 2000;  // 下載時間 2 秒
        String[] files = {"file1.txt", "file2.txt", "file3.txt", "file4.txt"};

        DownloadManager manager = new DownloadManager(N, T);
        long overallStartTime = System.nanoTime();
//        for (String file : files) {
//            new Thread(() -> manager.download(file)).start();
//        }
        Thread[] threads = new Thread[files.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> manager.download(Thread.currentThread().getName()));
            threads[i].setName(files[i]);
            threads[i].start();
        }

        for(Thread thread:threads){
            thread.join();
        }
        long overallEndTime = System.nanoTime();
        long totalExecutionTime = (overallEndTime - overallStartTime) / 1_000_000;
        double throughput = manager.getThroughput(totalExecutionTime);
        System.out.println("\n====== 結果 ======");
        System.out.println("總執行時間: " + totalExecutionTime + " ms");
        System.out.println("下載完成的檔案數量: " + manager.getCompletionTimes().size());
        System.out.println("吞吐量 (ops/sec): " + throughput);
        System.out.println("\n📌 各檔案下載時間（ms）：");
        for (Long time : manager.getCompletionTimes()) {
            System.out.println(time);
        }
    }
}
