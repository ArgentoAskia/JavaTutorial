package cn.argento.askia.cmd;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class ExecTest {

    private static final String TEST_EXEC_PATH = "E:\\OpenSourceProjects\\java-project\\core-java-8\\javax-tools\\src\\main\\resources\\cn\\argento\\askia\\cmd\\test-exec.bat";

    public static void main(String[] args) {
        System.out.println("Java exec() 方法测试程序");
        System.out.println("==========================");

        // 测试1：基础调用 - 执行批处理并等待完成
//        testBasicExec();

        // 测试2：带参数调用
//        testWithArguments();

        // 测试3：完全交互式调用（进程继承父进程IO）[citation:4]
//        testInteractiveExec();

        // 测试4：带超时控制的调用
//        testWithTimeout();

        // 测试5：代码形式参与交互
        testRunWithCode();
    }

    /**
     * 基础调用测试
     * 使用Runtime.exec()简单执行命令
     */
    public static void testBasicExec() {
        System.out.println("\n【测试1】基础调用测试");
        try {
            Process process = Runtime.getRuntime().exec(
                    "cmd /c start " + TEST_EXEC_PATH
            );

            // 等待进程完成
            int exitCode = process.waitFor();
            System.out.println("进程执行完成，退出码: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 带参数调用测试
     * 向批处理传递参数
     */
    public static void testWithArguments() {
        System.out.println("\n【测试2】带参数调用测试");
        try {
            String[] command = {
                    "cmd", "/c", TEST_EXEC_PATH, "参数1", "参数2", "带空格的参数"
            };

            Process process = Runtime.getRuntime().exec(command);

            // 读取输出
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("输出: " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("退出码: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 完全交互式调用测试
     * 使用ProcessBuilder实现真正的交互式体验 [citation:4]
     */
    public static void testInteractiveExec() {
        System.out.println("\n【测试3】交互式调用测试");
        System.out.println("注意：此模式下IO会直接继承，请直接与批处理交互");
        System.out.println("5秒后开始...");

        try {
            Thread.sleep(5000);

            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", TEST_EXEC_PATH);

            // 关键：继承父进程的IO，实现真正交互 [citation:4]
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = pb.start();
            int exitCode = process.waitFor();
            System.out.println("交互式进程结束，退出码: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 带超时控制的调用测试
     * 使用超时避免进程无限等待 [citation:10]
     */
    public static void testWithTimeout() {
        System.out.println("\n【测试4】超时控制测试");
        System.out.println("将执行长时间运行任务，但只等待10秒");

        try {
            Process process = Runtime.getRuntime().exec(
                    "cmd /c start " + TEST_EXEC_PATH + " 4"  // 选项4是长时间运行
            );

            // 等待最多10秒 [citation:10]
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);

            if (finished) {
                System.out.println("进程在超时前完成，退出码: " + process.exitValue());
            } else {
                System.out.println("进程超时，强制终止");
                process.destroy();  // 终止进程
                // 可选：等待进程真正终止
                if (!process.waitFor(3, TimeUnit.SECONDS)) {
                    process.destroyForcibly();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void testRunWithCode(){
        System.out.println("\n【测试5】代码中直接调用输入输出测试");
        System.out.println("注意：此模式下我们会使用代码实现往流中输入1和2，同时捕获输出信息");
        System.out.println("5秒后开始...");
        try {
            Thread.sleep(5000);
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", TEST_EXEC_PATH);
            Process start = pb.start();
            // 读取输出
            new Thread(() -> {
                try{
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(start.getInputStream())
                    );
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }).start();

            new Thread(()->{
                try{
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(start.getOutputStream())
                    );
                    writer.write("2");
                    // 模拟按下回车键
                    writer.newLine();
                    writer.flush();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
            Thread.currentThread().join();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
