package cn.argento.askia;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("123");
        arrayList.add("456");
        arrayList.add("789");
        System.out.println(arrayList.stream().count());
        arrayList.add("567");
        System.out.println(arrayList.stream().count());
        Stream<String> stringStream = arrayList.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                if (s.contains("7")) {
                    return false;
                }
                return true;
            }
        });
        System.out.println(stringStream.count());
        System.out.println(arrayList.size());

        Stream<String> stream = arrayList.stream();
        Stream<String> stringStream1 = arrayList.parallelStream();
        arrayList.remove(0);
        System.out.println(stream.count());
        System.out.println(stringStream1.count());
        // 切记请不要在流内使用原始集合来进行操作！
        stream.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (s.contains("7")){
                    arrayList.remove(s);
                }
            }
        });



    }
}
