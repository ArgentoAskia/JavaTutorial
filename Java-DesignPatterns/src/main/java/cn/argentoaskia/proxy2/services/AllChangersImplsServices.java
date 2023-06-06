package cn.argentoaskia.proxy2.services;

import cn.argentoaskia.proxy2.interfaces.BinaryChanger;
import cn.argentoaskia.proxy2.interfaces.HexChanger;
import cn.argentoaskia.proxy2.interfaces.OctalChanger;

public class AllChangersImplsServices implements BinaryChanger, HexChanger, OctalChanger {

    @Override
    public String toBinaryNumber(int decNumber) {
        return Integer.toBinaryString(decNumber);
    }

    @Override
    public String toString() {
        return "该类是所有代理接口的实现类\n" +
                "接口都有toString(),在继承关系中,实现类只会有一个toString()来覆盖掉所有接口的，动态代理也不例外！\n" +
                "因此生成的代理类无论强转为哪个接口，调用toString()都会转换为调用AllChangersImplsHandler对象的toString()";
    }

    @Override
    public String printBinaryChangerMessage() {
        return "这个是一个十进制转二进制的转换器！";
    }

    @Override
    public String toHexNumber(int decNumber) {
        return Integer.toHexString(decNumber);
    }

    @Override
    public String printHexChangerMessage() {
        return "这个是一个十进制转十六进制的转换器！";
    }

    @Override
    public String toOctalNumber(int decNumber) {
        return Integer.toOctalString(decNumber);
    }

    @Override
    public String printOctalChangerMessage() {
        return "这个是一个十进制转八进制的转换器！";
    }
}
