package cn.argentoaskia.proxy;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ProxyTest {
    public static void main(String[] args) throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Handler handler = new Handler();
        Object o = Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                new Class[]{Creatable.class, Readable.class, Writeable.class, Deletable.class},
                handler);
        // 1. 使用Creatable接口
        Creatable creatable = (Creatable) o;
        creatable.create(Date.class);

        Readable readable = (Readable) o;
        readable.read(5, 50);

        Writeable writeable = (Writeable) o;
        byte[] bytes =  new byte[5];
        new Random().nextBytes(bytes);
        writeable.write(bytes);

        Deletable deletable = (Deletable) o;
        ArrayList<String> objects = new ArrayList<>();
        objects.add("123");
        objects.add("456");
        deletable.delete(objects);
    }
}
