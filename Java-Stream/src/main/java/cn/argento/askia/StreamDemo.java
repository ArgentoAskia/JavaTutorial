package cn.argento.askia;

import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        // 创建流的API
        // 合并流
        // Stream.concat();
        // 创建一个空流
        // Stream.empty();
        // 采用发生器生成一个流
        // Stream.generate();
        // 采用迭代生成一个流
        // Stream.iterate();
        // 生成一个自带某几个元素的流
        // Stream.of();
        Stream<Integer> stream = Stream.generate(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(500);
            }
        }).limit(50);
        // 是否全部满足条件
        boolean b = stream.allMatch(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                if (integer > 0) return true;
                else return false;
            }
        });
        System.out.println(b);
        boolean b1 = stream.distinct().anyMatch(integer -> integer < 200);
        System.out.println(b1);
    }
}
