<%@ page contentType="text/html;charset=UTF-8" 
	language="java" 
	import="bd.dbos.User, accountmanager.AccountManager" %>

<%
	AccountManager user = (AccountManager)session.getAttribute("user");

    if (user == null)
        response.sendRedirect(".../");
%>

<html>
	<head>
		<title>WhaleMail</title>
	</head>
	
	<body>
	</body>
</html>