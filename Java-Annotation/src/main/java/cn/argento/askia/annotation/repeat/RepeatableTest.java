package cn.argento.askia.annotation.repeat;

import java.io.File;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

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
