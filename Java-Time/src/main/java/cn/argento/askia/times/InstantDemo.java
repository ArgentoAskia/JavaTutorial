package cn.argento.askia.times;

import java.time.*;
import java.time.temporal.*;


public class InstantDemo {
    public static void main(String[] args) {
        System.out.println("==================== Instant Demo ====================");
        System.out.println("1.创建Instant对象：");
        // 1.使用now()、parse()、ofEpochSecond()、ofEpochMilli()来创建Instant对象
        // ofEpochSecond()的纳秒字段默认是0，也就是整点
        Instant instant = Instant.ofEpochSecond(1533560248);
        Instant instant2 = Instant.ofEpochMilli(1213510241050L);
        Instant now = Instant.now();
        // 必须是这个格式！
        Instant parse = Instant.parse("2007-12-03T10:15:30.00Z");
        System.out.println("使用ofEpochSecond(long epochSecond),提供自1970.1.1到特定时间的秒数来创建：" + instant);
        System.out.println("使用ofEpochMilli(long epochMilli),提供自1970.1.1到特定时间的毫秒数来创建：" + instant2);
        System.out.println("使用now()获取当前UTC时间戳：" + now);
        System.out.println("使用parse()提供yyyy-MM-ddThh:mm:ss.xxZ的时间格式字符串来创建：" + parse);
        System.out.println();

        System.out.println("2另外一种提供偏移时钟来获取当前时间的创建方式：");
        // 2.另外的创建方法
        // 调用now()方法时拿到的时间不是当前时间，而是UTC时间，上海在UTC时间+8个小时，、
        // 下面这两个会获取系统时钟Clock(也就是UTC时间的Clock)
        // system()方法指定时区和systemDefaultZone()都会验证时区是否相同，但是还是获取的UTC时间,
        // System.out.println(Clock.systemDefaultZone());
        // System.out.println(Clock.systemUTC());
        // Clock.system(ZoneId.of("Asia/Shanghai"));
        // 所以要想拿到具体的时间一种方法试不使用系统时钟而是用偏移时钟，我们需要线拿到UTC时间
        Clock systemClock = Clock.systemDefaultZone();
        // 加上8个小时的偏移（UTC+8：00）
        Clock shanghaiClock = Clock.offset(systemClock, Duration.ofHours(8));
        // 下面两个代码等价
        System.out.println("shanghai now:" + shanghaiClock.instant());
        System.out.println("shanghai now:" + Instant.now(shanghaiClock));
        System.out.println();

        System.out.println("3.相关操作方法：");
        // 相关运算方法
        Instant plus = parse.plus(Duration.of(2, ChronoUnit.DAYS));
        Instant plus1 = now.plus(2, ChronoUnit.HOURS);
        System.out.println("2007-12-03 10:15:30 + 2 Days = " + plus);
        System.out.println("now + 2 HOURS = " + plus1);
        Instant minus = instant.minus(Duration.ofHours(2));
        System.out.println("2018-08-06 12:57:28 - 2 HOURS = " + minus);
        System.out.println();

        // 相关获取方法
        // 因为时间戳的表示方法不是人能读懂的，所以也不支持直接获取YEAR、MONTH这些
        // 下面这行代码将会抛出异常
        //  目前只支持：
        //      ChronoField.NANO_OF_SECOND（纳秒）、获取一个时间出去秒数部分剩余的毫秒数转为纳秒
        //      ChronoField.MICRO_OF_SECOND（微秒）、获取一个时间出去秒数部分剩余的毫秒数转为微秒
        //      ChronoField.MILLI_OF_SECOND（毫秒）、获取一个时间出去秒数部分剩余的毫秒数
        //      ChronoField.INSTANT_SECONDS（秒）获取一个时间的秒数部分
        // 1 second = 1000 milli second = 1_000_000 micro second = 1_000_000_000 nano second
        System.out.println("4.相关获取方法：");
        long aLong = instant2.getLong(ChronoField.MILLI_OF_SECOND);
        long aLong2 = instant2.getLong(ChronoField.MICRO_OF_SECOND);
//        instant.get();
        System.out.println("时间戳：1213510241050L剩余纳秒部分的毫秒表示：" + aLong);
        System.out.println("时间戳：1213510241050L剩余纳秒部分的微秒表示：" + aLong2);
        // 如果是使用ofEpochSecond()创建，则使用这个方法获取具体的秒数
        // 如果是使用ofEpochMilli()创建，则使用这个方法获取秒数，剩余的纳秒数请用getNano()
        // 相当于：instant2.getLong(ChronoField.INSTANT_SECONDS);
        long epochSecond = instant2.getEpochSecond();
        // 如果是使用ofEpochMilli()创建，则使用这个方法获取出去秒数之后剩余的纳秒数
        // 相当于：instant2.getLong(ChronoField.NANO_OF_SECOND);
        int nano = instant2.getNano();
        System.out.println("时间戳：1213510241050L秒数部分：" + epochSecond);
        System.out.println("时间戳：1213510241050L剩余纳秒部分:" + nano);

        // 判断字段的范围：支持的字段同上
        ValueRange range = instant.range(ChronoField.MILLI_OF_SECOND);
        System.out.println(range);
        System.out.println();

        // 相关判别方法
        System.out.println("5.相关判别方法：");
        boolean after = instant.isAfter(instant2);
        boolean before = instant.isBefore(instant2);
        System.out.println(instant + "is After " + instant2 + ": " + after);
        System.out.println(instant + "is before " + instant2 + ": " + before);
        System.out.println();

        // 相关转换方法
        // 将这个instant与一个UTC偏移量（如：UTC+9:00）组合以创建一个{@code OffsetDateTime}。
        // Combines this instant with an offset to create an {@code OffsetDateTime}.
        System.out.println("6.相关转换方法：");
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.UTC);
        System.out.println("转换成OffsetDateTime：" + offsetDateTime);
        // 转为ZoneDateTime
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.of("America/Los_Angeles"));
        System.out.println("转换成ZoneDateTime：" + zonedDateTime);

        String s = now.toString();
        System.out.println("转为字符串：" + s);
        long l = instant2.toEpochMilli();
        System.out.println("转为EpochMilli："+ l);
        // 用于省略时间
        // 如省略日期后面的时间单位
        Instant instant1 = now.truncatedTo(ChronoUnit.DAYS);
        // 省略小时后面的时间单位
        Instant instant3 = now.truncatedTo(ChronoUnit.HOURS);
        // 省略分钟后面的时间单位
        Instant instant4 = now.truncatedTo(ChronoUnit.SECONDS);
        System.out.println(now + " (省略日期后面的时间单位): " + instant1);
        System.out.println(now + " (省略日期后面的时间单位): " + instant3);
        System.out.println(now + " (省略日期后面的时间单位): " + instant4);

    }
}
