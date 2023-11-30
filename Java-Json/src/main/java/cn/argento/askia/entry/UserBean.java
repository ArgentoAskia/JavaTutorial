package cn.argento.askia.entry;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class UserBean {
    // 数字
    private int number;
    // 字符串
    protected String address;
    protected String name;
    // 不转json的字段
    private transient int age;
    // 逻辑值
    boolean isGrow;
    // 数组
    double[] flag;
    // 对象
    @JavaBean
    public Country country;

    public List<String> skillList;
    // null
    private InetAddress nullObj;

    public List<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<String> skillList) {
        this.skillList = skillList;
    }

    public UserBean(int number, String address, String name, int age, boolean isGrow, double[] flag, Country country, List<String> skillList) {
        this.number = number;
        this.address = address;
        this.name = name;
        this.age = age;
        this.isGrow = isGrow;
        this.flag = flag;
        this.country = country;
        this.skillList = skillList;
    }

    public UserBean() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGrow() {
        return isGrow;
    }

    public void setGrow(boolean grow) {
        isGrow = grow;
    }

    public double[] getFlag() {
        return flag;
    }

    public void setFlag(double[] flag) {
        this.flag = flag;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserBean{");
        sb.append("number=").append(number);
        sb.append(", address='").append(address).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", isGrow=").append(isGrow);
        sb.append(", flag=");
        if (flag == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < flag.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(flag[i]);
            sb.append(']');
        }
        sb.append(", country=").append(country);
        sb.append(", skillList=").append(skillList);
        sb.append(", nullObj=").append(nullObj);
        sb.append('}');
        return sb.toString();
    }

    public static UserBean newBean(){
//        String[] countries = new String[]{"美国", "中国", "日本", "新加坡", "俄罗斯", "白俄罗斯", "缅甸", "蒙古", "墨西哥"};
        Random random = new Random();
        UserBean userBean = new UserBean();
        userBean.setAddress("XXX省XXX市XXX镇XXXXX");
        userBean.setAge(random.nextInt(100));
        Country country = new Country();
        country.setNow(LocalDateTime.now());
        country.setZoneId(ZoneId.systemDefault());
        country.setCountryName(ZoneId.systemDefault().getId());
        userBean.setCountry(country);
        userBean.setFlag(new double[]{2.5f, 3.6f, 4.8f, 5.1f, 3.9f});
        userBean.setGrow(random.nextBoolean());
        userBean.setName(UUID.randomUUID().toString());
        userBean.setNumber(random.nextInt(300));
        userBean.setSkillList(new ArrayList<>(Arrays.asList("C++", "Java", "C")));
        return userBean;
    }
}
