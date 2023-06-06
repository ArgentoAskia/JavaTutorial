package cn.argentoaskia.proxy2;

import cn.argentoaskia.proxy2.handlers.AllChangersHandler;
import cn.argentoaskia.proxy2.handlers.AllChangersImplsHandler;
import cn.argentoaskia.proxy2.interfaces.BinaryChanger;
import cn.argentoaskia.proxy2.interfaces.HexChanger;
import cn.argentoaskia.proxy2.interfaces.OctalChanger;

import java.lang.reflect.Proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 1.创建有实现实现方法的处理器类
        AllChangersImplsHandler allChangersImplsHandler = new AllChangersImplsHandler();
        // 2.创建代理类
        Object o = Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(),
                new Class[]{
                        HexChanger.class,
                        OctalChanger.class,
                        BinaryChanger.class
                },
                allChangersImplsHandler);
        // 3.进行调用
        BinaryChanger binaryChanger = (BinaryChanger)o;
        binaryChanger.printBinaryChangerMessage();
        binaryChanger.toBinaryNumber(20);
        binaryChanger.toString();

        HexChanger hexChanger = (HexChanger)o;
        hexChanger.printHexChangerMessage();
        hexChanger.toHexNumber(20);
        hexChanger.toString();

        OctalChanger octalChanger = (OctalChanger)o;
        octalChanger.printOctalChangerMessage();
        octalChanger.toOctalNumber(20);
        octalChanger.toString();

        // 4. 创建非实现方法的处理器类
        AllChangersHandler allChangersHandler = new AllChangersHandler();
        // 5.创建代理
        Object o1 = Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(),
                new Class[]{
                        HexChanger.class,
                        OctalChanger.class,
                        BinaryChanger.class
                }, allChangersHandler);

        // 6.进行调用
        BinaryChanger binaryChanger1 = (BinaryChanger)o1;
        binaryChanger1.printBinaryChangerMessage();
        System.out.println(binaryChanger1.toBinaryNumber(20));
        System.out.println(binaryChanger1.toString());

        HexChanger hexChanger1 = (HexChanger)o1;
        hexChanger1.printHexChangerMessage();
        hexChanger1.toHexNumber(20);
        hexChanger1.toString();

        OctalChanger octalChanger1 = (OctalChanger)o1;
        octalChanger1.printOctalChangerMessage();
        octalChanger1.toOctalNumber(20);
        octalChanger1.toString();
    }
}
