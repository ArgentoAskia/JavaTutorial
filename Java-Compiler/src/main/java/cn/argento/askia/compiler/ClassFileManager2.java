package cn.argento.askia.compiler;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * ClassFileManager的另外一种写法，不实现Forwarding类而是直接实现StandardJavaFileManager
 */
public class ClassFileManager2 implements StandardJavaFileManager {

    private StandardJavaFileManager systemDefault;
    private List<ByteCodeJavaFileObject> byteCodes;
    public ClassFileManager2(StandardJavaFileManager systemDefault){
        this.systemDefault = systemDefault;
        byteCodes = new ArrayList<>();
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        System.out.println("==================== ClassFileManager.getClassLoader ================");
        System.out.println("location:" + location);
        // 如果当前位置类型是CLASS_PATH
        if (location == StandardLocation.CLASS_PATH){
            System.out.println("使用SecureClassLoader加载...");
            return new SecureClassLoader(){
                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    System.out.println("搜索类" + name + "中...");
                    for (ByteCodeJavaFileObject b :
                            byteCodes) {
                        if (b.getClassName().equals(name) || b.getName().equals("/" + name.replace('.', '/') + ".class")){
                            System.out.println("搜索完毕!");
                            System.out.println("定义Class对象...");
                            final byte[] byteCode = b.getByteCodes();
                            final Class<?> aClass = defineClass(name, byteCode, 0, byteCode.length);
                            System.out.println("定义完毕，Class对象：" + aClass);
                            return aClass;
                        }
                    }
                    throw new ClassNotFoundException(name);
                }
            };
        }else{
            return systemDefault.getClassLoader(location);
        }

    }

    @Override
    public Iterable<JavaFileObject> list(Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
        return systemDefault.list(location, packageName, kinds, recurse);
    }

    @Override
    public String inferBinaryName(Location location, JavaFileObject file) {
        return systemDefault.inferBinaryName(location, file);
    }

    @Override
    public boolean isSameFile(FileObject a, FileObject b) {
        return systemDefault.isSameFile(a, b);
    }

    @Override
    public boolean handleOption(String current, Iterator<String> remaining) {
        return systemDefault.handleOption(current, remaining);
    }

    @Override
    public boolean hasLocation(Location location) {
        return systemDefault.hasLocation(location);
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
        return systemDefault.getJavaFileForInput(location, className, kind);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        System.out.println("=============== StandardJavaFileManager:getJavaFileForOutput(Location, String, JavaFileObject.Kind, FileObject) ===============");
        System.out.println("Location：" + location.getName() + "，isOutput？" + location.isOutputLocation());
        System.out.println("className：" + className);
        System.out.println("kind：" + kind.extension);
        System.out.println("sibling：" + sibling + ",name：" + sibling.getName());
        // 如果是class类型，则输出对象让系统存储字节码
        if (kind == JavaFileObject.Kind.CLASS){
            ByteCodeJavaFileObject byteCodeJavaFileObject = new ByteCodeJavaFileObject(className);
            byteCodes.add(byteCodeJavaFileObject);
            return byteCodeJavaFileObject;
        }else{
            // 否则，则使用上层Filemanager进行处理
            return systemDefault.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    @Override
    public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
        return systemDefault.getFileForInput(location, packageName, relativeName);
    }

    @Override
    public FileObject getFileForOutput(Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
        return systemDefault.getFileForOutput(location, packageName, relativeName, sibling);
    }

    @Override
    public void flush() throws IOException {
        systemDefault.flush();
    }

    @Override
    public void close() throws IOException {
        systemDefault.close();
    }

    @Override
    public Iterable<? extends JavaFileObject> getJavaFileObjectsFromFiles(Iterable<? extends File> files) {
        return systemDefault.getJavaFileObjectsFromFiles(files);
    }

    @Override
    public Iterable<? extends JavaFileObject> getJavaFileObjects(File... files) {
        return systemDefault.getJavaFileObjects(files);
    }

    @Override
    public Iterable<? extends JavaFileObject> getJavaFileObjectsFromStrings(Iterable<String> names) {
        return systemDefault.getJavaFileObjectsFromStrings(names);
    }

    @Override
    public Iterable<? extends JavaFileObject> getJavaFileObjects(String... names) {
        return systemDefault.getJavaFileObjects(names);
    }

    @Override
    public void setLocation(Location location, Iterable<? extends File> path) throws IOException {
        systemDefault.setLocation(location, path);
    }

    @Override
    public Iterable<? extends File> getLocation(Location location) {
        return systemDefault.getLocation(location);
    }

    @Override
    public int isSupportedOption(String option) {
        return systemDefault.isSupportedOption(option);
    }
}
