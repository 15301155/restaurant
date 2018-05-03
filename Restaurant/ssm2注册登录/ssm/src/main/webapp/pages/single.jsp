<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="sf"  uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

    <sf:form method="post" modelAttribute="user" enctype="multipart/form-data">
        Username:<sf:input path="id"/> <sf:errors path="id"></sf:errors></br>
        Password:<sf:input path="name"/> <sf:errors path="name"></sf:errors></br>
       

        <input type="submit" value="添加用户" />
        
    </sf:form>
</body>
</html>