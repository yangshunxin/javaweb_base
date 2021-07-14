package org.example;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author yangshunxin
 * @create 2021-07-09-16:52
 */
public class SimpleMail {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
        //简单邮件：只发送纯文本，没有附件和图片
        // 复杂邮件：有附件和图片

        // 发送一个简单的邮件

        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com"); // 设置QQ邮箱服务器
        prop.setProperty("mail.transport.protocol", "smtp"); //邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 设置验证用户名和密码

        // QQ邮箱特有的，
        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 使用javaMail 发送邮件的五个步骤；


        // 创建定义整个应用程序所需的环境信息的Session 对象

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 发件人邮箱用户名和授权码
                return new PasswordAuthentication("12345@qq.com", "授权码");
            }
        });

        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);


        // 2. 通过session得到transport对象
        Transport ts = session.getTransport();

        // 3. 使用邮箱的用户和授权码连上邮件服务器
        ts.connect("smtp.qq.com", "发送人的邮箱@qq.com", "授权码");

        //4. 创建邮件

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);



        // 指明邮件的发件人
        message.setFrom(new InternetAddress("发件人的邮箱@qq.com"));


        // 指明邮件的发件人，现在发件人和收件人是一样的，那就是自己发自己
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("123456@qq.com"));


        // 4. 邮件主题
        message.setSubject("只包含文本的简单邮件,通过java发送");
        // 设置邮件内容
        message.setContent("<h1 style='color:red'>你好呀</h1>", "text/html;charset=UTF-8");


        // 5. 发送邮件
        ts.sendMessage(message, message.getAllRecipients());

        ts.close();
    }
}
