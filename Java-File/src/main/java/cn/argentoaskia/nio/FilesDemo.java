package cn.argentoaskia.nio;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FilesDemo {
    public static void main(String[] args) throws Exception{
        copy();
    }

    /**
     * 复制文件相关demo
     * @throws Exception
     */
    private static void copy() throws Exception {
        final Path path = Paths.get("Java-File/src/main/resources/copy/bigText.txt");
        Path realPath = path.toAbsolutePath();
        final Path path1 = Paths.get("Java-File/src/main/resources/FilesDemo/copy/bigText1.txt");
        if (Files.notExists(path1)){
            // 如果文件不存在，创建就好！
            Files.createFile(path1);
        }
        // 创建IO流
        OutputStream bigTextOutputFile = Files.newOutputStream(path1);
        // 复制文件到输出流，返回复制了的字节数！
        final long copy = Files.copy(realPath, bigTextOutputFile);
        System.out.println("复制了" + copy + "个字节！");
        bigTextOutputFile.close();

        System.out.println("=========================");
        // 复制过程中支持提供参数！

    }

    private static void move() throws IOException {
        final Path source = Paths.get("Java-File/src/main/resources/FilesDemo/move2/");
        final Path target = Paths.get("Java-File/src/main/resources/FilesDemo/move/3");
        System.out.println(source.toAbsolutePath());
        System.out.println(target.toAbsolutePath());

        final Path path = Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        System.out.println(path);
    }

    private static void create() throws IOException {
        // creationTime
        final Path path = Paths.get("Java-File/src/main/resources/FilesDemo/create/dir");
        final Path directory = Files.createDirectory(path);
        System.out.println(directory);
    }


}
