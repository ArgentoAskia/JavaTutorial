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

- `Stream<T>`：泛型类型的流，提供了一套处理任何类型的流操作
- `BaseStream<T, S extends BaseStream<T, S>>`：流的最顶层接口
- `Collector<T, A, R>`
- `Collectors`
- `DoubleStream`：浮点数流
- `IntStream`：整数流
- `LongStream`：长整数流
- `StreamSupport`

在所有`XXXStream`类中，一般都包含了三类操作：

1. 创建流操作：创建一个流，`Stream`类中提供了很多静态方法来创建流，但是也有很多不靠`Stream`类的静态方法来创建的流，如`JCF`框架中各种集合的`Stream()`方法，`Random`类中的各种`XXXStream()`
2. 中间流操作：如对流进行过滤、截断，转换等操作，**返回的仍然是一个**`Stream`**对象**，用于做进一步数据加工处理。
3. 终止流操作：如对流元素取最大值、最小值、统计符合条件的元素个数等，在执行完这些操作之后意味着流中数据要停止流动，**因此终止流方法返回值肯定不是**`Stream`**对象而是一个结果！**