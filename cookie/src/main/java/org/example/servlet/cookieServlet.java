package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @author yangshunxin
 * @create 2021-07-06-13:37
 */
public class cookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 第一次登陆给客户端一个时间,下次登陆就显示上次登陆时间并给一个新的时间,
        //解决中文乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        PrintWriter out = resp.getWriter();
        // 解决乱码的问题
        resp.setHeader("Content-type", "text/html;charset=UTF-8");

        //Cookie 服务器从客户端获得
        Cookie[] cookies = req.getCookies(); // 这里返回数组,说明cookie可能有多个
        
        //判断Cookie是否存在
        if (cookies!=null){
            
            out.write("你上一次登陆的时间:");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                // 获取cookie的名字
                if(cookie.getName().equals("lastLoginTime")){
                    //获取cookie的值
                    long lastLoginTime = Long.parseLong(cookie.getValue());
                    Date date = new Date(lastLoginTime);
                    out.write(date.toLocaleString());
                }
            }
            
        }else {
            out.write("这是您第一次访问本站");
        }

        //服务给客户端响应一个cookie
        // 每次都更新时间
        Cookie cookie = new Cookie("lastLoginTime", String.valueOf(System.currentTimeMillis()));
        // 设置cookie的有效期 一天
        cookie.setMaxAge(24*60*60); // 单位时秒
        resp.addCookie(cookie);

        /*
        * 在网络传输字符串时,如果发送乱码,可以使用
        * URLEncoder.encode("", "utf-8");
        * URLDecoder.decode("", "utf-8");
        *
        * */


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
