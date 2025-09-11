package cn.argento.askia.processors.runtime;


import cn.argento.askia.annotation.define.BugReport;
import cn.argento.askia.annotation.inherited.InheritedAnnotation;
import cn.argento.askia.annotation.repeatable.RepeatableAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;


public class AnnotationAPIs {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        final boolean declaredAnnotation = Bug2.class.isAnnotationPresent(RepeatableAnnotation.class);
        System.out.println(declaredAnnotation);
        final Method setBug = BugA.class.getDeclaredMethod("setBug", String.class);
        // 获取可重复的注解
        final RepeatableAnnotation[] annotationsByType = setBug.getAnnotationsByType(RepeatableAnnotation.class);
        // 获取BugReport
        final BugReport annotation = setBug.getAnnotation(BugReport.class);
        // 注意可重复注解的获取，标记的永远是注解容器
        // false
        System.out.println("setBug方法上是否标记了RepeatableAnnotation注解=" + setBug.isAnnotationPresent(RepeatableAnnotation.class));
        // true
        System.out.println("setBug方法上是否标记了RepeatableAnnotations注解容器=" + setBug.isAnnotationPresent(RepeatableAnnotation.RepeatableAnnotations.class));
        // 获取所有注解
        final Annotation[] annotations = setBug.getAnnotations();
        System.out.println("=============== 获取setBug‘s 可重复的注解 ===============");
        for (RepeatableAnnotation repeatableAnnotation:
             annotationsByType) {
            System.out.println(repeatableAnnotation);
        }
        System.out.println("====================================================");
        System.out.println();

        System.out.println("=============== 获取setBug‘s 上的BugReport注解 ===============");
        System.out.println(annotation);
        System.out.println("====================================================");
        System.out.println();

        System.out.println("=============== 获取setBug‘s 上的所有注解 ===============");
        System.out.println("注意，这种方式获取可重复注解时，获取的是承载注解的容器!!!");
        for (Annotation annotation1:
                annotations) {
            System.out.println(annotation1);
        }
        System.out.println("====================================================");
        System.out.println();


        // 获取直接注解
        final Annotation[] declaredAnnotations = Bug2.class.getDeclaredAnnotations();
        // 获取带继承的注解
        final Annotation[] annotations1 = Bug2.class.getAnnotations();
        System.out.println("=============== 获取直接注解 ===============");
        for (Annotation annotation1:
                declaredAnnotations) {
            System.out.println(annotation1);
        }
        System.out.println("====================================================");
        System.out.println();

        System.out.println("=============== 获取继承注解 ===============");
        for (Annotation annotation1:
                annotations1) {
            System.out.println(annotation1);
        }
        System.out.println("====================================================");
        System.out.println();


        System.out.println("也可以通过获取注解容器的形式获取可重复的注解");
        final RepeatableAnnotation.RepeatableAnnotations annotation1 = setBug.getAnnotation(RepeatableAnnotation.RepeatableAnnotations.class);
        final RepeatableAnnotation[] value = annotation1.value();
        System.out.println(Arrays.toString(value));
    }
}


@BugReport(2)
@InheritedAnnotation
class BugA{

    @RepeatableAnnotation
    @RepeatableAnnotation
    private String bug;

    @BugReport(2)
    @RepeatableAnnotation
    @RepeatableAnnotation
    public String getBug() {
        return bug;
    }

    @BugReport(2)
    @RepeatableAnnotation(description = "setBug method1")
    @RepeatableAnnotation(description = "setBug method2")
    public BugA setBug(String bug) {
        this.bug = bug;
        return this;
    }
}

@RepeatableAnnotation
@BugReport(3)
class Bug2 extends BugA{

}

