package cn.argento.askia.concurrent.Synchronized;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

/**
 * 可监控的Semaphore包装器
 * 能实时查看排队线程和队列长度
 */
public class MonitorableSemaphore {
    private final Semaphore semaphore;
    private final Map<Thread, Long> waitingThreads = new ConcurrentHashMap<>();
    private final AtomicInteger queueLength = new AtomicInteger(0);

    public MonitorableSemaphore(int permits, boolean fair) {
        this.semaphore = new Semaphore(permits, fair);
    }

    /**
     * 带监控的acquire方法
     */
    public void acquire() throws InterruptedException {
        Thread currentThread = Thread.currentThread();

        // 记录开始等待
        waitingThreads.put(currentThread, System.currentTimeMillis());
        queueLength.incrementAndGet();

        try {
            System.out.printf("[排队监控] %s 加入队列 | 当前队列长度: %d%n",
                    currentThread.getName(), queueLength.get());

            semaphore.acquire();

            // 获取成功，移除等待记录
            long waitTime = System.currentTimeMillis() - waitingThreads.get(currentThread);
            waitingThreads.remove(currentThread);

            System.out.printf("[排队监控] %s 获得许可！等待时间: %dms | 剩余队列: %s%n",
                    currentThread.getName(), waitTime, getQueueSnapshot());

        } finally {
            queueLength.decrementAndGet();
        }
    }

    public void release() {
        semaphore.release();
        System.out.printf("[排队监控] %s 释放许可 | 当前等待队列: %s%n",
                Thread.currentThread().getName(), getQueueSnapshot());
    }

    /**
     * 获取队列快照（不包含已获得许可的线程）
     */
    public String getQueueSnapshot() {
        StringBuilder sb = new StringBuilder("[");
        waitingThreads.forEach((thread, startTime) -> {
            long waitTime = System.currentTimeMillis() - startTime;
            sb.append(String.format("%s(%ds), ", thread.getName(), waitTime/1000));
        });

        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // 移除最后一个逗号
        }
        sb.append("]");
        return sb.toString();
    }

    public int getQueueLength() {
        return queueLength.get();
    }

    // 包装其他必要方法
    public int availablePermits() {
        return semaphore.availablePermits();
    }
}
