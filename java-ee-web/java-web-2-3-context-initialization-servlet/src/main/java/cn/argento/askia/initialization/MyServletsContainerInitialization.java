package cn.argento.askia.initialization;

import cn.argento.askia.servlet.ContextInitializationServlet;
import cn.argento.askia.servlet.WebHttpServlet;
import cn.argento.askia.servlet.WebHttpServlet2;

import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

@HandlesTypes({WebHttpServlet.class, WebHttpServlet2.class})
public class MyServletsContainerInitialization implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.print("扫描出来的Servlet: ");
        c.forEach(System.out::println);
        System.out.println("我们需要单独在这里注册的Servlet: " + ContextInitializationServlet.class);
        ContextInitializationServlet contextInitializationServlet = new ContextInitializationServlet();
        System.out.println("使用ServletContext进行注册...");
        System.out.println("正在注册" + contextInitializationServlet.getClass().getName() +"..., urlPatterns为：" + ctx.getContextPath() + "/contextInitializationServlet");
        ctx.addServlet("contextInitializationServlet", contextInitializationServlet)
                .addMapping("/contextInitializationServlet");
        for(Class<?> cl : c){
            System.out.println("正在注册"+ cl.getName() +"..., urlPatterns为：" + ctx.getContextPath() + "/" +cl.getSimpleName());
            ctx.addServlet(cl.getSimpleName(), (Class<? extends Servlet>)cl).addMapping("/" + cl.getSimpleName());
        }
        System.out.println("注册完成...");
    }
}
