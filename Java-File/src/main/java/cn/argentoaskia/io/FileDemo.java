package cn.argentoaskia.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Consumer;

public class FileDemo {

    public static void main(String[] args) throws IOException {
        File diskC = new File("C:/");
        File diskD = new File("D:/");
        // 在Idea中无论是否是多模块的项目，默认情况下的相对路径就是最顶层项目的项目路径
        // 如下面的FileDemo虽然在Java-File模块里面，但这个Java-File却在JavaProject里面
        // 所以relativeFile的位置就是JavaProject的位置
        File relativeFile = new File("");
        // 注意下面的两种写法，前一种是相对于当前路径而言的，后一种是相对于根目录而言的
        // 如：ModuleFile的值是：D:\OpenSourceProject\JavaProject\Java-File
        // 而：rootJavaFile的值则是：D:\Java-File
        File moduleFile = new File("Java-File/");
        File rootJavaFile = new File("/Java-File/");
        // 如果传递/ ,则代表根目录，任何的现有目录都会被删掉只剩下盘符（根目录嘛dddd）
        // 因此下面的root虽然项目在JavaProject路径里面，但是这样写就会变成盘符：/
        // 如下面的D:\OpenSourceProject\JavaProject会变成D:/
        File root = new File("/");

        System.out.println(relativeFile.getAbsolutePath());
        System.out.println(moduleFile.getAbsolutePath());
        System.out.println(rootJavaFile.getAbsolutePath());
        // ------------------- Files --------------------------

        File resourcesFolderFile = new File(moduleFile, "src\\main\\resources");
        File hiddenFile = new File(resourcesFolderFile,"hidden.txt");
        File readableFile = new File(resourcesFolderFile, "Readable.txt");
        File readableWritableFile = new File(resourcesFolderFile, "ReadableWritable.txt");
        File executableFile = new File(resourcesFolderFile, "geek.exe");
        File jarFile = new File(resourcesFolderFile, "FlyBird3.jar");


        System.out.println("=================================================");
        System.out.println("=       第一组API：判断文件是否可读可写可运行        =");
        System.out.println("=================================================");
        System.out.println(hiddenFile.getCanonicalPath() + "（这是一个隐藏文件）文件可运行？" + hiddenFile.canExecute());
        System.out.println(hiddenFile.getCanonicalPath() + "（这是一个隐藏文件）文件可读？" + hiddenFile.canRead());
        System.out.println(hiddenFile.getCanonicalPath() + "（这是一个隐藏文件）文件可写？" + hiddenFile.canWrite());
        System.out.println(readableFile.getCanonicalPath() + "（这是一个只读文件）文件可运行？" + readableFile.canExecute());
        System.out.println(readableFile.getCanonicalPath() + "（这是一个只读文件）文件可读？" + readableFile.canRead());
        System.out.println(readableFile.getCanonicalPath() + "（这是一个只读文件）文件可写？" + readableFile.canWrite());
        System.out.println(readableWritableFile.getCanonicalPath() + "（这是一个可读可写文件）文件可运行？" + readableWritableFile.canExecute());
        System.out.println(readableWritableFile.getCanonicalPath() + "（这是一个可读可写文件）文件可读？" + readableWritableFile.canRead());
        System.out.println(readableWritableFile.getCanonicalPath() + "（这是一个可读可写文件）文件可写？" + readableWritableFile.canWrite());
        System.out.println(executableFile.getCanonicalPath() + "（这是一个可执行文件）文件可运行？" + executableFile.canExecute());
        System.out.println(executableFile.getCanonicalPath() + "（这是一个可执行文件）文件可读？" + executableFile.canRead());
        System.out.println(executableFile.getCanonicalPath() + "（这是一个可执行文件）文件可写？" + executableFile.canWrite());
        System.out.println(jarFile.getCanonicalPath() + "（这是一个Jar）文件可运行？" + jarFile.canExecute());
        System.out.println(jarFile.getCanonicalPath() + "（这是一个Jar）文件可读？" + jarFile.canRead());
        System.out.println(jarFile.getCanonicalPath() + "（这是一个Jar）文件可写？" + jarFile.canWrite());
        System.out.println();
        System.out.println("=================================================");
        System.out.println("=       2.两个文件相比较                          =");
        System.out.println("=================================================");
        System.out.println("FlyBird.jar == geek.exe?" + jarFile.equals(executableFile));
        System.out.println("hidden.txt == Readable.txt?" + hiddenFile.equals(readableFile));
        System.out.println("hidden.txt == hidden.txt?" + hiddenFile.equals(hiddenFile));
        System.out.println("jarFile‘s path length " + (jarFile.compareTo(executableFile) > 0? ">" : "<") + "executableFile‘s path length");
        System.out.println();
        System.out.println("=================================================");
        System.out.println("=       3.通用文件操作,创建、删除、重命名等          =");
        System.out.println("=================================================");
        //        file.createNewFile();
        //        file.delete(); 特别注意，这个删除方法只能删除空文件夹和文件，文件夹内有东西的，如子文件夹或者文件的无法删除
        //        file.deleteOnExit(); 特备注意，使用这个方法，文件不会被立刻删除，只有再执行完了所有代码，jvm结束之时，才会删掉文件,该方法没有delete的空目录限制
        //        file.exists();
        //        file.mkdir();
        //        file.mkdirs();
        //        file.renameTo();
        File newFile = new File(resourcesFolderFile, "newFile.txt");
        File newDir = new File(resourcesFolderFile, "newDir/");
        File newDirs = new File(resourcesFolderFile, "Argento/Askia/Dirs");
        File newFileInNewDir = new File(resourcesFolderFile, "/newDir/newDirFile.txt");
        File renameFile = new File(resourcesFolderFile, "rename.txt");
        File renameFile2 = new File(resourcesFolderFile, "rename2.txt");
        if (renameFile.exists()){
            System.out.println("先删除renameFile.txt(防止重命名失败！):" + renameFile.delete());
        }
        if (renameFile2.exists()){
            System.out.println("删除重命名失败的renameFile2.txt:" + renameFile2.delete());
        }
        System.out.println("创建重命名文件renameFile2.txt：" + (renameFile2.createNewFile()? "成功" : "失败"));
        boolean b = renameFile2.renameTo(renameFile);
        System.out.println("重命名renameFile2.txt --> renameFile.txt：" + (b? "成功" : "失败"));
        if (newFile.exists()){
            // 文件存在，先删除再重新创建
            System.out.println("文件：" + newFile.getPath() + "已存在，即将进行删除操作：" + (newFile.delete()? "删除成功":"删除失败"));
        }
        if (newDir.exists() && newFileInNewDir.exists()){
            // 不能直接删除带文件的目录，要先把目录内的文件删除
            System.out.println("先删除文件：" + newFileInNewDir.getPath() + "：" + (newFileInNewDir.delete()? "删除成功" : "删除失败"));
            System.out.println("然后再删除目录：" + newDir.getPath() + "：" + (newDir.delete()? "删除成功" : "删除失败"));
        }
        System.out.println("创建新文件：" + newFile.getPath() + "：" + (newFile.createNewFile()? "创建成功": "创建失败"));
        System.out.println("创建新一级文件夹：" + newDir.getPath() + "：" + (newDir.mkdir()? "创建成功": "创建失败"));
        System.out.println("创建多级文件夹：" + newDirs.getPath() + "：" + (newDirs.mkdirs()? "创建成功" : "创建失败"));
        System.out.println("创建新文件夹内的新文件：" + newFileInNewDir.getPath() + "：" + (newFileInNewDir.createNewFile()? "创建成功" : "创建失败"));
        System.out.println("程序运行结束之后会删除创建的多级目录：" + newDirs.getPath());
        newDirs.deleteOnExit();
        System.out.println();

        System.out.println("=================================================");
        System.out.println("=       4.文件的相对、标准(规范)路径、绝对路径等      =");
        System.out.println("=================================================");
        //        // 绝对路劲
        //        file.getAbsoluteFile();
        //        file.getAbsolutePath();
        //        // 标准路劲
        //        file.getCanonicalFile();
        //        file.getCanonicalPath();
        //        // 相对路径
        //        file.getPath();
        File sampleFile = new File("../abc/efg/../123/../../");
        String canonicalPath = sampleFile.getCanonicalPath();
        String absolutePath = sampleFile.getAbsolutePath();
        String path = sampleFile.getPath();
        System.out.println("标准路径(会解析路径中的..和.)：" + canonicalPath);
        System.out.println("绝对路径(会加上根目录, 保留..和.)：" + absolutePath);
        System.out.println("相对路径(保留..和.)：" + path);
        System.out.println();
        System.out.println("特别注意如果File对象本身就代表一个绝对路径，则getPath()和getAbsolutePath()结果相同");
        File pathFileTest = new File("D:\\OpenSourceProject\\JavaProject\\Java-File\\src\\main\\resources\\FlyBird3.jar");
        System.out.println(pathFileTest.getPath());
        System.out.println(pathFileTest.getAbsolutePath());
        System.out.println();


        System.out.println("=================================================");
        System.out.println("=5.父类路径、文件名、剩余|总|使用空间、修改日期、文件大小=");
        System.out.println("=================================================");
        //        // 获取文件名&父类文件名
        //        file.getName();
        //        file.getParent();
        //        file.getParentFile();
        //        // 文件空间
        //        file.getFreeSpace();
        //        file.getTotalSpace();
        //        file.getUsableSpace();
        System.out.println(resourcesFolderFile);
        System.out.println(readableFile);
        String dirName = resourcesFolderFile.getName();
        String fileName = readableFile.getName();
        System.out.println("dirName：" +  dirName);
        System.out.println("fileName：" + fileName);
        String dirParent = resourcesFolderFile.getParent();
        String fileParent = readableFile.getParent();
        System.out.println("dirParent：" +  dirParent);
        System.out.println("fileParent：" + fileParent);
        System.out.println();
        long freeSpace = sampleFile.getFreeSpace();
        long usableSpace = sampleFile.getUsableSpace();
        long totalSpace = sampleFile.getTotalSpace();
        System.out.println(sampleFile.getCanonicalPath()+"：" + freeSpace + "(free)/" + usableSpace + "(use)/" + totalSpace + "(total)");
        long l = jarFile.lastModified();
        System.out.println(jarFile.getCanonicalPath() + " last modify time：" + l);
        long length = jarFile.length();
        System.out.println(jarFile.getCanonicalPath() + " ‘s length：" + length + "个字节");
        System.out.println();

        System.out.println("=================================================");
        System.out.println("=       6.一些判别方法                            =");
        System.out.println("=================================================");
        //        // 判别方法
        //        file.isAbsolute();
        //        file.isDirectory();
        //        file.isFile();
        //        file.isHidden();
        /*
            判断是否是绝对路径！
         */
        boolean absolute = root.isAbsolute();
        boolean absolute1 = diskC.isAbsolute();
        System.out.println(root.getPath() + " is absolute：" + absolute);
        System.out.println(diskC.getPath() + " is absolute：" + absolute1);

        /*
            判断是否是目录
         */
        boolean directory = jarFile.isDirectory();
        boolean directory1 = root.isDirectory();
        System.out.println(jarFile.getAbsolutePath() + " is directory：" + directory);
        System.out.println(root.getAbsolutePath() + " is directory：" + directory1);

        /*
            判断是否是文件
         */
        boolean file = jarFile.isFile();
        boolean file1 = root.isFile();
        System.out.println(jarFile.getAbsolutePath() + " is file：" + file);
        System.out.println(root.getAbsolutePath() + " is file：" + file1);

        /*
            判断是否是隐藏文件
         */
        boolean hidden = hiddenFile.isHidden();
        boolean hidden1 = jarFile.isHidden();
        System.out.println(hiddenFile.getAbsolutePath() + " is hidden file：" + hidden);
        System.out.println(jarFile.getAbsolutePath() + " is hidden file：" + hidden1);


        System.out.println("=================================================");
        System.out.println("=       7.一些遍历方法                            =");
        System.out.println("=================================================");
        //        // 遍历方法
        //        file.list();
        //        file.listFiles();
        //        file.toURI();
        //        file.toPath();
        //        file.toString();
        //        file.toURL();
        String[] list = diskD.list();
        System.out.println(Arrays.toString(list));
        Path path1 = jarFile.toPath();
        URI uri = jarFile.toURI();
        URL url = jarFile.toURL();
        String s = jarFile.toString();
        System.out.println(path1);
        System.out.println(uri);
        System.out.println(url);
        System.out.println(s);
    }
}
