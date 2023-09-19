package cn.argentoaskia.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileStoreAttributeView;

/**
 * 存储文件。FileStore表示存储池、设备、分区、卷、具体的文件系统或其他特定于文件存储的实现方式。
 * 存储文件的文件存储是通过调用getFileStore方法获得的，或者可以通过调用getFileStores方法枚举所有文件存储。
 * 除了这个类定义的方法之外，文件存储还可以支持一个或多个FileStoreAttributeView类，这些类提供一组文件存储属性的只读视图或可更新视图。
 */
public class FileStoreDemo {
    public static void main(String[] args) throws IOException {
        Path pathDemoPath = Paths.get("D:\\OpenSourceProject\\JavaProject\\Java-File\\src\\main\\java\\cn\\argentoaskia\\nio\\PathDemo.java");
        FileStore fileStore = Files.getFileStore(pathDemoPath);
        // WindowsFileStore中支持的Attributes
        final Object totalSpace = fileStore.getAttribute("totalSpace");
        final Object usableSpace = fileStore.getAttribute("usableSpace");
        final Object unallocatedSpace = fileStore.getAttribute("unallocatedSpace");
        final Object volume_vsn = fileStore.getAttribute("volume:vsn");
        final Object volume_isRemovable = fileStore.getAttribute("volume:isRemovable");
        final Object volume_isCdrom = fileStore.getAttribute("volume:isCdrom");
        System.out.println("Attribute totalSpace = " + totalSpace);
        System.out.println("Attribute usableSpace = " + usableSpace);
        System.out.println("Attribute unallocatedSpace = " + unallocatedSpace);
        System.out.println("Attribute volume_vsn = " + volume_vsn);
        System.out.println("Attribute volume_isRemovable = " + volume_isRemovable);
        System.out.println("Attribute volume_isCdrom = " + volume_isCdrom);
        // 该方法永远返回null
//        fileStore.getFileStoreAttributeView(FileStoreAttributeView.class);
//        fileStore.supportsFileAttributeView();
//        fileStore.supportsFileAttributeView();
        final String name = fileStore.name();
        final String type = fileStore.type();
        final long totalSpace1 = fileStore.getTotalSpace();
        final long unallocatedSpace1 = fileStore.getUnallocatedSpace();
        final long usableSpace1 = fileStore.getUsableSpace();
        final boolean readOnly = fileStore.isReadOnly();
        System.out.println(pathDemoPath + "所在的驱动器名称：" + name + " ,全名称：" + fileStore.toString() + " ,类型是：" + type + " ,总大小：" +
                totalSpace1 + " ,未分配大小：" + unallocatedSpace1 + " ,可使用大小：" + usableSpace1 + " ,分片是否是只读：" + readOnly);
        System.out.println("========================");
        System.out.println();

        final Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        for (FileStore f :
                fileStores) {
            printFileStore(f);
            System.out.println();
        }
    }

    private static void printFileStore(FileStore fileStore) throws IOException {
        final String name = fileStore.name();
        final String type = fileStore.type();
        final long totalSpace1 = fileStore.getTotalSpace();
        final long unallocatedSpace1 = fileStore.getUnallocatedSpace();
        final long usableSpace1 = fileStore.getUsableSpace();
        final boolean readOnly = fileStore.isReadOnly();
        System.out.println("驱动器名称：" + name + " ,全名称：" + fileStore.toString() + " ,类型是：" + type + " ,总大小：" +
                totalSpace1 + " ,未分配大小：" + unallocatedSpace1 + " ,可使用大小：" + usableSpace1 + " ,分片是否是只读：" + readOnly);
    }
}
