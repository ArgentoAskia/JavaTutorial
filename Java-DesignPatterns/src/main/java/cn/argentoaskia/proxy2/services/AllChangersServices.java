package cn.argentoaskia.proxy2.services;

public class AllChangersServices {

    @Override
    public String toString() {
        return "该类不是任何代理接口的实现类，但是有和所有接口同名的方法，旨在为了探索当具体业务类中不继承代理接口处理器调用会不出抛出异常！\n" +
                "接口都有toString(),在继承关系中,实现类只会有一个toString()来覆盖掉所有接口的，动态代理也不例外！\n" +
                "因此生成的代理类无论强转为哪个接口，调用toString()都会转换为调用AllChangersImplsHandler对象的toString()";
    }

    public String toBinaryNumber(int decNumber) {
        return Integer.toBinaryString(decNumber);
    }

    public String printBinaryChangerMessage() {
        return "这个是一个十进制转二进制的转换器！";
    }

    public String toHexNumber(int decNumber) {
        return Integer.toHexString(decNumber);
    }

    public String printHexChangerMessage() {
        return "这个是一个十进制转十六进制的转换器！";
    }


    public String toOctalNumber(int decNumber) {
        return Integer.toOctalString(decNumber);
    }

    public String printOctalChangerMessage() {
        return "这个是一个十进制转八进制的转换器！";
    }
}
