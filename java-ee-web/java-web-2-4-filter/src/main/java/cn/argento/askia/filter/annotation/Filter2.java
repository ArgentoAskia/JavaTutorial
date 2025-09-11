package cn.argento.askia.filter.annotation;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "Filter2", urlPatterns = "/filterServletDo")
public class Filter2 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("filter2 doing...");
        chain.doFilter(req, resp);
        System.out.println("After ServletDid, filter2 doing...");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
