package cn.argento.askia;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 此Demo演示了Java如何在代码层面调用外部控制台程序，执行CMD指令，并将结果通过流的方式读取出来！
 */
public class CMDDemo {
    public static void main(String[] args) throws IOException {
        final Runtime runtime = Runtime.getRuntime();
//        String cmds = "cmd /C E:\\download\\BBDown\\BBDown.exe --work-dir E:\\download\\BBDown BV1BP411s7Rz --debug";
//        String cmds = "C:\\Users\\asus\\Desktop\\tcping64.exe -t 127.0.0.1";

        File tcping64Exe = new File("Java-Compiler/src/main/resources/tcping64.exe");
        final String tcping64ExeAbsolutePath = tcping64Exe.getAbsolutePath();
        System.out.println("tcping64.exe的位置：" + tcping64ExeAbsolutePath);

        // 拼接参数
        String cmds = tcping64ExeAbsolutePath + " -t 127.0.0.1";
        final Process exec = runtime.exec(cmds);
//        final Process exec = runtime.exec("delete 我找到了佳一妈妈最油腻的视频，但是...._fix.mp4");
        final InputStream inputStream = exec.getInputStream();
        byte[] bytes = new byte[1024];
        int read = 0;
        while((read = inputStream.read(bytes)) != -1){
            final String string = new String(bytes, 0, read, "GBK");
            System.out.print(string);
        }
    }
}
