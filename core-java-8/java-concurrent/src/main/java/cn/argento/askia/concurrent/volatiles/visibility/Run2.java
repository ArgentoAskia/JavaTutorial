package cn.argento.askia.concurrent.volatiles.visibility;

public class Run2 {
    public static void main(String[] args) {
        PrintString2 printString2 = new PrintString2();
        // 启动新的线程运行PrintString2
        new Thread(printString2, "printStringThread").start();
        // 进行暂停是为了让printStringThread线程多打印点东西
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("停止打印退出循环！stopThread = " + Thread.currentThread().getName());
        printString2.setContinuePrint(false);
    }
}
