package services;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ServiceAnnotationProcessor {
    private static Map<String, Object> servicesMapper;

    static {
        servicesMapper = new HashMap<>();
        final String path = ServiceAnnotationProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        final Package servicePackage = ServiceAnnotationProcessor.class.getPackage();
        File services = new File(path + servicePackage.getName().replace('.', '\\') + "\\");
        final File[] listFiles = services.listFiles();
        assert listFiles != null;
        for (File service: listFiles){
            final String name = service.getName();
            final String packageName = servicePackage.getName();
            try {
                final Class<?> serviceClass = Class.forName(packageName + "." + name.replace(".class", ""));
                if (serviceClass.isAnnotationPresent(Service.class)){
                    final Method[] methods = serviceClass.getDeclaredMethods();
                    final Object serviceObj = serviceClass.newInstance();
                    for (Method m: methods){
                        final String methodName = m.getName();
                        servicesMapper.put(methodName.toUpperCase(), serviceObj);
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static String processor(String cmd){
        final Object services = servicesMapper.getOrDefault(cmd.toUpperCase(), cmd);
        if (services instanceof String){
            return (String) services;
        }
        final Optional<Method> first = Arrays.stream(services.getClass().getDeclaredMethods()).filter(method -> method.getName().equalsIgnoreCase(cmd)).findFirst();
        if (first.isPresent()){
            final Method method = first.get();
            try {
                final Object invoke = method.invoke(services);
                return invoke.toString();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return "请确认你的方法是否存在, 方法名为：" + cmd;
    }

    public static void main(String[] args) {
        System.out.println(ServiceAnnotationProcessor.processor("123"));
        System.out.println(ServiceAnnotationProcessor.processor("serverTimes"));
        System.out.println(ServiceAnnotationProcessor.processor("randomUUid"));
    }
}
