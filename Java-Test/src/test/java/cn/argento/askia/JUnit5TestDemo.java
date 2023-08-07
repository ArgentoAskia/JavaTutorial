package cn.argento.askia;


import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

/**
 * Demo for JUnit 5 Framework
 */
//  Junit 5 支持Tag标记该测试用来测试什么
@Tag("Junit-5-Test")
@Tag("Demo")
public class JUnit5TestDemo {


    @BeforeEach
    public void before(){
        System.out.println("@Before的方法会在每一个@Test注解方法前面执行....");
    }

    @AfterEach
    public void after(){
        System.out.println("@After的方法会在每一个@Test注解方法之后执行....");
    }

    @Test
    public void testFunc1(){
        System.out.println("running Func1...");
    }

    @Test
    public void testFunc2(){
        System.out.println("running Func2...");
    }

    /**
     * Junit 5支持重复测试！
     */
    @Tag("repeated测试！")
    @RepeatedTest(3)
    public void repeatedTestFunc(){
        System.out.println("running repeatedTestFunc with 3 times...");
    }

    /**
     * Junit 5支持超时测试！
     */
    @Tag("http超时测试")
    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void apiTimeoutTest() {
        System.out.println("模拟发送http请求响应超时过程...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void beforeClass(){
        System.out.println("@BeforeClass的方法会在所有@Test方法执行前执行，并且只会执行一次，并且要求方法是static的");
    }

    @AfterAll
    public static void afterClass(){
        System.out.println("@AfterClass的方法会在所有@Test方法执行完之后执行，并且只会执行一次，并且要求方法是static的");
    }
}
