package cn.argento.askia;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

/**
 * 基于getTask的编译运行方式
 */
public class DynamicCompilerTaskDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();

        // 创建监听器
        DiagnosticCollector<JavaFileObject> javaFileObjectDiagnosticCollector = new DiagnosticCollector<>();

        // 获取标准文件管理器
        final StandardJavaFileManager standardFileManager = systemJavaCompiler.getStandardFileManager(javaFileObjectDiagnosticCollector, Locale.CHINA, null);

        // 将HelloWorld.java转为JavaFileObject
        File javaFile = new File("Java-Compiler/src/main/resources/cn/argento/askia/HelloWorld.java");
        final Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(javaFile);


        final JavaCompiler.CompilationTask task = systemJavaCompiler.getTask(null, standardFileManager,
                javaFileObjectDiagnosticCollector, null, null, javaFileObjects);

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


        final ClassLoader classLoader = standardFileManager.getClassLoader(StandardLocation.CLASS_PATH);
        final Class<?> loadClass = classLoader.loadClass("cn.argento.askia.HelloWorld");
        final Method main = loadClass.getMethod("main", String[].class);
        // 调用方法
        main.invoke(null, (Object) new String[0]);

    }
}
