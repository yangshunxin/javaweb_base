package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author yangshunxin
 * @create 2021-07-06-9:53
 */
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置浏览器3秒自动刷新一次
        resp.setHeader("refresh", "3");

        // 在内存中创建一个图片
        BufferedImage img = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);
        // 得到图片
        Graphics2D g = (Graphics2D)img.getGraphics(); // 得到画笔
        //设置图片的背景颜色
        g.setColor(Color.white);
        g.fillRect(0, 0, 80, 20);

        // 给图片写数据
        g.setColor(Color.BLUE);
        g.setFont(new Font(null, Font.BOLD, 20));
        g.drawString(makeRandomNum(), 0, 20);

        // 告诉浏览器,这个请求用图片的方式打开
        resp.setContentType("image/jpeg");
        //网络存在缓存,不让浏览器缓存
        resp.setDateHeader("expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        // 把图片写给浏览器
        ImageIO.write(img, "jpg", resp.getOutputStream());




    }

    //生成随机数
    private String makeRandomNum(){
        Random rd = new Random();
        String num = rd.nextInt(99999999)+"";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 7 - num.length(); i++){
            sb.append("0");
        }

        num = sb.toString() + num;
        return num;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
