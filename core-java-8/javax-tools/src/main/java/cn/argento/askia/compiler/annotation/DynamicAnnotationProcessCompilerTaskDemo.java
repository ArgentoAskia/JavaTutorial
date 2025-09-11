package cn.argento.askia.compiler.annotation;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 基于Compiler API的源码级别的注解处理器！
 *
 */
public class DynamicAnnotationProcessCompilerTaskDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        final JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();

        // 创建监听器
        DiagnosticCollector<JavaFileObject> javaFileObjectDiagnosticCollector = new DiagnosticCollector<>();

        // 获取标准文件管理器
        final StandardJavaFileManager standardFileManager = systemJavaCompiler.getStandardFileManager(javaFileObjectDiagnosticCollector, Locale.CHINA, null);

        // 将HelloWorld.java转为JavaFileObject
        File javaFile = new File("Java-Compiler/src/main/java/cn/argento/askia/compiler/annotation/User.java");
        final Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(javaFile);

        // 标记了源码级别注解的类的类名！
        // 这里的类名会被放在 输入文件: {cn.argento.askia.compiler.annotation.User}内
        String annotatedClassName = Date.class.getName();

        // 传递编译参数参数！
        List<String> options = new ArrayList<>();
        options.add("-XprintRounds");
        // 传递带参数的编译器参数，需要这样传，不能直接传递拼接的版本
        options.add("-s");
        options.add("D:\\OpenSourceProject\\JavaProject\\Java-Compiler\\src\\main\\java\\cn\\argento\\askia\\compiler\\annotation");
        final JavaCompiler.CompilationTask task = systemJavaCompiler.getTask(null, standardFileManager,
                javaFileObjectDiagnosticCollector, options, Collections.singletonList(annotatedClassName), javaFileObjects);
        // 设置注解处理器
        DynamicCompilerTaskToStringAnnotationProcessor dynamicCompilerTaskToStringAnnotationProcessor = new DynamicCompilerTaskToStringAnnotationProcessor();
        task.setProcessors(Collections.singletonList(dynamicCompilerTaskToStringAnnotationProcessor));

        final Boolean call = task.call();

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
            standardFileManager.close();
            System.exit(1);
        }
        standardFileManager.close();


        // 动态加载注解处理器的类和编译出来的User类
        final ClassLoader classLoader = standardFileManager.getClassLoader(StandardLocation.CLASS_PATH);
        final Class<?> userClass = classLoader.loadClass("cn.argento.askia.compiler.annotation.User");
        final Class<?> toStringsClass = classLoader.loadClass("cn.argento.askia.compiler.annotation.ToStrings");
        System.out.println(userClass);
        System.out.println(toStringsClass);

        // User[id = 10, name = Askia, Address = china]
        final Object userObj = userClass.newInstance();
        final Field[] declaredFields = userClass.getDeclaredFields();
        for (Field f :
                declaredFields) {
            f.setAccessible(true);
            if (f.getName().equalsIgnoreCase("id")){
                f.setInt(userObj, 10);
            }
            else if (f.getName().equalsIgnoreCase("name")){
                f.set(userObj, "Askia");
            }
            else {
                f.set(userObj, "china");
            }
        }

        // call ToStrings.toString(User user)
        final Method toStringMethod = toStringsClass.getMethod("toString", userClass);
        final Object invoke = toStringMethod.invoke(null, userObj);
        System.out.println(invoke);

    }
}
