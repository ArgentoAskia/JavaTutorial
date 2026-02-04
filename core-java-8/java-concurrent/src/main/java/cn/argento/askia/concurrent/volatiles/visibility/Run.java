package cn.argento.askia.concurrent.volatiles.visibility;

public class Run {
    public static void main(String[] args) {
        PrintString printStringService = new PrintString();
        // 该方法会进入while循环阻塞，因为只在main线程中运行
        printStringService.printStringMethod();
        // 所以无法执行下面的sout
        System.out.println("停止打印退出循环！stopThread = " + Thread.currentThread().getName());
        printStringService.setContinuePrint(false);
    }
}
