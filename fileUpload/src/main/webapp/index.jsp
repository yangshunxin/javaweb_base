<%@page contentType="text/html; charset=UTF-8" language="java" %>>
<html>
<body>
<h2>Hello World!</h2>

<%---通过表单上传文件
    get: 文件大小有限制
    post: 上传文件大小没有限制，用post

----%>
<form action="${pageContext.request.contextPath}/upload.do",  enctype="multipart/form-data" method="post">
    <p><input type="file", name="file1"></p>
    <p><input type="file", name="file2"></p>
    <P>
        <input type="submit">|
        <input type="reset">
    </P>
</form>

</body>
</html>
