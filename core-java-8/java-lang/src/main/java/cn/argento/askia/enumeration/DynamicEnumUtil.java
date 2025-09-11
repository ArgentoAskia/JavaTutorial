package cn.argento.askia.enumeration;


import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import cn.argento.askia.enumeration.Size;

/**
 * 运行时动态修改枚举常量演示.
 * 目前能实现动态添加某个常量，动态修改已有常量值
 * 底层技术支持：Java反射
 * 原理：
 *      1. Java中所有的枚举类使用一个数组常量$VALUES来存储所有的枚举常量，通过修改$VALUES
 *         可以实现动态增加枚举量、动态修改枚举常量
 *      2. 动态添加的枚举量只能通过Enum类的valueOf()静态方法来获取，无法直接通过[枚举类.枚举常量]的形式来获取
 *         如果希望直接使用枚举类.枚举常量的方式获取，则需要修改字节码
 *      3. 枚举类的Class对象会缓存枚举类的所有枚举常量，由Class类的两个字段来存放：
 *          private volatile transient Map<String, T> enumConstantDirectory = null;
 *          private volatile transient T[] enumConstants = null;
 *          Enum类的valueOf()方法会调用Class类的包级私有方法：
 *              Map<String, T> enumConstantDirectory()方法
 *          这个方法会初始化enumConstantDirectory字段，并通过调用：
 *              T[] getEnumConstantsShared()方法
 *          同时初始化enumConstants字段
 *      4. getEnumConstantsShared()初始化enumConstants字段的原理是靠调用枚举类的
 *         静态合成方法values()获取枚举数组常量$VALUES实现的
 *      总结：
 *      因此要想实现动态增加枚举常量，需要 1.修改$VALUES变量，
 *      2.清空Class类中enumConstantDirectory字段和enumConstants字段，让系统重新调用静态合成方法values()
 *      触发更新
 *
 *      5.如何创建枚举类实例？Enum方法有一个protected的构造器，参数是String，int
 *      其中String代表枚举常量的常量名，如Size枚举类型有一个SMALL的枚举常量，则会传入”SMALL“字符串
 *      第二个int代表该枚举常量在数组$VALUES的index
 *      因此可以使用这个构造器，实际上自定义的枚举类型所有的构造器在编译成字节码之后都将会添加上这两个参数
 *      虽然这两个参数在Java代码中不可见，但在字节码中可见，如枚举类Size的构造器被定义为；
 *      private Size() 则编译成字节码时反编译结果会是：
 *      private <init>(Ljava/lang/String;I) ==> private Size(String, int);
 *      Java类型中使用I代表int
 *
 *
 *  参考：
 *  https://blog.51cto.com/u_16175447/11520817
 *  https://blog.csdn.net/u013813491/article/details/126511277
 *
 * @author Askia
 */
public class DynamicEnumUtil {

    public static <T extends Enum<T>> T addEnumConstant(Class<T> enumClass,
                                                        String enumName,
                                                        Class<?>[] paramsTypes,
                                                        Object[] params){
        Objects.requireNonNull(params);
        Objects.requireNonNull(paramsTypes);

        Object[] args = new Object[2 + params.length];
        // 1.get new Ordinal
        final int newOrdinal = getNewOrdinal(enumClass);
        // 2.设置前面的两个固定参数，枚举常量名和其对应的index
        args[0] = enumName;
        args[1] = newOrdinal;
        // 3.剩余的全部复制到args数组
        System.arraycopy(params, 0, args, 2, params.length);

        // 4.获取枚举类型的private构造器
        final Constructor<T> enumConstructor = getEnumConstructor(enumClass, paramsTypes);
        if (enumConstructor == null){
            // 无法获取构造器，失败
            return null;
        }

        // 5.创建新的枚举常量
        final T newEnumConstantObject = newEnumConstantObject(enumConstructor, args);
        if (newEnumConstantObject == null){
            // 无法创建枚举常量，失败
            return null;
        }
        System.out.println("新的枚举常量：" + newEnumConstantObject);

        // 6.添加到$VALUES内部
        addNewEnumConstantToValuesArray(enumClass, newEnumConstantObject);

        // 7.清除缓存
        clearEnumClassCache(enumClass);

        return newEnumConstantObject;
    }

    // 获取枚举构造器
    private static <T extends Enum<T>> Constructor<T> getEnumConstructor(Class<T> enumClass,
                                                                         Class<?>[] paramsTypes){
        // 1.判断构造器参数是否为空？
        Class<?>[] realParamsTypes = null;
        if (paramsTypes == null || paramsTypes.length == 0){
            realParamsTypes = new Class[2];
        }
        else{
            realParamsTypes = new Class[2 + paramsTypes.length];
            // 复制剩余参数到realParamsTypes
            System.arraycopy(paramsTypes, 0, realParamsTypes, 2, paramsTypes.length);
        }

        // 2.组合成真实的构造器，枚举类型的所有构造器（无论有参还是无参），默认都需要加上一个String、一个int参数，这些参数会
        // 传递给Enum类的构造器protected Enum(String name, int ordinal)，见doc的第五条
        realParamsTypes[0] = String.class;
        realParamsTypes[1] = int.class;

        // 3.获取枚举类型private构造器
        try {
            return enumClass.getDeclaredConstructor(realParamsTypes);
        } catch (NoSuchMethodException e) {
            // 找不到该构造器
            e.printStackTrace();
            return null;
        }
    }


    // 创建枚举常量对象
    private static <T extends Enum<T>> T newEnumConstantObject(Constructor<T> enumPrivateConstructor,
                                                               Object[] params){
        System.out.println("enum private constructor = [" + enumPrivateConstructor + "], accessible = " + enumPrivateConstructor.isAccessible());
        enumPrivateConstructor.setAccessible(true);
        try {
            // 由于SecurityManager，直接使用newInstance()可能会
            // 抛出IllegalArgumentException: Cannot reflectively create enum objects
            // 使用原始的ReflectionFactory来创建即可
            // return enumPrivateConstructor.newInstance(params);
            final Object newEnumConstant = ReflectionFactory.getReflectionFactory()
                    .newConstructorAccessor(enumPrivateConstructor).newInstance(params);
            return enumPrivateConstructor.getDeclaringClass().cast(newEnumConstant);
        } catch (InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 修改static final的字段，去除final，使其可修改
    private static void makeFieldAccessibleAndSetValue(Field field, Object belong, Object value){
        // 1.设置可访问
        field.setAccessible(true);
        // 2.去除Field的final修饰符，实现访问static final 的 $VALUES
        try {
            // 2.1 获取Field类的modifiers字段，该字段代表一个字段的修饰符
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            // 2.2 获取修饰符
            int modifiers = modifiersField.getInt(field);
            if (Modifier.isFinal(modifiers)) {
                // 2.3 去除final修饰符
                modifiers = modifiers & (~Modifier.FINAL);
                modifiersField.setInt(field, modifiers);
            }


            // 3. 设置属性值
            // 由于安全管理器（SecurityManager）的权限管理，部分实现直接使用下面的set()会抛出IllegalArgumentException异常，
            // 无法设置值，因此决定采用原始的ReflectionFactory来设置值
            // field.set(belong, value);
            final FieldAccessor fieldAccessor = ReflectionFactory.getReflectionFactory().newFieldAccessor(field, false);
            fieldAccessor.set(belong, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取新Enum constant的Ordinal
    private static <T extends Enum<T>> int getNewOrdinal(Class<T> enumClass){
        final T[] enumConstants = enumClass.getEnumConstants();
        return enumConstants == null? 0: enumConstants.length;
    }

    // 添加新的枚举常量到$VALUES数组
    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> void addNewEnumConstantToValuesArray(Class<T> enumClass, T newEnumConstant){
        try {
            // 1. get static enum array $VALUES and make it Accessible
            final Field valuesField = enumClass.getDeclaredField("$VALUES");
            valuesField.setAccessible(true);

            // 2. change to array and add new Enum Constant
            final T[] values = (T[])valuesField.get(null);
            System.out.println("values before: " + Arrays.toString(values));
            List<T> valuesList = new ArrayList<>(Arrays.asList(values));
            valuesList.add(newEnumConstant);
            // 引用类型的强制类型转换必须存在继承关系才行，也就是夫类型强制转换为子类型（要求父类型必须实际上是子类型）
            // 强制类型转换还能发张基本类型上，基本类型必须是同一类型才行！如整数的byte、int等进行强转，但无法将boolean强转为int
            // java的强制类型转换和C++的稍有区别！
            // 这也解释了为什么这里(T[]) valuesList.toArray();会报错，因为Object[]和T[]没有关系，而Object[]、T[]都继承自Object
            // 正确的做法应该是使用另一个toArray()重载体
//            final T[] newValues = (T[]) valuesList.toArray();
            final T[] newValues = valuesList.toArray((T[])Array.newInstance(enumClass, 0));
            System.out.println("values after: " + Arrays.toString(newValues));

            // 3. modify new $VALUES, static value belong arg set null
            makeFieldAccessibleAndSetValue(valuesField, null, newValues);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 清除枚举类的Class对象中相关枚举常量的缓存！
    private static void clearEnumClassCache(Class<? extends Enum<?>> enumClass){
        // jdk class enumConstantDirectory Field and enumConstants Field checked
        try {
            final Field enumConstantDirectoryMapField = Class.class.getDeclaredField("enumConstantDirectory");
            makeFieldAccessibleAndSetValue(enumConstantDirectoryMapField, enumClass, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            final Field enumConstantsField = Class.class.getDeclaredField("enumConstants");
            makeFieldAccessibleAndSetValue(enumConstantsField, enumClass, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    // 测试

    public static void main(String[] args) {
        final Size[] values = Size.values();
        System.err.println("添加前：" + Arrays.toString(values));
        // 下面的常量无法获取
        try{
            final Size size = Size.valueOf("LITTLE_SMALL");
            System.err.println(size);
        }catch (Exception e){
            e.printStackTrace();
        }

        final Size newSizeConstant = addEnumConstant(Size.class, "LITTLE_SMALL", new Class[0], new Object[0]);
        if (newSizeConstant == null){
            System.err.println("无法创建新常量");
            return;
        }
        final Size[] values2 = Size.values();
        System.err.println("添加后：" + Arrays.toString(values2));
        // 现在可以获取了
        try{
            final Size size = Size.valueOf("LITTLE_SMALL");
            System.err.println(size);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
