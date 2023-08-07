package cn.argento.askia;

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
    @Autowired
    private LocalDateTime localDateTime;

    @Autowired
    private LocalDate localDate;

    @Autowired
    private LocalTime localTime;

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
