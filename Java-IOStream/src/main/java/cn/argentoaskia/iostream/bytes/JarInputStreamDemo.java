package cn.argentoaskia.iostream.bytes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.FileTime;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Map;
import java.util.jar.*;

/**
 * JarInputStream能够读取一个Jar文件内的各种分片（JarEntry），但读取的内容非常有限
 * 实际上完整读取一个Jar文件，需要配合JarFile使用！
 */
public class JarInputStreamDemo {

    public static void main(String[] args) throws IOException {
        final InputStream jarResource = JarInputStreamDemo.class.getResourceAsStream("/JarStream/FlyBird3.jar");
        JarInputStream jarInputStream = new JarInputStream(jarResource,false);

        // 获取Manifest文件
        final Manifest manifest = jarInputStream.getManifest();
        // MainAttribute是所有的键值
        final Attributes mainAttributes = manifest.getMainAttributes();
        // Entries指的是以Name开头的组，String就是Name:后面的值，Attributes就是这一组的所有属性
        // Name和Name之间使用空行进行分割，请参考：/JarStream/Manifest.MF写法
        final Map<String, Attributes> entries = manifest.getEntries();
        entries.forEach((name, attribute1) ->{
            // 获取Name组！
            final Attributes attributes = manifest.getAttributes(name);
            System.out.println("Name=" + name);
            attributes.forEach((key, value) ->{
                System.out.println(key + "=" + value);
            });
        });
        mainAttributes.forEach((key , value) ->{
            System.out.println(key + "=" + value);
        });

        //  获取每一个Jar实体
        JarEntry nextJarEntry = null;
        int i = 0;
        while((nextJarEntry = jarInputStream.getNextJarEntry()) != null){
            System.out.println("================================");
            // 获取JarEntry的名称
            final String name = nextJarEntry.getName();
            System.out.println("JarEntry" + i + "=" + name);
            System.out.println("JarEntry" + i + " is Directory=" + nextJarEntry.isDirectory());
            // 返回该JarEntry的相关ManifestAttributes，如果没有则返回null
            final Attributes attributes = nextJarEntry.getAttributes();
            System.out.println("attributess For " + name);
            if (attributes != null && !attributes.isEmpty()){
                attributes.forEach((key, value) -> {
                    System.out.println("    " + key + "=" + value);
                });
            }
            // 获取JarEntry的证书？
            final Certificate[] certificates = nextJarEntry.getCertificates();
            System.out.println(name + "‘s Certificate" +"=" + Arrays.toString(certificates));
            // 获取JarEntry的代码签名？
            final CodeSigner[] codeSigners = nextJarEntry.getCodeSigners();
            System.out.println(name + "‘s CodeSigner" + "=" + Arrays.toString(codeSigners));
            // 获取JarEntry的描述信息
            final String comment = nextJarEntry.getComment();
            System.out.println(name + "‘s comment" + "=" + comment);
            // 获取JarEntry的压缩大小
            final long compressedSize = nextJarEntry.getCompressedSize();
            System.out.println(name + "‘s compressedSize" + "=" + compressedSize);
            // 获取JarEntry的CRC
            final long crc = nextJarEntry.getCrc();
            System.out.println(name + "‘s crc" + "=" + crc);
            // 获取JarEntry的创建时间
            final FileTime creationTime = nextJarEntry.getCreationTime();
            System.out.println(name + "‘s creationTime" + "=" + creationTime);
            // 获取JarEntry的扩展信息
            final byte[] extra = nextJarEntry.getExtra();
            System.out.println(name + "‘s extraData" + "=" + Arrays.toString(extra));
            // 获取JarEntry的最后访问时间
            final FileTime lastAccessTime = nextJarEntry.getLastAccessTime();
            System.out.println(name + "‘s lastAccessTime" + "=" + lastAccessTime);
            // 获取JarEntry的最后修改时间
            final FileTime lastModifiedTime = nextJarEntry.getLastModifiedTime();
            System.out.println(name + "‘s lastModifiedTime" + "=" + lastModifiedTime);
            // 获取JarEntry的压缩方法
            final int method = nextJarEntry.getMethod();
            System.out.println(name + "‘s method" + "=" + method);
            // 获取JarEntry的没有压缩大小
            final long size = nextJarEntry.getSize();
            System.out.println(name + "‘s size" + "=" + size);
            // 获取JarEntry的最后修改时间
            final long time = nextJarEntry.getTime();
            System.out.println(name + "‘s time" + "=" + time);
            System.out.println("================================");
            System.out.println();
            i++;
            jarInputStream.closeEntry();
        }
        jarInputStream.close();
    }
}
