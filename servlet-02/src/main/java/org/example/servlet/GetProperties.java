package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 后面写 properties 编译后项目的相对路径
        InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");

        Properties p = new Properties();
        p.load(in);

        String user = p.getProperty("username");
        String pwd = p.getProperty("password");

        resp.getWriter().print("user:"+user + " password:"+pwd);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
