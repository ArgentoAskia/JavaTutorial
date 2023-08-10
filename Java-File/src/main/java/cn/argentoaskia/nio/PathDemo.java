package cn.argentoaskia.nio;

import java.io.File;
import java.net.URI;
import java.nio.file.*;
import java.util.Iterator;

public class PathDemo {
    public static void main(String[] args) {
        Path relativePath  = FileSystems.getDefault().getPath("/abc", "efg", "hij", "..", "123", "456", "..", "789");
        Path pathDemoPath = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-File\\src\\main\\java\\cn\\argentoaskia\\PathDemo.java");
        Path path2 = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-DesignPatterns\\src\\main\\java\\cn\\argentoaskia\\observer\\ObserverTest.java");
        boolean endsWith = relativePath.endsWith("hij");
        System.out.println(relativePath + "是否以hij结尾：" + endsWith);

        Path fileName = relativePath.getFileName();
        System.out.println(fileName);

        FileSystem fileSystem = relativePath.getFileSystem();
        System.out.println(fileSystem);

        Path name = relativePath.getName(1);
        System.out.println(name);

        int nameCount = relativePath.getNameCount();
        System.out.println(nameCount);

        Path parent = relativePath.getParent();
        System.out.println(parent);

        Path root = relativePath.getRoot();
        System.out.println(root);

        boolean absolute = relativePath.isAbsolute();
        System.out.println(absolute);

        // 遍历方法
        Iterator<Path> iterator = relativePath.iterator();
        for (Path next : relativePath) {
            System.out.println(next);
        }

        Path normalize = relativePath.normalize();
        System.out.println(normalize);

        Path relativize = pathDemoPath.relativize(path2);
        System.out.println(relativize);

        Path resolve = relativePath.resolve("000");
        System.out.println(resolve);

        Path path = relativePath.resolveSibling("000");
        System.out.println(path);

        Path subpath = relativePath.subpath(2, 5);
        System.out.println(subpath);

        Path path1 = relativePath.toAbsolutePath();
        System.out.println(path1);

        File file = relativePath.toFile();
        System.out.println(file);

        String s = relativePath.toString();
        System.out.println(s);

        URI uri = relativePath.toUri();
        System.out.println(uri);
    }
}
