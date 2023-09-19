## Java-File

本篇主要介绍在`Java`中如何处理操作系统上的文件和目录，这些基础操作包括：

1. 查看修改文件属性（创建日期、修改日期、只读等）和权限
2. 创建、移动、复制、删除、重命名文件和目录

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
  - 文件权限及计算表示：`FilePermission`（仅仅是计算！）
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
  	// pathname:代表当前遍历到的子目录和文件，也是一个File对象
      boolean accept(File pathname);
  }
  ```

`File`类的最后，我们讨论一下`File`类无法实现的复制和移动操作，在`JDK 1.7`之前，想要实现复制、移动操作，一种方法是借助`IO`流来实现，`JDK 1.4`之后添加了`FileChannel`，则可以使用`Channel`来实现，在大文件复制上会比传统`IO`流快，实现文件复制的方式具体可以参考:

> src/main/java/cn/argentoaskia/FileCopyDemo.java

文件的遍历`API`的使用可以参考：

> Java-File/src/main/java/cn/argentoaskia/io/FileWalkDemo.java

另外这里提供了一个用于遍历分区盘的程序：

> Java-File/src/main/java/cn/argentoaskia/io/FileListDemo.java

### FileDescriptor

所谓的`FileDescriptor`，也叫文件描述符（`Linux`），文件句柄（`Windows`），一般用于代表一个打开的文件、套接字等，`Linux`系统进程就是通过文件描述符而不是文件名来访问文件的，`C`语言里面的`FILE *`实际上也是代表一个文件描述符！

文件描述符实际上是一个非负整数！实际上，它是一个索引值，指向内核为每一个进程所维护的该进程打开文件的记录表。当程序打开一个现有文件或者创建一个新文件时，内核向进程返回一个文件描述符。程序在此文件描述符的基础上进行文件读写，另外关闭文件的时候也需要使用文件描述符来关闭。

习惯上，标准输入（`standard input`）的文件描述符是 `0`，标准输出（`standard output`）是 `1`，标准错误（`standard error`）是 2。

该类在`Java`的使用程度并不大，因为`Java`中强大的`IO`流家族使你无需关系这些底层的内容，引入该类的原因可能是考虑到兼容性文件！`FileInputStream`、`FileOutputStream`和`RandomAccessFile`、`FileReader`、`FileWriter`等在读取文件的时候就需要使用`FileDescriptor`，可以调用他们的`getFD()`来获取！

```java
public final FileDescriptor getFD() throws IOException {
    if (fd != null) {
        return fd;
    }
    throw new IOException();
}
```

当然你不能自行创建一个`FileDescriptor`对象，但如果你代码中有`FileDescriptor`对象，你可以传递给`FileInputStream`、`FileOutputStream`来进行文件读写：

```java
FileOutputStream fileOutputStream = new FileOutputStream(FileDescriptor.out);
FileInputStream fileInputStream1 = new FileInputStream(FileDescriptor.in);
```

另外这个类中，标准输入输出流的文件描述符如下：

![image-20230916195309985](README/image-20230916195309985.png)

我们也可以通过使用标准输入输出流的文件描述符来实现控制台输入输出，而不使用`System.in`、`System.out`：

> 经过测试，只能输入，无法输出！

`FileDescriptor`内部有两个字段特别注意：

![image-20230916194523900](README/image-20230916194523900.png)

`int`类型的参数代表`Linux`的文件描述符，而long类型的参数代表`Windows`的文件句柄，具体使用哪一个字段取决于系统是`Windows`还是`Linux`，出现这两个字段二选一使用的原因在于`Windows`系统和`Linux`系统打开文件时返回的这个整数句柄的的不同，`Windows`系统返回的是`HANDLE`结构（`64`位），而Linux则返回一个`int`（`32`位）

### FilePermission

该类一般用于对文件权限进行运算！

### 文件目录增强

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

用于文件或者目录复制、重命名、删除、创建、移动、查找等操作：

```java
// 复制
// 默认情况下如果target已经存在或者是一个符号链接（symbolic link，可以理解为快捷方式），则复制失败
// 如果是source和target是同一个文件，则不进行复制！
// 复制文件不复制文件的相关属性！
// 如果source是一个符号链接，并且支持复制符号链接，则对应被链接的文件会被复制
// 如果source是一个目录，则复制目录本身（空目录），不复制目录内的所有子目录和子文件！，可以配合walkFileTree()方法来实现复制子文件和子目录！
// 默认options选项支持三个：
//       StandardCopyOption.REPLACE_EXISTING: 如果target文件或者空目录存在，则覆盖！如果target是一个符号链接，则只覆盖符号链接而不覆盖链接对应的目标
//       StandardCopyOption.COPY_ATTRIBUTES：复制文件连同属性一块复制，默认情况下最简单的复制会携带lastModifiedTime属性
// 		 LinkOption.NOFOLLOW_LINKS：当source是一个符号链接的时候，直接复制符号链接而不是链接对应的文件
public static Path copy(Path source, Path target, CopyOption... options);
public static long copy(InputStream in, Path target, CopyOption... options);
public static long copy(Path source, OutputStream out);
// 创建目录
// 连续创建多级目录，如果目录已存在不会抛出异常
// 代表需要设置的目录属性，具体参考https://www.ietf.org/rfc/rfc3530.txt
// 和AclEntry类有关，一般很少用！
public static Path createDirectories(Path dir, FileAttribute<?>... attrs);
// 只创建一级目录，如果目录已存在抛出异常
public static Path createDirectory(Path dir, FileAttribute<?>... attrs);
// 创建文件
public static Path createFile(Path path, FileAttribute<?>... attrs);
// 创建链接（硬链接）
public static Path createLink(Path link, Path existing);
// 创建符号链接
public static Path createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs);
// 创建临时目录
public static Path createTempDirectory(Path dir, String prefix, FileAttribute<?>... attrs);
public static Path createTempDirectory(String prefix,
                                       FileAttribute<?>... attrs);
// 创建临时文件
public static Path createTempFile(Path dir,String prefix,String suffix,FileAttribute<?>... attrs);
public static Path createTempFile(String prefix,String suffix,FileAttribute<?>... attrs);

// 删除文件
// 此方式删除文件或者空文件夹，如果文件夹不为空的情况下会报错
public static void delete(Path path) throws IOException;
// 如果是符号链接则删除符号链接本身！
public static boolean deleteIfExists(Path path) throws IOException;

// 查找文件
public static Stream<Path> find(Path start,
                                int maxDepth,
                                BiPredicate<Path, 
                                BasicFileAttributes> matcher,
                                FileVisitOption... options);
// 移动或者重命名文件
// 支持移动所有子文件和子文件夹！
// 支持两个属性：StandardCopyOption.REPLACE_EXISTING(如果目标存在，则覆盖！)、StandardCopyOption.ATOMIC_MOVE(保证原子性的移动，也就是说所有文件要么全移动过去，要么移动失败进行回滚！)
// 如现在有两个目录：/var/2 /var/3,移动的方式是移动文件夹2到文件夹3，包括文件夹2本身，这实际上就是重命名,注意3目录必须不存在，否则会抛出AccessDeniedException
// /var/2 /var/dir/3（3文件夹不存在） 同理，最终的移动结果是/var/2没有了，/var/2的内容全部都移动到了/var/dir/3
public static Path move(Path source, Path target, CopyOption... options) throws IOException;
```

用于判断、获取和设置文件相关属性、文件是否存在、文件大小、获取链接等：

```java
// 文件是否存在！
public static boolean exists(Path path, LinkOption... options);
public static boolean notExists(Path path, LinkOption... options);
public static Object getAttribute(Path path, String attribute, LinkOption... options);
public static <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options);
public static FileStore getFileStore(Path path) throws IOException;
public static FileTime getLastModifiedTime(Path path, LinkOption... options);
public static UserPrincipal getOwner(Path path, LinkOption... options);
public static Set<PosixFilePermission> getPosixFilePermissions(Path path,LinkOption... options);

public static boolean isDirectory(Path path, LinkOption... options);
public static boolean isExecutable(Path path);
public static boolean isHidden(Path path) throws IOException;
public static boolean isReadable(Path path);
public static boolean isRegularFile(Path path, LinkOption... options);
public static boolean isSameFile(Path path, Path path2) throws IOException;
public static boolean isSymbolicLink(Path path);
public static boolean isWritable(Path path);

// 获取文件的文件类型
public static String probeContentType(Path path);

public static <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options);
public static Map<String,Object> readAttributes(Path path, String attributes, LinkOption... options);

public static Path readSymbolicLink(Path link) throws IOException;
public static Path setAttribute(Path path, String attribute, Object value, LinkOption... options);
public static Path setLastModifiedTime(Path path, FileTime time);
public static Path setOwner(Path path, UserPrincipal owner);
public static Path setPosixFilePermissions(Path path, Set<PosixFilePermission> perms);
public static long size(Path path) throws IOException;
```

用于转换成`IO`、`channel`、`Stream`等：

```java
public static Stream<String> lines(Path path) throws IOException;
public static Stream<String> lines(Path path, Charset cs) throws IOException;

// 使用特定文件来创建BufferedReader
public static BufferedReader newBufferedReader(Path path) throws IOException;
public static BufferedReader newBufferedReader(Path path, Charset cs) throws IOException;
public static BufferedWriter newBufferedWriter(Path path, Charset cs, OpenOption... options);
public static BufferedWriter newBufferedWriter(Path path, OpenOption... options);
public static SeekableByteChannel newByteChannel(Path path, OpenOption... options);
public static SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs);
public static DirectoryStream<Path> newDirectoryStream(Path dir, String glob);
public static DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter);
public static DirectoryStream<Path> newDirectoryStream(Path dir, String glob);
public static InputStream newInputStream(Path path, OpenOption... options);
public static OutputStream newOutputStream(Path path, OpenOption... options);

```

用于简单文件读写：

```java
public static byte[] readAllBytes(Path path) throws IOException;
public static List<String> readAllLines(Path path) throws IOException;
public static List<String> readAllLines(Path path, Charset cs) throws IOExceptio;
public static Path write(Path path, byte[] bytes, OpenOption... options);
public static Path write(Path path, Iterable<? extends CharSequence> lines, Charset cs, OpenOption... options);
public static Path write(Path path, Iterable<? extends CharSequence> lines, OpenOption... options);
```

用于简单文件遍历：

```java
public static Stream<Path> list(Path dir) throws IOException;
public static Stream<Path> walk(Path start, FileVisitOption... options) throws IOException;
public static Stream<Path> walk(Path start, int maxDepth, FileVisitOption... options);
public static Path walkFileTree(Path start, FileVisitor<? super Path> visitor);
public static Path walkFileTree(Path start, Set<FileVisitOption> options, int maxDepth, FileVisitor<? super Path> visitor);
```

#### 基于Files的文件遍历

#### 文件属性

#### FileStore文件存储卷

`FileStore`表示存储池、设备、分区、卷、具体的文件系统或其他特定于文件存储的实现方式！

JDK默认带两个实现：`WindowsFileStore`（`Windows`的存储池）和`ZipFileStore`（`zip`文件系统储存池）

可以使用下面的`API`获取`FileStore`对象：

```java
// Files类的
public static FileStore getFileStore(Path path) throws IOException
// FileSystem类的
public abstract Iterable<FileStore> getFileStores();
```

`API`如下：

```java
// 获取驱动器名称，也就是Windows资源管理器里面的C、D、E盘等后面跟着的盘符标识名称
public abstract String name();
// 获取驱动器类型，如NTFS、FAT32等
public abstract String type();
// 驱动器是否只读！
public abstract boolean isReadOnly();
// 获取总空间
public abstract long getTotalSpace() throws IOException;
// 获取驱动器剩余可使用的空间
public abstract long getUsableSpace() throws IOException;
// 获取驱动器未分配空间，和getUsableSpace()作用相同
public abstract long getUnallocatedSpace() throws IOException;
// 获取相关属性，可用的属性名请参考WindowsFileStore的getAttribute()和ZipFileStore的getAttribute()
public abstract Object getAttribute(String attribute) throws IOException;

public abstract boolean supportsFileAttributeView(Class<? extends FileAttributeView> type);
public abstract boolean supportsFileAttributeView(String name);
public abstract <V extends FileStoreAttributeView> V
        getFileStoreAttributeView(Class<V> type);
```

另外默认情况下`WindowsFileStore`提供了这些属性给`getAttribute()`

```
totalSpace
usableSpace
unallocatedSpace
volume_vsn
volume_isRemovable
volume_isCdrom
```

#### 文件监听机制

`JDK 1.7`引入了文件监听系统，旨在监听文件系统目录的增删改查情况，其核心：

- `Watchable`接口，用于注册监听服务，`Path`接口继承了该接口，里面使用`register`方法来注册监听：

  ```java
  WatchKey register(WatchService watcher,
                    WatchEvent.Kind<?>[] events,
                    WatchEvent.Modifier... modifiers)
      throws IOException;
  WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events)
          throws IOException;
  ```

- `WatchEvent<T>`接口，监听事件接口，当目录存在更新的时候就会触发一个或者多个事件，产生多个`WatchEvent<T>`对象

  ```java
  // 表示获取监视事件的类型。
  WatchEvent.Kind<T> kind();
  
  // 表示获取监视事件的数量，若大于 1，则这是一个重复事件。
  int count();
  
  // 表示获取监视事件的上下文，即事件类型的泛型对象，通常为 Path 对象，可能为 null。
  T context();
  ```

- `WatchKey`接口，代表被监听的目录对象！

  ```java
  // 表示判断 watch key 是否有效。
  boolean isValid();
  
  // 表示获取 watch key 的所有事件，且清空事件集合，也就是说再次获取，将获取到一个空集合。
  List<WatchEvent<?>> pollEvents();
  
  // 表示重置 watch key，若 watch key 无效，则返回 false。
  boolean reset();
  
  // 表示取消监视服务，且将 watch key 设为无效。
  void cancel();
  ```

- `WatchService`接口，代表监听服务，整个文件监听的核心！

  ```java
  // 表示关闭监视服务。
  void close();
  
  /*
      表示立即获取且移除下一个 watch key，若没有，则返回 null。一个 watch key 代表着监视
      一个注册对象的事件集合，当一个注册对象发生改变时，就会产生一个 watch key。
      非阻塞
   */
  WatchKey poll();
  
  /*
      表示在指定时间之内获取且移除下一个 watch key，若没有，则返回 null。一个 watch key 
      代表着监视一个注册对象的事件集合，当一个注册对象发生改变时，就会产生一个 watch key。
      非阻塞
   */
  WatchKey poll(long timeout, TimeUnit unit);
  
  /* 
      表示获取且移除下一个 watch key，若没有，则一直处于等待状态。一个 watch key 代表着监视
      一个注册对象的事件集合，当一个注册对象发生改变时，就会产生一个 watch key。
      阻塞
   */
  WatchKey take();
  ```

- `StandardWatchEventKinds`，枚举量，代表监听的事件的类型，如监听目录文件创建、修改、删除等！

```java
// 表示已丢失事件。
static WatchEvent.Kind<Object> OVERFLOW;

// 表示条目创建事件。
static WatchEvent.Kind<Path> ENTRY_CREATE;

// 表示条目删除事件。
static WatchEvent.Kind<Path> ENTRY_DELETE;

// 表示条目修改事件。
static WatchEvent.Kind<Path> ENTRY_MODIFY;
```

基本的使用方法如下：

1. 调用`FileSystems.getDefault().newWatchService()`获取`WatchService`实现
2. 定义要监听的目录的监听的类型（只监听创建、或者监听创建修改等），需要使用`StandardWatchEventKinds`
3. 使用`Path`对象的`register()`注册要监听的目录，传递`WatchService`对象和`StandardWatchEventKinds`的监听类型数组，每次调用`register()`都会返回一个`WatchKey`对象，代表一个被监听的目录（因为你可能会向同一个`WatchService`对象监听多个目录！因此可能会有多个`WatchKey`对象）
4. 编写监听循环，这个循环可以放在一个线程内进行，简单的死循环即可
5. 在这个死循环内，调用`watchService.take();`来获得当前哪个目录产生变更事件（`WatchKey`对象）
6. 然后通过调用`WatchKey`对象的`isValid()`判断是否是一个合法的`key`，如果可以不合法，跳过！
7. `key`合法，则调用`pollEvents()`获取在这个目录上产生的所有文件变更，会返回一个`List`
8. 遍历这个`List`，调用`WatchEvent`的`context()`可以拿到在这个目录里面变更的文件名或者目录名，调用`count()`获取这个事件是否是一个重复事件，调用`kind()`获取变更的具体类型（创建、修改、删除）
9. 遍历完成之后，调用`WatchKey`对象的`reset()`方法进行重置，等待下一轮监听
10. 在这个过程中，如果出现任何问题需要关停监听服务的，调用`WatchService`对象的`close()`

> 代码实现参考：Java-File/src/main/java/cn/argentoaskia/nio/FileStoreDemo.java

#### 文件系统



