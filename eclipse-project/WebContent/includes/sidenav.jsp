<%@ page contentType="text/html;charset=UTF-8" 
	language="java" 
	import="accountmanager.*, bd.dbos.*, javax.mail.*, java.util.*, mail.*" %>

	<%
		AccountManager accountsSidenav = (AccountManager)session.getAttribute("user");
		
		User userSidenav = null;
		if (accountsSidenav != null)
			userSidenav = accountsSidenav.getUser();
		
		Account currentSidenav = null;
		if (accountsSidenav != null)
			currentSidenav = accountsSidenav.getCurrentAccount();
	%>

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
        		if (userSidenav != null)
  					out.println(userSidenav.getUser()); 
			%>
	        </h4>
	      </div>
	    </li>

    <% 
        if (accountsSidenav != null && currentSidenav != null) {
       		out.println("<li><a class='subheader'><i class='material-icons'>account_circle</i> Conta atual</a></li>");
       		
       		try {
				out.println("<li><a class='dropdown-trigger waves-effect' href='#' data-target='ddaccounts'>" + currentSidenav.getUser() + "</a></li>");
				
				out.println("<ul id='ddaccounts' class='dropdown-content'>");
				
				for (int i = 0; i < accountsSidenav.getAccounts().size(); i++) {
					Account aux = accountsSidenav.getAccounts().get(i);
					out.println("<li><a href='../AccountOperations?current=" + i + "'>" + aux.getUser() + "</a></li>");
				}
				
				out.println("</ul>");
			}
			catch(Exception ex) {
				if (ex.getMessage() != null)
					out.println("<li><a class='subheader'>" + ex.getMessage() + "</a></li>");
				else
					out.println("<li><a class='subheader'>Ocorreu algum erro!</a></li>");
			}
       		
       		out.println("<li><div class='divider'></div></li>");
        }
	%>

    <li>
      <a class="subheader"><i class="material-icons">group</i> Contas</a>
    </li>

	<li><a class='waves-effect modal-trigger' data-target='modal-add-account'>Adicionar conta <i class='material-icons'>person_add</i></a></a></li>

	<%
		if (accountsSidenav != null && accountsSidenav.isValid() && currentSidenav != null) {
			out.println("<li><a class='waves-effect modal-trigger' data-target='modal-update-account'>Alterar conta atual <i class='material-icons'>edit</i></a></li>");
			out.println("<li><a class='waves-effect modal-trigger' data-target='modal-delete-account'>Excluir conta atual <i class='material-icons'>backspace</i></a></li>");

			out.println("<li><div class='divider'></div></li>");
			out.println("<li><a class='subheader'><i class='material-icons'>folder</i> Pastas</a></li>");
			
			out.println("<li><a class='modal-trigger waves-effect' data-target='modal-create-folder'>Criar nova pasta <i class='material-icons'>create_new_folder</i></a></li>");
			
			try {
				Folder[] folders = accountsSidenav.getCurrentFolders();
				
				for (int i = 0; i < folders.length; i++) {
					try {
						folders[i].open(Folder.READ_ONLY);
						
						if (folders[i].getMode() == Folder.HOLDS_MESSAGES) {
							String foldername = folders[i].getName();
							
							out.println("<li>");
							out.println("<a class='waves-effect' href='../FolderOperations?action=change_folder&folder=" + foldername + "'>" + foldername + "</a>");
							out.println("</li>");	
						}
					}
					catch(Exception ex) {
						if (!ex.getMessage().equals("folder cannot contain messages"))
							throw ex;
					}
				}
			} catch (Exception ex) {
				if (ex.getMessage() != null)
					out.println("<li><a class='subheader'>" + ex.getMessage() + "</a></li>");
				else
					out.println("<li><a class='subheader'>Ocorreu algum erro!</a></li>");	
			}
		}
	%>

      <li>
        <div class="divider"></div>
      </li>

      <li>
        <a class="waves-effect" href="../Logout"><i class="material-icons">exit_to_app</i> Sair</a>
      </li>
    </ul>