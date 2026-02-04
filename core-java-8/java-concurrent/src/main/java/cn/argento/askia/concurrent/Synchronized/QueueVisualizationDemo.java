package cn.argento.askia.concurrent.Synchronized;

public class QueueVisualizationDemo {
    public static void main(String[] args) throws InterruptedException {
        // 创建可监控的Semaphore
        MonitorableSemaphore semaphore = new MonitorableSemaphore(1, false);

        // 启动8个线程
        for (int i = 0; i < 8; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.printf("Thread-%d 准备获取许可...%n", id);
                    semaphore.acquire();
                    System.out.printf("Thread-%d 开始执行任务%n", id);
                    Thread.sleep(2000); // 模拟耗时任务
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release();
                }
            }, "Thread-" + i).start();

            Thread.sleep(300); // 控制启动节奏
        }
    }
}
