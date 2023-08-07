package cn.argento.askia;


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
