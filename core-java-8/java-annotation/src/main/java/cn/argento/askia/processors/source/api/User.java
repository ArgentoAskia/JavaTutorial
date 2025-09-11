package cn.argento.askia.processors.source.api;


import cn.argento.askia.processors.source.ToString;

@ToString(delimiter = ",\n")
public class User<@API T> {
    @API(name = "Field", version = "2.0")
    private String name;
    @API(name = "Field2", version = "3.0")
    private String address;
    @API(name = "Field3", version = "4.0")
    private int id;

    public String getName() {
        return name;
    }

    @API(name = "Method", version = "1.0")
    public User setName(String name) {
        this.name = name;
        return this;
    }

    @API(name = "Method", version = "2.0")
    public String getAddress() {
        return address;
    }


    public User setAddress(@API String address) throws @API Exception {
        this.address = address;
        return this;
    }

    @API
    public User() {
        @API String a = "123";
    }

    @API(version = "1.0", name = "constructor")
    public User(String name, String address, int id) {
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public int getId() throws @API Exception {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }
}
