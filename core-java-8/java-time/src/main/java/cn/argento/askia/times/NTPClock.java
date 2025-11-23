package cn.argento.askia.times;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

/**
 * Clock时钟的自定义实现，参考{@link Clock}的文档
 * 基于NTP服务器创建的Clock时钟Demo
 *
 */
public class NTPClock extends Clock implements Serializable {

    private static class NTPClockBuilder{
        private ZoneId zone;
        private String timeServerUrl;
        private int port;
        private int timeout;

        private NTPClockBuilder() {
        }

        public NTPClockBuilder setZone(ZoneId zone) {
            this.zone = zone;
            return this;
        }

        public NTPClockBuilder setTimeServerUrl(String timeServerUrl) {
            this.timeServerUrl = timeServerUrl;
            return this;
        }

        public NTPClockBuilder setPort(int port) {
            this.port = port;
            return this;
        }

        public NTPClockBuilder setTimeout(int timeout) {
            if (timeout > 6000)
                throw new IllegalArgumentException("timeout 过大, 由于一般的NTP服务器性能都比较强，请将延迟秒数设置在6秒内！");
            this.timeout = timeout;
            return this;
        }
    }

    private static final long serialVersionUID = 5568246783764309693L;
    private volatile static NTPClock instance = null;


    // double checked lock
    public static NTPClock getInstance(NTPClockBuilder builder){
        if (instance == null) {
            synchronized (NTPClock.class){
                if (instance == null){
                    instance = new NTPClock(builder.timeServerUrl, builder.port, builder.zone, builder.timeout);
                }
            }
        }
        return instance;
    }


    /**
     * 创建一个默认的 NTPClockBuilder来构建参数
     * @return
     */
    public static NTPClockBuilder defaultBuilder(){
        NTPClockBuilder ntpClockBuilder = new NTPClockBuilder();
        ntpClockBuilder.port = DEFAULT_TIME_SERVER_PORT;
        ntpClockBuilder.timeServerUrl = DEFAULT_TIME_SERVER_URL;
        ntpClockBuilder.timeout = DEFAULT_RECEIVE_MILLI_TIME_OUT;
        ntpClockBuilder.zone = ZoneId.systemDefault();
        return ntpClockBuilder;
    }

    // 网络编程环节
    public static void main(String[] args) {
        NTPClockBuilder ntpClockBuilder = NTPClock.defaultBuilder();
        ntpClockBuilder.setTimeServerUrl(TIME_SERVER_OS_APPLE);
        NTPClock instance = NTPClock.getInstance(ntpClockBuilder);
        Instant now = Instant.now(instance);
        System.out.println(now);
    }

    // 可用的NTP服务器源，注意部分可能随着时间推移而失效或者不对中国大陆开放
    // 参考：https://blog.csdn.net/lovelock2019/article/details/128299349
    public static final String TIME_SERVER_OS_WINDOWS = "time.windows.com";
    public static final String TIME_SERVER_OS_CENTOS = "centos.pool.ntp.org";
    public static final String TIME_SERVER_OS_RHEL = "rhel.pool.ntp.org";
    public static final String TIME_SERVER_OS_DEBIAN = "debian.pool.ntp.org";
    public static final String TIME_SERVER_OS_APPLE = "time.apple.com";


    public static final String TIME_SERVER_NTSC_AC_CN = "ntp.ntsc.ac.cn";
    public static final String TIME_SERVER_NIM_1_AC_CN = "ntp1.nim.ac.cn";
    public static final String TIME_SERVER_NIM_2_AC_CN = "ntp2.nim.ac.cn";
    public static final String TIME_SERVER_JST_MFEED_JP = "ntp.jst.mfeed.ad.jp";
    public static final String TIME_SERVER_NICT_JP = "ntp.nict.jp";
    public static final String TIME_SERVER_KRISS_RE_KR = "time.kriss.re.kr";

    public static final String TIME_SERVER_ALIYUN_1 = "ntp1.aliyun.com";
    public static final String TIME_SERVER_ALIYUN = "ntp.aliyun.com";
    public static final String TIME_SERVER_ALIYUN_2 = "ntp2.aliyun.com";

    public static final String TIME_SERVER_TENCENT_1 = "ntp1.tencent.com";
    public static final String TIME_SERVER_TENCENT = "ntp.tencent.com";
    public static final String TIME_SERVER_TENCENT_2 = "ntp2.tencent.com";

    // port 13
    public static final String TIME_SERVER_NIST = "time.nist.gov";
    public static final String TIME_SERVER_NIST_A = "time-a.nist.gov";

    public static final String TIME_SERVER_CN_POOL_NTP = "cn.pool.ntp.org";
    public static final String TIME_SERVER_UBUNTU = "ntp.ubuntu.com";
    public static final String TIME_SERVER_POOL_NTP = "pool.ntp.org";

    public static final String TIME_SERVER_EDU_SJTU = "ntp.sjtu.edu.cn";
    public static final String TIME_SERVER_EDU_USTC = "time.ustc.edu.cn";
    // 北京邮电大学
    public static final String TIME_SERVER_EDU_BUPT = "s2c.time.edu.cn";
    // 清华大学
    public static final String TIME_SERVER_EDU_THU = "s2a.time.edu.cn";
    public static final String TIME_SERVER_EDU_TSINGHUA = "ntp.tuna.tsinghua.edu.cn";
    public static final String TIME_SERVER_EDU_FUDAN = "ntp.fudan.edu.cn";
    public static final String TIME_SERVER_EDU_NORTH_EAST_NETWORK_CENTER = "s2f.time.edu.cn";
    public static final String TIME_SERVER_EDU_EAST_NETWORK_CENTER = "s2g.time.edu.cn";
    public static final String TIME_SERVER_EDU_GUILIN_MASTER = "s2k.time.edu.cn";





    public static final int DEFAULT_RECEIVE_MILLI_TIME_OUT = 500;
    public static final String DEFAULT_TIME_SERVER_URL = TIME_SERVER_OS_WINDOWS;
    public static final int DEFAULT_TIME_SERVER_PORT = 123;

    private final ZoneId zone;
    private final String timeServerUrl;
    private final int port;
    private final int timeout;

    private final NTPUDPClient timeClient;

    private final InetAddress timeServerAddress;


    protected NTPClock(String timeServerUrl, int port, ZoneId zone, int timeout){
        this.zone = zone;
        this.timeServerUrl = timeServerUrl;
        this.port = port;
        this.timeout = timeout;

        timeClient = new NTPUDPClient();

        try {
            timeServerAddress = InetAddress.getByName(this.timeServerUrl);
        } catch (UnknownHostException e) {
            // 地址无法访问
            throw new DateTimeException("未知Host：" + this.timeServerUrl + ", 请确保NTP服务器地址可访问！即将返回系统UTC时间...", e);
        }
        timeClient.setDefaultTimeout(this.timeout);
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        if (zone.equals(this.zone)) {  // intentional NPE
            return this;
        }
        return new NTPClock(timeServerUrl, port, zone, timeout);
    }

    @Override
    public Instant instant() {
        TimeInfo timeInfo;
        try {
            timeInfo = timeClient.getTime(timeServerAddress, port);
            long instant = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            return Instant.ofEpochMilli(instant);
        } catch (IOException e) {
            String message = e.getCause() == null? e.getMessage():e.getCause().getMessage();
            throw new DateTimeException(message, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NTPClock)) return false;
        NTPClock ntpClock = (NTPClock) o;
        return Objects.equals(zone, ntpClock.zone) &&
                Objects.equals(timeServerUrl, ntpClock.timeServerUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), zone, timeServerUrl);
    }
}
