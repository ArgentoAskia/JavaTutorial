package cn.argentoaskia.files;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.*;
import java.util.zip.ZipFile;

/**
 * java.util.jar.*(JDK 1.5引入)包中声明了用来处理Jar文件的相关类
 * - Attributes类：
 * - JarEntry类：代表一个jar实体，一个Jar文件有很多Jar实体组成
 * - JarFile类：代表一个Jar文件
 * - JarInputStream：
 * - JarOutputStream：
 * - Manifest：代表Jar文件中，META-INF中的MANIFEST.MD文件
 * - Pack200：
 */
public class JarFileDemo {
    public static void main(String[] args) throws IOException {
//        readAJarFile();
        final Manifest manifest = createManifest();
        writeAJarFile(manifest);
    }
    // 读入一个JarFile
    private static void readAJarFile() throws IOException {
        File file = new File("Java-File/src/main/resources/JarFile/FlyBird3.jar");
        JarFile jarFile = new JarFile(file);
        final String fileName = jarFile.getName();
        System.out.println("Jar's path=" + fileName);

        // 打印Jar文件的注释信息,注意，jar文件默认使用UTF-8编码，但ZipCoder的toString()[zc.toString(bcomm, bcomm.length);]不支持中文！
        final String comment = jarFile.getComment();
        System.out.println("Jar's comment=" + comment);

        // 获取MANIFEST.MD
        final Manifest manifest = jarFile.getManifest();
        printManifest(manifest);

        // 获取Jar压缩包内entries(所谓entries实际上就是Jar压缩包内的文件和文件夹)的个数
        final int size = jarFile.size();
        System.out.println("Jar's entries size=" + size);

        // 获取某一片entry
        final Enumeration<JarEntry> entries = jarFile.entries();
        // 根据Name获取JarEntry
//        jarFile.getJarEntry();
        // 根据Name获取Entry
//        jarFile.getEntry();

        while(entries.hasMoreElements()){
            final JarEntry jarEntry = entries.nextElement();
            printJarEntry(jarEntry);
            // 获取entry的内容！
            final InputStream inputStream = jarFile.getInputStream(jarEntry);
        }
        jarFile.close();
    }
    private static void printManifest(Manifest manifest){
        System.out.println("========================= MANIFEST.MD =========================");
        final Attributes mainAttributes = manifest.getMainAttributes();
        printMainAttributes(mainAttributes);
        System.out.println();
        final Map<String, Attributes> entries = manifest.getEntries();
        final Iterator<String> iterator = entries.keySet().iterator();
        iterator.forEachRemaining(key ->{
            final Attributes attributes = manifest.getAttributes(key);
            printEntries(key, attributes);
        });
        System.out.println("========================= MANIFEST.MD =========================");
        System.out.println();
    }
    private static void printMainAttributes(Attributes attributes){
        System.out.println("Main Attributes： ==> {");
        attributes.forEach((key, value) ->{
            System.out.println("       " + key + " = " + value);
        });
        System.out.println("}");
    }
    private static void printEntries(String key, Attributes attributes){
        System.out.println("Name:" + key + " ==> {");
        attributes.forEach((attrKey, attrvalue) ->{
            System.out.println("       " + attrKey + " = " + attrvalue);
        });
        System.out.println("}");
    }
    private static void printJarEntry(JarEntry jarEntry) throws IOException {
        final Attributes attributes = jarEntry.getAttributes();
        final Certificate[] certificates = jarEntry.getCertificates();
        final CodeSigner[] codeSigners = jarEntry.getCodeSigners();
        final String comment = jarEntry.getComment();
        final long compressedSize = jarEntry.getCompressedSize();
        final long crc = jarEntry.getCrc();
        final FileTime creationTime = jarEntry.getCreationTime();
        final byte[] extra = jarEntry.getExtra();
        final FileTime lastAccessTime = jarEntry.getLastAccessTime();
        final FileTime lastModifiedTime = jarEntry.getLastModifiedTime();
        final int method = jarEntry.getMethod();
        final String name = jarEntry.getName();
        final long size = jarEntry.getSize();
        final long time = jarEntry.getTime();
        final boolean directory = jarEntry.isDirectory();

        final int i = name.lastIndexOf("\\");
        final int i1 = name.lastIndexOf("/");
        final int index = Math.max(i, i1);
        final String lastName = name.substring(index + 1);

        System.out.println("JarEntry " + lastName + " ==>{");
        System.out.println("      path = " + name);
        System.out.println("      directory = " + directory);
        System.out.println("      lastModifiedTime(epoch time) = " + time + "["+ Instant.ofEpochMilli(time) +"]");
        System.out.println("      uncompressed size = " + size);
        // JarEntry.STORED ? JarEntry.DEFLATED
        System.out.println("      method = " + (method == JarEntry.DEFLATED? "Deflated":"stored") + "["+ method +"]");
        System.out.println("      lastModifiedTime = " + lastModifiedTime);
        System.out.println("      lastAccessTime = " + lastAccessTime);
        System.out.println("      extra = " + Arrays.toString(extra));
        System.out.println("      codeSigners = " + Arrays.toString(codeSigners));
        System.out.println("      certificates = " + Arrays.toString(certificates));
        System.out.println("      creationTime = " + creationTime);
        System.out.println("      crc = " + crc);
        System.out.println("      compressedSize = " + compressedSize);
        System.out.println("      comment = " + comment);
        System.out.println("      comment = " + comment);
        System.out.println("      attributes = " + attributes);
        System.out.println("}");
        System.out.println();
    }

    private static Manifest createManifest() throws IOException {
        File manifestFile = new File("Java-File/src/main/resources/JarFile/target/log4j/META-INF/MANIFEST.MF");
        final FileInputStream fileInputStream = new FileInputStream(manifestFile);
        Manifest manifest = new Manifest(fileInputStream);
        return manifest;
    }
    private static void writeAJarFile(Manifest manifest) throws IOException {
        // 输出的Jar包位置
        File jarFile = new File("Java-File/src/main/resources/JarFile/target/log4j.jar");
        final FileOutputStream fileOutputStream = new FileOutputStream(jarFile);

        JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream, manifest);
        // 设置Jar压缩算法
        jarOutputStream.setMethod(JarEntry.DEFLATED);
        // 级别9代表最高压缩，压缩速度最慢，体积最小！级别0代表不压缩，
        jarOutputStream.setLevel(0);
        // 设置Jar文件的注释！
        jarOutputStream.setComment("Log4j Api Common!!");

        File fileWalker = new File("Java-File/src/main/resources/JarFile/target/log4j/");
        final Path path = fileWalker.toPath();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                final Path relativize = path.relativize(file);
                final String s = relativize.toString();
                System.out.println(s);
                if (s.equalsIgnoreCase("META-INF\\MANIFEST.MF")){
                    return FileVisitResult.CONTINUE;
                }
                // 1.先创建一个JarEntry,使用
                JarEntry jarEntry = new JarEntry(s);
                // 2.设置下方JarEntry
                jarOutputStream.putNextEntry(jarEntry);
                // 3.写入内容
                final byte[] bytes = Files.readAllBytes(file);
                jarOutputStream.write(bytes);
                // 4.完成JarEntry
                jarOutputStream.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        // 写完！
        jarOutputStream.close();
    }


}
