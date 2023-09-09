package cn.argento.askia.annotation.inherited;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

public class InheritedTest {
    public static void main(String[] args) throws NoSuchMethodException {
        // 方法上不会被继承
        final Method getName = Son.class.getMethod("getName");
        final Annotation[] annotations = getName.getAnnotations();
        System.out.println(Arrays.toString(annotations));

        // 子类被继承
        final Annotation[] annotations1 = Son.class.getAnnotations();
        System.out.println(Arrays.toString(annotations1));
        // DeclaredAnnotations获取直接注解！
        final Annotation[] annotations12 = Son.class.getDeclaredAnnotations();
        System.out.println(Arrays.toString(annotations12));

        // 接口上也不会被继承
        final Annotation[] annotations2 = Son2.class.getAnnotations();
        System.out.println(Arrays.toString(annotations2));

        // 获取继承的可被Annotated的类性！
        final AnnotatedType[] annotatedInterfaces = Son.class.getAnnotatedInterfaces();
        final AnnotatedType annotatedSuperclass = Son.class.getAnnotatedSuperclass();
        for (AnnotatedType a :
                annotatedInterfaces) {
            System.out.println(a.getType().getTypeName());
        }
        System.out.println(annotatedSuperclass.getType().getTypeName());

        final Type[] genericInterfaces = Son.class.getGenericInterfaces();
        final Type genericSuperclass = Son.class.getGenericSuperclass();
        for (Type a :
                genericInterfaces) {
            System.out.println(a.getTypeName());
        }
        System.out.println(genericSuperclass.getTypeName());


    }
}
