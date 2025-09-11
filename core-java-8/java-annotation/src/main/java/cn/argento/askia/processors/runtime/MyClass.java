package cn.argento.askia.processors.runtime;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Arrays;

class MyClass3 extends MyClass<MyClass2>{}

class MyClass2 extends MyClass<MyClass3> {}

public class MyClass<@MyAnnotation T extends MyClass<?>> {
    public void myMethod(@MyAnnotation MyClass<T> this) {
    }

    @MyAnnotation
    public void myMethod2(){

    }
    public static void main(String[] args) throws NoSuchMethodException {
        MyClass<MyClass2> obj = new MyClass<>();
        Class<?> clazz = obj.getClass();
        // 获取方法上的参数类型,该类型是MyClass<T> this 中的MyClass<T>，类型是一个参数化类型（AnnotatedParameterizedType）
        AnnotatedType annotatedType = clazz.getMethod("myMethod").getAnnotatedReceiverType();
        final Annotation[] myMethods = clazz.getMethod("myMethod").getAnnotations();
        // 获取标记在方法上的注解
        System.out.println(Arrays.toString(myMethods));

        // 获取参数上的所有注解
        Annotation[] annotations = annotatedType.getAnnotations();
        // 输出注解信息
        System.out.println(annotatedType);
        Arrays.stream(annotations).forEach(annotation -> System.out.println(annotation.annotationType().getSimpleName() + "=" + annotation.annotationType()));
        System.out.println(annotations[0]);
        System.out.println();

        final Method myMethod2 = clazz.getMethod("myMethod2");
        final Annotation[] annotations1 = myMethod2.getAnnotations();
        final Annotation[] annotations2 = myMethod2.getAnnotatedReturnType().getAnnotations();
        System.out.println(Arrays.toString(annotations1));
        System.out.println(Arrays.toString(annotations2));

        System.out.println();

        final TypeVariable<? extends Class<?>>[] typeParameters = clazz.getTypeParameters();
        System.out.println("123" + typeParameters[0]);
        System.out.println(typeParameters[0].getAnnotatedBounds()[0].getClass());
        System.out.println(typeParameters[0].getBounds()[0].getTypeName());
        System.out.println(Arrays.toString(((AnnotatedParameterizedType) typeParameters[0].getAnnotatedBounds()[0]).getAnnotatedActualTypeArguments()));
        final AnnotatedType[] annotatedUpperBounds = ((AnnotatedWildcardType) ((AnnotatedParameterizedType) typeParameters[0].getAnnotatedBounds()[0]).getAnnotatedActualTypeArguments()[0])
                .getAnnotatedUpperBounds();
        System.out.println(annotatedUpperBounds[0].getType().getTypeName());
    }
}
// 自定义注解

@Target({ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
}
