package cn.argento.askia.concurrent.Synchronized;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公平模式（fair=true）
 * FIFO队列：线程按照调用 acquire() 的顺序排队等待
 * 先到先得：等待时间最长的线程优先获得许可
 * 避免饥饿：确保所有线程最终都能获得执行机会

 * // 工作流程
 * Thread-1 调用 acquire() → 获取许可
 * Thread-2 调用 acquire() → 进入等待队列（第1个）
 * Thread-3 调用 acquire() → 进入等待队列（第2个）
 * Thread-4 调用 acquire() → 进入等待队列（第3个）
 *
 * 当有许可释放时：
 * → Thread-2 获得许可（等待最久）
 * → Thread-3 获得许可（第二久）
 * → Thread-4 最后获得
 *
 *
 * 非公平模式（fair=false，默认）
 * 允许插队：新线程可以"挤到"等待队列前面
 * 性能更高：减少线程切换开销，吞吐量更高
 * 可能饥饿：某些线程可能长期得不到执行
 *
 * // 工作流程
 * Thread-1 调用 acquire() → 获取许可
 * Thread-2 调用 acquire() → 进入等待队列
 * Thread-3 调用 acquire() → 进入等待队列
 *
 * Thread-4 新调用 acquire() → 直接插队获得许可（即使Thread-2在等待）
 */
public class FairnessSemDemo {
    public static void main(String[] args) throws InterruptedException {
        // 对比参数
        final int THREAD_COUNT = 8;
        final int PERMITS = 1;  // 同时允许2个线程执行

        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          Semaphore 公平性对比测试                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // 测试非公平模式
        System.out.println("========== 非公平模式 (fair=false) ==========");
        testFairness("非公平", new Semaphore(PERMITS, false), THREAD_COUNT);

        Thread.sleep(3000); // 等待前一轮完全结束

        // 测试公平模式
        System.out.println("\n\n========== 公平模式 (fair=true) ==========");
        testFairness("公平", new Semaphore(PERMITS, true), THREAD_COUNT);
    }

    static void testFairness(String modeName, Semaphore semaphore, int threadCount)
            throws InterruptedException {

        AtomicInteger executionOrder = new AtomicInteger(0);

        // 创建并启动线程，确保严格按顺序启动
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            new Thread(() -> {
                // 记录开始等待时间
                long waitStart = System.currentTimeMillis();
                System.out.printf("【Thread-%d】 等待获取许可... (启动顺序: %d)\n",
                        threadId, threadId);

                try {
                    // 获取许可
                    semaphore.acquire();
                    long waitEnd = System.currentTimeMillis();
                    int order = executionOrder.incrementAndGet();

                    // 打印关键信息
                    System.out.printf("【Thread-%d】 ✓ 获得许可！等待时间: %dms | 实际执行顺序: %d\n",
                            threadId, (waitEnd - waitStart), order);

                    // 模拟工作（持有许可1秒）
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.printf("【Thread-%d】 → 释放许可\n", threadId);
                    semaphore.release();
                }
            }).start();

            // 关键：确保线程按 ID 顺序启动
            Thread.sleep(100);
        }

        // 等待所有线程完成
        Thread.sleep((threadCount / 2 + 1) * 1000L);
    }
}
