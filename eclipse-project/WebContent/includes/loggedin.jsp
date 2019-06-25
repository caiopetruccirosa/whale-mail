<%@ page
	language="java" 
	import="accountmanager.AccountManager" %>
    
<%
	if (session.getAttribute("user") == null)
		response.sendRedirect("../");
%>