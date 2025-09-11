package cn.argento.askia.servlet.impl;

import cn.argento.askia.servlet.WebHttpServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WebHttpServletImpl1 extends HttpServlet implements WebHttpServlet {
    @Override
    public void commonService(HttpServletRequest request, HttpServletResponse response, HttpSession session, ServletContext servletContext) {
        System.out.println("WebHttpServletImpl1");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        commonService(req, resp, req.getSession(), req.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
