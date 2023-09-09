package cn.argento.askia.annotation.repeat;


import java.lang.annotation.Native;

@RepeatableAnnotation(description = "class")
//@RepeatableAnnotation
public class Father {

    @RepeatableAnnotation(description = "field1")
    @RepeatableAnnotation(description = "field2")
    @RepeatableAnnotation(description = "field3")
    @Native
    private String name;

    @RepeatableAnnotation(description = "constructor1")
    @RepeatableAnnotation(description = "constructor2")
    @RepeatableAnnotation(description = "constructor3")
    @RepeatableAnnotation(description = "constructor4")
    public Father() {
        this.name = "Askia";
    }

    public Father(String name) {
        this.name = name;
    }

    @RepeatableAnnotation(description = "method1")
    @RepeatableAnnotation(description = "method2")
    public String getName() {
        return name;
    }

    @Deprecated
    public Father setName(String name) {
        this.name = name;
        return this;
    }
}
