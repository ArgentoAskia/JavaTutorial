package cn.argento.askia.gson.parsing;

import cn.argento.askia.entry.Country;
import cn.argento.askia.entry.JavaBean;
import cn.argento.askia.entry.UserBean;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

public class GsonBuilderConfigDemo {
//    public static void main(String[] args) {
//        final GsonBuilder gsonBuilder = new GsonBuilder();
//        // 用于序列化或者排除序列化某些类或者类的字段
//        // 参考ExclusionStrategy接口
//        gsonBuilder.addDeserializationExclusionStrategy();
//        gsonBuilder.addSerializationExclusionStrategy();
//        gsonBuilder.setExclusionStrategies();
//
//        // 禁止忽略Html字符
//        gsonBuilder.disableHtmlEscaping();
//        // 禁止内部类序列化！
//        gsonBuilder.disableInnerClassSerialization();
//        // 不使用sun.misc.Unsafe类来创建无默认构造器的对象
//        gsonBuilder.disableJdkUnsafe();
//        // 允许使用引用类型作为map的key进行序列化和反序列化，默认调用key的toString()方法来进行序列化
//        gsonBuilder.enableComplexMapKeySerialization();
//        // 排除某些级别的字段
//        gsonBuilder.excludeFieldsWithModifiers();
//        // 排除所有没有标记@Expose注解的字段
//        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
//
//        // 通过为生成的JSON添加一些特殊的文本前缀，使输出的JSON在Javascript中不可执行。这可以防止第三方网站通过脚本来源进行攻击。
//        gsonBuilder.generateNonExecutableJson();
//
//        // 使用TypeAdapter来进行自定义类型适配器来序列化自己的类型
//        // 需要配合TypeAdapter、InstanceCreator、JsonDeserializer<T>、JsonSerializer<T>
//        gsonBuilder.registerTypeAdapter();
//        gsonBuilder.registerTypeAdapterFactory();
//        gsonBuilder.registerTypeHierarchyAdapter();
//
//        // 序列化null字段
//        gsonBuilder.serializeNulls();
//        // 序列化特殊浮点型，如infinite等
//        gsonBuilder.serializeSpecialFloatingPointValues();
//        // 序列化时间时，采用这个格式进行格式化
//        gsonBuilder.setDateFormat();
//        // 配置Gson，使其在序列化和反序列化期间对对象的字段应用特定的命名策略。
//        gsonBuilder.setFieldNamingPolicy();
//        gsonBuilder.setFieldNamingStrategy();
//
//        // 配置Gson以允许不严格遵守JSON规范的JSON数据。
//        //注意:由于遗留原因，Gson的大多数方法总是比较宽松的，无论是否使用此构建器方法。
//        gsonBuilder.setLenient();
//        // 设置Long类型的序列化策略
//        gsonBuilder.setLongSerializationPolicy();
//        // 设置数字转数字的序列化策略
//        gsonBuilder.setNumberToNumberStrategy();
//        // 设置对象转数字的序列化策略
//        gsonBuilder.setObjectToNumberStrategy();
//        // 配置Json格式化输出
//        gsonBuilder.setPrettyPrinting();
//        // 配置Gson以启用版本支持。版本支持基于自那时以来的注释类型工作。它允许根据指定的版本包含或排除字段和类。有关更多信息，请参阅这些注释类型的文档。
//        // 默认情况下，禁用版本支持，使用@Since和@Until无效。
//        gsonBuilder.setVersion();
//        // 创建对象
//        final Gson gson = gsonBuilder.create();
//    }
}

/**
 * 允许序列化带Null的字段：
 *  gsonBuilder.serializeNulls();
 *
 */
class SerializeNullsGson{
    private static Country jsonBean = new Country();
    static {
        jsonBean.setCountryName("China");
        jsonBean.setZoneId(null);
        jsonBean.setNow(null);
    }
    public static void main(String[] args) {
        Gson gson = new Gson();
        final String s = gson.toJson(jsonBean);
        System.out.println("不开启serializeNulls时，序列化带null的对象将会被过滤：" + s);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        final Gson gson1 = gsonBuilder.create();
        System.out.println("开启serializeNulls时将保留：" + gson1.toJson(jsonBean));
    }
}

/**
 * 不转义HTML字符： gsonBuilder.disableHtmlEscaping();
 */
class DisableHtmlEscaping{
    private static String jsonStr = "{\"countryName\":\"<h3>China</h3>\",\"zoneId\":null,\"now\":null}";
    private static Country jsonBean;
    static {
        jsonBean = new Country();
        jsonBean.setCountryName("<h3>China</h3>");
    }
    public static void main(String[] args) {
        serial();
        deserial();
    }
    private static void serial(){
        Gson gson = new Gson();
        final String s = gson.toJson(jsonBean);
        System.out.println(s);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        final Gson gson1 = gsonBuilder.create();
        System.out.println(gson1.toJson(jsonBean));
    }

    private static void deserial(){
        Gson gson1 = new Gson();
        final Country country1 = gson1.fromJson(jsonStr, Country.class);
        System.out.println("允许HTML转义：" + country1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        final Gson gson = gsonBuilder.create();
        final Country country = gson.fromJson(jsonStr, Country.class);
        System.out.println("不允许HTML转义：" +country);
    }
}

/**
 * 排除序列化相关规则：ExclusionStrategy，比如你想让类中的某些字段不参与序列化和反序列化，
 * 或者你想让标记了某个注解的类不参与序列化等，该规则接口的扩展性非常强，带读者自己挖掘！
 * // ------------------------------
 * // 用于序列化或者排除序列化某些类或者类的字段
 * // 参考ExclusionStrategy接口
 * //  规则只应用于反序列化
 * gsonBuilder.addDeserializationExclusionStrategy();
 *  //  规则只应用于序列化
 * gsonBuilder.addSerializationExclusionStrategy();
 *  //  序列化反序列化都应用！
 * gsonBuilder.setExclusionStrategies();
 *
 */
class ExclusionStrategy{
    private static String jsonStr = "{\"countryName\":\"<h3>China</h3>\",\"zoneId\":null,\"now\":null}";
    private static Country jsonBean;
    static {
        jsonBean = new Country();
        jsonBean.setCountryName("<h3>China</h3>");
        jsonBean.setNow(LocalDateTime.now());
        jsonBean.setZoneId(ZoneId.systemDefault());
    }
    private final static RandomExclusionStrategy exclusionStrategy = new RandomExclusionStrategy();
    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(exclusionStrategy);
        final Gson gson = gsonBuilder.create();
        final String s = gson.toJson(jsonBean);
        System.out.println("序列化结果：" + s);
        System.out.println("================= 我是分割线 ================");
        final Country country = gson.fromJson(jsonStr, Country.class);
        System.out.println(country);
    }
    // 随机规则
    private static class RandomExclusionStrategy implements com.google.gson.ExclusionStrategy{
        private final Random random = new Random();

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            System.out.println("============== shouldSkipField ================");
            System.out.println("当前FieldAttributes：" + f);
            // 获取字段上的注解
            final JavaBean annotation = f.getAnnotation(JavaBean.class);
            // 获取字段的类型
            final Class<?> declaredClass = f.getDeclaredClass();
            final Type declaredType = f.getDeclaredType();
            // 获取字段所定义在的类的类型
            final Class<?> declaringClass = f.getDeclaringClass();
            // 获取字段名
            final String name = f.getName();
            System.out.println("字段名：" + name);
            System.out.println("字段类型（Class）：" + declaredClass);
            System.out.println("字段类型（Type接口）：" + declaredType);
            System.out.println("字段所在类的类型：" + declaringClass);
            System.out.println("字段上的注解：" + annotation);
            final boolean b = random.nextBoolean();
            System.out.println("是否序列化该字段：" + !b);
            System.out.println("===============================================");
            System.out.println();
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            System.out.println("============= shouldSkipClass =============");
            System.out.println("类型：" + clazz);
            final boolean b = random.nextBoolean();
            System.out.println("是否序列化该类：" + !b);
            System.out.println("===============================================");
            System.out.println();
            return false;
        }
    }
}

/**
 *
 */
class DisableJdkUnsafeAndInstanceCreator{

}

/**
 * 禁止序列化内部类！
 */
class DisableInnerClassSerialization{
    public static class A {
        public String a;

        class B {

            public String b;

            public B() {
                // No args constructor for B
            }
        }
    }

    public static void main(String[] args) {
        A.B a = new A().new B();
        a.b = "111";
        Gson gson = new Gson();
        final String s = gson.toJson(a);
        System.out.println(s);
        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson1 = gsonBuilder.disableInnerClassSerialization().create();
        System.out.println(gson1.toJson(a));
    }
}

/**
 * 排除具有指定修饰符的字段，注意该规则会让默认的不序列化transient字段的规则失效！
 */
class ExcludeFieldsWithModifiers{
    private static final UserBean userBean = UserBean.newBean();

    public static void main(String[] args) {
        Gson gson = new Gson();
        final String s = gson.toJson(userBean);
        System.out.println(s);
        System.out.println();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED, Modifier.PUBLIC);
        final Gson gson1 = gsonBuilder.create();
        final String s1 = gson1.toJson(userBean);
        System.out.println(s1);
    }
}
