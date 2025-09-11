package cn.argento.askia.gson.parsing;

import cn.argento.askia.entry.UserBean;
import com.google.gson.Gson;

/**
 * Gson解析和转换的核心在于Gson类，理论上默认的Gson类就够用了
 * 但是一些特殊情况，如需要定制化Gson对象，则可以使用GsonBuilder进行调配！
 * 将一个Java对象转为Json字符串：toJson()方法
 * 将一个Json字符串转为Java对象：fromJson()方法
 */
public class GsonObjectAndJsonParsingDemo {
    public static void main(String[] args) {
        final UserBean userBean = UserBean.newBean();
        Gson gson = new Gson();
        // Gson将对象转为json字符串的方法是不断地遍历对象，知道该对象的所有成员是可以被输出成json字符串的
        // 换句话说，如果java对象里面有其他对象字段，则会继续深入遍历这些对象字段！
        // 使用toJson()转为json字符串
        // 默认情况下不转null字段！
        final String s = gson.toJson(userBean);
        System.out.println("json Of userBean=" + s);
        System.out.println();
        // 另外一个全版本的转换方法，参数说明如下：
        // 参数1 src:代表带转换的对象
        // 参数2 typeOfSrc：指定src的类型，可以使用src.getClass()，是java.lang.reflect.Type类型（反射的），因此可以传递Class<?>对象
        // 参数3 Appendable：指定输出的Json字符串存放在哪里，可以使用实现了java.lang.Appendable接口的类，如StringBuilder、StringBuffer、StringWriter等
        StringBuilder stringBuilder = new StringBuilder();
        // 目前不能直接将s字符串转为UserBean，因为ZoneId是抽象类，需要使用一些特殊手段来进行转换！（这个在后面讲）
        // 所以我们先将Country字段设置为null
        userBean.setCountry(null);
        gson.toJson(userBean, userBean.getClass(), stringBuilder);
        // 这里可以看出Gson的默认的参数插入不是靠Getter和Setter方法，而是靠反射获取字段！
        stringBuilder.insert(1, "\"nullObj\":\"192.168.0.1\",");
        System.out.println("json字符串：" + stringBuilder);

        // 使用fromJson()转为Obj
        // 参数1指定json字符串的来源
        // 参数2指定json要转换成的对象的类型
        final UserBean userBean1 = gson.fromJson(stringBuilder.toString(), UserBean.class);
        System.out.println("json字符串转为对象之后：" + userBean1);


        System.out.println();
        // 可以使用下面的方法来查看gson当前的支持特性
        // 是否忽略html字符
        final boolean b = gson.htmlSafe();
        //
        final String s1 = gson.toString();
        // 是否序列化null
        final boolean b1 = gson.serializeNulls();
        System.out.println("否忽略html字符" + b);
//        System.out.println(s1);
        System.out.println("是否序列化null" + b1);



    }
}
