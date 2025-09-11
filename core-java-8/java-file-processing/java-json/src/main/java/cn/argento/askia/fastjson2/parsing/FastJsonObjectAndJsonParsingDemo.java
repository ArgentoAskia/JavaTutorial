package cn.argento.askia.fastjson2.parsing;

import cn.argento.askia.entry.UserBean;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.Arrays;

public class FastJsonObjectAndJsonParsingDemo {
    public static void main(String[] args) {
        final UserBean userBean = UserBean.newBean();
        // 1.使用JSON类的静态方法parse[XXX]()来实现：Json字符串 => Java对象
        // 使用toJSON[XXX]()方法来实现：Java对象 => Json字符串
        // 对类似的时间、ZoneId等Java对象的处理，FastJson会自己进行转换！
        // 忽略null值的对象
        final String s = JSON.toJSONString(userBean);
        System.out.println("userBean Json String:" + s);
        final byte[] bytes = JSON.toJSONBytes(userBean);
        System.out.println("userBean Json Bytes:" + Arrays.toString(bytes));


        // 2.和Jackson的区别是Jackson在处理这个nullObj的时候会抛异常而FastJson2不会，FastJson2将会使用null值
        // 这里间接证明了jackson json字符串转Java对象实际上靠的是Setter方法, Java对象是靠Getter方法获取的
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.insert(1, "\"nullObj\":\"192.168.0.1\",");
        final UserBean userBean1 = JSON.parseObject(stringBuilder.toString(), UserBean.class);
        System.out.println("反序列化UserBean：" + userBean1);

        // FastJson提供了JsonObject、JsonArray等类型，提供了用于非Java-Object绑定的使用
        final JSONObject jsonObject = JSON.parseObject(s);
        // JSONObject实际上扩展了Map（LinkedHashMap）集合,里面有很多getXXX()方法来按照具体类型来获取
    }
}
