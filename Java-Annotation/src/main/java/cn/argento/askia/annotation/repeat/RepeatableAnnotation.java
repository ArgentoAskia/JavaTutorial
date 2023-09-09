package cn.argento.askia.annotation.repeat;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(RepeatableAnnotation.RepeatableAnnotations.class)
public @interface RepeatableAnnotation {

    String description() default "";

    @Documented
    @Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface RepeatableAnnotations{
        RepeatableAnnotation[] value() default {};
    }
}
