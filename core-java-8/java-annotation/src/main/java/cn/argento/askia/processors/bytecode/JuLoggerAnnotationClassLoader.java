package cn.argento.askia.processors.bytecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class JuLoggerAnnotationClassLoader extends ClassLoader{

    private byte[] itemBytes;

    public static void main(String[] args) {
        final String classPath = findClassPath();
        System.out.println(classPath);
    }


    public JuLoggerAnnotationClassLoader(ClassLoader classLoader){
        super(classLoader);
        final String classPath = findClassPath();
        try {
            itemBytes = Files.readAllBytes(Paths.get(classPath, "cn/argento/askia/processors/bytecode/Item.class"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 寻找maven中编译之后的classpath
    private static String findClassPath(){
        final String property = System.getProperty("java.class.path", "");
        final String[] split = property.split(";");
        for (int i = 0; i < split.length; i++){
            if (split[i].contains("target\\classes")){
                return split[i];
            }
        }
        return null;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("class name:" + name);
        // 如果是cn.argento.askia.processors.bytecode.Item, 则单独处理，否则委托双亲委托模型
        if (name.equalsIgnoreCase("cn.argento.askia.processors.bytecode.Item")){
            System.out.println("进入处理,处理Item类的字节码...");
            // 得到处理后的字节码文件,我们将其结果保存一份为Item_after_annotation_processor.class
            final byte[] newItemBytes = JuLoggerAnnotationProcessor.process(name, itemBytes);
            System.out.println("处理完毕！");
            System.out.println("写出一份处理结果，参考Item_after_annotation_processor.class...");
            try {
                FileOutputStream fos = new FileOutputStream(Paths.get(Objects.requireNonNull(findClassPath()),
                        "cn/argento/askia/processors/bytecode/Item_after_annotation_processor.class").toFile());
                fos.write(newItemBytes);
                fos.close();
                System.out.println("写出成功！");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("写出失败！");
            }
            System.out.println("开始定义类...");
            // 加载item_test.class
            return defineClass(name, newItemBytes, 0, newItemBytes.length);
        }
        return super.loadClass(name);
    }
}
