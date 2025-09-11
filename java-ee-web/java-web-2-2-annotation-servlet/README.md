### 基于注解的配置

1. 创建`JavaEE`项目，选择`Servlet`的版本**3.0**以上，可以不创建`web.xml`
2. 定义一个类，实现`Servlet`接口
3. 复写方法

```java
 public class ServletDemo1 implements Servlet{
  	...
  	  @Override
     public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
         System.out.println("Hello World!");
     }
     ...
  }
```

4. 在类上使用`@WebServlet`注解，进行配置

```java
   @WebServlet("资源路径")
```

   这个注解的源代码如下：

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {
	String name() default "";//相当于<Servlet-name>
	String[] value() default {};//代表urlPatterns()属性配置
	String[] urlPatterns() default {};//相当于<url-pattern>
	int loadOnStartup() default -1;//相当于<load-on-startup>
	WebInitParam[] initParams() default {};
	boolean asyncSupported() default false;
	String smallIcon() default "";
	String largeIcon() default "";
	String description() default "";
	String displayName() default "";
}
```

```java
@WebServlet(name = "demo1", urlPatterns = "/demo1")
 public class ServletDemo1 implements Servlet{
  	...
  	  @Override
     public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
         System.out.println("Hello World!");
     }
     ...
  }
```