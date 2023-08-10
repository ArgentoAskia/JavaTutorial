package cn.argentoaskia.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 基于Channel和IO实现的文件复制和移动！
 * @author Askia
 */
public class FileCopyDemo {


    private static void checkSrcAndDest(File src, File dest) throws FileNotFoundException {
        if (dest.exists()) {
            throw new RuntimeException("dest已存在！");
        } else {
            try {
                if (!dest.createNewFile()) {
                    throw new RuntimeException("无法创建dest！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!src.exists()) {
            throw new FileNotFoundException("src not found！");
        }
    }

    private static void fastCopyFile(File src, File dest) throws IOException {
        checkSrcAndDest(src, dest);
        try (final FileChannel destChannel = new FileOutputStream(dest).getChannel();
             final FileChannel srcChannel = new FileInputStream(src).getChannel()) {
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
        }
    }

    private static void copyFile(File src, File dest) throws IOException {
        checkSrcAndDest(src, dest);
        try (FileOutputStream fileOutputStream = new FileOutputStream(dest);
             FileInputStream fileInputStream = new FileInputStream(src)) {
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }
        }
    }

    private static void copyDir(File src, File dest) throws IOException {
        // 目录不存在
        if (src == null || !src.exists()) {
            throw new RuntimeException();
        }
        // 不是目录
        if (!src.isDirectory()) {
            throw new RuntimeException();
        }
        copyDir0(src, dest);
    }

    private static void copyDir0(File src, File dest) throws IOException {
        // 如果目录不存在则创建目录
        if (!dest.exists()) {
            dest.mkdirs();
        }
        LinkedList<File> queue = new LinkedList<>();
        // 获取子目录和文件并放在队列
        final File[] files = src.listFiles();
        if (files != null && files.length != 0) {
            Collections.addAll(queue, files);
            int size = queue.size();
            for (int i = 0; i < size;  i++) {
                final File poll = queue.poll();
                // 构建下一级dest
                File newDest = new File(dest, poll.getName());
                // 如果是一个目录，则继续进行目录复制
                if (poll.isDirectory()) {
                    copyDir0(poll, newDest);
                } else if (poll.isFile()){
                    copyFile(poll, newDest);
                }
            }
        }
    }

    private static void moveDir0(File src, File dest) throws IOException {
        // 如果目录不存在则创建目录
        if (!dest.exists()) {
            dest.mkdirs();
        }
        LinkedList<File> queue = new LinkedList<>();
        // 获取子目录和文件并放在队列
        final File[] files = src.listFiles();
        if (files != null && files.length != 0) {
            Collections.addAll(queue, files);
            int size = queue.size();
            for (int i = 0; i < size;  i++) {
                final File poll = queue.poll();
                // 构建下一级dest
                File newDest = new File(dest, poll.getName());
                // 如果是一个目录，则继续进行目录复制
                if (poll.isDirectory()) {
                    moveDir0(poll, newDest);
                    poll.delete();
                } else if (poll.isFile()){
                    moveFile(poll, newDest);
                }
            }
        }
    }

    private static void moveDir(File src, File dest, boolean deleteSrcDir) throws IOException {
        // 目录不存在
        if (src == null || !src.exists()) {
            throw new RuntimeException();
        }
        // 不是目录
        if (!src.isDirectory()) {
            throw new RuntimeException();
        }
        moveDir0(src, dest);
        if (deleteSrcDir){
            src.delete();
        }
    }


    /**
     * 移动的方式可以通过复制，然后删除源文件实现！
     *
     * @param src
     * @param dest
     */
    private static void moveFile(File src, File dest) throws IOException {
        fastCopyFile(src, dest);
        src.delete();
    }

    // 目录移动
    public static void main(String[] args) throws IOException {
        File src = new File("Java-File/src/main/resources/copydir");
        File dest2 = new File("Java-File/src/main/resources/copydir2");
        moveDir(src, dest2, true);
    }
//    public static void main(String[] args) throws IOException {
//        File src = new File("Java-File/src/main/resources/copy/bigText.txt");
//        File dest2 = new File("Java-File/src/main/resources/copy/bigText2.txt");
//        File dest3 = new File("Java-File/src/main/resources/copy/bigText3.txt");
//        final long l = System.currentTimeMillis();
//        fastCopyFile(src, dest2);
//        final long l1 = System.currentTimeMillis();
//        System.out.println("采用Channel复制: " + ((l1 - l)/ 1000.0) + "s");
//
//        final long l2 = System.currentTimeMillis();
//        copyFile(src, dest3);
//        final long l3 = System.currentTimeMillis();
//        System.out.println("采用IO流复制: " + ((l3 - l2) / 1000.0) + "s");
//
//        System.out.println("=================================");
//        File dest4 = new File("Java-File/src/main/resources/copy/bigText4.txt");
//        moveFile(dest2, dest4);
//
//
//    }
}
