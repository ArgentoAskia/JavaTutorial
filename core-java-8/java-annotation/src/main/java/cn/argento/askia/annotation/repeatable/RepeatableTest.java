package cn.argento.askia.annotation.repeatable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 此Demo演示了可重复标记注解的获取
 * 1. 注解容器的@Target要和可重复标记的@Target相同，否则不重合部分不能可重复标记
 * 2. 可重复标记注解在注解被编译完成之后，会强制加上注解容器，因此在JDK 5以前能通过获取注解容器的方式来模拟可重复注解标记的功能
 */
public class RepeatableTest {
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        final Class<Father> fatherClass = Father.class;
        final RepeatableAnnotation[] annotationsByType = fatherClass.getAnnotationsByType(RepeatableAnnotation.class);
        System.out.println(Arrays.toString(annotationsByType));
        System.out.println();

        final Method getName = fatherClass.getMethod("getName");
        final Constructor<Father> constructor = fatherClass.getConstructor();
        final Field name = fatherClass.getDeclaredField("name");
        final RepeatableAnnotation[] annotationsByType1 = getName.getAnnotationsByType(RepeatableAnnotation.class);
        final RepeatableAnnotation[] annotationsByType2 = constructor.getAnnotationsByType(RepeatableAnnotation.class);
        final RepeatableAnnotation[] annotationsByType3 = name.getAnnotationsByType(RepeatableAnnotation.class);
        System.out.println(Arrays.toString(annotationsByType1));
        System.out.println();
        System.out.println(Arrays.toString(annotationsByType2));
        System.out.println();
        System.out.println(Arrays.toString(annotationsByType3));

    }


}
