package cn.argentoaskia.iostream.bytes;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * FileInputStream主要用于读入一个外部资源！
 */
public class FileInputStreamDemo {
    public static void main(String[] args) throws IOException {
        // 1.创建一个FileInputStream时特别注意，无论是使用
        // public FileInputStream(String name)还是：
        // public FileInputStream(File file) throws FileNotFoundException
        // 底层都是创建File对象来实现，所以特别注意路径的写法，参考File类中的
        // 注意一般File类只能创建到父工程！
        String file = "Java-IOStream/src/main/resources/FileStream/data.txt";
        FileInputStream fileInputStream = new FileInputStream(file);

        // 2.这样就读入一个外部资源了
        // 因为是外部文件，所以可以先取出内容有多少字节，然后再读！
        int available = fileInputStream.available();
        byte[] datas = new byte[available];
        int read = fileInputStream.read(datas);
        if (read == available){
            System.out.println(new String(datas));
        }else{
            System.out.println("实际读取的字节小于可读取的！");
        }

        // 3.记得关闭流
        fileInputStream.close();
    }
}
