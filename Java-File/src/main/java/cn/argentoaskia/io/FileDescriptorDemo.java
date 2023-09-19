package cn.argentoaskia.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

// // TODO: 2023/9/16 .FileInputStream.open0()的寻址方式！
public class FileDescriptorDemo {
    public static void main(String[] args) throws IOException {
        File file = new File("Java-File/src/main/java/cn/argentoaskia/io/FileDescriptorDemo.java");
        System.out.println(file.getAbsolutePath());
        FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
        FileDescriptor fileDescriptor = fileInputStream.getFD();
        System.out.println(fileDescriptor);

//        FileOutputStream err = new FileOutputStream(FileDescriptor.err);
//        FileOutputStream out = new FileOutputStream(FileDescriptor.out);
//        FileInputStream in = new FileInputStream(FileDescriptor.in);

        FileReader reader = new FileReader(FileDescriptor.in);
        FileWriter writer = new FileWriter(FileDescriptor.out);
        FileWriter error = new FileWriter(FileDescriptor.err);
        writer.write("123132131");

        char[] chars = new char[1024];
        int read = 0;
        while((read = reader.read(chars)) != 0){
            final String string = new String(chars, 0, read);
            if(string.contains("exit")){
                System.exit(0);
            }
            final String[] s = string.split(" ");
            if (s[0].equalsIgnoreCase("out")){
                writer.write(s[1]);
            }else if (s[0].equalsIgnoreCase("err")){
                error.write(s[1]);
            }else{
                error.write("输出错误！");
            }
        }

    }
}
