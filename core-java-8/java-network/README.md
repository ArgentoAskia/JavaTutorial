## java-network

本文章主要介绍基于`java.net`包、`java.nio.channel`包以及`javax.net`包下的和网络相关的类。

其中`java.net`包下的类基本覆盖了基础网络设施、`tcp socket`、`udp socket`、以及简单的`Http`请求实现（协议实现）。此包下的类也被称之为阻塞`IO`（`BIO`）实现。

而`java.nio.channel`下的类，则包含了以`IOCP`（`I/O Completion Port`，`I/O`完成端口，`windows`平台）和`epoll`多路复用`IO`技术（`Linux`平台）为底层实现的非阻塞`IO`（`NIO`）、异步`IO`（`AIO`），或者说是基于`chanel Socket`的`IO`技术。

而最后，在`javax.net`包下，我们将会见证使用`SSL`的`Http`技术，即`Https`协议。

由于文章篇幅的限制，我们在介绍这些`API`的时候只会简单介绍基于这些`API`的背景知识而不是很细节地讲解，这就需要读者自行补充相关知识，当然我们也会给出链接（~~尽量~~）

### v1版本文章前言

`Java`网络编程（`BIO`、`NIO`、`AIO`、`HTTP`、`SMTP`邮件、`NTP`、`FTP`等等）

本节主要介绍如何使用`Java`中的`Socket`和`Channel`技术来实现网络编程，会先介绍基于`TCP`协议的连接方式，该方式有三种连接迭代（`BIO`、`NIO`、`AIO`），然后会介绍基于`UDP`方式的无状态连接，再介绍上一层协议的编程，如`HTTP`、`NTP`、`SMTP`、`FTP`等，最后将会介绍一些常见的解决方案应用实现，如：大文件分片上传、断点续传、加密上传、群体聊天室、心跳检测等等。

--------

本章节主要介绍`Java`语言中常见的网络编程，包括但不仅限于：`TCP`、`UDP`、`HTTP`、`SMTP`、`NTP`、`FTP`等！后续有新的协议再逐渐补充。

在此基础上，会实现一些解决方法，如：大文件分片上传、断点续传、加密上传、聊天室、心跳检测等，这部分内容会...给出！

文章不会具体介绍协议本身，文章只关注`Java`中网络编程的实现，但是相关协议内容的文章会设置跳转链接！

## java.net.*

该包下包含的类大致能分为：`udp`、`tcp`、基础网络设施、代理等等。利用现有的AI技术，我们很容易知道每一个类是干什么用的：

| 类名 | 分类 | 简单介绍 |
| ---- | ---- | -------- |
|      |      |          |
|      |      |          |
|      |      |          |

### 基础网络设施

- 网络`IP`地址、`Socket`地址（`IP`+端口）：`Inet4Address`、`Inet6Address`、`InetAddress`、`InetSocketAddress`、`SocketAddress`

- 域名处理相关：`IDN`
- 基础网卡信息：`InterfaceAddress`、`NetworkInterface`
- `URL`、`URI`相关：`URI`、`URL`、`URLDecoder`、`URLEncoder`
- 网络`IP`协议：`ProtocolFamily`接口、`StandardProtocolFamily`

- 保留：`URLClassLoader`（在介绍类加载器的时候介绍）、`NetPermission`（网络权限有，一般很少使用）

#### 网络IP地址

在网络中，网络`IP`地址就是基础，一个`IP`就代表着网络上的一台电脑。而在`Java`类库中，代表IP地址的类一共有两种，即以`InetAddress`为基础的仅代表`IP`地址（或者主机名、域名）的类和以`SocketAddress`为基础的`IP + port`的类。

另外在计算机网络中，还存在着很多特殊的`IP`地址，为了让读者能够看明白`InetAddress`和`SocketAddress`的相关`API`，我们补充一些前置的网络`IP`知识：

> 通配符地址（`Wildcard Address`）：
>
> 在`Java`网络编程中通常指`InetAddress`类中的`ANY`地址，表示所有可用的网络接口（也就是网卡，包括物理的实体网卡和虚拟网卡，下面都用网络接口表示）。
>
> 对于`IPv4`，通配符地址是`0.0.0.0`，而对于`IPv6`，它是`::`。
>
> 通配符地址的主要用于服务器端监听，当服务器端绑定到通配符地址时，它将监听所有网络接口上的连接请求。这对于服务器来说非常有用，因为它可以接受来自任何网络接口的连接。
>
> 比如说当前服务器上插有`3`张网卡，他们都处于非断开连接状态且都有`ip`，比如第一张网卡的`ip`为：`192.168.0.2`，第二张：`192.168.0.3`，第三张：`192.168.0.4`，则客户端连接服务端时，只要服务端监听的地址是通配符地址，则客户端通过这三个IP的任意一个都能访问到服务器端！
>
> 使用通配符地址的另外一个好处就在于多网络接口并且在跨网的时候，也能够访问到的问题，同样是一台电脑有多个网卡，其中一个配置了内网地址`192.168.0.5`，内网的主机可以访问这台机器，另外一个网卡直接配置的`ISP`提供商提供的外网地址，比如：`118.252.125.3`，这两个地址分别位于不同的网络环境，他们的网关和子网掩码都不一样，但同样不影响客户端访问，如果是内网的客户端，则既可以通过内网地址进行访问，也可以通过外网地址来访问，而如果是外网地址的机器，则能通过外网地址来访问。
>
> 这就好比个移动设备在连接到`Wi-Fi`和移动数据网络时，绑定到 `0.0.0.0` 地址的服务器应用可以确保无论设备当前连接的是哪种网络，服务器都能接收到来自客户端的连接请求。
>
> 在Java中，InetAddress类的getAnyLocalAddress()方法返回一个表示通配符地址的InetAddress对象。

`InetAddress`和`SocketAddress`的继承关系如下：![image-20250602135013552](README/image-20250602135013552.png)

##### InetAddress族

`InetAddress`会封装`Host`和`IP`地址的类，传递`Host`（主机名、域名等）将会自动解析出`IP`，`InetAddress`的实例由IP地址和可能对应的主机名组成(取决于它是用主机名构造的还是已经完成了反向主机名解析)，支持IPv4和IPv6！

因此，`InetAddress`类除了能用来代表`IP`地址，还提供`IP`地址和主机名之间的解析功能。

1. `InetAddress`类中提供了下面的静态方法来创建`InetAddress`对象：

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

2. 另外还有获取本机地址的方法：

```java
// 获取本机地址，如：LAPTOP-045ABBCV4/192.168.56.1
public static InetAddress getLocalHost() throws UnknownHostException;
// 获取本机回环测试地址，也就是localhost/127.0.0.1
public static InetAddress getLoopbackAddress();
```

3. `InetAddress`的对象方法：

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

// 该地址是否是一个通配符地址（wildcard address）
public boolean isAnyLocalAddress();
// 是否是一个链路本地地址（Link Local Address），即本机地址，如192.168.1.2等！
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

`InetAddress`类的子实现`Inet4Address`类没有扩展的方法，也就是说，我们使用`InetAddress`类的静态方法默认创建的就是`Inet4Address`对象。

而对于`Inet6Address`类中，则添加下面的新方法：

```java
// 这是一个工具方法，用于检查当前的 InetAddress 对象是否为一个“IPv4兼容的IPv6地址”。IPv4 兼容的 IPv6 地址是一种特殊的 IPv6 地址格式，用于在 IPv6 网络中表示 IPv4 地址，以便在早期的 IPv6 部署阶段实现 IPv4 与 IPv6 的兼容性。格式为：::a.b.c.d（其中 a.b.c.d 是一个 IPv4 地址），例如：::192.0.2.1 表示 IPv4 地址 192.0.2.1。
public boolean isIPv4CompatibleAddress();
/**
返回数字形式的 scopeId（作用域标识符），前提是当前实例（Inet6Address）已经关联到一个网络接口.如果没有设置 scopeId，则返回 0。
scopeId 是 IPv6 地址的一个额外标识，用于区分同一链路（link）上的不同网络接口,在 IPv6 中，某些地址类型（如链路本地地址 fe80::/10,下文会解释）只在本地链路范围内有效。如果一台主机有多个网络接口（或者网卡，尤其是带NAT的网卡），仅凭链路本地地址本身无法确定具体使用哪个接口，因此需要额外的 scopeId 来指定接口。在 Java 中，这个 scopeId 通常对应于网络接口的索引（interface index）。

例如：假设你的电脑有两个网络接口：
	接口1（以太网）：索引为 2
	接口2（Wi-Fi）：索引为 3
它们的 IPv6 链路本地地址可能都是类似：fe80::1
为了区分这两个地址具体属于哪个接口，就需要额外的 scopeId：
对于	以太网接口：fe80::1%2（scopeId 为 2）
对于 	Wi-Fi 接口：fe80::1%3（scopeId 为 3）

那么何为链路本地地址？专门为同一物理链路（如局域网、直连网络）内的通信而设计的一类特殊地址，其固定前缀为：fe80::/10。具备下面的特点：
	前缀范围：从 fe80:: 到 febf:ffff:ffff:ffff:ffff:ffff:ffff:ffff。
	作用范围：仅在本地链路（link-local）内有效，不会被路由器转发到其他网络
	自动生成：每个支持 IPv6 的网络接口都会自动生成一个链路本地地址，即使没有任何其他配置（如 DHCPv6 或手动配置）
用途广泛：
	邻居发现协议（NDP，类似 IPv4 的 ARP）
	无状态地址自动配置（SLAAC）
	路由器之间的通信（如 OSPFv3、BGP 等路由协议的下一跳地址）
	DHCPv6 客户端与服务器之间的初始通信

注意事项：
	链路本地地址不可路由，因此不能用于跨网络通信。
	由于链路本地地址在每个链路上都可能重复（例如多个网卡都可能出现 fe80::1），因此在使用时需要明确指定网络接口（如 Linux 中的 fe80::1%eth0）【这也是本API出现的原因】。
	如果你在网络配置中看到类似 fe80::/10 的 ACL 或防火墙规则，通常是为了允许或限制本地链路内的通信流量

举个例子：
	假设你有一个简单的 IPv6 网络，两台电脑通过网线直连：
		电脑 A 的链路本地地址：fe80::a1b2:c3d4:e5f6:1234
		电脑 B 的链路本地地址：fe80::9876:5432:10fe:abcd
	这两台电脑可以直接使用上述地址互相通信，无需额外配置网关或路由器，也无需全球单播地址。
再例如：
	假设你有一台电脑和一台打印机连接到同一个交换机，则它们可以通过 IPv6 链路本地地址（如 fe80::1234 和 fe80::5678）直接通信。

注意链路本地地址并不是本地回环地址（127.0.0.1），IPv6中的本地回环地址是::1，虽然看起来都用于“本机通信”，但它们的作用范围、用途和通信方式完全不同。
链路本地地址用于同一物理链路（局域网）内的设备间通信，会出网卡（但不会出路由），必须有物理网卡支持，而本地回环地址（localhost/127.0.0.1）只用于本机内部通信，仅限本机不会出网卡，不会出网卡也不可路由。

在 IPv4 里，链路本地地址的官方叫法是 APIPA（Automatic Private IP Addressing） 地址，范围是：
169.254.0.0/16
即 169.254.0.1 ～ 169.254.255.254（169.254.255.255 为广播），当讲两台电脑直连网线，都没启用 DHCP，系统会给各自分配一个 169.254.x.x 地址，它们就能用这些地址互相访问共享文件。IPv6同样场景下，它们会各自生成 fe80::xxxx 地址，例如：fe80::a1b2:c3d4:e5f6:1234，也能直接通信，但用的是 IPv6 协议栈。InetAddress的getByName()提供了对该参数的指定
*/
public int getScopeId();

// 如果当初在创建这个 Inet6Address 实例时，指定了某个“作用域接口”（scoped interface），就返回这个接口；否则返回 null。
// IPv6 地址（尤其是链路本地地址 fe80::/10）只在本地链路有效。如果一台机器有多块网卡，每块网卡各自有一个链路本地地址，光凭地址本身区分不了“从哪块网卡发出/接收”。因此在 Java 里创建 Inet6Address 时，可以额外绑定一个 NetworkInterface 对象，告诉它“这个地址属于哪块网卡”。这个被绑定的网卡就是所谓的 scoped interface。
// 用来解决链路本地地址在多网卡环境下的歧义问题。
public NetworkInterface getScopedInterface();
// 比如：
NetworkInterface eth0 = NetworkInterface.getByName("eth0");
Inet6Address addr = Inet6Address.getByAddress(
        null,                     // hostname
        new byte[]{...},          // 16 字节的 IPv6 地址
        eth0                      // 指定作用域接口
);
System.out.println(addr.getScopedInterface());  // 输出 eth0
// 如果当初没传 eth0，则：
Inet6Address addr2 = Inet6Address.getByAddress(null, new byte[]{...}, null);
System.out.println(addr2.getScopedInterface());  // 输出 null
```

##### SocketAddress族

`InetSocketAddress`类则在`InetAddress`类的基础上添加加上端口（`Port`），他继承自`SocketAddress`抽象类，`SocketAddress`是一个标记类，内部没有任何的方法，而

- `InetSocketAddress`：该类代表一个地址（`IP`或者`Host`）和端口

    > 这个类实现了一个IP套接字地址(IP地址+端口号)，它也可以是一对(主机名+端口号)，在这种情况下，将尝试解析主机名。如果解析失败，则该地址被认为是未解析的，但仍然可以在某些情况下使用，例如通过代理连接！



#### 域名处理相关

现代化的域名一般都支持中文, 比如：`www.askia.网站`。而因为许多网络协议和系统只支持`ASCII`字符。`IDN`类可以将非`ASCII`域名转换为兼容的格式。通过使用`IDN.toASCII()`，而通过`IDN.toUnicode()`可以转回来

```java
public static String toASCII(String input);
public static String toUnicode(String input);

// 另外IDN类还有两个其他方法
public static String toASCII(String input, int flag);
public static String toUnicode(String input, int flag);

// 其需要接收两个参数
// Flag to allow processing of unassigned code points
public static final int ALLOW_UNASSIGNED = 0x01;
// Flag to turn on the check against STD-3 ASCII rules
public static final int USE_STD3_ASCII_RULES = 0x02;
```

#### 基础网卡信息

你可能在之前遇到过一个叫网络接口（`network interface`）的计算机网络名词。在网络编程中，网络接口是一个逻辑概念，代表操作系统中用于管理和配置网络连接的软件抽象。它表示一个网络连接的逻辑实体，**可以是物理网卡，也可以是虚拟接口**（如虚拟专用网络`VPN`接口、虚拟机的虚拟网卡等等）。它提供网络配置和管理功能，如设置`IP`地址、子网掩码、默认网关等。支持多网络连接，即使计算机只有一个物理网卡，也可以配置多个网络接口（如通过`VLAN`或虚拟化技术）。

而我们计算机中使用的网卡，则一般指代物理的网卡硬件。网卡是计算机硬件组件，用于连接计算机到网络。它提供了物理层的连接，使计算机能够与其他设备通信。它提供网络连接端口（如以太网口、`Wi-Fi`接口等），处理数据的物理传输，包括发送和接收数据帧并提供唯一的物理地址（`MAC`地址）。

我们的计算机中理论上可以插入多张物理网卡，不管是有线还是无线，假如我的电脑上插了2张网卡，一张是有线的，一张是无线的，然后电脑安装了`Vbox`和`Vmware`，配置了三张虚拟网卡，因此我就可以说我电脑上有`5`个网络接口，其中两个是真实的，`3`个是虚拟的。

那么计算机的数据包是如何通过正确的网络接口发出的呢？

```
当然可以！计算机选择正确的网络接口来发送网络包的过程涉及到多个步骤和组件的协同工作。以下是一个更为详细和全面的解释：

---

### 1. **目的地址分析**
当计算机需要发送一个网络包时，它首先会查看该包的目的IP地址。这个地址决定了数据包的最终去向。

- **本地通信**：如果目的IP地址是`127.0.0.1`（本地回环地址），数据包会在本机内部循环，不需要通过物理网络接口发送。
- **远程通信**：对于其他IP地址，计算机需要进一步判断数据包是发送到本地网络还是外部网络。

---

### 2. **子网掩码与本地子网判断**
计算机使用子网掩码来确定目的IP地址是否在本地子网内。

- **子网掩码的作用**：子网掩码是一个32位的数字，用于划分IP地址中的网络部分和主机部分。例如，常见的子网掩码有`255.255.255.0`（表示C类网络）。
- **计算过程**：计算机将本机IP地址和子网掩码进行按位与运算，得到本地网络地址。同样地，将目的IP地址和子网掩码进行按位与运算，得到目的网络地址。如果两者相同，则目的IP在本地子网内；否则，目的IP在远程网络。

---

### 3. **路由表查询**
如果目的IP地址不在本地子网内，计算机需要通过路由表来确定如何将数据包转发到远程网络。

- **路由表结构**：路由表包含多个条目，每个条目通常包括以下信息：
  - **目标网络**：定义了路由条目所指向的网络范围（如`192.168.1.0/24`）。
  - **子网掩码**：与目标网络结合，明确网络范围。
  - **下一跳路由器**：数据包离开本地网络后需要经过的下一个路由器IP地址。
  - **输出接口**：本机用于发送数据包的网络接口（如`eth0`）。
  - **度量值**：表示该路由的优先级或成本，用于选择最佳路径。
- **路由选择算法**：计算机使用最长前缀匹配算法选择路由条目。即在路由表中找到与目的IP地址匹配的最长网络前缀的条目。例如，目的IP为`192.168.1.100`，路由表中有两个条目：
  - `192.168.1.0/24`（子网掩码`255.255.255.0`）
  - `192.168.0.0/16`（子网掩码`255.255.0.0`）
  计算机会选择`192.168.1.0/24`条目，因为它提供了更具体的网络地址。

---

### 4. **默认网关**
如果路由表中没有匹配的条目，数据包会被发送到默认网关。

- **默认网关的作用**：默认网关是本地网络中的一个路由器，它负责将数据包转发到其他网络。计算机配置中通常会指定一个默认网关IP地址（如`192.168.1.1`）。
- **最后的选择**：默认网关是计算机在找不到其他路由时的最后手段，它确保了数据包可以离开本地网络并尝试到达外部网络。

---

### 5. **网络接口匹配**
根据路由表确定的输出接口，计算机选择对应的网络接口。

- **物理接口**：如果是发送到本地网络中的其他设备，数据包会通过相应的物理网络接口（如以太网接口`eth0`或Wi-Fi接口`wlan0`）发送。
- **虚拟接口**：如果是虚拟化环境（如VMware、Docker），数据包可能通过虚拟网络接口（如`VMnet8`）发送。

---

### 6. **链路层处理**
网络接口会将IP数据包封装成帧（如以太网帧），并添加链路层的源和目的MAC地址。然后，数据帧通过物理介质（如网线、无线信号等）发送出去。

- **ARP协议（Address Resolution Protocol）**：在以太网中，计算机使用ARP协议将目的IP地址解析为MAC地址。如果目的IP在本地子网内，计算机通过ARP查询目标设备的MAC地址；如果不在本地子网内，计算机查询默认网关的MAC地址。

---

### 7. **特殊情况处理**
- **多播（Multicast）**：多播数据包会发送到特定的多播组地址（如`224.0.0.1`），计算机根据多播路由表选择合适的网络接口。
- **广播（Broadcast）**：广播数据包会发送到本地子网的所有主机（如`192.168.1.255`），通常使用本地网络接口广播。
- **虚拟网络接口**：在虚拟化环境中，计算机根据虚拟机的配置和虚拟网络的拓扑结构选择对应的虚拟接口。

---

### 8. **路由表的动态更新**
路由表不是静态的，它可以通过以下方式动态更新：
- **动态路由协议**：如RIP（Routing Information Protocol）、OSPF（Open Shortest Path First）等，这些协议允许路由器之间自动交换路由信息。
- **操作系统路由管理**：管理员可以通过命令行（如`route add`）或图形界面手动添加或删除路由条目。

---

### 总结

计算机选择正确的网络接口来发送网络包的过程是一个复杂的、多步骤的决策过程。它涉及到目的地址分析、子网划分、路由表查询、默认网关选择、网络接口匹配和链路层处理等多个环节。这个过程确保了数据包能够高效、准确地到达目标设备。

希望这个详细的解释能够帮助你更好地理解计算机网络包发送的原理！
```

`NetworkInterface`：代表网络接口（网卡）

> 该类表示由名称和分配给该接口的IP地址列表组成的网络接口。用于标识加入组播组的本地接口。接口通常被称为“le0”。

#### URL、URI解析和编解码

- `URI`：`Uniform Resource Identifier`（统一资源标识），`URI`类不包括任何用于访问资源的方法，它只存储链接地址，作用是用于解析。

- `URL`：`Uniform Resource Locator`（统一资源定位），`URL`类不仅能用来访问资源，还能进行解析操作，但也只能作用于`Java`类库知道如何解析的协议，如：`http`、`https`、`ftp`、`file`、`jar`、`zip`等

- `URLDecoder`：`URL`解码器

- `URLEncoder`：`URL`编码器

- 编码器的编码规则如下：
  - 保留字符`A`到`Z`、`a`到`z`、`0`到`9`、`.`（点）`-`（短连接线）`~`（波浪号）`_`（下划线）
  - 用`+`字符替换所有的空格
  - 其他字符使用字符编码`UTF-8`，并将每个字符都编码为`%`后面紧跟一个两位的十六进制数字


> #### URI、URL、URN
>
> `RFC2396`和`RFC2732`中定义了`URI`(`Uniform Resource Identifier`). `URI`是满足特定格式的字串,  进一步可以分作 `URL(Uniform Resource Locator)`和`URN(Uniform Resource Name)`
>
> 但在`RFC3986`的声明中，我们已经没必要把`URI、URL、URN`这三者分的太细：
>
> > Future specifications and related documentation should use the general
> > term “URI” rather than the more restrictive terms
> > “URL” and “URN”
>
> 一般的`URI`格式如下：
>
> ```http
> [scheme:]scheme-specific-part[#fragment] 
> ```
>
> 其中，如果一个`URI`带`[scheme:]`，则这个`URI`叫绝对`URI`（`absolute URI`）
>
> 其中，`absolute URI`中`scheme-specific-part`部分不是以`/`开头则称为`opaque URI`
>
> 下面这些链接都可以叫`URI`：
>
> ```http
> http://java.oracle.com
> // absolute URI
> file:///C:/jdk-11.0.9_doc-all/docs/api/java.base/java/net/URI.html	
> // opaque URI
> mailto:java-net@java.sun.com
> // opaque URI
> news:comp.lang.java
> ```
>
> `URI`里面`scheme-specific-part`部分可以细分：
>
> ```http
> [//[user:password@]host[:port]][/]path[?query]
> ```
>
> 这也就是熟悉的`URL`格式！
>
> ```http
> [scheme:][//[user:password@]host[:port]][/]path[?query][#fragment] 
> ```
>
> 其中：`[//[user:password@]host[:port]]`这一部分可以组成验证部分（`authority`）,这里有一个举例说明各个部分
>
> ```http
> // 全的URI URL
> jdbc:mysql://root:root@localhost:3306/mysql?charsetEncoding=utf8#abc
> // 协议(scheme):jdbc:mysql
> // userinfo(username:password): root:root
> // host: localhost
> // port: 3306
> // path: /mysql
> // query: charsetEncoding=utf8
> // fragment: abc
> // authority: root:root@localhost:3306
> // schemeSpecificPart: //root:root@localhost:3306/mysql?charsetEncoding=utf8
> ```
>

##### URI

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

如果`URI`中带`.`、`..`这一类表示位置的字符，可以使用下面的`API`进行化简！

```java
public URI normalize();
```

##### URL

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

##### URL编码器解码器（URLDecoder、URLEncoder）

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

#### 网络IP协议族





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





 //下面是修改好的内容

### UDP

`UDP`（`User Datagram Protocol`，用户数据报协议）是一种无连接的、不可靠的传输层协议。它不保证数据的可靠交付，也不保证数据的顺序，但具有简单、快速的特点。`UDP`通常用于对实时性要求较高但对数据丢失不太敏感的应用，例如视频流、音频流、在线游戏等。和`UDP`相关的类主要有：`DatagramSocket`、`DatagramPacket`、`MulticastSocket`。

在`Java`中，可以通过 `DatagramSocket` 和 `DatagramPacket` 类来实现基于`UDP`的网络通信。其中：

-  `DatagramSocket` ：`UDP`的`Socket`，`UDP`的类设计中没有明显地区分服务端和客户端`Socket`，无论是服务端还是客户端基本通用 `DatagramSocket` 类
-  `DatagramPacket`：作为`UDP`客户端和服务端之间通信的`UDP`数据包实现，服务端和客户端都是通过相互发送 `DatagramPacket`来进行通信

`UDP`提供了一种组播的方法，即允许服务端将数据包发送到多个客户端，这类组播的实现主要靠`MulticastSocket`类实现：

- `MulticastSocket`：

#### UDP简单网络通信

基于`UDP`的网络通信我们需要完成以下的步骤：

1. 创建一个`UDP`服务器，并绑定`IP`和端口，监听客户端的`UDP`数据包。

   1. 使用`DatagramSocket` 可以创建一个`UDP`服务器，下面的方法可以帮助你绑定`IP`和端口，一般情况下，**我们只需要绑定端口即可，因为这样系统会使用通配符地址**，除非你的电脑上有多个网络接口（并且最好是物理网卡），并且你只希望使用这个网络接口进行收发数据包！

      ```java
      // 构造一个UDP Socket,并将其绑定到本地主机上的任何可用端口。使用通配符地址(0.0.0.0)作为绑定的IP地址
      public DatagramSocket() throws SocketException;
      // 绑定到指定的端口，使用通配符地址(0.0.0.0)作为绑定的IP地址
      public DatagramSocket(int port) throws SocketException;
      // 绑定到指定的本机地址(注意不是远程地址, 因为你的电脑上可能存在多张网卡，无论有线或者无线，并且他们可能都被使用，因此指定特定的网卡的上网地址将决定使用机器上的哪张网卡来接收UDP数据包)。
      // 绑定的端口必须在0到65535之间。如果提供的地址是0.0.0.0,那么套接字将绑定到内核的wildcard address（通配符地址）
      public DatagramSocket(int port, InetAddress laddr) throws SocketException;
      // 提供一个SocketAddress对象，一般是InetSocketAddress(IP+Port)形式, 参考DatagramSocket(int port, InetAddress laddr)
      public DatagramSocket(SocketAddress bindaddr) throws SocketException;
      // 该方法绑定一个IP地址和端口，DatagramSocket的所有构造方法都会调用这个方法来完成UDP的IP端口绑定
      // 注意，该方法一旦调用，bound属性就会被设置为true，就不能调用第二次，多次调用这个方法会抛出already bounded异常。因此一般这个方法我们不需要调用！！只需要使用构造器即可！
      public synchronized void bind(SocketAddress addr) throws SocketException;
      ```

   2. 使用

2. 创建一个`UDP`客户端，指定要连接的`UDP`服务器



#### UDP组播





#### UDP方法、参数







#### 应用层协议请求

应用层协议的`URL`请求在`Java`中也是

![image-20250612141026816](README/image-20250612141026816.png)



