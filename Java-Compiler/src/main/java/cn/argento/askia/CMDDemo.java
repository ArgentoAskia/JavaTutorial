package cn.argento.askia;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CMDDemo {
    public static void main(String[] args) throws IOException {
        final Runtime runtime = Runtime.getRuntime();
//        String cmds = "cmd /C E:\\download\\BBDown\\BBDown.exe --work-dir E:\\download\\BBDown BV1BP411s7Rz --debug";
        String cmds = "C:\\Users\\asus\\Desktop\\tcping64.exe -t 127.0.0.1";
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
