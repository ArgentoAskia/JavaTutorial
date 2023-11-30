package cn.argento.askia.JUL;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.function.Supplier;
import java.util.logging.*;

public class JULLogger4jLoggerLevelTest {
    private static Logger logger =  Logger.getLogger("cn.argento.askia.JUL.JULLogger4jLoggerLevelTest");
    private static final LogManager logManager = LogManager.getLogManager();
    private static final LoggingMXBean loggingMXBean = LogManager.getLoggingMXBean();
    public static void main(String[] args) {
//        System.out.println(loggingMXBean.getLoggerLevel("cn.argento.askia.JUL.JULLogger4jLoggerLevelTest"));
        logger.severe("severe级别日志...");
        logger.warning("warning级别日志...");
        logger.info("info级别日志...");
        logger.config("config级别日志...");
        logger.fine("fine级别日志...");
        logger.finer("finer级别日志...");
        logger.finest("finest级别日志...");

        final String name = logger.getName();
        final Level level = logger.getLevel();
        System.out.println(name);
        System.out.println(level);

        // isLoggable方法
        final boolean all = logger.isLoggable(Level.ALL);
        final boolean config = logger.isLoggable(Level.CONFIG);
        final boolean warning = logger.isLoggable(Level.WARNING);
        final boolean info = logger.isLoggable(Level.INFO);
        final boolean severe = logger.isLoggable(Level.SEVERE);
        final boolean fine = logger.isLoggable(Level.FINE);
        final boolean finer = logger.isLoggable(Level.FINER);
        final boolean finest = logger.isLoggable(Level.FINEST);
        final boolean off = logger.isLoggable(Level.OFF);

        // 通用log方法
        logger.log(Level.SEVERE, "severe级别日志1...");
        logger.log(Level.WARNING, "warning级别日志1...");
        logger.log(Level.INFO, "info级别日志1...");
        logger.log(Level.CONFIG, "config级别日志1...");
        logger.log(Level.FINE, "fine级别日志1...");
        logger.log(Level.FINER, "finer级别日志...");
        logger.log(Level.FINEST, "finest级别日志1...");

        // 比较麻烦的记录方式：
//        logger.log(Level.SEVERE, new Supplier<String>() {
//            @Override
//            public String get() {
//                return "null";
//            }
//        });

        // 带参数的记录
        logger.log(Level.SEVERE, "带单个参数的severe级别日志..., 参数：{0}", new Date());
        logger.log(Level.SEVERE, "带多个参数的severe级别日志..., 参数0：{0}, 参数1：{1}, 参数2：{2} ",
                new Object[]{"username=Askia", "password=Askia", "date=" + LocalDateTime.now()});
        // 带异常信息的日志
        logger.log(Level.SEVERE, "带异常的severe级别日志...", new NullPointerException());

        // 指定日志发生地和发生方法的日志
        logger.logp(Level.SEVERE, "123", "main","带异常的severe级别日志...");

        // FINER级别的Logger
        // 记录方法进入时的方法名称，参数等
        logger.entering("cn.argento.askia.JUL.JULLogger4jLoggerLevelTest", "static method[main]");
        logger.entering("cn.argento.askia.JUL.JULLogger4jLoggerLevelTest", "static method[main]", args.getClass().getComponentType());
        // 记录方法进入时的方法退出时的名称及返回值等
        logger.exiting("cn.argento.askia.JUL.JULLogger4jLoggerLevelTest", "static method[main]", Void.TYPE);
        // 记录方法进入时的方法抛出的异常
        logger.throwing("cn.argento.askia.JUL.JULLogger4jLoggerLevelTest", "static method[main]", new NullPointerException("void"));
    }
}
