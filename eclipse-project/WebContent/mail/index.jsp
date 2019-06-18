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

		<% 
			try {
				for(Account ac : acc.getAccounts()) {
					out.println("<li>");
					out.println("<a class='waves-effect' href='#'>" + ac.getUser() + "</a>");
					out.println("</li>");
				}
			}
			catch(Exception ex) {
				out.println("<li>");
				out.println("<a class='waves-effect' href='#'>" + ex.getMessage() + "</a>");
				out.println("</li>");
			}
		%>

      <li>
        <div class="divider"></div>
      </li>

      <li>
        <a class="subheader"><i class="material-icons">folder</i> Pastas</a>
      </li>
      
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

      <li>
        <div class="divider"></div>
      </li>

      <li>
        <a class="waves-effect" href="../Logout"><i class="material-icons">exit_to_app</i> Sair</a>
      </li>
    </ul>

    <div class="main-container">
      <div class="mails-container">
        <ul class="collection">
          <li class="collection-item avatar mail-collection-item">
            <span class="title">Title</span>

            <p>
              First Line
              <br>
              Second Line
            </p>

            <a href="#!" class="secondary-content">
              <i class="material-icons">arrow_forward</i>
            </a>
          </li>

          <li class="collection-item avatar mail-collection-item">
            <span class="title">Title</span>

            <p>
              First Line
              <br>
              Second Line
            </p>
            
            <a href="#!" class="secondary-content">
              <i class="material-icons">arrow_forward</i>
            </a>
          </li>
        </ul>
      </div>

      <div class="fixed-action-btn">
        <a class="btn-floating btn-large waves-effect waves-light red"><i class="material-icons">border_color</i></a>
      </div>

    </div>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/materialize.js"></script>
    <script type="text/javascript" src="../js/script.js"></script>

    <script type="text/javascript">
      $('.sidenav').sidenav();
    </script>
  </body>
</html>