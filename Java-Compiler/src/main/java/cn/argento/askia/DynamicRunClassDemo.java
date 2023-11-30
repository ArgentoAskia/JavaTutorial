package cn.argento.askia;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;

/**
 * Demo演示了如何运行字节码
 */
public class DynamicRunClassDemo {
    public static void main(String[] args) throws Exception {
        File file = new File("Java-Compiler/src/main/resources/cn/argento/askia/HelloWorld.class");
        // 创建类加载器
        final InputStreamClassLoader inputStreamClassLoader = new InputStreamClassLoader(file, "cn.argento.askia.HelloWorld");
        // 加载类，拿到Class对象，包名
        final Class<?> helloWorld = inputStreamClassLoader.findClass("cn.argento.askia.HelloWorld");
        System.out.println();
        // 获取静态main方法
        final Method main = helloWorld.getMethod("main", String[].class);
        // 调用方法
        main.invoke(null, (Object) new String[0]);
    }
}

/**
 * 用于加载字节码的类加载器
 */
class InputStreamClassLoader extends ClassLoader{

    private InputStream classByteCode;
    private File classFile;
    // className = package name + class name
    private String className;
    private void loadClassByteCode(){
        if (!classFile.exists()) {
            throw new RuntimeException("classFile is not exist!!!");
        }
        try {
            this.classByteCode = new FileInputStream(classFile);
        } catch (FileNotFoundException e) {
            // this exception is not throw！
        }
    }
    private byte[] readAllBytes(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4086);
        int index = 0;
        byte[] buffer = new byte[1024];
        int read = 0;
        while(true){
            try {
                if (((read = classByteCode.read(buffer)) != -1)){
                    byteArrayOutputStream.write(buffer, index, read);
                    index = index + read;
                }else{
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final byte[] bytes = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public InputStreamClassLoader(File classFile, String className){
        this.classFile = classFile;
        this.className = className;
        loadClassByteCode();
    }
    public InputStreamClassLoader(InputStream classByteCode, File classFile, String className){
        this.classByteCode = classByteCode;
        this.classFile = classFile;
        this.className = className;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
       if (name.equals(className) || name.equals(classFile.getAbsolutePath())){
           System.out.println("使用InputStreamClassLoader加载...");
           final byte[] bytes = readAllBytes();
           return defineClass(className, bytes, 0, bytes.length);
       }
        throw new ClassNotFoundException(name);
    }
}
