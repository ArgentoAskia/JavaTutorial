## Java-File

### 简介

本篇主要介绍在`Java`中如何处理操作系统上的文件和目录，这些处理包括：

1. 重命名文件和目录
2. 查看修改文件和目录创建、更新日期
3. 移动、复制、删除文件和目录
4. 文件和目录的创建
5. ...

在此基础上还会介绍：

1. 文件位置路径

2. 简易的文件读写
3. 项目相关的资源读写和路径操作
4. 文件流&文件遍历
5. 文件系统

最后还会介绍两个高阶内容：

1. 文件加锁
2. 内存文件

前者能够保证文件的安全，后者用于提高文件读写速度。

### Java中的文件处理

在`JDK1.7`之前处理文件主要靠`File`类，但`JDK1.7`之后，就多了很多在相关的类和一些伴随类，用于方便操作，如`Paths`、`Files`、`FileVisitor`等，在处理文件操作中最常见的类：

- `File、Files`：一般用于处理文件和目录，如重命名、移动、创建等
- `Path、Paths`：一般用于处理路径

在`JDK`的中，常常会见到这样的类，如：`Array`和`Arrays`，`Collection`和`Collections`，这些带`s`的类一般是作为原类的工具类来设计和使用的，以增强原类的功能，因此也叫伴随类。如`Arrays`中添加了`sort()`、`toString()`、`fill()`等方法，增强了`Array`类所创建的动态数组。

`File`类和`Files`类、`Path`类和`Paths`类也是同样的道理。

#### 使用File类处理文件目录

`File`类的`API`非常丰富，大概能够分8组来认识，具体情况可以参考这个`Demo`：

> JavaProject\Java-File\src\main\java\cn\argentoaskia\FileDemo.java

- 用于判断文件是否可读可写可运行 

  - ```java
    boolean canExecute();
    boolean canRead();
    boolean canWrite();
    ```

- 两个文件相比较

  - ```java
    boolean equals(Object object);
    int compareTo(File file);
    ```

- 通用文件操作,创建、删除、重命名等

  - ```java
    public boolean exists();
    public boolean createNewFile() throws IOException;
    public void deleteOnExit();
    public boolean delete();
    public boolean mkdir();
    public boolean mkdirs();
    ```

- 文件的相对、标准(规范)路径、绝对路径等

  - ```java
    public String getPath();
    public String getAbsolutePath();
    public File getAbsoluteFile();
    public String getCanonicalPath() throws IOException;
    public File getCanonicalFile() throws IOException;
    ```

- 父类路径、文件名、剩余|总|使用空间、修改日期、文件大小

  - ```java
    public String getName();
    public String getParent();
    public File getParentFile();
    public long getFreeSpace();
    public long getUsableSpace();
    ```

- 一些判别方法 

  - ```java
    public boolean isAbsolute();
    public boolean isDirectory();
    public boolean isFile();
    public boolean isHidden(); 
    ```

- 一些遍历方法和转换方法

  - ```java
    public String[] list();
    public String[] list(FilenameFilter filter);
    public File[] listFiles();
    public File[] listFiles(FilenameFilter filter);
    public File[] listFiles(FileFilter filter);
    public Path toPath();
    public URI toURI();
    public URL toURL() throws MalformedURLException;
    public String toString();
    ```

- 设置相关的方法

  - ```java
    public boolean setExecutable(boolean executable);
    public boolean setLastModified(long time);
    public boolean setReadable(boolean readable);
    public boolean setReadOnly();
    public boolean setWritable(boolean writable);
    ```

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

