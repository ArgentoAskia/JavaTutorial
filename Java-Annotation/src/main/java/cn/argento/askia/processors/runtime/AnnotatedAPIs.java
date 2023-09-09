package cn.argento.askia.processors.runtime;


import java.lang.reflect.*;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class AnnotatedAPIs<T, S extends Number> implements Comparable<S>, Callable<List<T>> {


    private S s;
    private T t;
    private List<? extends S> list;
    private Set<? super Integer> set;
    private List<String> b;
    private String string;
    private String[] strings;
    private T[] e;

    public static void main(String[] args) {
        AnnotatedAPIs<InetAddress, Double> annotatedAPIs = new AnnotatedAPIs<>();
        final Class<? extends AnnotatedAPIs> aClass = annotatedAPIs.getClass();
        // 获取继承的接口的类型Comparable<S>和Callable<List<T>>, 该类型是一个参数化类型，所以是一个AnnotatedParameterizedType对象
        final AnnotatedType[] annotatedInterfaces = aClass.getAnnotatedInterfaces();
        System.out.println(Arrays.toString(annotatedInterfaces));

        // 解析Comparable<S>接口
        AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType)annotatedInterfaces[0];
        // 使用getAnnotatedActualTypeArguments()来获取泛型部分，即<S>,他是一个泛型参数，所以是AnnotatedTypeVariable类型
        final AnnotatedType[] annotatedActualTypeArguments = annotatedParameterizedType.getAnnotatedActualTypeArguments();
        System.out.println(Arrays.toString(annotatedActualTypeArguments));
        AnnotatedTypeVariable annotatedTypeVariable = (AnnotatedTypeVariable) annotatedActualTypeArguments[0];
        // 拿到类型参数的边界,该边界也就是<S extends Number>中的上界Number
        final AnnotatedType[] annotatedBounds = annotatedTypeVariable.getAnnotatedBounds();
        System.out.println(Arrays.toString(annotatedBounds));
        System.out.println(annotatedBounds[0].getType());
        System.out.println();

        // 解析Callable<List<T>>
        AnnotatedParameterizedType annotatedParameterizedType2 = (AnnotatedParameterizedType)annotatedInterfaces[1];
        // 使用getAnnotatedActualTypeArguments()来获取泛型部分，即List<T>,他仍然是一个参数化类型
        final AnnotatedType[] annotatedActualTypeArguments2 = annotatedParameterizedType2.getAnnotatedActualTypeArguments();
        System.out.println(Arrays.toString(annotatedActualTypeArguments2));
        // 使用getAnnotatedActualTypeArguments()来获取泛型部分，即<T>,他是一个泛型变量，AnnotatedTypeVariable类型
        AnnotatedParameterizedType annotatedParameterizedType3 = (AnnotatedParameterizedType) annotatedActualTypeArguments2[0];
        final AnnotatedType[] annotatedActualTypeArguments3 = annotatedParameterizedType3.getAnnotatedActualTypeArguments();
        System.out.println(Arrays.toString(annotatedActualTypeArguments3));
        // 拿到类型参数的边界,该边界也就是<S extends Number>中的上界Number
        AnnotatedTypeVariable annotatedTypeVariable2 = (AnnotatedTypeVariable) annotatedActualTypeArguments3[0];
        final AnnotatedType[] annotatedBounds2 = annotatedTypeVariable2.getAnnotatedBounds();
        System.out.println(Arrays.toString(annotatedBounds2));
        System.out.println(annotatedBounds2[0].getType());

        System.out.println();
        // 字段
        final Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            final AnnotatedType annotatedType = declaredField.getAnnotatedType();
            if (annotatedType instanceof AnnotatedArrayType) {
                System.out.println();
                System.out.println("AnnotatedArrayType");
                AnnotatedArrayType annotatedArrayType = (AnnotatedArrayType) annotatedType;
                // 获取数组类型的ComponentType
                final AnnotatedType annotatedGenericComponentType = annotatedArrayType.getAnnotatedGenericComponentType();
                // 有可能是泛型数组
                if (annotatedGenericComponentType instanceof AnnotatedTypeVariable){
                    System.out.println("AnnotatedTypeVariable");
                    AnnotatedTypeVariable annotatedTypeVariable1 = (AnnotatedTypeVariable) annotatedGenericComponentType;
                    System.out.println(annotatedTypeVariable1);
                    System.out.println(annotatedTypeVariable1.getType());
                    System.out.println(annotatedTypeVariable1.getAnnotatedBounds()[0].getType());
                }else {
                    System.out.println(annotatedGenericComponentType);
                    System.out.println(annotatedGenericComponentType.getType());
                }
                System.out.println("=======================================");
            }
            else if (annotatedType instanceof AnnotatedTypeVariable){
                System.out.println();
                System.out.println("AnnotatedTypeVariable");
                AnnotatedTypeVariable annotatedTypeVariable1 = (AnnotatedTypeVariable) annotatedType;
                System.out.println(annotatedTypeVariable1);
                System.out.println(annotatedTypeVariable1.getType());
                System.out.println(annotatedTypeVariable1.getAnnotatedBounds()[0].getType());
                System.out.println("=======================================");
            }
            else if (annotatedType instanceof AnnotatedParameterizedType){
                System.out.println();
                System.out.println("AnnotatedParameterizedType");
                AnnotatedParameterizedType annotatedParameterizedType1 = (AnnotatedParameterizedType) annotatedType;
                final AnnotatedType[] annotatedActualTypeArguments1 = annotatedParameterizedType1.getAnnotatedActualTypeArguments();
                System.out.println(Arrays.toString(annotatedActualTypeArguments1));
                if (annotatedActualTypeArguments1[0] instanceof AnnotatedWildcardType){
                    System.out.println("AnnotatedWildcardType");
                    AnnotatedWildcardType annotatedWildcardType = (AnnotatedWildcardType) annotatedActualTypeArguments1[0];

                    final AnnotatedType[] annotatedLowerBounds = annotatedWildcardType.getAnnotatedLowerBounds();
                    final AnnotatedType[] annotatedUpperBounds = annotatedWildcardType.getAnnotatedUpperBounds();

                    if (annotatedLowerBounds == null || annotatedLowerBounds.length == 0){
                        System.out.println("UpperBounds");
                        System.out.println(Arrays.toString(annotatedUpperBounds));
                        System.out.println(annotatedUpperBounds[0].getType());
                    }else{
                        System.out.println("LowerBounds");
                        System.out.println(Arrays.toString(annotatedLowerBounds));
                        System.out.println(annotatedLowerBounds[0].getType());
                    }
                    System.out.println("=======================================");
                }else{
                    System.out.println(annotatedType);
                    System.out.println(annotatedType.getType());
                    System.out.println("=======================================");
                }
            }else{
                // base type
                System.out.println();
                System.out.println("baseType");
                System.out.println(annotatedType);
                System.out.println(annotatedType.getType());
                System.out.println("=======================================");

            }

        }
    }

    @Override
    public int compareTo(S o) {
        return 0;
    }

    @Override
    public List<T> call() throws Exception {
        return null;
    }
}
