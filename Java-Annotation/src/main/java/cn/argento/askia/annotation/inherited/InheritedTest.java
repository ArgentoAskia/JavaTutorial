package cn.argento.askia.annotation.inherited;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
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
        // 方法上不会被继承
        // 结果：[]
        final Method getName = Son.class.getMethod("getName");
        final Annotation[] annotations = getName.getAnnotations();
        System.out.println(Arrays.toString(annotations));

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

        // 以下代码和本次测试无关！！！！！

        // 获取继承的可被Annotated的类型！
        final AnnotatedType[] annotatedInterfaces = Son.class.getAnnotatedInterfaces();
        final AnnotatedType annotatedSuperclass = Son.class.getAnnotatedSuperclass();
        for (AnnotatedType a :
                annotatedInterfaces) {
            System.out.println(a.getType().getTypeName());
        }
        System.out.println(annotatedSuperclass.getType().getTypeName());
        System.out.println();

        // 获取泛型化的接口和超类
        final Type[] genericInterfaces = Son.class.getGenericInterfaces();
        final Type genericSuperclass = Son.class.getGenericSuperclass();
        for (Type a :
                genericInterfaces) {
            System.out.println(a.getTypeName());
        }
        System.out.println(genericSuperclass.getTypeName());


    }
}
