## Java-Test
在`Java`中最常见的测试框架莫过于`JUnit`了！这个由`Kent Beck`和`Erich Gamma`建立的测试框架是`xUnit`家族中最成功的一个（实际上`XUnit`几乎为每一门语言都实现了一个测试框架，如`CppUnit`、`Pyunit`、`CUnit`等）

- 官网：https://junit.org
- 发布版本：
  - 5 ：截至目前的稳定版5.10.0， 更新于2023.7.23
  - 4 ：最后稳定版4.13.2, 更新于2021.2.13
  - 3 ：最后稳定版3.8.2, 更新于2007.5.14

- 主流使用还是以4和5为主！

### 基本使用

由于`4`版本和`5`版本的区别比较大，所以这里区分来讲解，所有的测试生命周期可以包括：

```
启动测试前准备 --> 
执行测试方法1前准备 --> 执行测试方法1 --> 执行测试方法1完成后工作 --> 
执行测试方法2前准备 --> 执行测试方法2 --> 
执行测试方法2完成后工作 --> 
...(如此循环直到没有测试方法) --> 
测试完成时工作
```

- 每个测试方法执行前后都会做一些准备和完成工作
- 进行测试之前和进行测试之后也会做一次准备和完成的工作，这个工作只会做一次

对应的注解：

- 启动测试前准备：`@BeforeClass（JUnit4）`、`@BeforeAll（JUnit 5）`
- 执行测试方法X前准备：`@Before（JUnit4）`、`@BeforeEach（JUnit 5）`
- 执行测试方法X：`@org.junit.Test（JUnit4）`、`@org.junit.jupiter.api.Test（JUnit 5）`
- 执行测试方法X后准备：`@After（JUnit4）`、`@AfterEach（JUnit 5）`
- 测试完成时工作：`@AfterClass（JUnit4）`、`@AfterAll（JUnit 5）`

可以参考`Demo`，特别注意`@Test`，`Junit5`和`Junit4`他们所在的包是不一样的

### Spring整合

整合`JUnit 4`：

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class SpringJUnit4TestDemo {

}
```

整合`JUnit 5`：

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringJUnitConfig(locations = "classpath:spring-context.xml")
//  或者
// @SpringJUnitConfig
// @ContextConfiguration("classpath:spring-context.xml")
// 或者
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:spring-context.xml")
public class SpringJUnit5TestDemo {
}
```

### SpringBoot整合

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
public class SpringBootJUnitTestDemo {

}
```

