<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="bd.daos.Users, bd.dbos.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Login</title>
	</head>
	<body>
	
		<%
			try {
				String user = request.getParameter("user");
				String pass = request.getParameter("pass");
				
				if (Users.existe(user, pass)) {
					User usr = Users.getUser(user);
				}
			}
			catch (Exception ex) {}
		%>
	
	</body>
</html>