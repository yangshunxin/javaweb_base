package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author yangshunxin
 * @create 2021-07-09-13:55
 */
public class FIleUploadSerlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断 上传的文件是普通表单还是带文件的表单
        if (!ServletFileUpload.isMultipartContent((RequestContext) req)){
            return; // 终止方法运行，说明这是一个普通表单，直接返回

        }
        // 创建上传文件的保存路径，建议在WEB-INF路径下，安全，用户无法直接访问上传的文件
        String uploadPath = this.getServletContext().getRealPath("/WEB_INF/upload");
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()){
            uploadFile.mkdir();//创建这个目录
        }

        // 缓存，临时文件
        // 临时路径，假如文件超过了预期的大小，我们就把他放到一个临时文件中，过几天自动删除，或者提醒用户转存为永久
        String tmpPath = this.getServletContext().getRealPath("/WEB_INF/tmp");
        File tmpUploadFile = new File(tmpPath);
        if (!tmpUploadFile.exists()){
            uploadFile.mkdir();// 创建这个目录
        }

        // 处理上传文件，一般都需要通过流来获取，我们可以使用request.getInputStream(),原生态的文件上传流获取，十分麻烦
        // 但是我们可以使用Apache的文件上传组件来实现，common-fileupload, 它需要依赖于commons-io组件：
        //1、创建DiskFileItemFactory对象，处理文件上传路径或大小的限制
        DiskFileItemFactory factory = getDiskFileItemFactory(uploadFile);
        //2、获取ServletFileUpload
        ServletFileUpload upload = getServletFileUpload(factory);
        //3、处理上传的文件
        try {
            String msg = uploadParseRequest(upload,req,uploadPath);
            req.setAttribute("msg",msg);
            req.getRequestDispatcher("msg.jsp").forward(req,resp);

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static DiskFileItemFactory getDiskFileItemFactory(File file){
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //通过这个工厂设置一个缓冲区，当上传的文件大于这个缓冲区的时候，将他放到临时文件中
        factory.setSizeThreshold(1024*1024); //缓冲区大小为1M
        factory.setRepository(file);//临时文件保存的目录，需要一个File
        return factory;
    }

    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory){
        ServletFileUpload upload = new ServletFileUpload(factory);
        //监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            @Override
            //pBytesRead:已经读取到的文件大小
            //pContentLength ： 文件大小
            public void update(long pBytesRead, long pContentLength, int pItems) {
                System.out.println("总大小："+pContentLength+"已上传"+pBytesRead);
            }
        });
        //处理乱码问题
        upload.setHeaderEncoding("utf-8");
        //设置单个文件的最大值
        upload.setFileSizeMax(1024*1024*10);
        //设置总共能够上传文件的大小
        upload.setSizeMax(1024 * 1024 * 10);
        return upload;
    }

    public static String uploadParseRequest(ServletFileUpload upload, HttpServletRequest request, String uploadPath) throws FileUploadException, IOException {
        String msg = "";
        //把前端请求解析，封装成一个FileItem对象
        List<FileItem> fileItems = upload.parseRequest((RequestContext) request);
        for (FileItem fileItem : fileItems) {
            if (fileItem.isFormField()){
                String name = fileItem.getFieldName();
                String value = fileItem.getString("UTF-8");
                System.out.println(name+":"+value);
            }else {
                //****************************处理文件****************************
                //拿到文件名字
                String uploadFileName = fileItem.getName();
                System.out.println("上传的文件名："+uploadFileName);
                if (uploadFileName.trim().equals("") || uploadFileName==null){
                    continue;
                }
                //获得上传的文件名
                String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
                //获得文件的后缀名
                String fileExName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
                /*
                 * 如果文件后缀名fileExName不是所需的直接return，不进行处理，告诉用户文件类型不对
                 * */
                System.out.println("文件信息 [文件名："+fileName+"---文件类型"+fileExName+"]");
                //可以使用UUID（唯一识别通用码）保证文件名唯一
                String uuidPath = UUID.randomUUID().toString();
                //****************************处理文件完毕****************************
                //真实存在的路径
                String realPath = uploadPath+"/"+uuidPath;
                //给每个文件创建一个对应的文件夹
                File realPathFile = new File(realPath);
                if (!realPathFile.exists()){
                    realPathFile.mkdir();
                }
                //****************************存放地址完毕*****************************
                //获得文件上传的流
                InputStream inputStream = fileItem.getInputStream();
                //创建一个文件输出流
                //realPath是真实的文件夹
                FileOutputStream fos = new FileOutputStream(realPath + "/"+fileName);
                //创建一个缓冲区
                byte[] buffer = new byte[1024 * 1024];
                //判断是否读取完毕
                int len = 0;
                while ((len=inputStream.read(buffer))>0){
                    fos.write(buffer,0,len);
                }
                //关闭流
                fos.close();
                inputStream.close();
                msg = "文件上传成功";
                fileItem.delete();//上传成功，清除临时文件
                //*************************文件传输完毕**************************
            }
        }
        return msg;
    }
}
