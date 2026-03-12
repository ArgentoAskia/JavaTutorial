## jar文件解析

在`Java`标准库中，和`Jar`文件解析有关的类位于`java.util.jar.*`下，类并不多，但是基本都有用处：

-   `Attributes`：表示`MANIFEST.MF`清单中的属性名-值对映射，内嵌于 `Manifest` 类中（`Manifest.Attributes`）
-   `JarEntry`：表示`JAR`文件中的单个条目（文件或目录）
-   `JarException`
-   `JarFile`：表示一个`JAR`文件，用于读取其中的条目和清单。
-   `JarInputStream`：用于从输入流读取`JAR`文件内容，适合网络流或需要边下载边处理的场景。
-   `JarOutputStream`：用于创建和写入`JAR`文件
-   `Manifest`：表示`JAR`的`META-INF/MANIFEST.MF`文件，存储元数据。
-   `Pack200`：提供`JAR`文件的高压缩比算法，专门针对类文件优化，`Pack200`在**Java 11**中已被标记为废弃（`deprecated`），在**Java 14**中被彻底移除。仅作历史参考。

在进行`API`讲解之前，读者可能不知道`jar`包是什么，简单来说就是`java`平台中的一种源代码打包格式，打出来的包可被其他`java`代码依赖和使用，或者在`java`平台中作为程序运行，内部的组成包括代码编译之后的类字节码以及一个包含`jar`包元数据的清单文件（`MANIFEST.MF`，该文件一般位于`jar`包的`META-INF`中）