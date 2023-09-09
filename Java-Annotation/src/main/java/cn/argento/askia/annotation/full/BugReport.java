package cn.argento.askia.annotation.full;

import jdk.nashorn.internal.ir.annotations.Reference;

import java.lang.annotation.*;
import java.net.URL;

/**
 * 定义注解时可以编写的东西
 * @interface实际上也是一个类，因此可以在@interface定义枚举类型和嵌套类,甚至是@interface
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BugReport {
    long value() default 2000;
    String versions() default "1.1.0";
    int version() default 1;
    double subVersion() default 1.0;
    boolean showStopper() default false;
    String assignedTo() default "[none]";
    Class<?> testCase() default Void.class;
    Status status() default Status.CONFIRMED;
    Reference ref() default @Reference;
    String[] reportedBy() default "";
    Class<?>[] clazz() default {};

    enum Status {UNCONFIRMED, CONFIRMED, FIXED, NOTABUG}

    @interface Reference{
        String ref() default "";
        String url() default "";
    }
    interface B{

    }

    class A{
        public void test(){

        }
    }
}
