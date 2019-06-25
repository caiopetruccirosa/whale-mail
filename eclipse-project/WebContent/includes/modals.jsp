<%@ page
	language="java" 
	import="accountmanager.*, bd.dbos.*, javax.mail.*" %>

	<%
		AccountManager accountsModals = (AccountManager)session.getAttribute("user");
	
		User userModals = null;
		if (accountsModals != null)
			userModals = accountsModals.getUser();
		
		Account currentModals = null;
		if (accountsModals != null)
			currentModals = accountsModals.getCurrentAccount();
	%>

	<div id="modal-email" class="modal modal-fixed-footer">
	    <form method="post" action="../MailOperations">
	    	<a class="modal-close close-btn btn-flat vertical-align"><i class="material-icons">close</i></a>
	    	
		    <div class="modal-content">
		    	<div class="row">
		        	<h4>Enviar mensagem</h4>
		        </div>
		        
		        <div class="row">
					<div class="input-field col s12">
						<input id="to" type="text" name="to">
						<label for="to">Destinatário</label>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s6">
						<input id="cc" type="text" name="cc">
						<label for="cc">CC</label>
					</div>
					
					<div class="input-field col s6">
						<input id="bcc" type="text" name="bcc">
						<label for="bcc">CCO</label>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s12">
						<input id="subject" type="text" name="subject">
						<label for="subject">Assunto</label>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s12">
						<textarea id="message" class="materialize-textarea" name="message"></textarea>
						<label for="message">Mensagem</label>
					</div>
				</div>

				<div class="file-field input-field">
					<div class="file-path-wrapper">
						<i class="material-icons prefix center icon-responsive"><input type="file" multiple> attach_file</i>
						<input id='attachments' name='attachments' class="file-path validate" type="text" placeholder="Escolha seus anexos" type="text">
					</div>
				</div>
		    </div>
		    
		    <div class="modal-footer">
		      <button class="modal-close waves-effect btn-flat">Enviar</a>
			</div>
	    </form>
	</div>

	<% 
		if (userModals != null) {
			// Modal de cadastro de conta
			out.println("<div id='modal-add-account' class='modal modal-fixed-footer'>");
			out.println("<form method='post' action='../AccountOperations'>");
			out.println("<a class='modal-close close-btn btn-flat vertical-align'><i class='material-icons'>close</i></a>");
			
			out.println("<div class='modal-content'>");
			out.println("<div class='row'>");
			out.println("<h4>Cadastrar nova conta</h4>");
			out.println("</div>");
			
			out.println("<div class='data-fields'>");
			out.println("<div class='row'>");
			out.println("<div class='input-field col s12'>");
			out.println("<input id='emailadd' type='email' class='validate' name='user'>");
			out.println("<label for=emailadd>E-mail</label>");
			out.println("</div>");
			out.println("</div>");
			
			out.println("<div class='row'>");
			out.println("<div class='input-field col s12'>");
			out.println("<input id='passadd' type='password' name='pass'>");
			out.println("<label for='passadd'>Senha</label>");
			out.println("</div>");
			out.println("</div>");
			
			out.println("<div class='row'>");
			out.println("<div class='input-field col s12'>");
			out.println("<input id='hostadd' type='text' name='host'>");
			out.println("<label for='hostadd'>Host</label>");
			out.println("</div>");
			out.println("</div>");
			
			out.println("<div class='row'>");
			out.println("<div class='input-field col s6'>");
			out.println("<input id='protocoladd' type='text' maxlength=5 name='protocol'>");
			out.println("<label for='protocoladd'>Protocolo</label>");
			out.println("</div>");
			
			out.println("<div class='input-field col s6'>");
			out.println("<input id='portadd' type='number' name='port'>");
			out.println("<label for='portadd'>Porta</label>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			
			out.println("<input type='hidden' name='action' value='add'>");
			
			out.println("<input type='hidden' name='id' value='0'>");
			out.println("<input type='hidden' name='owner_id' value='" + userModals.getId() + "'>");
			
			out.println("<div class='modal-footer'>");
			out.println("<button class='modal-close waves-effect btn-flat'>Adicionar</a>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");
		}
	
		if (currentModals != null) {			
			// Modal de alteração de conta
			out.println("<div id='modal-update-account' class='modal modal-fixed-footer'>");
			out.println("<form method='post' action='../AccountOperations'>");
			out.println("<a class='modal-close close-btn btn-flat vertical-align'><i class='material-icons'>close</i></a>");
			out.println("<div class='modal-content'>");
			out.println("<div class='row'>");
			out.println("<h4>Alterar conta</h4>");
			out.println("</div>");
			out.println("<div class='data-fields'>");
			out.println("<div class='row'>");
			out.println("<div class='input-field col s12'>");
			out.println("<input id='emailupdate' type='email' class='validate' value='" + currentModals.getUser() + "' name='user'>");
			out.println("<label for='emailupdate'>E-mail</label>");
			out.println("</div>");
			out.println("</div>");
			out.println("<div class='row'>");
			out.println("<div class='input-field col s12'>");
			out.println("<input id='passupdate' type='password' value='" + currentModals.getPassword() + "' name='pass'>");
			out.println("<label for='passupdate'>Senha</label>");
			out.println("</div>");
			out.println("</div>");
			out.println("<div class='row'>");
			out.println("<div class='input-field col s12'>");
			out.println("<input id='hostupdate' type='text' value='" + currentModals.getHost() + "' name='host'>");
			out.println("<label for='hostupdate'>Host</label>");
			out.println("</div>");
			out.println("</div>");
			out.println("<div class='row'>");
			out.println("<div class='input-field col s6'>");
			out.println("<input id='protocolupdate' type='text' maxlength=5 value='" + currentModals.getProtocol() + "' name='protocol'>");
			out.println("<label for='protocolupdate'>Protocolo</label>");
			out.println("</div>");
			out.println("<div class='input-field col s6'>");
			out.println("<input id='portupdate' type='number' value='" + currentModals.getPort() + "' name='port'>");
			out.println("<label for='portupdate'>Porta</label>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("<input type='hidden' name='action' value='update'>");
			out.println("<input type='hidden' name='owner_id' value='" + userModals.getId() + "'>");
			out.println("<input type='hidden' name='id' value='" + currentModals.getId() + "'>");
			out.println("<div class='modal-footer'>");
			out.println("<button class='modal-close waves-effect btn-flat'>Alterar</a>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");
			
			// Modal de excluir conta
			out.println("<div id='modal-delete-account' class='modal'>");
			out.println("<form method='post' action='../AccountOperations'>");
			out.println("<div class='modal-content'>");
			out.println("<h4>Deseja mesmo excluir esta conta?</h4>");
			out.println("</div>");
			out.println("<input type='hidden' name='action' value='delete'>");
			out.println("<input type='hidden' name='id' value='" + currentModals.getId() + "'>");
			out.println("<div class='modal-footer'>");
			out.println("<a class='modal-close waves-effect btn-flat'>Cancelar</a>");
			out.println("<button class='modal-close waves-effect btn-flat'>Excluir</a>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");
		}
		
		if (accountsModals != null && accountsModals.isValid()) {
			// Modal de criar pasta
			out.println("<div id='modal-create-folder' class='modal'>");
			out.println("<form method='post' action='../FolderOperations'>");
			out.println("<div class='modal-content'>");
			out.println("<div class='row'>");
			out.println("<h4>Criar nova pasta</h4>");
			out.println("</div>");
			out.println("<input type='hidden' name='action' value='create'>");
			out.println("<div class='row'>");
			out.println("<div class='input-field col s12'>");
			out.println("<input id='folder-create' type='text' name='folder'>");
			out.println("<label for='folder-create'>Nome da pasta</label>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("<div class='modal-footer'>");
			out.println("<a class='modal-close waves-effect btn-flat'>Cancelar</a>");
			out.println("<button class='modal-close waves-effect btn-flat'>Criar</a>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");

			if (!accountsModals.getCurrentFolder().getName().equals("INBOX")) {
				// Modal de renomear pasta
				out.println("<div id='modal-rename-folder' class='modal'>");
				out.println("<form method='post' action='../FolderOperations'>");
				out.println("<div class='modal-content'>");
				out.println("<div class='row'>");
				out.println("<h4>Renomear pasta</h4>");
				out.println("</div>");
				out.println("<input type='hidden' name='action' value='rename'>");
				out.println("<input type='hidden' name='folder' value='" + accountsModals.getCurrentFolder().getName() + "'>");
				out.println("<div class='row'>");
				out.println("<div class='input-field col s12'>");
				out.println("<input id='folder-rename' type='text' name='new_folder'>");
				out.println("<label for='folder-rename'>Novo nome da pasta</label>");
				out.println("</div>");
				out.println("</div>");
				out.println("</div>");
				out.println("<div class='modal-footer'>");
				out.println("<a class='modal-close waves-effect btn-flat'>Cancelar</a>");
				out.println("<button class='modal-close waves-effect btn-flat'>Renomear</a>");
				out.println("</div>");
				out.println("</form>");
				out.println("</div>");
	
				// Modal de deletar pasta
				out.println("<div id='modal-delete-folder' class='modal'>");
				out.println("<form method='post' action='../FolderOperations'>");
				out.println("<div class='modal-content'>");
				out.println("<div class='row'>");
				out.println("<h4>Deseja mesmo excluir esta pasta?</h4>");
				out.println("</div>");
				out.println("<input type='hidden' name='action' value='delete'>");
				out.println("<input type='hidden' name='folder' value='" + accountsModals.getCurrentFolder().getName() + "'>");
				out.println("</div>");
				out.println("<div class='modal-footer'>");
				out.println("<a class='modal-close waves-effect btn-flat'>Cancelar</a>");
				out.println("<button class='modal-close waves-effect btn-flat'>Excluir</a>");
				out.println("</div>");
				out.println("</form>");
				out.println("</div>");	
			}
		}
	%>