package cn.argento.askia.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jLoggerLevelTest {
    private static final Logger logger = LogManager.getLogger("hello");
    public static void main(String[] args) {
        System.out.println(logger.getName());
        logger.isDebugEnabled();
        logger.isInfoEnabled();
        logger.isTraceEnabled();
        logger.debug("Level 1(lowest):" + Level.ALL.toString().toLowerCase() + "输出所有日志！");
        logger.trace("Level 1.5:" + Level.TRACE.toString().toLowerCase() + ",输出TRACE、DEBUG、INFO、WARN、EROR、FATAL级别日志！");
        logger.debug("Level 2:" + Level.DEBUG.toString().toLowerCase() + ",输出DEBUG、INFO、WARN、EROR、FATAL级别日志！");
        logger.info("Level 3:" + Level.INFO.toString().toLowerCase() + ",输出INFO、WARN、ERROR、FATAL级别日志！");
        logger.warn("Level 4:" + Level.WARN.toString().toLowerCase() + ",输出WARN、ERROR、FATAL级别日志！");
        logger.error("Level 5:" + Level.ERROR.toString().toLowerCase() + ",输出ERROR、FATAL级别日志！");
        logger.fatal("Level 6:" + Level.FATAL.toString().toLowerCase() + ",只输出FATAL级别日志");
        logger.fatal("Level 7(highest),关闭所有日志记录:" + Level.OFF);
        logger.error("特别注意Log4j官方只建议使用Level2-Level5这四个级别！他们分别代表：");
        logger.debug(Level.ALL.toString().toLowerCase() + "用于打开所有日志记录！");
        logger.debug(Level.DEBUG.toString().toLowerCase() + "：一般用于细粒度级别上，对调试应用程序非常有帮助！");
        logger.info(Level.INFO.toString().toLowerCase() + "：一般和在粗粒度级别上，强调应用程序的运行全程！");
        logger.warn(Level.WARN.toString().toLowerCase() + "：表明会出现潜在的错误情形！");
        logger.error(Level.ERROR.toString().toLowerCase() + "：指出虽然发生错误事件，但仍然不影响系统的继续运行！");
        logger.fatal(Level.FATAL.toString().toLowerCase() + "：指出每个严重的错误事件将会导致应用程序的退出！");
        logger.fatal(Level.OFF.toString().toLowerCase() + "：最高等级，用于关闭所有日志记录！");
    }
}
