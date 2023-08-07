package cn.argento.askia;

import org.junit.*;

/**
 * Demo for JUnit 4 Framework
 */
public class JUnit4TestDemo {


    @Before
    public void before(){
        System.out.println("@Before的方法会在每一个@Test注解方法前面执行....");
    }

    @After
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

    @BeforeClass
    public static void beforeClass(){
        System.out.println("@BeforeClass的方法会在所有@Test方法执行前执行，并且只会执行一次，并且要求方法是static的");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("@AfterClass的方法会在所有@Test方法执行完之后执行，并且只会执行一次，并且要求方法是static的");
    }
}
