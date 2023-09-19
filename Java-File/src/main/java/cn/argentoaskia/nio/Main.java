package cn.argentoaskia.nio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 这个类涉及到符号链接，需要使用管理员权限进行运行才能过，否则会抛出：java.nio.file.FileSystemException: D:\test2_link.txt: 客户端没有所需的特权。
 * 编译运行的参数：java -cp [你的JavaProject的位置]\JavaProject\Java-File\target\classes cn.argentoaskia.nio.Main
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Path existingFilePath = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-File\\link\\test2.txt");
        // 可以不加后缀名
//        Path symLinkPath = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-File\\link\\test2_link.symlink");
        Path symLinkPath = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-File\\link\\test1_link.link");
        Files.createLink(symLinkPath,existingFilePath);
//        Files.createSymbolicLink(symLinkPath,existingFilePath);
    }
}
