package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pojo.User;
import org.example.util.Sendmail;

import java.io.IOException;

/**
 * @author yangshunxin
 * @create 2021-07-14-15:25
 */
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("pwd");
        String email=req.getParameter("email");

        System.out.println(username);
        System.out.println(password);
        System.out.println(email);

//        User user=new User(username,password,email);
//        Sendmail send=new Sendmail(user);
//        send.start();
//        System.out.println("success");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
