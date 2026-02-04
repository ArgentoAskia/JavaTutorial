package cn.argento.askia.concurrent.Synchronized;

import java.util.concurrent.Semaphore;

/**
 * 此Demo演示了如何基于两个Semaphore实现同步get/set, 即每一个Set比配对一个Get
 */
public class SyncGetSet {
    public static void main(String[] args) {
        Q q = new Q();
        new Thread(new Consumer(q), "Consumer").start();
        new Thread(new Producer(q), "Producer").start();
    }
}


class Q{
    int n;
    static Semaphore semaphoreCon = new Semaphore(0);
    static Semaphore semaphoreProd = new Semaphore(1);

    void get(){
        try{
            // 尝试获取消费者（如果是第一次调用则百分百阻塞，因为semaphoreCon初始化被设置为0）
            // 因此保证了只有调用了set()才能Get
            semaphoreCon.acquire();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Got: " + n);
        System.out.println();
        // 尝试释放生产者
        semaphoreProd.release();
    }

    void put(int n){
        try{
            semaphoreProd.acquire();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        this.n = n;
        System.out.println("Put: " + n);
        semaphoreCon.release();
    }
}


class Producer implements Runnable{
    Q q;
    public Producer(Q q){
        this.q = q;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            q.put(i);
        }
    }
}


class Consumer implements Runnable{
    Q q;
    public Consumer(Q q){
        this.q = q;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            q.get();
        }
    }
}