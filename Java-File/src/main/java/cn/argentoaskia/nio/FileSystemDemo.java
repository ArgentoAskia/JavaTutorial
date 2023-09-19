package cn.argentoaskia.nio;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;

public class FileSystemDemo {
    public static void main(String[] args) {
        final FileSystem defaultFileSystem = FileSystems.getDefault();
        final List<FileSystemProvider> fileSystemProviders = FileSystemProvider.installedProviders();
        fileSystemProviders.forEach(System.out::println);
    }
}
