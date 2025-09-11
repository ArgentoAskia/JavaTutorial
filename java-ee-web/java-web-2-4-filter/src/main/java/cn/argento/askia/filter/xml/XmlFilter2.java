package cn.argento.askia.filter.xml;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "XmlFilter2", urlPatterns = "/XmlFilterServlet")
public class XmlFilter2 extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("XmlFilter2 doing...");
        chain.doFilter(req, res);
        System.out.println("After ServletDid, XmlFilter2 doing...");
    }
}
