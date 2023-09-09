package cn.argento.askia.annotation.inherited;

@InheritedAnnotation(name = "Askia3")
public class Father {
    private String name;

    public Father() {
        this.name = "Askia";
    }

    public Father(String name) {
        this.name = name;
    }

    @InheritedAnnotation(name = "Askia2")
    public String getName() {
        return name;
    }

    public Father setName(String name) {
        this.name = name;
        return this;
    }
}
