## Java-Network

Java 网络编程（BIO、NIO、AIO、HTTP、SMTP邮件、NTP、FTP等等）

本节主要介绍如何使用`Java`中的`Socket`和`Channel`技术来实现网络编程，会先介绍基于`TCP`协议的连接方式，该方式有三种连接迭代（`BIO`、`NIO`、`AIO`），然后会介绍基于`UDP`方式的无状态连接，再介绍上一层协议的编程，如`HTTP`、`NTP`、`SMTP`、`FTP`等，最后将会介绍一些常见的解决方案应用实现，如：大文件分片上传、断点续传、加密上传、群体聊天室、心跳检测等等。

--------

本章节主要介绍`Java`语言中常见的网络编程，包括但不仅限于：`TCP`、`UDP`、`HTTP`、`SMTP`、`NTP`、`FTP`等！后续有新的协议再逐渐补充。

在此基础上，会实现一些解决方法，如：大文件分片上传、断点续传、加密上传、聊天室、心跳检测等，这部分内容会...给出！

文章不会具体介绍协议本身，文章只关注`Java`中网络编程的实现，但是相关协议内容的文章会设置跳转链接！

### 基础网络设施类

在`Java`中，下面这些基础类描述了网络中主机名、`IP`、端口、网络接口（网卡）、`URL`编码：

- `URI`：`Uniform Resource Identifier`：[跳转到API](#api1)

  > 统一资源标识，URI类不包括任何用于访问资源的方法，它只存储链接地址，作用是用于解析

- `URL`：`Uniform Resource Locator`：[跳转到API](#api2)

  > 统一资源定位，URL类不仅能用来访问资源，还能进行解析操作，但也只能作用于Java类库知道如何解析的协议，如：http、https、ftp、file、jar

- `URLDecoder`：`URL`解码器

- `URLEncoder`：`URL`编码器

  > 编码器的编码规则如下：
  >
  > - 保留字符A到Z、a到z、0到9、.（点）-（短连接线）~（波浪号）_（下划线）
  > - 用+字符替换所有的空格
  > - 其他字符使用字符编码UTF-8，并将每个字符都编码为%后面紧跟一个两位的十六进制数字

- `InetAddress`：该类代表一个`IP`地址

  > 封装Host和IP地址的类，传递Host（主机名、域名等）将会自动解析出IP，InetAddress的实例由IP地址和可能对应的主机名组成(取决于它是用主机名构造的还是已经完成了反向主机名解析)，支持IPv4和IPv6！

- `InetSocketAddress`：该类代表一个地址（`IP`或者`Host`）和端口

  > 这个类实现了一个IP套接字地址(IP地址+端口号)，它也可以是一对(主机名+端口号)，在这种情况下，将尝试解析主机名。如果解析失败，则该地址被认为是未解析的，但仍然可以在某些情况下使用，例如通过代理连接！

- `NetworkInterface`：代表网络接口（网卡）

  > 该类表示由名称和分配给该接口的IP地址列表组成的网络接口。用于标识加入组播组的本地接口。接口通常被称为“le0”。

#### URI、URL、URN

`RFC2396`和`RFC2732`中定义了`URI`(`Uniform Resource Identifier`). `URI`是满足特定格式的字串,  进一步可以分作 `URL(Uniform Resource Locator)`和`URN(Uniform Resource Name)`

但在`RFC3986`的声明中，我们已经没必要把`URI、URL、URN`这三者分的太细：

> Future specifications and related documentation should use the general
> term “URI” rather than the more restrictive terms
> “URL” and “URN”

一般的`URI`格式如下：

```http
[scheme:]scheme-specific-part[#fragment] 
```

其中，如果一个`URI`带`[scheme:]`，则这个`URI`叫绝对`URI`（`absolute URI`）

其中，`absolute URI`中`scheme-specific-part`部分不是以`/`开头则称为`opaque URI`

下面这些链接都可以叫`URI`：

```http
http://java.oracle.com
// absolute URI
file:///C:/jdk-11.0.9_doc-all/docs/api/java.base/java/net/URI.html	
// opaque URI
mailto:java-net@java.sun.com
// opaque URI
news:comp.lang.java
```

`URI`里面`scheme-specific-part`部分可以细分：

```http
[//[user:password@]host[:port]][/]path[?query]
```

这也就是熟悉的`URL`格式！

```http
[scheme:][//[user:password@]host[:port]][/]path[?query][#fragment] 
```

其中：`[//[user:password@]host[:port]]`这一部分可以组成验证部分（`authority`）,这里有一个举例说明各个部分

```http
// 全的URI URL
jdbc:mysql://root:root@localhost:3306/mysql?charsetEncoding=utf8#abc
// 协议(scheme):jdbc:mysql
// userinfo(username:password): root:root
// host: localhost
// port: 3306
// path: /mysql
// query: charsetEncoding=utf8
// fragment: abc
// authority: root:root@localhost:3306
// schemeSpecificPart: //root:root@localhost:3306/mysql?charsetEncoding=utf8
```

------

<a name="api1" id="api1"></a>

在`URI`类中，可以使用`getXXX()`获取各个`URL`中的节，如：`getPort()`获取`port`部分，`getPath()`获取`Path`部分，当然获取的链接是经过解码的，也就是说，如果你的链接是这样的：

```http
https://Askia:Askia@www.baidu.com:80/baidu?ie=utf-8&wd=%E6%88%91%E7%9A%84OGNL#123
```

则获取`Query`部分的时候会进行解码：

```html
ie=utf-8&wd=我的OGNL
```

如果不希望解码，则可以使用`getRawXXX()`方法！所有获取的方法签名如下：

```java
public String getAuthority();
public String getFragment();
public String getHost();
public String getPath();
public int getPort();
public String getQuery();
public String getScheme();
public String getSchemeSpecificPart();
public String getUserInfo();

public String getRawAuthority();
public String getRawFragment();
public String getRawPath();
public String getRawQuery();
public String getRawSchemeSpecificPart();
public String getRawUserInfo();
```

如果希望判断`URI`是否是`absolute URI`和`opaque URI`可以使用：

```java
public boolean isAbsolute();
public boolean isOpaque();
```

`URI`和`URL`可以转换，使用：

```java
public URL toURL();
```

同时，我们可以使用

```java
public URI resolve(URI uri);
```

将一个绝对`URI`和相对`URI`进行组合：![image-20230805153718020](README/image-20230805153718020.png)

也可以使用

```java
public URI relativize(URI uri);
```

求出两个上下级关系的`URI`相对化的部分：

![image-20230805154027929](README/image-20230805154027929.png)

如果URI中带`.`、`..`这一类表示位置的字符，可以使用

```java
public URI normalize();
```

进行化简！

<a name="api2" id="api2"></a>

在`URL`中，也有和`URI`类似的`getXXX()`，部分不相同签名的方法也进行了解释，这里只介绍`URL`解析地址部分，关于`URL`发送请求部分请看后面的`http`章节！

```java
public String getAuthority();
public String getHost();
public String getPath();
public int getPort();
public String getQuery();
public String getUserInfo();

// 相当于public String getFragment();
public String getRef()
// 相当于public String getScheme();
public String getProtocol();
// 相当于getPath() + getQuery()
public String getFile();
// 获取getProtocol()的协议的默认端口，如http就是80，https就是443等
public int getDefaultPort();
// 除去Ref（Fragment）比较两个链接是否相等
public boolean sameFile(URL other)
```

#### URL编码器解码器

你可能在浏览器中看到类似的`URL`：

```
https://www.baidu.com/s?ie=utf-8&wd=%E9%98%BB%E5%A1%9E%E5%92%8C%E9%9D%9E%E9%98%BB%E5%A1%9E%E5%8C%BA%E5%88%AB
```

为什么需要将一个采用这种手段呢？实际上，**进行URL编码的主要原因是为了避免引发歧义与混乱。**

首先`URL`允许你给服务端携带一些数据，一种携带方法是在`URL`后面拼接`Key=value`的键值对（`Form`表单中的`GET`请求方式，多个使用`&`进行区分），在这种情况下，你可能就需要带一些中文数据！

出于成本、解析难度等因素考虑，`URL`中只允许包含英文字母、数字、`-_.~`（四个特殊字符：横杠、下划线、点、波浪号）。

如果`URL`中包含了其他字符如：空格、引号、中文等，那么那么可能会造成服务器或者浏览器的解析错误，或者导致`URL`的语义变化，但我们在数据传递的时候又确实需要这些字符，因此浏览器定下了下面两个扩展的解析条件：

- 使用`+`代替所有的空格，多个连续的空格都会被`+`代替
- 将其他所有字符编码为`UTF-8`，并将每一个字符都编码为`%`后面紧跟一个两位的十六进制数字

因此也就有了上面的链接，在网上有很多关于`URL`编码和解码的工具。那么在`Java`中又如何将一个`URL`进行编码呢？`java.net`包中提供了`URLDecoder`类和`URLEncoder`类：

```java
// URLDecoder
public static String decode(String s, String enc) throws UnsupportedEncodingException;

// URLEncoder
public static String encode(String s, String enc) throws UnsupportedEncodingException;

// 参数s：代表提供的URL
// 参数enc：代表用于编码的字符集，如：UTF-8、GBK、UTF-16等！
```

```java
public class URLCoderDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "https://www.baidu.com/s?ie=utf-8&wd=%E9%98%BB%E5%A1%9E%E5%92%8C%E9%9D%9E%E9%98%BB%E5%A1%9E%E5%8C%BA%E5%88%AB";
        // 解码，第一个参数传递要解码的url，第二个指定编码！
        String decode = URLDecoder.decode(url, "utf-8");
        System.out.println(decode);
        // 编码
        String encode = URLEncoder.encode(decode, "utf-8");
        System.out.println(encode);
        String gbk = URLEncoder.encode(decode, "GBK");
        System.out.println(gbk);
    }
}
// 输出：
https://www.baidu.com/s?ie=utf-8&wd=阻塞和非阻塞区别

https%3A%2F%2Fwww.baidu.com%2Fs%3Fie%3Dutf-8%26wd%3D%E9%98%BB%E5%A1%9E%E5%92%8C%E9%9D%9E%E9%98%BB%E5%A1%9E%E5%8C%BA%E5%88%AB
    
https%3A%2F%2Fwww.baidu.com%2Fs%3Fie%3Dutf-8%26wd%3D%D7%E8%C8%FB%BA%CD%B7%C7%D7%E8%C8%FB%C7%F8%B1%F0
```

#### 网络IP地址和主机名

`java.net`包中提供了`InetAddress`类来代表`IP`地址，并提供IP地址和主机名之间的解析功能，`InetSocketAddress`类则在`InetAddress`类的基础上添加加上端口（`Port`）

`InetAddress`类中提供了下面的静态方法来创建`InetAddress`对象：

```java
// 通过主机名，域名等进行解析，Java会自动解析出对应的所有IP地址
// 因此可以使用该方法来实现DNS解析功能
public static InetAddress[] getAllByName(String host) throws UnknownHostException;
// 提供一个数组如：[10,0,1,79]这样的数组来创建IP地址
public static InetAddress getByAddress(byte[] addr) throws UnknownHostException;
// 提供主机名和IP地址进行创建
// 该方法不会DNS解析，所以创建出来的地址和主机名可能是一个假地址！
public static InetAddress getByAddress(String host, byte[] addr) throws UnknownHostException;
// 通过主机名，域名等进行解析，Java会自动解析出对应的所有IP地址，返回第一个结果！
// 该方法也会进行DNS解析
public static InetAddress getByName(String host) throws UnknownHostException;
```

另外还有获取本机地址的方法：

```java
// 获取本机地址，如：LAPTOP-045ABBCV4/192.168.56.1
public static InetAddress getLocalHost() throws UnknownHostException;
// 获取本机回环测试地址，也就是localhost/127.0.0.1
public static InetAddress getLoopbackAddress();
```

`InetAddress`的对象方法：

```java
// 获取IP地址，由于Java中byte数组的范围在[-128-127]之间，所以一些高于127的字段将会使用负数代替，计算公式如下：127 + |129 + 负数的IP段| = 真正的IP段：
// 如：[-71, -57, 108, -103]使用上面的的公式计算出来的IP是：185.199.108.153
public byte[] getAddress();
// 获取计算之后的IP，该API会计算IP字节数组得到真正的IP地址！
public String getHostAddress();
// 获取原始域名（最原始的解析）
public String getCanonicalHostName();
// 获取最接近用户层的域名
public String getHostName();

// 该地址是否是一个子网掩码（wildcard address）
public boolean isAnyLocalAddress();
// 是否是一个链路本地地址（Link Local Address）
public boolean isLinkLocalAddress();
// 是否是本地回环地址（127.0.0.1）
public boolean isLoopbackAddress();
// check if the multicast address has global scope.
// 范围224.0.1.0 to 238.255.255.255
public boolean isMCGlobal();
// check if the multicast address has link scope.
// 224.0.0/24开头
public boolean isMCLinkLocal();
// check if the multicast address has node scope.
// IPv6专属
public boolean isMCNodeLocal();
// check if the multicast address has organization scope.
// IP地址范围：239.192 - 239.195
public boolean isMCOrgLocal();
// check if the multicast address has site scope.
// 即IP地址以239.255开头
public boolean isMCSiteLocal();
// 是否是广播地址（IP multicast address），即D类地址。
public boolean isMulticastAddress();
// 该地址是否可达，指定超时时间！
public boolean isReachable(int timeout) throws IOException;
public boolean isReachable(NetworkInterface netif, int ttl,
                               int timeout) throws IOException;
// 是否是本地站点地址（SiteLocalAddress）
public boolean isSiteLocalAddress();
```

#### 网络接口（网卡接口）









### TCP

关于`TCP`的介绍，可以参考这里：

`Java`里面实现`TCP`的有`Socket`、`Channel`两种方式，其中`Channel`是基于`Socket`提出的`NIO`（`Non-Block IO`）实现，最早出现于`JDK 1.4`，在之后的`1.7`中增加了`Asynchronous IO`，总结来说，`JDK`中实现`TCP`的`Api`目前有三套：

- 早期的原生`Socket`，一般叫做`BIO`（`Block IO`，同步且阻塞的`IO`）
- `1.4`开始的`NIO`（`Non-Block IO`，同步但非阻塞）
- `1.7`开始的`AIO`（`Asynchronous IO`，异步且非阻塞）

这里解释以下所谓的同步、异步、阻塞、非阻塞（已下纯个人理解）：

- 同步可以理解为一切东西的运行都是有序地安排的，一类事情完成接着下一类，比如说先调用函数`A`，再调用函数`B`，先写作业再洗澡等，总之，同步就是协同步调，按预定的先后次序进行运行。如：你说完，我再说。

- 异步则可以理解为东西的进行是没有安排的，但每个子模块的运行可能会影响其他模块，比如现在你需要做炖菜，则你需要先烧水，在烧水的过程中顺带洗菜，切菜，然后水开了，你在将菜放进去炖，那么可以说烧水和洗菜，切菜这两个过程是异步执行的。一般异步执行过程中或者执行完毕之后会进行对应的通知，这个通知一般也叫回调（`Callback`），如我们将菜放进去炖的过程就可以说是回调！

那么换成代码里面，异步方法一般需要传递一个回调对象，当请求执行完成之后，就会调用这个对象的方法，而同步则没有这个参数！

阻塞和非阻塞的理解也很简单，在代码里头，阻塞会导致方法卡死而不会往下执行，而非阻塞方法不会，无论调用方法是否有结果（没有结果则返回`null`、`0`等特殊值），不会导致方法卡死。





### UDP





