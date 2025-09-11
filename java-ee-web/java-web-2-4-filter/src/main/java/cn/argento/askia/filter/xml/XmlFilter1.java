package cn.argento.askia.filter.xml;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// HttpFilter在Servlet 4.0才支持，也就是需要Tomcat 10版本以上
@WebFilter(filterName = "XmlFilter1", urlPatterns = "/XmlFilterServlet")
public class XmlFilter1 extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("XmlFilter1 doing...");
        chain.doFilter(req, res);
        System.out.println("After ServletDid, XmlFilter1 doing...");
    }
}
