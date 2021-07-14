package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      1.获取文件的路径
//        String realPath = this.getServletContext().getRealPath("/a.jpeg"); // 拿到的时Tomcat的路径
        String realPath = "E:\\ppk_proj\\dukang\\javaweb-maven-only\\response\\src\\main\\resources\\杨顺新.jpeg";
        System.out.println("文件路径为:"+realPath);
//      2.下载的文件是啥
        // 截取最后一个下划线后的内容
        String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);

//      3.设置想办法让浏览器能够支持下载我们需要的东西
//        resp.setHeader("Content-Disposition","attachment;filename="+fileName);
        //解决中文名字乱码
        resp.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));

//      4.获取下载文件的输入流
        FileInputStream fileInputStream = new FileInputStream(realPath);
//      5.创建缓冲区
        int len = 0;
        byte[] buffer = new byte[1024];
//      6.获取OutputStream对象
        ServletOutputStream out = resp.getOutputStream();
//      7.将FileOutputStream流写入到buffer缓冲区
        while ((len = fileInputStream.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
//      8.使用outputStream将缓冲区中的数据输出到客户端！
        fileInputStream.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
