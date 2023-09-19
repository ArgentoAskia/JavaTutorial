package cn.argentoaskia.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * 基于File类的文件遍历
 */
public class FileWalkDemo {
    public static void main(String[] args) {

        File file = new File("D:/");
        final File[] listFiles = file.listFiles();
        System.out.println(Arrays.toString(listFiles));

        final File[] listFiles1 = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                System.out.println(dir + ", fileName：" + name);
                if (name.contains("Open"))  return false;
                else                        return true;
            }
        });
        System.out.println(Arrays.toString(listFiles1));

        final File[] listFiles2 = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.toString().contains("Open")){
                    return true;
                }else{
                    return false;
                }
            }
        });
        System.out.println(Arrays.toString(listFiles2));




    }
}
