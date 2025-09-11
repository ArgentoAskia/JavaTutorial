package cn.argento.askia.compiler;

import javax.tools.*;
import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理器（JavaFileManager），用于进行将任何东西转为FileObject及其子接口
 * 如将一个File对象转为FileObject或者JavaFileObject
 * 将一个代表Java代码的JavaFileObject转为代表字节码的JavaFileObject等
 * 同时FileManager也是用来处理工具参数的，里面有一个方法handleOption()来处理参数
 *
 * 包中提供了另外一个接口StandardJavaFileManager，来用于处理将一个磁盘的java文件转为JavaFileObject
 *
 * 调用compiler.getStandardFileManager(null, null, null);可以获得jdk系统提供的StandardJavaFileManager实例
 * 我们很多JavaFileManager的方法的实现也要借助这个实例（）
 * 可惜的是因为接口方法在JDK 7才有接口默认方法，编写tool时是JDK6
 * 好在有提供了ForwardingXXX的类，用于进行反向调用
 * 所有ForwardingXXX的类内部都会聚合一个XXX对象，如ForwardingJavaFileManager则聚合JavaFileManager，所有的方法都会调用JavaFileManager实现
 *
 * 因此我们子需要实现ForwardingJavaFileManager，改写我们希望改写的方法，其他方法交由默认的StandardJavaFileManager实现
 */
public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
    // 存储各个编译完成的字节码（因为有内部类的存在，所以编译一个java文件可能会产生多个class）
    private List<ByteCodeJavaFileObject> byteCodes;

    public ClassFileManager(StandardJavaFileManager fileManager) {
        super(fileManager);
        byteCodes = new ArrayList<>();
    }

    /**
     * 编译器编译完成之后会调用该方法将字节码进行存储！！！
     * @param location
     * @param className
     * @param kind
     * @param sibling
     * @return
     * @throws IOException
     */
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
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    // 我们定义一个ClassLoader来专门加载这些类！
    // 使用SecureClassLoader
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
            return super.getClassLoader(location);
        }

    }
}
