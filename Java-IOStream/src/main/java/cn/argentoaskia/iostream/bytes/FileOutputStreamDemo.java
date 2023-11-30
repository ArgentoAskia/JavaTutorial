package cn.argentoaskia.iostream.bytes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        // 1.写出外部文件
        // FileOutputStream的构造参数和FileInputStream很像
        // 只不过FileOutputStream多了第二个参数用来决定是追加写还是覆盖写
        File dataOutputFile = new File("Java-IOStream/src/main/resources/FileStream/dataOutput.txt");
        if (!dataOutputFile.exists())       dataOutputFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(dataOutputFile);
        // 2.
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            stringBuilder.append(UUID.randomUUID().toString()).append(System.lineSeparator());
        }

        // 3. 通过write()写出字节到文件
        fileOutputStream.write(stringBuilder.toString().getBytes());

        // 4.记得关闭流！
        fileOutputStream.close();
    }
}
