package cn.argento.askia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;

@WebServlet(name = "AnnotationServlet",urlPatterns = "/annotationServlet")
public class AnnotationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入Annotation形式的注解");
        // 如果发现输出中文乱码，请加上-Dfile.encoding=UTF-8，检查tomcat的properties
    }

    public static void main(String[] args) {

    }
}
