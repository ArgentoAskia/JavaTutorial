package cn.argento.askia;

import java.math.BigInteger;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        java.util.stream.Stream<BigInteger> iterate =
                java.util.stream.Stream.iterate(BigInteger.ONE, new UnaryOperator<BigInteger>() {
            @Override
            public BigInteger apply(BigInteger bigInteger) {
                return bigInteger.add(BigInteger.ONE);
            }
        });
//        System.out.println(iterate.count());
//       final int size = 10;
//        List<BigInteger> collect = iterate.limit(size + 1).collect(Collectors.toList());
//        System.out.println(collect.toString());
    }
}
