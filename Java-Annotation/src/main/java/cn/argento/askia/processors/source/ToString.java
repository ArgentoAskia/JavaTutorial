package cn.argento.askia.processors.source;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

@Target({TYPE})
@Retention(SOURCE)
public @interface ToString {
    boolean includeClassName() default true;
    String delimiter() default ", ";
    boolean ignoreFieldName() default false;
    boolean appendHashCode() default false;
}
