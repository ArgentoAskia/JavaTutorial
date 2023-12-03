package cn.argento.askia.processors.source;

import lombok.NonNull;
import lombok.Synchronized;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.*;
import java.beans.Introspector;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@SupportedAnnotationTypes("cn.argento.askia.processors.source.ToString")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ToStringAnnotationProcessor extends AbstractProcessor {
    // annotations参数代表要被处理的注解
    // roundEnv代表当前和之前伦茨的轮询环境
    private int rounds = 0;
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment currentRoundEnv) {
        System.out.println("第" + (++ rounds) + "次循环...");
        System.out.println("待处理的注解：" + annotations);
        // 返回由前一轮生成的注释处理的根元素
//        final Set<? extends Element> rootElements = roundEnv.getRootElements();
        // 获取标记了某个注解的所有元素成员
//        final Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(ToString.class);
        final Set<? extends Element> rootElements = currentRoundEnv.getRootElements();
        System.out.println("返回输入文件：" + rootElements);
        final boolean b = currentRoundEnv.processingOver();
        System.out.println("是否处理完毕？" + b);
        final boolean b1 = currentRoundEnv.errorRaised();
        System.out.println("是否出现了错误" + b1);

        try {

            // 创建ToStrings源文件！多次创建将会抛出异常
            // ToStrings.java会放在运行javac.exe指令的目录里面！

            for (TypeElement typeElement :
                    annotations) {
                // 如果注解是@ToString，则进行@ToString的处理
                if (typeElement.getSimpleName().contentEquals("ToString")) {
                    System.out.println("当前处理的注解：" + typeElement);
                    // 打印当前处理的注解内容
                    printTypeElement(typeElement);
                    // 生成ToStrings.java
                    toStringAnnotationProcess(currentRoundEnv, typeElement);
                    // 打印当前轮次的环境
                    printRoundEnvironment(currentRoundEnv, typeElement);
                }
            }
        } catch (IOException e) {
            // 无法创建文件时，或者IO流出问题时，则输出错误信息！
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,  e.getMessage());
        }
        return true;
    }

    /**
     * 打印注解上的相关API
     * @param typeElement
     */
    private void printTypeElement(TypeElement typeElement) {
        // 获取所有注解
//            final Annotation annotation = typeElement.getAnnotation();
        // 获取所有注解
        final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
        System.out.println("getAnnotationMirrors()：" + annotationMirrors);
        // 获取可重复注解
//            final Annotation[] annotationsByType = typeElement.getAnnotationsByType();
        // 返回在该类或接口中直接声明的字段、方法、构造函数和成员类型。这包括任何(隐式)默认构造函数以及枚举类型的隐式values和valueOf方法。
        final List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        System.out.println("getEnclosedElements()注解定义的所有成员：" + enclosedElements);

        // 返回顶级类所在的包
        final Element enclosingElement = typeElement.getEnclosingElement();
        System.out.println("getEnclosingElement()注解类所在包：" + enclosingElement);
        // 返回所有继承的接口
        final List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
        System.out.println("getInterfaces()继承的接口：" + interfaces);
        // 返回节点的类型，如接口、类、字段等等
        final ElementKind kind = typeElement.getKind();
        System.out.println("getKind()类型：" + kind);
        // 返回修饰符类型
        final Set<Modifier> modifiers = typeElement.getModifiers();
        System.out.println("修饰符：" + modifiers);
        // 返回类的级别，内部类、顶级类、匿名类等
        final NestingKind nestingKind = typeElement.getNestingKind();
        System.out.println("getNestingKind()类级别：" + nestingKind);
        // 返回此类型元素的完全限定名。更准确地说，它返回规范名称。对于没有规范名称的本地类和匿名类，将返回空名称。 泛型类型的名称不包括对其形式类型参数的任何引用。例如，接口java.util的完全限定名。Set  为"java.util.Set"。嵌套类型使用“。”作为分隔符，如“java.util.Map.Entry”。
        final Name qualifiedName = typeElement.getQualifiedName();
        System.out.println("全限定类名：" + qualifiedName);
        // 返回简单类型名称
        final Name simpleName = typeElement.getSimpleName();
        System.out.println("简单名：" + simpleName);
        // 返回继承的类
        final TypeMirror superclass = typeElement.getSuperclass();
        System.out.println("父类：" + superclass.toString());
        // 获取泛型化参数！
        final List<? extends TypeParameterElement> typeParameters = typeElement.getTypeParameters();
        System.out.println("参数化泛型：" + typeParameters);
    }

    /**
     * 打印当前轮询的环境和处理的注解
     * @param currentRoundEnv
     * @param typeElement
     */
    private void printRoundEnvironment(RoundEnvironment currentRoundEnv, TypeElement typeElement){
        // 获取标记这个注解的所有元素
        final Set<? extends Element> elementsAnnotatedWith = currentRoundEnv.getElementsAnnotatedWith(typeElement);
        System.out.println(typeElement + "标记在哪个元素上：" + elementsAnnotatedWith);
        final Set<? extends Element> rootElements = currentRoundEnv.getRootElements();
        System.out.println("返回由前一轮生成的注释处理的根元素" + rootElements);
        final boolean b = currentRoundEnv.processingOver();
        System.out.println("是否处理完毕？" + b);
        final boolean b1 = currentRoundEnv.errorRaised();
        System.out.println("是否出现了错误" + b1);
    }


    /**
     * 处理User类上的ToString注解，并生成ToStrings.java
     * @param currentRoundEnv
     * @param typeElement
     * @throws IOException
     */
    private void toStringAnnotationProcess(RoundEnvironment currentRoundEnv, TypeElement typeElement) throws IOException {
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile("ToStrings");
        // 开始处理注解,获取标记了该注解的所有元素
        final Set<? extends Element> elementsAnnotatedWith = currentRoundEnv.getElementsAnnotatedWith(typeElement);
        try (PrintWriter out = new PrintWriter(sourceFile.openWriter())) {
            // 写ToStrings.java头信息
            printlnReference(out);
            // 写入具体的ToString()
            for (Element e :
                    elementsAnnotatedWith) {
                if (e instanceof TypeElement) {
                    TypeElement te = (TypeElement) e;
                    writeToStringMethod(out, te);
                }
            }
            // 写ToString.java的尾
            printlnEnd(out);
        }
    }

    /**
     * 将public static String toString(cn.argento.askia.processors.source.User obj)方法的内容和方法声明写入ToStrings类
     * @param out
     * @param te
     */
    private void writeToStringMethod(PrintWriter out, TypeElement te) {
        // 获取全限定类名
        String className = te.getQualifiedName().toString();
        // 拼接方法头！
        out.println("    public static String toString(" + className + " obj) {");
        // 获取注解，弄属性！
        final ToString toStringAnnotation = te.getAnnotation(ToString.class);
        out.println("        StringBuilder result = new StringBuilder();");

        if (!toStringAnnotation.includeClassName())
            out.println("        result.append(\"" + className + "\");");
        out.println("        result.append(\"[\");");
        // 获取类上的所有子元素
        for (Element c : te.getEnclosedElements()) {
            // 获取Getter方法，其他东西忽略
            final ElementKind kind = c.getKind();
            // 如果是字段，才处理
            if (kind == ElementKind.METHOD){
                // Getter方法名
                final Name methodName = c.getSimpleName();
                // 不是getter方法，则继续遍历
                if (!methodName.toString().startsWith("get")){
                    continue;
                }
                // 字段名
                CharSequence fieldName = methodName.subSequence(3, methodName.length());

                if (!toStringAnnotation.ignoreFieldName()){
                    out.println("        result.append(\""+ fieldName + "=" + "\");");
                }
                out.println("        result.append(toString(obj." + methodName + "()));");
            }
        }
        out.println("        result.append(\"]\");");
        out.println("        return result.toString();");
        out.println("    }");
    }

    /**
     * 往ToStrings.java中输出生成代码声明！
     * @param out
     */
    private void printlnReference(PrintWriter out){
        // 开头的声明
        out.println("// Automatically generated by "
                + "cn.argento.askia.processors.source.ToStringAnnotationProcessor");
        out.println("// Thanks for core java 11, it is a excellent book to read!!!");
        out.println();
        out.println("package cn.argento.askia.processors.source;");
        out.println("public class ToStrings {");
    }

    /**
     * 往ToStrings.java中输出生成代码声明！
     * @param out
     */
    private void printlnEnd(PrintWriter out){
        out.println("    public static String toString(Object obj) {");
        out.println("        return java.util.Objects.toString(obj);");
        out.println("    }");
        out.println("}");
    }
}
