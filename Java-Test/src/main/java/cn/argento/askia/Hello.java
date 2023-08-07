package cn.argento.askia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class Hello {


    @Bean
    public LocalDateTime now(){
        return LocalDateTime.now();
    }

    @Bean
    @Autowired
    public LocalDate nowDate(LocalDateTime localDateTime){
        return localDateTime.toLocalDate();
    }

    @Bean
    @Autowired
    public LocalTime nowTime(LocalDateTime localDateTime){
        return localDateTime.toLocalTime();
    }
}
