package cn.argento.askia.times;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Locale;

/**
 * 使用LocalDate：表示区域日期，如： 2007-12-03
 * 使用LocalDateTime：表示区域日期时间，如：2007-12-03T10:15:30
 * 使用LocalTime：表示区域时间，如：10:15:30
 * 使用OffsetTime：表示带偏移的时间类型(UTC偏移)，如：10:15:30+01:00
 * 使用OffsetDateTime：代表带偏移的日期时间类型 ,如：2007-12-03T10:15:30+01:00
 * 使用ZonedDateTime: 代表带时区的偏移日期类型, 如：2007-12-03T10:15:30+01:00 Europe/Paris
 */
public class DatesTimesDemo {
    public static void main(String[] args) {
        // 1.使用now方法来获取当前时间(可以传递当前时区ID、Clock对象等)
        LocalDate nowLocalDate = LocalDate.now();
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalTime nowLocalTime = LocalTime.now();
        OffsetTime nowOffsetTime = OffsetTime.now();
        OffsetDateTime nowOffsetDateTime = OffsetDateTime.now();
        ZonedDateTime nowZonedDateTime = ZonedDateTime.now();


        // 2.可以使用parse方法来讲一个字符串转为时间，也可以指定格式
        LocalDate parseLocalDate = LocalDate.parse("2007-12-03");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parseLocalDateTime = LocalDateTime.parse("2008-12-12 02:23:59", dateTimeFormatter);

        // 3.使用of()来直接创建
        OffsetDateTime ofOffsetDateTime = OffsetDateTime.of(2000, 2, 13, 12, 23, 34, 0, ZoneOffset.UTC);

        // 4.使用from来将一个时间换成另外一个时间
        LocalDate fromLocalDate = LocalDate.from(nowLocalDateTime);


        // 5.所有的时间类的方法大概都能分成下面的几类：
        // getXXX()方法，用于获取字段值
        // plus()方法，用于进行时间相加
        // min()方法，用于进行时间减法
        // to()方法，用于进行时间单位转换
        // atXX()方法，用于进行时间单位部分转换
        // isXXX()方法，用于判别！
        // withXXX(),用于调整日期时间，相当于set()方法，使用该方法
        // 可以计算类似于“这个月的最后一天”和“下周三”，这些内容
        // 需要接口TemporalAdjuster和伴随类TemporalAdjusters，该伴随类提供了很多静态方法用于这些计算！
        LocalDate localDate = fromLocalDate.plusDays(2);
        System.out.println(fromLocalDate + " 加上2天：" + localDate);
        int hour = nowOffsetDateTime.getHour();
        System.out.println(nowOffsetDateTime + " 的小时是：" + hour);
        ZonedDateTime zonedDateTime = nowZonedDateTime.minusDays(2);
        System.out.println(nowZonedDateTime + "减去2天：" + zonedDateTime);
        long l = nowLocalDate.toEpochDay();
        System.out.println(nowLocalDate + "转为EpochDay（自1970.1.1到现在）有多少天：" + l);
        LocalDateTime localDateTime = nowLocalDate.atTime(2, 30);
        System.out.println(nowLocalDate + "使用atTime()方法局部添加时间：" + localDateTime);
        boolean leapYear = fromLocalDate.isLeapYear();
        System.out.println(fromLocalDate + "是否是闰年？" + leapYear);
        LocalDate with = fromLocalDate.with(Month.JUNE);
        System.out.println(fromLocalDate + "设置为6月：" + with);
        // 求2033年的最后一天
        LocalDate with1 = fromLocalDate.with(ChronoField.YEAR, 2033).with(TemporalAdjusters.lastDayOfYear());
        System.out.println("2033年的最后一天是星期几：" + with1.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
        System.out.println("2033年的最后一天是：" + with1);
    }
}
