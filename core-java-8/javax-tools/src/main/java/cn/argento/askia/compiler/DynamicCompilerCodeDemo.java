package cn.argento.askia.compiler;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Demo演示了如何编译一个String变量中的代码并动态执行，返回结果！
 */
public class DynamicCompilerCodeDemo {
    private static final String DEFAULT_CODE_CLASS_NAME = "cn.argento.askia.HelloWorld";
    private static final String DEFAULT_CODE = "package cn.argento.askia;\n" +
            "\n" +
            "/**\n" +
            " * 测试代码\n" +
            " */\n" +
            "public class HelloWorld {\n" +
            "    public static void main(String[] args) {\n" +
            "        System.out.println(\"Hello World!\");\n" +
            "    }\n" +
            "}\n";
    public static void main(String[] args) throws Exception{
        // 1.首先各种途径读入Java代码
        StringBuilder stringBuilder;
        String className = null;
        List<String> codeList;
        File file = new File("Java-Compiler/src/main/resources/code.txt");
        if (file.exists()){
            codeList = Files.readAllLines(Paths.get(file.toURI()));
            className = findClassName(codeList);
            stringBuilder = toCode(codeList);
        }else{
            stringBuilder = new StringBuilder().append(DEFAULT_CODE);
            className = DEFAULT_CODE_CLASS_NAME;
        }
        System.out.println("代码：\n" + stringBuilder);
        System.out.println("类名：" + className);
        System.out.println();

        // 2.编译内存中的Java代码
        // 2.1 获取编译器
        final JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
        // 2.2 获取默认的StandardJavaFileManager实例创建ClassFileManager
        final StandardJavaFileManager standardFileManager = systemJavaCompiler.getStandardFileManager(null, null, null);
        ClassFileManager classFileManager = new ClassFileManager(standardFileManager);

        // 2.3 注册编译信息监听器,用于监听编译信息
        final DiagnosticCollector<JavaFileObject> javaFileObjectDiagnosticCollector = new DiagnosticCollector<>();

        // 2.4 处理待编译的代码
        StringSourceJavaFileObject stringSourceJavaFileObject = new StringSourceJavaFileObject(stringBuilder, className);
        final List<JavaFileObject> javaFileObjects = new ArrayList<>();
        javaFileObjects.add(stringSourceJavaFileObject);
        // Writer out：使用System.out
        // JavaFileManager javaFileManager:
        // DiagnosticListener<? super JavaFileObject> diagnosticListener:
        // Iterable<String> options:编译参数
        // terable<String> classes:带处理的注解，需要使用task.setProcessors()设置注解处理器
        // Iterable<? extends JavaFileObject> compilationUnits:需要编译的代码
        final JavaCompiler.CompilationTask task = systemJavaCompiler.getTask(null, classFileManager,
                javaFileObjectDiagnosticCollector, null, null, javaFileObjects);
        // 编译代码
        final Boolean call = task.call();
        // 编译失败情况
        if (!call){
            System.out.println("编译失败");
            final List<Diagnostic<? extends JavaFileObject>> diagnostics = javaFileObjectDiagnosticCollector.getDiagnostics();
            for (Diagnostic<? extends JavaFileObject> diagnostic:
                 diagnostics) {
                final long lineNumber = diagnostic.getLineNumber();
                final String code = diagnostic.getCode();
                final JavaFileObject source = diagnostic.getSource();
                final String message = diagnostic.getMessage(Locale.CHINA);
                System.out.println("lineNumber：" + lineNumber);
                System.out.println("code：" + code);
                System.out.println("source：" + source);
                System.out.println("message：" + message);
            }
            System.exit(1);
        }

        standardFileManager.close();
        classFileManager.close();
        // 编译成功则调用类
        System.out.println();
        // 加载类，该类的源代码来自内存（DEFAULT_CODE常量，而没有实体的java文件）
        // 再次获取SecureClassLoader，因为编译完成的类的字节码在这个SecureClassLoader中defineClass()的！
        final ClassLoader classLoader = classFileManager.getClassLoader(StandardLocation.CLASS_PATH);
        // 加载类
        final Class<?> loadClass = classLoader.loadClass(className);
        System.out.println("========================================================================");

        System.out.println("类信息：" + loadClass);
        // 调用方法
        // 获取静态main方法
        System.out.println("调用main方法...");
        final Method main = loadClass.getMethod("main", String[].class);
        // 调用方法
        main.invoke(null, (Object) new String[0]);


    }

    private static String findClassName(List<String> codeList) {
        final String s = codeList.get(0);
        String className = s.split(" ")[1];
        className = className.substring(0, className.length() - 1);
        for (String codeLine :
                codeList) {
            if (codeLine.contains("public class ")){
                final String classNameOnly = codeLine.split(" ")[2];
                className = className + "." + classNameOnly;
                return className;
            }
        }
        throw new RuntimeException();
    }

    private static StringBuilder toCode(List<String> codeList){
        final StringBuilder stringBuilder = new StringBuilder();
        for (String codeLine :
                codeList) {
         stringBuilder.append(codeLine).append("\n");
        }
        return stringBuilder;
    }
}
