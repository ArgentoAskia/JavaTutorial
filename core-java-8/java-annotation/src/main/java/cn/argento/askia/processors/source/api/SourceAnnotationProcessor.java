package cn.argento.askia.processors.source.api;

import cn.argento.askia.processors.source.ToString;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementKindVisitor8;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

// @SupportedAnnotationTypes指定要处理的源码级别注解，不写在这里的源码级别的注解将不会处理
@SupportedAnnotationTypes(
        {"cn.argento.askia.processors.source.ToString", "cn.argento.askia.processors.source.api.API"}
)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SourceAnnotationProcessor extends AbstractProcessor {
    // annotations参数代表要被处理的注解
    // roundEnv代表当前和之前伦茨的轮询环境
    private int rounds = 0;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment currentRoundEnv) {
        System.out.println();
        System.out.println();
        System.out.println("===================================================================");
        System.out.println("第" + (++rounds) + "次循环...");
        System.out.println();
        System.out.println("待处理的注解：" + annotations);
        annotations.forEach(annotation -> {
            System.out.println(annotation + ", class=" + Arrays.toString(annotation.getClass().getInterfaces()) + ", type=" + annotation.asType());
            printTypeElement(annotation);
            printRoundEnvironment(currentRoundEnv, annotation);
        });

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

        System.out.println("===================================================================");
        return true;
    }

    /**
     * 打印注解上的相关API
     * TypeElement 代表一个类型节点，可以代表一个注解类型、类、接口、枚举等等
     * TypeParameterElement 代表泛型参数
     * VariableElement 代表字段，变量等
     * PackageElement 代表包
     * ExecutableElement 代表可执行的元素，如方法、构造器
     *
     * @param typeElement
     */
    private void printTypeElement(TypeElement typeElement) {
        // 获取typeElement节点上的特定注解
        final Documented annotation = typeElement.getAnnotation(Documented.class);
        System.out.println(typeElement + "上是否标记了@Document ? " + (annotation != null));
        // 获取可重复注解
//            final Annotation[] annotationsByType = typeElement.getAnnotationsByType();
        // 获取typeElement节点的定义信息
        final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
        System.out.println("getAnnotationMirrors()：");
        annotationMirrors.forEach((Consumer<AnnotationMirror>) annotationMirror -> {
            final DeclaredType annotationType = annotationMirror.getAnnotationType();
            System.out.print(" 注解成员：" + annotationType + ", ");
            final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
            System.out.println("注解成员属性：" + elementValues);
        });

        // 返回在该类或接口中直接声明的字段、方法、构造函数和成员类型。这包括任何(隐式)默认构造函数以及枚举类型的隐式values和valueOf方法。
        final List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        System.out.println("getEnclosedElements()注解定义的所有成员：" + enclosedElements);
        enclosedElements.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                System.out.println(typeElement + "属性定义：" + element);
            }
        });

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
        System.out.println();
    }


    /**
     * 打印当前轮询的环境和处理的注解
     *
     * @param currentRoundEnv
     * @param typeElement
     */
    private void printRoundEnvironment(RoundEnvironment currentRoundEnv, TypeElement typeElement) {
        // 获取标记这个注解的所有元素
        final Set<? extends Element> elementsAnnotatedWith = currentRoundEnv.getElementsAnnotatedWith(typeElement);
        elementsAnnotatedWith.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                System.out.println(typeElement + "标记在元素：" + element + ", element's type" + Arrays.toString(element.getClass().getInterfaces()));
            }
        });
        System.out.println();
    }
}
