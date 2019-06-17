<%@ page contentType="text/html;charset=UTF-8" 
	language="java" 
	import="accountmanager.*, bd.dbos.*, javax.mail.*, java.util.*" %>

<%
	AccountManager acc = (AccountManager)session.getAttribute("user");

	//User user = (User) session.getAttribute("user");

    if (acc == null)
        response.sendRedirect("../");
%>

<html>
	<head>
		<title>WhaleMail</title>
	</head>
	
	
	<div>
		<%		
			out.println(acc.getUser().getUser());
		%>
	</div>
	
	
	<div>
		<%
			// Listagem das contas do usuário
		
			ArrayList<Account> accounts = acc.getAccounts();
		
			for (int i = 0; i < accounts.size(); i++) {
				out.println("<label>" + accounts.get(i).getUser() + "</label> <br />");
			}
		%>
	</div>
	
	<div>
		<%
			// Listagem de pastas
		
			ArrayList<Folder> folders = acc.getCurrentFolders();
		
			for (int i = 0; i < folders.size(); i++) {
				out.println("<label>" + folders.get(i).getName() + "</label> <br />");
			}
		%>
	</div>
	
	<div>
		<%
			// ATUALMENTE ESTÁ LANÇANDO A EXCEÇÃO FolderClosedException
			// Provavelmente é algum erro na classe FolderHandler, no método getCurrentMails
			
			/*
			ArrayList<Message> messages = acc.getCurrentMessages();
		
			for (int i = 0; i < messages.size(); i++) {
				out.println("<label>" + messages.get(i).getSubject() + "</label> <br />");
			}
			*/
		%>
	</div>
	
	<body>
	</body>
</html>