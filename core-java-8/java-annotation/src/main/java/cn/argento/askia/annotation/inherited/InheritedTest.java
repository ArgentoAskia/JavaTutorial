package cn.argento.askia.annotation.inherited;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 继承性注解测试Demo
 * 结论：带@Inherited元注解的注解只有被标记在父类上（只能是class）的时候才能使用反射API通过子类获取！
 *      其他如父类的方法、父类是一个接口则无效！
 */
public class InheritedTest {
    public static void main(String[] args) throws NoSuchMethodException {
        // 方法被重写，则以重写后的方法上的为准！
        // 结果：[]
        final Method getName = Son.class.getMethod("getName");
        final Annotation[] annotations = getName.getAnnotations();
        System.out.println(Arrays.toString(annotations));

        // 方法没有被重写，则以父类的方法的为基准
        // 结果：[@InheritedAnnotation、@BugReport]
        final Method setName = Son.class.getMethod("setName", String.class);
        final Annotation[] annotations3 = setName.getAnnotations();
        System.out.println(Arrays.toString(annotations3));

        // 子类被继承 Son类继承Father类，Father类上标记有@InheritedAnnotation注解！
        // 结果：[@cn.argento.askia.annotation.inherited.InheritedAnnotation(name=Askia3, clazz=class java.lang.Void, version=1.0)]
        final Annotation[] annotations1 = Son.class.getAnnotations();
        System.out.println(Arrays.toString(annotations1));
        // DeclaredAnnotations获取直接注解！
        // 结果：[]
        final Annotation[] annotations12 = Son.class.getDeclaredAnnotations();
        System.out.println(Arrays.toString(annotations12));

        // 接口上也不会被继承
        // 结果: []
        final Annotation[] annotations2 = Son2.class.getAnnotations();
        System.out.println(Arrays.toString(annotations2));


    }
}

