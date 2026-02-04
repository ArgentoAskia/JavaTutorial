package cn.argento.askia.concurrent.volatiles.visibility;

// 创建一个Runnable任务
public class PrintString2 extends PrintString implements Runnable{
    @Override
    public void run() {
        printStringMethod();
    }
}
