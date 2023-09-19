package cn.argentoaskia.io;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 遍历整个磁盘的文件夹，并将结果存储到文件中...
 * @author asus
 */
public class FileListDemo {
    // 修改这里的值，实现遍历各个磁盘
    private static final String diskRoot = "C:\\";

    private static final String root = "/";
    private static PrintStream printStream;

    private static void listDisk() throws IOException {
        /**
         * @apiNote 逐级判断并创建相应的目录
         */
        File outputFile = new File("Java-File/src/main/resources/outputDisk/disk.txt");
        if (!outputFile.exists()){
            String createFileResult = outputFile.createNewFile()? "创建输出文件成功":"创建输出文件失败";
            System.out.println(createFileResult);
        }
        printStream = new PrintStream(outputFile);
        File disk = new File(diskRoot);
        System.out.println("正在遍历：" + disk);
        final LocalDateTime begin = LocalDateTime.now();
        System.out.println("开始时间：" + begin);
        nameListDisk(disk);
        final LocalDateTime end = LocalDateTime.now();
        System.out.println("结束时间：" + end);
        System.out.println("遍历" + disk + "总共花了：" + (Duration.between(begin, end).getSeconds()) + "秒");
        printStream.close();
    }
    private static void printCurrentDisk(File[] files){
        for (File f :
                files) {
            printStream.println(f);
        }
    }
    private static void listDisk(File src){
        LinkedList<File> queue = new LinkedList<>();
        final File[] files = src.listFiles();
        if (files != null && files.length != 0){
            Collections.addAll(queue, files);
            printCurrentDisk(files);
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final File poll = queue.poll();
                if (poll != null) listDisk(poll);
            }
        }
    }

    private static void conditionListDisk(File src){
        LinkedList<File> queue = new LinkedList<>();
        final File[] files = src.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                System.out.println(dir);
                System.out.println(name);
                return !name.contains(".") && !name.contains("$");
            }
        });
        if (files != null && files.length != 0){
            Collections.addAll(queue, files);
            printCurrentDisk(files);
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final File poll = queue.poll();
                if (poll != null) conditionListDisk(poll);
            }
        }
    }

    private static void nameListDisk(File src){
        LinkedList<File> queue = new LinkedList<>();
        // 删除Windows文件夹
        final File[] files = src.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                // System.out.println(pathname);
                if (pathname.toString().contains("Windows")){
                    return false;
                }else{
                    return true;
                }
            }
        });
        if (files != null && files.length != 0){
            Collections.addAll(queue, files);
            printCurrentDisk(files);
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final File poll = queue.poll();
                if (poll != null) nameListDisk(poll);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        listDisk();
    }
}
