<%--
  Created by IntelliJ IDEA.
  User: u17167
  Date: 07/06/2019
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="account.Account" %>

<%
    Account acc  = (Account)session.getAttribute("acc");

    if (acc == null)
        response.sendRedirect("../");
%>

<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
