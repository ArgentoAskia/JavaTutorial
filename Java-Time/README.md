## Java Time API

在`Java`中早期，表示时间常用`Date`、`Calendar`、`TimeZone`这三个类，这些类在`java.utils`包下，由于历史原因，数据库编程用到的`Time`、`Date`、`Timestamp`也是基于这些类来制作。

但是这些类处理时间都过于简单，`JDK 8`以后，引入了`java.time`包来专门处理时间：

- `java.time`包：新的`Java`日期/时间`API`的基础包，内部包括了很多

- `java.time.chrono`为非`ISO`的日历系统定义了一些泛用的`API`（如日本帝国历、佛历、民国历等）
- `java.time.format`：格式化和解析日期时间对象的类

- `java.time.temporal`：包含一些时态对象还有各类时间操作接口，可以用其找出关于日期/时间对象的某个特定日期或时间
- `java.time.zone`：包含支持不同时区以及相关规则的类

### 相关时间概念

- 时间戳、时间线、时刻：

- 日期：
- 时间：
- 时区：
- 时区时间偏移：
- `UTC`：

### Time包核心时间类及其API

在新的`API`中：

- 使用`Instant`类表示时间戳
- 使用`LocalDate`代表区域日期、`LocalTime`代表区域时间、`LocalDateTime`代表区域日期时间、`ZonedDateTime`代表特定时区日期时间、`OffsetDateTime`代表`UTC`偏移日期时间、`OffsetTime`代表`UTC`偏移时间
- 还支持一些单个元素的类，如：`DayOfWeek`、`Month`、`MonthDay`、`Year`、`YearMonth`、`ZoneId`、`ZoneOffset`等
- 一些表示时间粒度的枚举常量，如：`ChronoUnit`、`ChronoField`
- 用于支持日期时间加减的类：`Duration`、`Period`

#### Time.temporal包顶层接口





#### 时间表示API

##### Instant

##### LocalDate

##### LocalTime

##### LocalDateTime

##### ZonedDateTime

##### OffsetDateTime

##### OffsetTime

#### 时间单位API和粒度常量

##### DayOfWeek

##### Month

##### MonthDay

##### Year

##### YearMonth

##### ZoneId

##### ZoneOffset

##### ChronoUnit

##### ChronoField

#### 时间计算API

##### Duration

##### Period

#### 时钟Clock

`Clock`类 `NTP`协议、NET服务器











### 时间格式化类



### 其他非标准日历

