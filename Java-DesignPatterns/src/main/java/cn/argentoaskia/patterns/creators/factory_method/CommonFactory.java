package cn.argentoaskia.patterns.creators.factory_method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonFactory {

    public <T extends Product> T getProduct(Class<T> tClass){
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }



    private Class<?>[] getArgsClasses(Object[] args){
        List<Class<?>> argClassList = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            argClassList.add(args[i].getClass());
        }
        return argClassList.toArray(new Class[0]);
    }
    // 获取符合参数输入的构造器
    private Constructor<?>[] matchParams(Constructor<?>[] constructors, Class<?>[] paramClasses){
        for (int i = 0; i < paramClasses.length; i++) {
            constructors = matchConstructors(constructors, paramClasses[i], i);
        }
        return constructors;
    }
    // 判断构造器的某个参数的类型是否能放入参数
    private Constructor<?>[] matchConstructors(Constructor<?>[] constructors, Class<?> paramClass, int paramIndex){
        List<Constructor<?>> constructorList = new ArrayList<>();
        for (Constructor<?> c :
                constructors) {
            final Class<?> parameterType = c.getParameterTypes()[paramIndex];
            // 参数类型符合
            if (matchType(parameterType, paramClass)){
                constructorList.add(c);
            }
        }
        return constructorList.toArray(new Constructor[0]);
    }

    // 判断cl1是否等于cl2或者cl1是否是cl2的父类或者父接口
    private boolean matchType(Class<?> cl1, Class<?> cl2){
        // 如果是Void，则之后Void与之相等
        if (cl1 == Void.class || cl2 == Void.class)         return cl1 == cl2;
        if (cl1 == cl2)                                     return true;
        else if (cl1.isAssignableFrom(cl2))                 return true;
        else if (cl2.isPrimitive())                         return cl1 == Object.class;
        else if (cl2.isArray())                             return cl1 == Object.class;
        else if (cl2.isAnnotation())                        return cl1 == Annotation.class || cl1 == Object.class;
        else if (cl2.isEnum())                              return cl1 == Enum.class || cl1 == Object.class;
        // this will not happend!
        throw new RuntimeException();
    }
    private Constructor<?>[] matchParamCounting(Constructor<?>[] target, int argLength){
        List<Constructor<?>> matchConstructorList = new ArrayList<>();
        for (Constructor<?> constructor:
                target) {
            final int parameterCount = constructor.getParameterCount();
            if (parameterCount == argLength){
                matchConstructorList.add(constructor);
            }
        }
        return matchConstructorList.toArray(new Constructor[0]);
    }



    public <T extends Product> T getProduct(Class<T> tClass, Object... args){
        final Constructor<?>[] declaredConstructors = tClass.getDeclaredConstructors();
        final Constructor<?>[] matchConstructors = matchParamCounting(declaredConstructors, args.length);
        final Class<?>[] argsClasses = getArgsClasses(args);
        final Constructor<?>[] constructors = matchParams(matchConstructors, argsClasses);
        System.out.println(constructors.length);

        System.out.println(matchConstructors.length);
        System.out.println(declaredConstructors.length);
        return null;
    }

    public static void main(String[] args) {
        CommonFactory creator = new CommonFactory();
        final ConcreteProductA productA = creator.getProduct(ConcreteProductA.class);
        final ConcreteProductB productB = creator.getProduct(ConcreteProductB.class);
        final ConcreteProductC productC = creator.getProduct(ConcreteProductC.class);
        final ConcreteProductC productD = creator.getProduct(ConcreteProductC.class, 0);

    }
}
