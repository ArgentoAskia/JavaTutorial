package cn.argento.askia.concurrent.Synchronized;

import java.util.concurrent.Semaphore;

/**
 * 此Demo介绍了如何使用Semaphore
 */
public class SemDemo {
    public static void main(String[] args) {
        // 设置资源访问只有一个许可证
        Semaphore semaphore = new Semaphore(1, true);
        new Thread(new IncThread("A", semaphore)).start();
        new Thread(new DecThread("B", semaphore)).start();
    }
}

// 共享资源
class Shared{
    static int counter = 0;
}

class IncThread extends Thread{
    String name;
    Semaphore semaphore;

    public IncThread(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Starting " + name);

        // 获取许可证一定要使用try
        try{
            System.out.println(name + " is waiting for a permit.");
            semaphore.acquire();
            System.out.println(name + " gets a permit.");
            // 如果许可证获取了，则证明可以访问资源了
            for (int i = 0; i < 5; i++){
                Shared.counter++;
                System.out.println(name + ": " + Shared.counter);
                // 正常来说只要线程Interrupted之后，其他线程就会尝试获取许可证
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 释放锁
        System.out.println(name + " releases a permit.");
        semaphore.release();
    }
}


class DecThread extends Thread{
    String name;
    Semaphore semaphore;

    public DecThread(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Starting " + name);

        // 获取许可证一定要使用try
        try{
            System.out.println(name + " is waiting for a permit.");
            semaphore.acquire();
            System.out.println(name + " gets a permit.");
            // 如果许可证获取了，则证明可以访问资源了
            for (int i = 0; i < 5; i++){
                Shared.counter--;
                System.out.println(name + ": " + Shared.counter);
                // 正常来说只要线程Interrupted之后，其他线程就会尝试获取许可证
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 释放锁
        System.out.println(name + " releases a permit.");
        semaphore.release();
    }
}
