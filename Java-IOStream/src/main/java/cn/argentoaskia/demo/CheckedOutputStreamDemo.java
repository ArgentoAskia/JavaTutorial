package cn.argentoaskia.demo;

import sun.misc.CRC16;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.*;

public class CheckedOutputStreamDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("=================== 写入文件 ===================");
        // 1.创建准备用作生成校验码的算法，并创建CheckedOutputStream
        CRC32 crc32 = new CRC32();
        String context = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        File outputFile = new File("Java-IOStream/src/main/resources/CheckedStream/data-output.txt");
        if (!outputFile.exists()){
            outputFile.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, crc32);

        // 2.写出文件，生成CRC32校验值
        Checksum checksum = checkedOutputStream.getChecksum();
        checkedOutputStream.write(context.getBytes());


        // 3.获取checksum
        long value = checksum.getValue();
        System.out.println("冗余检测对照值：" + value);
        System.out.println("冗余检测对照值（binary）：" + Long.toBinaryString(value));
        System.out.println("冗余检测对照值（hex）：" + Long.toHexString(value));

        // 4.关闭流
        checkedOutputStream.close();
        fileOutputStream.close();
        System.out.println();

        System.out.println("=================== 读取文件 ===================");
        // 5.验证
        readChecked();
    }


    private static void readChecked() throws IOException {
        InputStream data = InputStreamDemo.class.getResourceAsStream("/CheckedStream/data-output.txt");
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkedInputStream = new CheckedInputStream(data, crc32);
        Checksum checksum = checkedInputStream.getChecksum();
        int available = checkedInputStream.available();
        checkedInputStream.mark(available);
        byte[] context = new byte[available];
        int readResult = checkedInputStream.read(context);
        System.out.println("共读入了" + readResult + "个字节");
        System.out.println(Arrays.toString(context));
        long value = checksum.getValue();
        System.out.println("冗余检测对照值：" + value);
        System.out.println("冗余检测对照值（binary）：" + Long.toBinaryString(value));
        System.out.println("冗余检测对照值（hex）：" + Long.toHexString(value));
    }
}
