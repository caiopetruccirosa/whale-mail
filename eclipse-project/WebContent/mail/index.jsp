  <%@ page contentType="text/html;charset=UTF-8" 
language="java" 
import="accountmanager.*, bd.dbos.*, javax.mail.*, java.util.*, mail.*" %>

<%
AccountManager acc = (AccountManager)session.getAttribute("user");
  if (acc == null)
      response.sendRedirect("../");
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">

    <title>WhaleMail</title>

    <link rel="stylesheet" type="text/css" href="../css/animate.css">
    <link rel="stylesheet" type="text/css" href="../css/fonts.css">
    <link rel="stylesheet" type="text/css" href="../css/fontawesome.css">
    <link rel="stylesheet" type="text/css" href="../css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
  </head>

  <body>
    <nav>
      <div class="nav-wrapper">
        <a href="#" class="brand-logo right">WhaleMail</a>

        <a href="#" data-target="slide-out" class="sidenav-trigger">
          <i class="material-icons">menu</i>
        </a>
      </div>
    </nav>

    <ul id="slide-out" class="sidenav sidenav-fixed">
      <li>
        <div class="user-view user-header">
          <h4 class="white-text center">
          	<% 
				try {
					out.println(acc.getUser().getUser()); 
				}
				catch(Exception ex) {
					out.println(ex.getMessage());
				}
			%>
          </h4>
        </div>
      </li>

      <li>
        <a class="subheader"><i class="material-icons">account_circle</i> Conta atual</a>
      </li>

      <li>
        <a class="waves-effect" href="#!">
	        <% 
				try {
					out.println(acc.getCurrentAccount().getUser()); 
				}
				catch(Exception ex) {
					out.println(ex.getMessage());
				}
			%>
        </a>
      </li>

      <li>
        <div class="divider"></div>
      </li>

      <li>
        <a class="subheader"><i class="material-icons">group</i> Contas</a>
      </li>

		<li>
			<a class="waves-effect" href="#"> Todas as contas </a>
		</li>
		<li>
		<select>
			<% 
				try {
					for(Account ac : acc.getAccounts()) {
						out.println("<option class='waves-effect' href='#'>");
						out.println(ac.getUser());
						out.println("</option>");
					}
				}
				catch(Exception ex) {
					out.println("<option>");
					out.println("<a class='waves-effect' href='#'>" + ex.getMessage() + "</a>");
					out.println("</option>");
				}
			%>
			
		</select>
	  </li>
      <li>
        <div class="divider"></div>
      </li>

      <li>
        <a class="subheader"><i class="material-icons">folder</i> Pastas</a>
      </li>
       <form action="get" method="/FolderOperations"> 
		<%
			try {
				Folder[][] folders = acc.getCurrentFolders();
				
				for (int i = 0; i < folders.length; i++) {
					for (int j = 0; j < folders[i].length; j++) {
						out.println("<li>");
						out.println("<a class='waves-effect' href='#'>" + folders[i][j].getName() + "</a>");
						out.println("</li>");
					}
				}
			} catch (Exception ex) {
				out.println("<li>");
				out.println("<a class='waves-effect' href='#'>" + ex.getMessage() + "</a>");
				out.println("</li>");
			}
		%>
	  </form>
      <li>
        <div class="divider"></div>
      </li>

      <li>
        <a class="waves-effect" href="../Logout"><i class="material-icons">exit_to_app</i> Sair</a>
      </li>
    </ul>

    <div class="main-container">
      <div class="mails-container">
        <ul class="collection" id="slide-out">
      	<%
      		try{
      			Mail[][] mails = acc.getCurrentMessages();
	      			for(int i =0; i<mails.length ;i++) {
	      				if (mails[i].length<20){
	      				for(int j=0;j<mails[i].length;j++) {
	      					out.println("");
          					out.println("<li class='collection-item avatar mail-collection-item'>");
          					out.println("<span class='title'>" + mails[i][j].getFrom()+ "</span>");
          					out.println("<p>");
          					out.println(mails[i][j].getSubject());
          		            out.println("<br>");
          		        	//out.println("Second Line");
          		        	out.println("</p>");
          		        	out.println("<a href='#!' class='secondary-content'>");
          		        	out.println("<i class='material-icons'>arrow_forward</i>");
          		        	out.println("</a>");
          		        	out.println("</li>");
	      				}
	      			} else { 
	      				for(int j=0;j<20;j++) {
      					out.println("");
      					out.println("<li class='collection-item avatar mail-collection-item'>");
      					out.println("<span class='title'>" + mails[i][j].getFrom()+ "</span>");
      					out.println("<p>");
      					out.println(mails[i][j].getSubject());
      		            out.println("<br>");
      		        	//out.println("Second Line");
      		        	out.println("</p>");
      		        	out.println("<a href='#!' class='secondary-content'>");
      		        	out.println("<i class='material-icons'>arrow_forward</i>");
      		        	out.println("</a>");
      		        	out.println("</li>");
	      				}
      				}
      			}
      		}catch(Exception ex)
      		{
      			out.println(ex.getMessage());
      		}
      	%>
          
            
         
        </ul>
      </div>
	
      <div class="fixed-action-btn">
        <a class="btn-floating btn-large waves-effect waves-light red modal-trigger" onclick="$('#email_modal').modal('open')"><i class="material-icons">border_color</i></a>
      </div>
	
	<!-- Modal Structure -->
	  <div id="email_modal" class="modal modal-fixed-footer">
	  		<div class="modal-header">
	  			 <h3>Escrever mensagem</h3>
	  			 <i class="material-icons prefix center icon-responsive modal-close" style="margin-right: 10px">close</i>
	  		</div>
		  <form method="get" action="Authentication">
		    <div class="modal-content">
		      
						<div class="input-field">
							<i class="material-icons prefix center icon-responsive modal-close">person</i>
							<input id="to" type="text" name="to">
							<label for="to">Destinatário...</label>
						</div>
	
						<div class="input-field">
							<i class="material-icons prefix center icon-responsive">chat_bubble</i>
							<input id="subject" type="text" name="Subject">
							<label for="subject">Assunto...</label>
						</div>
						
						<div class="input-field">
							<i class="material-icons prefix center icon-responsive">person_add</i>
							<input id="cc" type="text" name="cc">
							<label for="cc">CC</label>
						</div>
						
						<div class="input-field">
							<i class="material-icons prefix center icon-responsive">person_outline</i>
							<input id="cco" type="text" name="cco">
							<label for="cco">CCO</label>
						</div>
						
						<div class="input-field col s12">
						<i class="material-icons prefix center icon-responsive">edit</i>
	          				<textarea id="message_area" class="materialize-textarea" name="message_area"></textarea>
	          				<label for="message_area">Escrevas sua mensagem...</label>
        				</div>
						
        				 <div class="file-field input-field">
					      <div class="file-path-wrapper">
					        <i class="material-icons prefix center icon-responsive"><input type="file" multiple>attach_file</i>
					        <input  id='attachments' class="file-path validate" type="text" placeholder="Upload one or more files" type="text">
					      </div>
					    </div>		
		     </div>
		     <div class="modal-footer">
		     	
		       <a class="modal-close  waves-effect waves-light"><input type="submit">Enviar</a>
		     </div>
		    </form>
		    
		  </div>
	
    </div>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/materialize.js"></script>
    <script type="text/javascript" src="../js/script.js"></script>

    <script type="text/javascript">
      $('.sidenav').sidenav();
      $('.modal').modal();
    </script>
  </body>
</html>