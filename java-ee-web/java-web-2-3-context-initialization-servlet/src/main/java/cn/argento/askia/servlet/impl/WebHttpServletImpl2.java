package cn.argento.askia.servlet.impl;

import cn.argento.askia.servlet.WebHttpServlet2;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebHttpServletImpl2 extends WebHttpServlet2 {
    @Override
    public void commonService(HttpServletRequest request, HttpServletResponse response, HttpSession session, ServletContext servletContext) {
        System.out.println("WebHttpServletImpl2");
    }
}
