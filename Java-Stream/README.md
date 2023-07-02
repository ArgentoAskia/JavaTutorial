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

1. 创建流操作：创建一个流，`Stream`类中提供了很多静态方法来创建流，但是也有很多不靠`Stream`类的静态方法来创建的流，如`JCF`框架中各种集合的`Stream()`方法，`Random`类中的各种如：`ints()`、`longs()`等方法
2. 中间流操作：如对流进行过滤、截断，转换等操作，**返回的仍然是一个**`Stream`**对象**，用于做进一步数据加工处理。
3. 终止流操作：如对流元素取最大值、最小值、统计符合条件的元素个数等，在执行完这些操作之后意味着流中数据要停止流动，**因此终止流方法返回值肯定不是**`Stream`**对象而是一个结果！**

#### 特别注意

所有的流操作都是一次性的，因为`Stream`库设计初衷就是操作为基础，因此当调用过`Stream`对象的`API`后，**原先的流对象就变得不可用了**，**这个时候在调用原先的流对象将会抛出**：

```java
Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
```

并且流是具备

#### Stream接口

> 中间操作（intermediate operation）

```java
// 去除流中重复的选项
Stream<T> distinct();
Stream<T> filter(Predicate<? super T> predicate);
<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
Stream<T> limit(long maxSize);
<R> Stream<R> map(Function<? super T, ? extends R> mapper);
DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
IntStream mapToInt(ToIntFunction<? super T> mapper);
LongStream mapToLong(ToLongFunction<? super T> mapper);
S onClose(Runnable closeHandler);
S parallel();
S sequential();
S unordered();
Stream<T> peek(Consumer<? super T> action);
Stream<T> skip(long n);
Stream<T> sorted();
Stream<T> sorted(Comparator<? super T> comparator);
```

> 终止操作（terminal operation）

```java
boolean allMatch(Predicate<? super T> predicate);
boolean anyMatch(Predicate<? super T> predicate);
<R, A> R collect(Collector<? super T, A, R> collector);
<R> R collect(Supplier<R> supplier,
              BiConsumer<R, ? super T> accumulator,
              BiConsumer<R, R> combiner);
long count();
Optional<T> findAny();
Optional<T> findFirst();
void forEach(Consumer<? super T> action);
void forEachOrdered(Consumer<? super T> action);
Iterator<T> iterator();
Optional<T> max(Comparator<? super T> comparator);
Optional<T> min(Comparator<? super T> comparator);
boolean noneMatch(Predicate<? super T> predicate);
Spliterator<T> spliterator();
Optional<T> reduce(BinaryOperator<T> accumulator);
T reduce(T identity, BinaryOperator<T> accumulator);
<U> U reduce(U identity,
             BiFunction<U, ? super T, U> accumulator,
             BinaryOperator<U> combiner);
Object[] toArray();
<A> A[] toArray(IntFunction<A[]> generator);
```

> 其他方法

```java
boolean isParallel();
void close();
```

