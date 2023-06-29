package cn.argento.askia;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class StreamDemo {

    private static Stream<String> stringStream;
    private static Stream<String> emptyStream;
    // 斐波那契数列
    private static Stream<Integer> fibonacciStream;

    private static Stream<Long> randomLongStream;

    private static Stream<InetAddress> inetAddressStream;

    private static Stream<Float> floatStream;

    // 创建流的API
    private static void howToCreateAStream(){
        // 1.可以通过Stream.Builder接口对象创建一个Stream，并往流里面添加成员
        Stream.Builder<String> streamBuilder = Stream.<String>builder();
        // 1.1 accept()和add()方法都可以添加成员,
        // accept()方法没有返回值,add()返回streamBuilder本身来实现链式调用
        streamBuilder.accept("abcdefg");
        streamBuilder.add("hijklmn").add("opqrst")
                .add("uvwxyz");
        // 1.2.通过build()方法来创建！
        stringStream = streamBuilder.build();

        // 2.可以通过Stream.<String>empty()创建一个空Stream
        emptyStream = Stream.<String>empty();

        // 3.可以使用Stream.iterate()来创建一个不断迭代的无限流
        // 3.1 该流的特点是会一直迭代下去，需要传递一个初始值x和函数f来迭代,
        // 3.2 因此流种第一个(index = 0)是:初始值x,第二个元素是:f(x),第三个元素是:f(f(x)),第四个元素：f(f(f(x))), 以此类推
        // 3.3 因为生成的是一个无限有序序列流(infinite sequential ordered stream),所以该流需要进行一些过滤或者截断操作（中间操作！）才能做操作！
        // 3.4 该方法最常用于生成数列、有序序列等！
        fibonacciStream = Stream.iterate(1, new UnaryOperator<Integer>() {

            private Integer iterator = 1;

            @Override
            public Integer apply(Integer integer) {
                Integer result = iterator + integer;
                iterator = integer;
                return result;
            }
        });

        // 4. 可以使用Stream.generate()来创建一个不断迭代的无限流
        // 4.1 该流的特点是会一直迭代下去，需要传递一个用来生成流成员的Supplier(生产者)，同样因为是无限流，需要中间操作才能使用！
        // 4.2 最常用于生成恒定流、随机数流等
        randomLongStream = Stream.generate(new Supplier<Long>() {
            private Random random = new Random();
            @Override
            public Long get() {
                long l = random.nextLong();
                return l;
            }
        });

        // 5.返回包含单或者多个元素的顺序流
        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            Stream<InetAddress> stringStream = Stream.of(localHost);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        floatStream = Stream.of(4.5F, 5.6F, 6.7F);


    }
    public static void main(String[] args) {
        howToCreateAStream();

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
