package cn.argento.askia.annotation.inherited;


import cn.argento.askia.annotation.define.BugReport;

@InheritedAnnotation(name = "Askia3")
public class Father {
    private String name;

    public Father() {
        this.name = "Askia";
    }

    public Father(String name) {
        this.name = name;
        test("1", "2", "3", "4", "5");
        test("1", "2", "3");
    }

    // 可变参数 ==> 数组（java）
    // 遇到可变参数都要加上@SafeVarargs
    @SafeVarargs
    public final void test(String... name){
    }

    @InheritedAnnotation(name = "Askia2")
    @BugReport
    public String getName() {
        return name;
    }

    @InheritedAnnotation(name = "Askia2")
    @BugReport
    public Father setName(String name) {
        this.name = name;
        return this;
    }
}
