package cn.argento.askia.filter.annotation;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "Filter4", urlPatterns = "/filterServletDo")
public class Filter4 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("filter4 doing...");
        chain.doFilter(req, resp);
        System.out.println("After ServletDid, filter4 doing...");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
