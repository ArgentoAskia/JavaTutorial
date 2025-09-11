package cn.argento.askia.gson.parsing;

import cn.argento.askia.entry.BagOfPrimitives;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

/**
 * Json的转换类型：String、number、object、array、true、false、null，以object类型为主
 * Java的转换类型：基本类型、String、引用类型、数组、集合类型、Map、[内部类]、泛型
 */
public class GsonObjectSupportDemo {
    private static final Gson gson = new Gson();
    public static void main(String[] args) {
        javaTypeToJson();
        System.out.println("=========================================");
        jsonToJavaType();
    }

    private static void javaTypeToJson(){
        // 基本类型
        final String number1 = gson.toJson(1);
        final String number2 = gson.toJson(999999999999999999L);
        final String number3 = gson.toJson(3.58);
        final String bool = gson.toJson(true);
        final String character = gson.toJson('a');
        System.out.println("======= 基本类型序列化 ======");
        System.out.println("整数序列化：" + number1);
        System.out.println("整数序列化：" + number2);
        System.out.println("整数序列化：" + number3);
        System.out.println("布尔序列化：" + bool);
        System.out.println("字符序列化：" + character);
        System.out.println();
        // 字符串
        final String str = gson.toJson("abcde");
        System.out.println("======= 字符串类型序列化 ======");
        System.out.println("字符串序列化：" + str);
        System.out.println();

        BagOfPrimitives bagOfPrimitives = new BagOfPrimitives();
        bagOfPrimitives.setValue1(2);
        bagOfPrimitives.setValue2("1234");
        bagOfPrimitives.setValue3(23);
        // 引用类型
        final String referenceType = gson.toJson(bagOfPrimitives);
        System.out.println("======= 引用类型序列化 ======");
        System.out.println("引用类型：" + bagOfPrimitives);
        System.out.println("引用类型序列化：" + referenceType);
        System.out.println();
        // 数组
        BagOfPrimitives[] bagOfPrimitives1 = new BagOfPrimitives[2];
        bagOfPrimitives1[0] = new BagOfPrimitives();
        bagOfPrimitives1[1] = new BagOfPrimitives();
        bagOfPrimitives1[0].setValue1(33);
        bagOfPrimitives1[0].setValue2("123456789");
        bagOfPrimitives1[0].setValue3(12345);
        bagOfPrimitives1[1].setValue1(33);
        bagOfPrimitives1[1].setValue2("123456789");
        bagOfPrimitives1[1].setValue3(12345);
        int[] ints = new int[]{2,3,4,5,6};
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        Set<BagOfPrimitives> bagOfPrimitivesSet = new HashSet<>();
        Collections.addAll(bagOfPrimitivesSet, bagOfPrimitives1);
        final String s = gson.toJson(bagOfPrimitives1);
        final String s1 = gson.toJson(ints);
        final String s2 = gson.toJson(list);
        final String s3 = gson.toJson(bagOfPrimitivesSet);
        System.out.println("======= 数组、集合类型序列化 ======");
        System.out.println("数组类型序列化：" + s);
        System.out.println("数组类型序列化：" + s1);
        System.out.println("List类型序列化：" + s2);
        System.out.println("Set类型序列化：" + s3);
        System.out.println();

        // map
        Map<String, String> stringMap = new LinkedHashMap<>();
        stringMap.put("key", "value");
        stringMap.put(null, "null-entry");
        String json = gson.toJson(stringMap);
        System.out.println("======= Map类型序列化 ======");
        System.out.println(json);
    }


    private static void jsonToJavaType() {
        int i = gson.fromJson("1", int.class);
        Integer intObj = gson.fromJson("1", Integer.class);
        Long longObj = gson.fromJson("1", Long.class);
        Boolean boolObj = gson.fromJson("false", Boolean.class);
        String str = gson.fromJson("\"abc\"", String.class);
        String[] strArray = gson.fromJson("[\"abc\", \"efg\", \"hij\"]", String[].class);
        System.out.println("整数json：" + i);
        System.out.println("整数json：" + intObj);
        System.out.println("整数json：" + longObj);
        System.out.println("布尔json：" + boolObj);
        System.out.println("字符串json：" + str);
        System.out.println("字符串数组json：" + Arrays.toString(strArray));

        // 对象
        String obj = "{\"value1\":2,\"value2\":\"1234\"}";
        BagOfPrimitives bagOfPrimitives = gson.fromJson(obj, BagOfPrimitives.class);
        System.out.println("对象json：" + bagOfPrimitives);

        // 数组
        String array1 = "[2,3,4,5,6]";
        String array2 = "[{\"value1\":33,\"value2\":\"123456789\"},{\"value1\":33,\"value2\":\"123456789\"}]";
        final int[] ints = gson.fromJson(array1, int[].class);
        final BagOfPrimitives[] bagOfPrimitives1 = gson.fromJson(array2, BagOfPrimitives[].class);
        System.out.println("引用类型数组：" + Arrays.toString(bagOfPrimitives1));
        System.out.println("基本类型数组：" +Arrays.toString(ints));

        // 集合、Map等类型等因为带了泛型，所以不能直接转换！
        // 需要使用TypeToken类来记录泛型类型
        // 早期版本中TypeToken是可以直接new的，2.10.1版本提供了工厂方法的形式进行访问！
        final TypeToken<?> parameterized =TypeToken.getParameterized(List.class, Integer.class);
        final List<Integer> o = (List<Integer>)gson.fromJson(array1, parameterized);
        System.out.println("TypeToken采用的泛型类型的反序列化：" + o);
    }
}
