package cn.argentoaskia.iostream.bytes;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class BufferedOutputStreamDemo {


    private static char[] randomAlphabetsNumbers(){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEF";
        Random random = new Random();
        int loop = random.nextInt(30);
        char[] chars = new char[loop];
        for (int i = 0; i < loop; i++) {
            int ranIndex = random.nextInt(str.length());
            char c = str.charAt(ranIndex);
            chars[i] = c;
        }
        return chars;
    }
    public static void main(String[] args) throws IOException {
        File output = new File("Java-IOStream/src/main/resources/BufferedStream/RandomDataFile.txt");
        if (!output.exists()) output.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 2048);

        char[] chars = randomAlphabetsNumbers();
        // TODO: 2023/1/25 写出类似于DataOutput的数组实现
        String s = String.valueOf(chars);
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        System.out.println("生成的随机文本：" + s);
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        fileOutputStream.close();
    }
}
