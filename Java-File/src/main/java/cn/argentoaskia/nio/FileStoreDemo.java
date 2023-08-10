package cn.argentoaskia.nio;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 存储文件。FileStore表示存储池、设备、分区、卷、具体的文件系统或其他特定于文件存储的实现方式。
 * 存储文件的文件存储是通过调用getFileStore方法获得的，或者可以通过调用getFileStores方法枚举所有文件存储。
 * 除了这个类定义的方法之外，文件存储还可以支持一个或多个FileStoreAttributeView类，这些类提供一组文件存储属性的只读视图或可更新视图。
 */
public class FileStoreDemo {
    public static void main(String[] args) throws IOException {
        Path pathDemoPath = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-File\\src\\main\\java\\cn\\argentoaskia\\PathDemo.java");
        FileStore fileStore = Files.getFileStore(pathDemoPath);
        String type = fileStore.type();
        System.out.println(type);
    }
}
