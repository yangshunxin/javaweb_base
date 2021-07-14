package org.example;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author yangshunxin
 * @create 2021-07-09-16:52
 */
public class MailAttachment {
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

        //4. 创建邮件---复杂邮箱，带附件

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
//        MimeMessage mm = new MimeMessage(session);

        // 指明邮件的发件人
        message.setFrom(new InternetAddress("发件人的邮箱@qq.com"));


        // 指明邮件的发件人，现在发件人和收件人是一样的，那就是自己发自己
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("123456@qq.com"));


        // 4. 邮件主题
        message.setSubject("带图片的邮件");

        // 准备邮件数据

        // 准备附件数据
        MimeBodyPart body1= new MimeBodyPart();
        body1.setDataHandler(new DataHandler(new FileDataSource("D:\\Bert\\cmd.txt")));
        body1.setFileName("1.txt");

        // 准备图片数据
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("imagePath"));
        image.setDataHandler(dh);
        image.setContentID("bz.jpg"); // 设置图片名称， 设置一个id

        // 准备文本
        MimeBodyPart text = new MimeBodyPart();
        // 这里的cid是固定写法，后面的名称必须跟 setContentID 相同
        text.setContent("这是一封正文带图片<img src='cid:bz.jpg'>的邮件", "text/html;charset=UTF-8");


        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(body1); // 附件添加文件
        mm.addBodyPart(text);  // 附件添加文本
        mm.addBodyPart(image); // 附件添加图片
        // 纯文本：alternative  带有图片附件：related,  带有文件附件：mixed
        mm.setSubType("related");

        // 设置到消息中，保存修改
        message.setContent(mm);  // 把最后编辑好的 邮件放到消息中，
        message.saveChanges();

        // 设置邮件内容 --- 这是简单的邮件内容，上面是设置复杂的邮件内容
//        message.setContent("<h1 style='color:red'>你好呀</h1>", "text/html;charset=UTF-8");


        // 5. 发送邮件
        ts.sendMessage(message, message.getAllRecipients());

        ts.close();
    }
}
