## Java-File

本篇主要介绍在`Java`中如何处理操作系统上的文件和目录，这些基础操作包括：

1. 查看修改文件属性（创建日期、修改日期、只读等）和权限
2. 移动、复制、删除、重命名文件和目录
3. 文件和目录的创建

然后介绍`JDK 7`之后带来的新文件操作特性，包括：

1. 文件路径表示及存储容量格式等
2. 文件遍历
3. 文件系统
4. 文件监听服务

最后补充两个文件读写相关的内容：

1. `Files`类实现简易文件读写
2. 项目工程（`Classpath`）资源读写

-------

下面两个内容放在`Buffer`模块进行

最后还会介绍两个高阶内容：

1. 文件加锁
2. 内存文件

前者能够保证文件的安全，后者用于提高文件读写速度。

### API介绍

首先，在`JDK`的中，常常会见如：`Array`和`Arrays`，`Collection`和`Collections`，这些带`s`的类一般是作为原类的工具类来设计和使用的，以增强原类的功能，因此也叫伴随类。如`Arrays`中添加了`sort()`、`toString()`、`fill()`等方法，增强了`Array`类所创建的动态数组，在文件操作中，也存在这种关系的类：

- `File、Files`：一般用于处理文件和目录，如重命名、移动、创建等
- `Path、Paths`：一般用于处理路径

在`JDK1.7`之前处理文件主要靠`File`类，但`JDK1.7`之后，出现了`java.nio`包，在这个包里面，有一个`file`的子包，里面就有很多相关的文件操作类，如`Paths`、`Files`、`FileVisitor`等，因此本篇主要介绍的`API`主要分两大类：

- `JDK1.7`以前位于`java.io`包的文件类如：
  - 基础文件目录操作：`File`
  - 文件描述符：`FileDescriptor`
  - 文件权限及计算表示：`FilePermission`
- `JDK1.7`以后位于`java.nio.file`包的类，包括：
  - 文件目录增强：`Files`、`Path`、`Paths`
  - 文件权限属性增强：`FileStore`、`attribute`包下各类操作系统的权限视图和权限属性
  - 文件系统：`FileSystem`、`FileSystems`、`FileSystemProvider`
  - 文件目录监听：`Watchable`、`WatchEvent<T>`、`WatchKey`接口、`WatchService`接口
  - 文件遍历：`FileVisitor`、`FileVisitResult`、`FileVisitOption`等

### File类操作文件

`File`类作为最开始代表文件的类，自`JDK1.0`开始就存在，`File`类的对象仅仅代表文件系统上的一个文件及其相关属性（路径、权限、类型、修改日期等属性），他并不会涉及该文件的具体内容。这个尴尬的设计导致了`File`类虽然能够创建文件、删除文件，却无法直接实现文件复制、文件移动，`JDK1.7`以后出现了`Files`伴随类，弥补了这方面的缺陷，同时添加了一些简单的读写方法。

`File`类的`API`非常丰富，这些`API`基本覆盖了所有文件基础操作（除了复制、移动，理由上面说了）和路径操作，并且实现了简单的文件遍历，我们将分`8`组来认识，具体参考这个`Demo`：

> JavaProject\Java-File\src\main\java\cn\argentoaskia\FileDemo.java

- 用于获取或者设置文件属性和权限相关

  ```java
  // 是否可执行
  public boolean canExecute();
  // 是否可读
  public boolean canRead();
  // 是否可写
  public boolean canWrite();
  // 是否是目录
  public boolean isDirectory();
  // 是否是文件
  public boolean isFile();
  // 是否带隐藏属性（目录或文件）
  public boolean isHidden(); 
  // 获取当前文件所在磁盘的空闲空间
  public long getFreeSpace();
  // 获取当前文件所在磁盘的可用空间,通常该API和getFreeSpace()得到值是一样的
  public long getUsableSpace();
  // 获取磁盘总空间
  public long getTotalSpace();
  // 最后修改日期
  public long lastModified();
  // 文件大小，以字节为单位！
  public long length();
  // 设置文件可执行
  public boolean setExecutable(boolean executable);
  // 设置最后修改时间
  public boolean setLastModified(long time);
  // 设置文件是否可读
  public boolean setReadable(boolean readable);
  // 设置文件只读
  public boolean setReadOnly();
  // 设置文件是否可写
  public boolean setWritable(boolean writable);
  ```

- 两个文件相比较

  ```java
  boolean equals(Object object);
  int compareTo(File file);
  ```

- 通用文件操作,创建、删除、重命名等

  ```java
  // 判断文件是否存在
  public boolean exists();
  // 创建新文件
  public boolean createNewFile() throws IOException;
  // 在程序完成之后，执行删除文件
  public void deleteOnExit();
  // 删除文件
  public boolean delete();
  // 创建目录
  public boolean mkdir();
  public boolean mkdirs();
  // 重命名
  public boolean renameTo(File dest);
  ```

  **特别注意：**

  1. `delete()`只能删除空文件夹和文件，文件夹内有东西的，如子文件夹或者文件的无法删除，而`deleteOnExit()`则没有这个限制，但文件不会被立刻删除，只有在执行完了所有代码，`jvm`即将结束之时，才会删掉文件
  2. 无论是`delete()`还是`deleteOnExit()`，当要删除一个没有删除权限的文件或者目录时（如`Windows`下普通用户删除`C`盘根目录文件，`Linux`系统非`root`删除根等），会抛出权限异常，无法删除！
  3. `mkdir()`只能创建一级不存在目录，如现在所在目录是`/opt`，希望在`opt`里面创建一个`oracle`文件夹，则可以使用`mkdir()`，但如果希望创建多级不存在的文件夹，如创建`/opt/jdk/openjdk`，则要使用`mkdirs()`

- 文件的相对、标准(规范)路径、绝对路径、父类路径、文件名等路径文件名操作

  ```java
  // 获取new File()是传递给File参数的路径
  // 如果传递给File构造器的路径是一个绝对路径，则getPath()和getAbsolutePath()返回结果一样
  // 如果传递的是一个相对路径，则返回相对路径本身
  public String getPath();
  
  // 获取绝对路径，如果File对象本身是一个相对路径，则会计算出绝对路径并返回
  public String getAbsolutePath();
  public File getAbsoluteFile();
  // 获取标准路径，无论是绝对路径还是相对路径，都可以带..或者.这些目录操作符,绝对路径将会对这些操作父进行计算，得到不含..和.的路径
  public String getCanonicalPath() throws IOException;
  public File getCanonicalFile() throws IOException;
  // 获取文件名
  public String getName();
  // 获取父路径
  public String getParent();
  public File getParentFile();
  // 判断File对象代表的路径是否已经是一个绝对路径
  public boolean isAbsolute();
  ```

  **特别注意：**

  1. 所谓绝对路径、相对路径、标准路径：

     ```
     绝对路径：D:/OpenSourceProject/JavaProject/../Hello.java
     则相对路径：
         如果当前目录是：D:/OpenSourceProject
         则./JavaProject/../Hello.java就是相对路径
     标准路径：
     	标准路径会计算..和.，让最后的路径不包含..和.
     	..表示上一级，JavaProject的上一级是OpenSourceProject
     	则：D:/OpenSourceProject/Hello.java
     ```

  2. `getPath()`只是返回创建`File`类时使用的原始类路径，提供给`File`构造器的路径是什么则返回什么，不会讲一个路径计算成相对路径，`File`类本身路径操作的方法非常有限，如果需要涉及到计算相对路径，这种计算应该交给`JDK1.7`引入的专门的路径类`Path`

- 一些遍历方法和转换方法

  ```java
  public String[] list();
  public String[] list(FilenameFilter filter);
  public File[] listFiles();
  public File[] listFiles(FilenameFilter filter);
  public File[] listFiles(FileFilter filter);
  public Path toPath();
  public URI toURI();
  @Deprecated public URL toURL() throws MalformedURLException;
  public String toString();
  ```

  `FilenameFilter`和`FileFilter`是两个过滤接口，用于过滤掉特定条件的文件和目录，他们的定义如下：

  ```java
  // 用户过滤文件
  @FunctionalInterface
  public interface FilenameFilter {
  	// dir:代表当前遍历的目录
  	// name：代表当前遍历到的文件名
  	// 返回true则输出该文件,否则丢弃
      boolean accept(File dir, String name);
  }
  ```

  ```java
  // 用户过滤目录
  @FunctionalInterface
  public interface FileFilter {
  	// pathname:代表当前遍历到的文件名
      boolean accept(File pathname);
  }
  ```

这里给出了基于`File`的文件遍历方法：



`File`类的最后，我们讨论一下`File`类无法实现的复制和移动操作，在`JDK 1.7`之前，想要实现复制、移动操作，一种方法是借助`IO`流来实现，`JDK 1.4`之后添加了`FileChannel`，则可以使用`Channel`来实现，在大文件复制上会比传统`IO`流快，实现文件复制的方式具体可以参考:

> src/main/java/cn/argentoaskia/FileCopyDemo.java



### FileDescriptor

### FilePermission





#### 使用Path类处理文件目录路径

在`Java 1.7`之后新增了`Path`接口来专门处理文件系统中的路径，`Oracle JDK`中存在两个该接口的实现类：`WindowsPath（rt.jar）`和`ZipPath（zipfs.jar）`，因为是反编译的结果，并没有这两个类的相关文档，因此只能从名字中猜测意思。该接口的实现是不可变且安全的，可供多个并行线程使用。

要创建`Path`接口的对象可以通过伴随类`Paths`，`Paths`类只有两个`get`方法：

```java
//将路径字符串或连接到路径字符串的字符串序列转换为 Path，可以get("c:/abc");或者get("c:","abc")，注意这里可以有多个参数String... more代表n个参数，这个比较常用
public static Path get(String first, String... more);
public static Path get(URI uri);
Path path1 = Paths.get("/a/b/c/d/e/f");
```

除此之外，你也可以使用`FileSystems`（`Paths.get()`就是使用这种方法）和`File`中的`toPath()`来创建`Path`对象：

```java
Path path  = FileSystems.getDefault().getPath("/abc", "efg", "hij");
Path path2 = new File("D:/abc.txt").toPath();
```

`Path`接口的`API`：

```java
// 是否是绝对路径
boolean isAbsolute(); 
// 测试此路径是否以给定的路径结束
boolean endsWith(Path other);
// 测试此路径是否以给定字符串结束，如"c:/a/banana/cat"可以以"/banana/cat"结尾，但不能以"t"结尾
boolean endsWith(String other);
// 测试此路径是否以给定的路径开始。  
boolean startsWith(Path other);
// 测试此路径是否以给定字符串开始，跟上面一样规律
boolean startsWith(String other);
// 将此路径表示的文件或目录的名称返回为Path对象
// 文件名或文件夹名，不含路径
Path getFileName();

// 返回此路径的某个路径节作为Path对象。
// 路径节最靠近root的为0，最远的为(count-1)
// 路径节可以理解为路径的某个组成部分，如路径/abc/efg/hij中abc、efg、hij目录都是一个路径节，对于这个路径，getName(1)就是获取efg路径节
Path getName(int index);

// 返回路径中的名路径节的数量。0则只有root
int getNameCount(); 
// 返回父路径，如果此路径没有父返回null，如/a/b/c返回/a/b，配合下面的方法消除"."或".."
Path getParent(); 
// 返回一个标准路径，该路径消除冗余路径节。如消除掉"."、".."
Path normalize();
// 返回此路径的根组分作为Path对象，或 null如果该路径不具有根组件。如返回"c:/"
Path getRoot(); 

// 构造从此路径到达给定路径的操作相对路径。就是描述如何从这个路径到达给定路径的
// p1-"Topic.txt", p2-"Demo.txt",
// p3-"/Java/JavaFX/Topic.txt", p4-"/Java/2011"
// 那么p1到p2的结果是"../Demo.txt"
// p2到p1的结果是"../Topic.txt"
// p3到p4的结果是"../../2011"
// p4到p3的结果是"../JavaFX/Topic.txt"
Path relativize(Path other); 

// 将给定的路径字符串转换为 Path。如"c:/a/b"和字符串"c.txt"的结果是"c:/a/b/c.txt"；更像是拼接
Path resolve(String other);

// 将给定的路径字符串转换为 Path。如"c:/a/b.txt"和字符串"c.txt"的结果是"c:/a/c.txt"；更像是替换
Path resolveSibling(String other); 

// 返回一个相对的 Path ，它是该路径的路径节的子序列，如"d:/a/b/c.txt"参数为(1,3)返回一个"b/c.txt"
Path subpath(int beginIndex, int endIndex); 

// 返回表示此路径的绝对路径的 Path对象。包括盘符和文件名或文件夹名
Path toAbsolutePath(); 

//返回此路径的路径节的迭代器。"c:/a/b/c.txt"的迭代器可以next出以下"a""b""c.txt"
Iterator<Path> iterator(); 

// 返回表示此路径的File对象
File toFile();
```

值得一提的是`Path`接口继承了`Watchable`接口，在使用`WatchService`时的时候，用于监听路径的变化。

#### 使用Files伴随类做高级处理

#### FileDescriptor

#### FilePermission
