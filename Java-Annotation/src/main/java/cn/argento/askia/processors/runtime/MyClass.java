package cn.argento.askia.processors.runtime;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

class MyClass3 extends MyClass<MyClass2>{}

class MyClass2 extends MyClass<MyClass3>{}

public class MyClass<@MyAnnotation T extends MyClass<?>> {
    public void myMethod(@MyAnnotation MyClass<T> this) {
    }
    public static void main(String[] args) throws NoSuchMethodException {
        MyClass<MyClass2> obj = new MyClass<>();
        Class<?> clazz = obj.getClass();
        // 获取方法上的参数类型,该类型是MyClass<T> this 中的MyClass<T>，类型是一个参数化类型（AnnotatedParameterizedType）
        AnnotatedType annotatedType = clazz.getMethod("myMethod").getAnnotatedReceiverType();

        // 获取参数上的所有注解
        Annotation[] annotations = annotatedType.getAnnotations();

        // 输出注解信息
        System.out.println(annotatedType);
        Arrays.stream(annotations).forEach(annotation -> System.out.println(annotation.annotationType().getSimpleName() + "=" + annotation.annotationType()));

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

@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
}
