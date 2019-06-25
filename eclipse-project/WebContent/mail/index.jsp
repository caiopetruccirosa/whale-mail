<%@ page contentType="text/html;charset=UTF-8" 
	language="java" 
	import="accountmanager.*, bd.dbos.*, javax.mail.*, java.util.*, mail.*" %>

<%@include file="../includes/loggedin.jsp" %>

<html>
	<head>
		<title>WhaleMail</title>
	
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		
		<link rel="stylesheet" type="text/css" href="../css/animate.css">
		<link rel="stylesheet" type="text/css" href="../css/fontawesome.css">
		<link rel="stylesheet" type="text/css" href="../css/fonts.css">
		<link rel="stylesheet" type="text/css" href="../css/materialize.css">
		<link rel="stylesheet" type="text/css" href="../css/style.css">
	</head>

  <body>
	<%@include file="../includes/sidenav.jsp" %>
	<%@include file="../includes/modals.jsp" %>
	
	<%
		AccountManager accounts = (AccountManager)session.getAttribute("user");
	
		User user = null;
		if (accounts != null)
			user = accounts.getUser();
		
		Account current = null;
		if (accounts != null)
			current = accounts.getCurrentAccount();
	%>

	<div class="main-container">
      <div class="mail-container card">
		<%
		  if (accounts != null && accounts.isValid()) {
		    out.println("<div class='mail-header text-right'>");
		    
		    out.println("<span class='info-span folder-span'>Pasta: " + accounts.getCurrentFolder().getName() + "</span>");
		    out.println("<span class='info-span'>Page: " + (accounts.getPage() + 1) + "</span>");
		    out.println("<a href='../FolderOperations?action=go_backwards' class='circle waves-effect'><i class='material-icons'>arrow_back</i></a>");
		    out.println("<a href='../FolderOperations?action=go_forward' class='circle waves-effect'><i class='material-icons'>arrow_forward</i></a>");
		    
		    if (!accounts.getCurrentFolder().getName().equals("INBOX")) {
		    	out.println("<a class='circle waves-effect modal-trigger' data-target='modal-rename-folder'><i class='material-icons'>settings</i></a>");
		    	out.println("<a class='circle waves-effect modal-trigger' data-target='modal-delete-folder'><i class='material-icons'>delete</i></a>");	
		    }
		    
		    out.println("</div>");
		
		    Mail[] messages = accounts.getCurrentMails();
		
		    out.println("<div class='mail-content'>");
		
		    if (messages != null) {
		    	out.println("<ul class='collection no-margin'>");
		    	
		    	for (int i = messages.length-1; i >= 0; i--) {
			      Mail aux = messages[i];
			
			      out.println("<li class='collection-item avatar mail-collection-item'>");
			      out.println("<span class='title text-bold'>" + aux.getFrom() + "</span>");
			      out.println("<p>");
			      out.println(aux.getSubject());
			      out.println("</p>");
			      out.println("<a href='message.jsp?message=" + aux.getId() + "' class='secondary-content'>");
			      out.println("<i class='material-icons'>arrow_forward</i>");
			      out.println("</a>");
			      out.println("</li>");
			    }
		    	
		    	out.println("</ul>");
		    } else {
		    	out.println("<br/><br/><center><h4>Não há nenhum e-mail nesta pasta!</h4></center>");
		    }
		
		    out.println("</div>");
		  } else {
			out.println("<br/><br/><center><h4>Há algum problema com sua conta!</h4></center>");
		  }
		%>
      </div>
    </div>

	<%
		if (accounts != null && accounts.isValid() && current != null) {
			out.println("<div class='fixed-action-btn'>");
			out.println("<a class='btn-floating btn-large waves-effect waves-light red modal-trigger' data-target='modal-email'><i class='material-icons'>border_color</i></a>");
			out.println("</div>");
		}
	%>
    
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/materialize.js"></script>
	<script type="text/javascript" src="../js/script.js"></script>
	
	<script type="text/javascript">	
		$('.sidenav').sidenav();
		
		$('.modal').modal();
		
		$('.dropdown-trigger').dropdown();
		 
		<%
			String err = (String)session.getAttribute("err");
			String success = (String)session.getAttribute("success");
			
			session.removeAttribute("err");
			session.removeAttribute("success");
			
			if (err != null)
				out.println("M.toast({html: '" + err + "'})");
			
			
			if (success != null)
				out.println("M.toast({html: '" + success + "'})");
		%>
	</script>
  </body>
</html>