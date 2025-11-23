package cn.argento.askia.lang;

import java.io.IOException;
import java.math.BigDecimal;

public class PrimitiveTypes {

    // 基本数据类型的默认值
    private static int defaultInt;
    private static long defaultLong;
    private static short defaultShort;
    private static byte defaultByte;

    private static float defaultFloat;
    private static double defaultDouble;

    private static char defaultChar;
    private static boolean defaultBool;
    public static void main(String[] args) throws IOException {
        // Java的每个变量必须要指定具体的类型
        // JDK 11之后支持自动类型推断，可以使用var来声明变量，编译器在编译时会自动替换掉var为具体的变量类型
        // 如何声明变量？[变量类型] [变量名] = [值];
        int a = 2;
        // 字符串使用+号进行拼接，字符串和任何类型的变量拼接都会得到一个字符串
        System.out.println("变量a的值：" + a);
        System.out.println();

        // 默认值
        System.out.println("基本类型的默认值：");
        System.out.println("int的默认值：" + defaultInt);
        System.out.println("long的默认值：" + defaultLong);
        System.out.println("short的默认值：" + defaultShort);
        System.out.println("byte的默认值：" + defaultByte);
        System.out.println("float的默认值：" + defaultFloat);
        System.out.println("double的默认值：" + defaultDouble);
        System.out.println("char的默认值：" + (int)defaultChar + ", is " + Character.getName(defaultChar));
        System.out.println("boolean的默认值：" + defaultBool);

        // 整数类型
        int number = 20;
        int number2 = 0b10010001;
        int number3 = 0127;
        int number4 = 0x1234AE;
        long number5 = 999999999999999999L;
        long number6 = 100_000_000_000_000L;

        // 浮点类型
        float f1 = 2.32f;
        float f2 = 6.52f;
        float f3 = 6.23E-2F;    // 科学计数法表示浮点数 E-2 ==> 10^-2，但由于小数常量默认是double,所以我们需要加F
        double d1 = 10.2564;
        double d2 = 2.2564D;
        double d5 = 2.563E5;
        // 浮点类型的运算要来考虑精度
        System.out.println(f2 - f1);
        System.out.println((double) f2 - f1);
        System.out.println(d1 - d2);

        // 浮点类型的比较，误差仍然存在
        // 下面的代码结果是true
        double d3 = 58.30000000000000011;
        double d4 = 58.29999999999999999;
        System.out.println(d3 == d4);
        final int read = System.in.read();
        System.out.println(read - '0');



    }
}
