package cn.argentoaskia.nio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 无法直接运行该类，因为Maven无法复制Windows上的符号链接
 * 你可以手动编译该类进行运行，参数如下：
 * java -cp D:\OpenSourceProject\JavaProject\Java-File\target\classes cn.argentoaskia.nio.Main2
 * 结果会输出true
 */
public class Main2 {
    public static void main(String[] args) {
        final Path path = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-File\\link\\test2_link.symlink");
        final boolean symbolicLink = Files.isSymbolicLink(path);
        System.out.println(symbolicLink);
    }
}
