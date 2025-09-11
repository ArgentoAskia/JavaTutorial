package cn.argento.askia.jackson.parsing;

import cn.argento.askia.entry.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

public class JacksonObjectAndJsonParsingDemo {
    public static void main(String[] args) throws IOException {
        // jackson会[反]序列化transient的字段、而Gson和FastJson不会！
        final UserBean userBean = UserBean.newBean();
        // jackson默认情况下不支持处理LocalDateTime、ZoneId等类，需要导入jackson-datatype-jsr310才支持
        userBean.setCountry(null);
        // 1.先创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        // 2.使用writeValueAsString()、writeValueAsBytes()、writeValue()三种方法实现：Java对象 => Json字符串
        final String s = objectMapper.writeValueAsString(userBean);
        final byte[] bytes = objectMapper.writeValueAsBytes(userBean);
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, userBean);
        final String s1 = stringWriter.toString();
        // 结果
        System.out.println("userBean Json:" + s);
        System.out.println("userBean Json:" + s1);
        System.out.println("userBean Json's bytes:" + Arrays.toString(bytes));


        // 3.使用readValue()来实现：Json字符串 => Java对象
        final UserBean userBean1 = objectMapper.readValue(s, UserBean.class);
        System.out.println(userBean1);

        // 这里间接证明了jackson json字符串转Java对象实际上靠的是Setter方法, Java对象是靠Getter方法获取的
        stringWriter.getBuffer().insert(1, "\"nullObj\":\"192.168.0.1\",");
        final UserBean userBean2 = objectMapper.readValue(stringWriter.toString(), UserBean.class);
        System.out.println(userBean2);
    }
}
