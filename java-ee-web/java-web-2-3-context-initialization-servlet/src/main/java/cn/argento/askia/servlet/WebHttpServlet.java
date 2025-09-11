package cn.argento.askia.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface WebHttpServlet {
    void commonService(HttpServletRequest request, HttpServletResponse response,
                       HttpSession session, ServletContext servletContext);

}
