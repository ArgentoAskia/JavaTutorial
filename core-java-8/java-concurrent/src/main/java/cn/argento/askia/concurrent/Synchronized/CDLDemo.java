package cn.argento.askia.concurrent.Synchronized;

import java.util.concurrent.CountDownLatch;

/**
 * 此Demo演示CountDownLatch的使用
 */
public class CDLDemo {

    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(5);
        System.out.println("Starting...");
        new Thread(new MyThread(cdl)).start();
        try {
            cdl.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
}

class MyThread implements Runnable {
    private CountDownLatch cdl;
    public MyThread(CountDownLatch cdl) {
        this.cdl = cdl;
    }
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            cdl.countDown(); // 下降
        }
    }
}
