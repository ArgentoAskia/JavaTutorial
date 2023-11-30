package cn.argento.askia;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class URLCoderDemo {
    public static void main(String[] args) throws IOException {
        String url = "https://www.baidu.com/s?ie=utf-8&wd=%E9%98%BB%E5%A1%9E%E5%92%8C%E9%9D%9E%E9%98%BB%E5%A1%9E%E5%8C%BA%E5%88%AB";
        // 解码，第一个参数传递要解码的url，第二个指定编码！
        String decode = URLDecoder.decode(url, "utf-8");
        System.out.println(decode);
        // 编码
        String encode = URLEncoder.encode(decode, "utf-8");
        System.out.println(encode);
        String gbk = URLEncoder.encode(decode, "GBK");
        System.out.println(gbk);
    }

//        final Process exec = Runtime.getRuntime().exec("E:\\RTE\\JDK\\OracleJDK\\jdk-11.0.16.1\\bin\\jshell.exe");
//        Scanner scanner = new Scanner(System.in);
//        if (scanner.hasNext()){
//            final String s = scanner.nextLine();
//            writeOutput(exec.getOutputStream(), s);
//        }
//
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    printInput(exec.getInputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    private static void printInput(InputStream inputStream) throws IOException {
//        byte[] bytes = new byte[1024];
//        int read = 0;
//        while ((read = inputStream.read(bytes)) != -1) {
//            if (read != 0) {
//                final String string = new String(bytes, 0, read, "GBK");
//                System.out.print(string);
//                Arrays.fill(bytes, (byte) 0);
//                read = -1;
//                Files.write(Paths.get("D:\\1.txt"),bytes, StandardOpenOption.APPEND);
//            } else {
//                break;
//            }
//        }
//    }
//
//    private static void writeOutput(OutputStream outputStream, String s) throws IOException{
//        outputStream.write(s.getBytes());
//        outputStream.flush();
//    }
}
