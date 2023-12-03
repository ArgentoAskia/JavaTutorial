package cn.argento.askia;

import javax.script.Invocable;
import javax.tools.JavaCompiler;
import javax.tools.Tool;
import javax.tools.ToolProvider;
import java.io.*;

/**
 * 代码级别运行javac.exe 并通过CMD的形式运行java.exe执行编译后的class文件！
 */
public class DynamicCompilerDemo {
    public static void main(String[] args) throws IOException {
        // 获取编译器,相当于javac.exe
        final JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
        // 会去掉最后一个/
        File file = new File("Java-Compiler/src/main/resources/");
        String classpath = "/cn/argento/askia/";
        final String helloWorldJavaFilePath = file.getAbsolutePath() + classpath + "HelloWorld.java";
        // 编译成功将返回0
        // 可以传递信息给run方法，前面三个代表输入输出错误流
        // 如果传递null流，则使用标准流
        // 相当于执行了 javac.exe D:\OpenSourceProject\JavaProject\Java-Compiler\src\main\java/cn/argento/askia/HelloWorld.java
        File file1 = new File("Java-Compiler/src/main/resources/cn/argento/askia/output.txt");
        if (!file1.exists()) file1.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        final int run = systemJavaCompiler.run(null, fileOutputStream, fileOutputStream, helloWorldJavaFilePath);
        if (run == 0){
            System.out.println("编译成功");
        }else{
            System.out.println("编译失败");
        }
        System.out.println(helloWorldJavaFilePath);
        System.out.println(file.getAbsolutePath() + classpath);

        // 相当于执行了
        // java -classpath D:\OpenSourceProject\JavaProject\Java-Compiler\src\main\java cn.argento.askia.HelloWorld
        final Process exec = Runtime.getRuntime().exec("java -classpath " + file.getAbsolutePath() + " " + "cn.argento.askia.HelloWorld");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }

    }
}
