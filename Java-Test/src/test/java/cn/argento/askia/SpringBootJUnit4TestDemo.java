package cn.argento.askia;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// SpringRunner是SpringJUnit4ClassRunner的子类，需要JUnit 4.12以上版本使用
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class SpringBootJUnit4TestDemo {
    @Autowired
    public LocalDateTime localDateTime;

    @Autowired
    public LocalDate localDate;

    @Autowired
    public LocalTime localTime;

    @Autowired
    private Hello hello;



    @Test
    public void testPrint(){
        System.out.println(localDateTime);
        System.out.println(localDate);
        System.out.println(localTime);
        System.out.println(hello);
    }
}
