package cn.argento.askia.annotation.reasons;

import cn.argento.askia.annotation.full.BugReport;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 生成Proxy源代码
 */
public class BugReportProxy {
    public static void main(String[] args) {
//        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        final BugReport annotation = Myclass2.class.getAnnotation(BugReport.class);
        final String s = annotation.assignedTo();
        final Class<? extends BugReport> proxyClass = annotation.getClass();
        System.out.println(proxyClass);
        // 拿到InvocationHandler对象
        final Class<? extends Annotation> annotationClass = annotation.annotationType();
        System.out.println(annotationClass);
        final InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        System.out.println(invocationHandler.getClass());


    }
}
