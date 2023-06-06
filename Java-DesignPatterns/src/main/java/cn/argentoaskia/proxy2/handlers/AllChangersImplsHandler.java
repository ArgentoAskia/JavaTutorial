package cn.argentoaskia.proxy2.handlers;

import cn.argentoaskia.proxy2.interfaces.HexChanger;
import cn.argentoaskia.proxy2.services.AllChangersImplsServices;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AllChangersImplsHandler implements InvocationHandler {
    private AllChangersImplsServices services;

    public AllChangersImplsHandler(){
        services = new AllChangersImplsServices();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("====================== AllChangersImplsHandler ======================");
        System.out.println("当前代理类：" + proxy.getClass());
        System.out.println("当前调用方法：" + method);
        System.out.println("方法参数：" + Arrays.toString(args));
        Class<?>[] interfaces = proxy.getClass().getInterfaces();
        System.out.println("当前代理类实现了哪些接口：" + Arrays.toString(interfaces));
        Class<?>[] interfaces1 = services.getClass().getInterfaces();
        System.out.println("当前业务类实现了哪些接口：" + Arrays.toString(interfaces1));
        Class<?> declaringClass = method.getDeclaringClass();
        System.out.println("当前方法在哪个接口中被定义：" + declaringClass);
        System.out.println("方法调用中....");
        Object invoke = method.invoke(services, args);
        System.out.println("调用的返回值：" + invoke);
        System.out.println("=====================================================================\n");
        return invoke;
    }

}
