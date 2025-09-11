package cn.argento.askia.processors.runtime;

import cn.argento.askia.annotation.inherited.Son;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

/**
 * 此Demo主要说明：
 * `Runtime`级别的注解处理器`API`实际上和反射`API`是高度重合的，部分`API`甚至都能重合在一块：
 */
public class ExtendsAPITest {
    public static void main(String[] args) {
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
