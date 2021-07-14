package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.pojo.Person;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yangshunxin
 * @create 2021-07-06-14:41
 */
public class Session extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决中文乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");

        // 得到session,
        // 浏览器每次请求都会生成要给ID, 并把ID放到cookie中传过来
        // 每个浏览器都有一个唯一的ID
        HttpSession session = req.getSession();

        // 存字符串
//        session.setAttribute("name", "杨顺新");

        //存 对象
        session.setAttribute("name", new Person("杨顺新", 18));

        // 获取sessionID
        String sessonId = session.getId();

        // 判断session是不是新创建的
        if(session.isNew()){
            resp.getWriter().write("session创建成功,ID:"+sessonId);
        }else {
            resp.getWriter().write("session已经在服务器存在了, ID:"+sessonId);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
