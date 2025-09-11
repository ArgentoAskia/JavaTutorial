package cn.argento.askia.entry;

import java.time.LocalDateTime;
import java.time.ZoneId;

@JavaBean
public class Country {
    @JavaBean
    private String countryName;
    private ZoneId zoneId;
    private LocalDateTime now;

    public Country() {
    }

    public Country(String countryName, ZoneId zoneId, LocalDateTime now) {
        this.countryName = countryName;
        this.zoneId = zoneId;
        this.now = now;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Country{");
        sb.append("countryName='").append(countryName).append('\'');
        sb.append(", zoneId=").append(zoneId);
        sb.append(", now=").append(now);
        sb.append('}');
        return sb.toString();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }
}
