package cn.argento.askia.processors.source.api;


import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.LOCAL_VARIABLE, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.TYPE, ElementType.TYPE_PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface API {
    String id() default "";
    String name() default "";
    String version() default "";
}
