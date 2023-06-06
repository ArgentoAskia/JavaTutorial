package cn.argentoaskia.proxy2.handlers;

import cn.argentoaskia.proxy2.services.AllChangersImplsServices;
import cn.argentoaskia.proxy2.services.AllChangersServices;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class AllChangersHandler implements InvocationHandler {
    private AllChangersServices services;

    // 解决无法直接调用的问题
    private HashMap<String, Method> serviceMethodsMapping;

    public AllChangersHandler(){
        services = new AllChangersServices();
        serviceMethodsMapping = new HashMap<>();
        Method[] methods = services.getClass().getMethods();
        Object
        for (Method method :
                methods) {
            String name = method.getName();
            serviceMethodsMapping.put(name, method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("====================== AllChangersHandler ======================");
        // 无法调用这行语句，抛StackOverflowError
        // System.out.println(proxy);
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
        // 因为 AllChangersServices不实现任何的代理接口，所以直接调用会抛出java.lang.IllegalArgumentException: object is not an instance of declaring clas
        // Object invoke = method.invoke(services, args);
        Method serviceObjMethod = serviceMethodsMapping.get(method.getName());
        Object invoke = serviceObjMethod.invoke(services, args);
        System.out.println("调用的返回值：" + invoke);
        System.out.println("=====================================================================\n");
        return invoke;
    }

}
