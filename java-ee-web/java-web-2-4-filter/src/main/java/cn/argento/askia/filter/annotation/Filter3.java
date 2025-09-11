package cn.argento.askia.filter.annotation;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "Filter3", urlPatterns = "/filterServletDo")
public class Filter3 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("filter3 doing...");
        chain.doFilter(req, resp);
        System.out.println("After ServletDid, filter3 doing...");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
