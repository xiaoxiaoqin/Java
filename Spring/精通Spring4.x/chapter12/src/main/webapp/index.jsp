
<%@ page   contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Simple jsp page</title>
    <%
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    	System.out.println(path);
	%>
  </head>
  <body>
    	<form aciton="${request.getContextPath()}/logon.do" method="post">
    		<input type="submit"/>
    	</form>
  </body>
</html>