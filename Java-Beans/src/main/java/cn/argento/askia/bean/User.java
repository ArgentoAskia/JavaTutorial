package cn.argento.askia.bean;

import java.beans.ConstructorProperties;
import java.util.Random;
import java.util.UUID;

public class User {
    private String name;
    private Integer id;
    private String address;

    // 对应字段名和Getter方法
    @ConstructorProperties({"name", "id", "address"})
    public User(String name, Integer id, String address) {
        this.name = name;
        this.id = id;
        this.address = address;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("cn.argento.askia.bean.User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id=").append(id);
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }

    // getter
    public User getRandomUser(){
        Random random = new Random();
        final int id = random.nextInt(500);
        final String name = String.valueOf(random.nextLong());
        final String address = UUID.randomUUID().toString();
        return new User(name, id,address);
    }
    public static User setRandomUser(User user){
        Random random = new Random();
        final int id = random.nextInt(500);
        final String name = String.valueOf(random.nextLong());
        final String address = UUID.randomUUID().toString();
        user.setId(id);
        user.setName(name);
        user.setAddress(address);
        return user;
    }
}
