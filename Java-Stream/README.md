## Java Stream

本章节将会介绍`JDK 8`中引入的所谓流（`Stream`）库，该库的所有`API`位于`java.util.stream`包下。

### 什么是Stream

官方文档中对流的说明是：

```
A sequence of elements supporting sequential and parallel aggregate operations.
```

也就是说他的核心在于对序列的操作。所有的流，可以看成是一种类似于管道的东西，你可以对管道内的所有元素进行过滤，创建、分流、统计等，在`Java 8`中流的作用可以体现在：



### Stream API

在对`java.util.stream`包进行拆解时，会发现大部分的流式接口：

- `Stream<T>`
- `BaseStream<T, S extends BaseStream<T, S>>`
- `Collector<T, A, R>`
- `Collectors`
- `DoubleStream`
- `IntStream`
- `LongStream`
- `StreamSupport`