package cn.argento.askia.gson.parsing;

import com.google.gson.*;

import java.math.BigInteger;

/**
 * 该Demo显示了Gson如何讲Json子字符串转化为库内的各个中间元素入：
 * - JsonArray: Json的数组类型
 * - JsonNull: Json中的null
 * - JsonObject: Json中的Object类型
 * - JsonPrimitive: Json中的任何基本类型
 * --------
 * - 通用的类型：
 * - JsonElement：代表一个Json节点！可以是整一个Json字符串、也可以是Json字符串的其中某个字段的内容等！
 * - JsonParser：将JSON解析为JsonElement解析树的解析器
 * JsonParser采用树的解析方式！本Demo主要也是介绍JsonParser及JsonElement的各种子类..
 */
public class GsonJsonParserDemo {
    public static void main(String[] args) {
        jsonParserChecked();
        System.out.println();
        jsonParserPrimitive();
        System.out.println();
        jsonParserArray();
        System.out.println();
        jsonParserNull();
        System.out.println();
        jsonParserObject();

//        // 如果是一个基本类型则可以使用下面的方法
//        final BigDecimal asBigDecimal = jsonElement.getAsBigDecimal();
//        final BigInteger asBigInteger = jsonElement.getAsBigInteger();
//        final boolean asBoolean = jsonElement.getAsBoolean();
//        final byte asByte = jsonElement.getAsByte();
//        final char asCharacter = jsonElement.getAsCharacter();
//        final double asDouble = jsonElement.getAsDouble();
//        final float asFloat = jsonElement.getAsFloat();
//        final int asInt = jsonElement.getAsInt();
//        final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
//        final long asLong = jsonElement.getAsLong();
//        final Number asNumber = jsonElement.getAsNumber();
//        final short asShort = jsonElement.getAsShort();
//        final String asString = jsonElement.getAsString();
//
//        // 如果是一个JsonArray
//        final JsonArray asJsonArray = jsonElement.getAsJsonArray();
//
//        // 如果是一个JsonNull
//        final JsonNull asJsonNull = jsonElement.getAsJsonNull();
//
//        // 如果是一个JsonObject类型
//        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
//
//        // 深度复制节点
//        final JsonElement jsonElement1 = jsonElement.deepCopy();

    }
    private static void jsonParserPrimitive(){
        String jsonstr2 = "100";
        final JsonElement jsonNumber = JsonParser.parseString(jsonstr2);
        final BigInteger bigInteger = jsonNumber.getAsBigInteger();
        final byte asByte = jsonNumber.getAsByte();
        final char asCharacter = jsonNumber.getAsCharacter();
        final int asInt = jsonNumber.getAsInt();
        final long asLong = jsonNumber.getAsLong();
        final Number asNumber = jsonNumber.getAsNumber();
        final short asShort = jsonNumber.getAsShort();
        final String asString = jsonNumber.getAsString();
        System.out.println("BigInteger：" + bigInteger);
        System.out.println("Byte：" + asByte);
        System.out.println("Character：" + asCharacter);
        System.out.println("Int：" + asInt);
        System.out.println("Long：" + asLong);
        System.out.println("Number：" + asNumber);
        System.out.println("Short：" + asShort);
        System.out.println("String：" + asString);
    }
    private static void jsonParserArray(){
        String array = "[2,3,4,5,6,7,8,9]";
        final JsonElement jsonElement = JsonParser.parseString(array);
        final JsonArray asJsonArray = jsonElement.getAsJsonArray();
        asJsonArray.forEach(System.out::println);
    }
    private static void jsonParserNull(){
        final JsonElement aNull = JsonParser.parseString("null");
        final JsonNull asJsonNull = aNull.getAsJsonNull();
        System.out.println(asJsonNull);
    }
    private static void jsonParserObject(){
        String jsonStr = "{\"number\":283,\"address\":\"XXX省XXX市XXX镇XXXXX\",\"name\":\"8d0aa645-f80a-46a9-8770-1626974cfb38\",\"isGrow\":true,\"flag\":[2.5,3.5999999046325684,4.800000190734863,5.099999904632568,3.9000000953674316],\"country\":{\"countryName\":\"Asia/Shanghai\",\"zoneId\":{\"id\":\"Asia/Shanghai\"},\"now\":{\"date\":{\"year\":2023,\"month\":9,\"day\":29},\"time\":{\"hour\":16,\"minute\":41,\"second\":23,\"nano\":880000000}}},\"skillList\":[\"C++\",\"Java\",\"C\"]}";
        final JsonElement jsonElement = JsonParser.parseString(jsonStr);
        System.out.println("原本的json字符串：" + jsonElement);
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        // 查
        final JsonElement number = asJsonObject.get("number");
        System.out.println("获取number元素的值：" + number);
        // 增
        asJsonObject.add("nullValue", JsonNull.INSTANCE);
        System.out.println("添加一个nullValue元素！");
        // 删
        final JsonElement name = asJsonObject.remove("name");
        System.out.println("删除name元素：" + name);
        // 改
        asJsonObject.addProperty("address", "无地址！！");
        // 对象成员数
        final int size = asJsonObject.size();
        System.out.println("对象成员数：" + size);
        final boolean abc = asJsonObject.has("abc");
        System.out.println("是否拥有abc元素：" + abc);
        System.out.println("现在的Json：" + asJsonObject);
    }
    private static void jsonParserChecked(){
        String jsonStr = "{\"number\":283,\"address\":\"XXX省XXX市XXX镇XXXXX\",\"name\":\"8d0aa645-f80a-46a9-8770-1626974cfb38\",\"isGrow\":true,\"flag\":[2.5,3.5999999046325684,4.800000190734863,5.099999904632568,3.9000000953674316],\"country\":{\"countryName\":\"Asia/Shanghai\",\"zoneId\":{\"id\":\"Asia/Shanghai\"},\"now\":{\"date\":{\"year\":2023,\"month\":9,\"day\":29},\"time\":{\"hour\":16,\"minute\":41,\"second\":23,\"nano\":880000000}}},\"skillList\":[\"C++\",\"Java\",\"C\"]}";
        final JsonElement jsonElement = JsonParser.parseString(jsonStr);
        // Json验证方法
        final boolean isJsonArray = jsonElement.isJsonArray();
        final boolean isJsonNull = jsonElement.isJsonNull();
        final boolean isJsonObject = jsonElement.isJsonObject();
        final boolean isJsonPrimitive = jsonElement.isJsonPrimitive();
        System.out.println("jsonStr是否是一个JsonArray？" + isJsonArray);
        System.out.println("jsonStr是否是一个JsonNull？" + isJsonNull);
        System.out.println("jsonStr是否是一个JsonObject？" + isJsonObject);
        System.out.println("jsonStr是否是一个JsonPrimitive？" + isJsonPrimitive);
    }

}
