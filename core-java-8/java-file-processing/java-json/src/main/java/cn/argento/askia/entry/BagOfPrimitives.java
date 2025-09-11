package cn.argento.askia.entry;


public class BagOfPrimitives  {


    private int value1 = 1;
    private String value2 = "abc";
    private transient int value3 = 3;
    public BagOfPrimitives() {
        // no-args constructor
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BagOfPrimitives{");
        sb.append("value1=").append(value1);
        sb.append(", value2='").append(value2).append('\'');
        sb.append(", value3=").append(value3);
        sb.append('}');
        return sb.toString();
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }
}