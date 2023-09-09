package cn.argento.askia.annotation.full;

import java.lang.annotation.*;

@Target({ElementType.PACKAGE, ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.LOCAL_VARIABLE, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.TYPE,
        ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Common {
}
