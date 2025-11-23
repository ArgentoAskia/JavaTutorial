package cn.argento.askia.enumeration;

import com.sun.tools.corba.se.idl.StringGen;
import org.omg.CORBA.UNKNOWN;

// 1.可以继承接口
public enum FlexSize implements AutoCloseable{

    UNKNOWN,
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L"),
    EXTRA_LARGE("XL"),
    SMALL_H_W("S", 150, 60),
    MEDIUM_H_W("M", 160, 80),
    LARGE_H_W("L", 170, 100),
    EXTRA_LARGE_H_W("XL", 180, 120);

    // 2.可以定义成员变量
    private String inch;
    public int height;
    public int weight;

    private static boolean isFlex = false;

    // 3.你甚至可以定义静态常量
    public static final String FlexSizeConstant = "flexSizes";


    // 4.可以定义构造器
    private FlexSize(){
        inch = "?";
        height = -1;
        weight = -1;
    }

    private FlexSize(String inch) {
        this.inch = inch;
        height = 0;
        weight = 0;
    }

    // 5.定义静态方法
    public static boolean isIsFlex() {
        return isFlex;
    }

    public static void setIsFlex(boolean isFlex) {
        FlexSize.isFlex = isFlex;
    }

    private FlexSize(String inch, int height, int weight){
        this.inch = inch;
        this.height = height;
        this.weight = weight;
    }

    public String getInch() {
        return inch;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void close() throws Exception {
        System.out.println("close method");
    }

    // 甚至可以定义main
    public static void main(String[] args) {
        FlexSize flexSize = FlexSize.LARGE;
        System.out.println(flexSize);
    }
}
