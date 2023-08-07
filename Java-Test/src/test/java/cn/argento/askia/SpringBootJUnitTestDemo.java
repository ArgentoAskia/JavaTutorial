package cn.argento.askia;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// 默认SpringBoot整合的测试框架是JUnit 5
@SpringBootTest
public class SpringBootJUnitTestDemo {
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
