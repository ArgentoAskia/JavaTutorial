package cn.argento.askia.processors.bytecode;

import java.util.logging.Logger;

@JuLogger
public class Item {
    private String address;
    // 仅作为填充字段
    private static Logger logger;

    public static void main(String[] args) {
        Item item = new Item();
        item.setAddress("123456");
        item.getAddress();
        System.out.println(ClassLoader.getSystemClassLoader());
    }

    public String getAddress() {
        logger.entering(Item.class.getName(), "getAddress()");
        return address;
    }

    public void setAddress(String address) {
        logger.entering(Item.class.getName(), "setAddress(\"" + address+ "\")");
        this.address = address;
    }
}
