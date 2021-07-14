package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author yangshunxin
 * @create 2021-07-06-11:23
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 处理接收乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password =req.getParameter("password");
        String[] hobbys = req.getParameterValues("hobbys");
        System.out.println("======================");
        System.out.println(username);
        System.out.println(password);
        System.out.println(hobbys);
        System.out.println("======================");
        // 通过请求转发
        System.out.println(req.getContextPath());
//        转发不用带路径  /代表了当前web应用
//        req.getRequestDispatcher(req.getContextPath()+"/success.jsp").forward(req, resp);
        req.getRequestDispatcher("/success.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
