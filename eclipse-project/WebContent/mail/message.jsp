  <%@ page contentType="text/html;charset=UTF-8" 
language="java" 
import="accountmanager.*, bd.dbos.*, javax.mail.*, java.util.*, mail.*, javax.mail.internet.*" %>

<%@include file="../includes/loggedin.jsp" %>

<%
	AccountManager accounts = (AccountManager)session.getAttribute("user");

	User user = null;
	if (accounts != null)
		user = accounts.getUser();
	
	Account current = null;
	if (accounts != null)
		current = accounts.getCurrentAccount();
		
	int message = -1;
	if (request.getParameter("message") != null)
		message = Integer.parseInt(request.getParameter("message"));
%>

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
    
    <div class="main-container">
      <div class="mail-container card">
      <%
      	if (accounts != null && accounts.isValid() && current != null) {
      		String folder = accounts.getCurrentFolder().getName();
      		Mail mail = accounts.getMail(folder, message);
      		
      		out.println("<div class='mail-header text-right'>");

      		out.println("<a href='./' class='circle waves-effect'><i class='material-icons'>arrow_back</i></a>");
      		out.println("<a class='circle waves-effect modal-trigger' data-target='modal-delete-mail'><i class='material-icons'>delete</i></a>");	

      		out.println("</div>");

      		out.println("<div class='mail-content card-content'>");

      		out.println("<div class='row'>");
      		
      		out.println("<div class='col s8'>");
      		out.println("<h4 class='no-margin'>" + mail.getFrom() + "</h4>");
      		out.println("</div>");

      		String date = mail.getDate().getDay() + "/" + mail.getDate().getMonth() + "/" + (1900 + mail.getDate().getYear());
      		String time = mail.getDate().getHours() + "h" + mail.getDate().getMinutes();
      		out.println("<div class='col s4 mail-date'>Data: " + date +  " <br /> Horário: " + time + "</div>");
      		
      		out.println("</div>");

      		out.println("<div class='row'>");
      		out.println("<div class='col s12'>");
      		out.println("<span class='card-title'>Assunto: " + mail.getSubject() + "</span>");
      		out.println("</div>");
      		out.println("</div>");
      		
      		out.println("<div class='row'>");
      		out.println("<div class='col s12'>");
      		
      		/*
      		if (mail.getMessage() instanceof String) {
      			out.println("<p class='flow-text'>");
          		out.println(mail.getMessage());
          		out.println("</p>");
      		} else if (mail.getMessage() instanceof MimeMultipart) {
      			MimeMultipart multipart = (MimeMultipart) mail.getMessage();
      			
      			BodyPart part = (BodyPart) multipart.getBodyPart(0);
      			
      			out.println("<p class='flow-text'>");
      			out.println(part.getContent().toString());
      			out.println("</p>");
      		}
      		*/
      		out.println(mail.getMessage());
      		
      		if (mail.getAttachments() != null)
      			out.println("<a href='#' class='circle waves-effect'><i class='material-icons'>file_download</i></a>");
      		
      		out.println("</div>");
      		out.println("</div>");

      		out.println("</div>");
      	}
      %>
      </div>
    </div>
    
    <%
    	if (accounts != null && accounts.isValid() && current != null) {
    		String folder = accounts.getCurrentFolder().getName();
    		
			// Modal de deletar e-mail
			out.println("<div id='modal-delete-mail' class='modal'>");
			out.println("<form method='post' action='../FolderOperations'>");
			out.println("<div class='modal-content'>");
			out.println("<div class='row'>");
			out.println("<h4>Deseja mesmo excluir este e-mail?</h4>");
			out.println("</div>");
			out.println("<input type='hidden' name='action' value='delete_mail'>");
			out.println("<input type='hidden' name='folder' value='" + folder + "'>");
			out.println("<input type='hidden' name='message' value='" + message + "'>");
			out.println("</div>");
			out.println("<div class='modal-footer'>");
			out.println("<a class='modal-close waves-effect btn-flat'>Cancelar</a>");
			out.println("<button class='modal-close waves-effect btn-flat'>Excluir</a>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");
    	}
    %>
    
    <script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/materialize.js"></script>
	<script type="text/javascript" src="../js/script.js"></script>
    
    <script type="text/javascript">	
			$(document).ready(function(){
				$('.main').ready(function(){
					$('.form-login').show();
					$('.main').addClass('animated bounceInDown').one('webkitAnimationEnd', function(){
						$('.main').removeClass('animated bounceInDown');
					});
				});
			});
			
			$('.login').click(function () {
				changeForm('login');
			});
			
			$('.cadastro').click(function () {
				changeForm('cadastro');
			});
			
			$('.modal').modal();
			 
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