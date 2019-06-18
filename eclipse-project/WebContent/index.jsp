<%@ page contentType="text/html;charset=UTF-8" 
	language="java" 
	import="accountmanager.AccountManager" %>

<html>
	<head>

		<title>WhaleMail</title>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

		<link rel="stylesheet" type="text/css" href="css/animate.css">
		<link rel="stylesheet" type="text/css" href="css/fontawesome.css">
		<link rel="stylesheet" type="text/css" href="css/fonts.css">
		<link rel="stylesheet" type="text/css" href="css/materialize.css">
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<%
		AccountManager acc = (AccountManager)session.getAttribute("user");
		String err = (String)session.getAttribute("err");
		String success = (String)session.getAttribute("success");
		
		session.removeAttribute("err");
		session.removeAttribute("success");
	
	    if (acc != null)
	        response.sendRedirect("mail/");
	%>

	<body>
		<div class="center-div form-container">
			<h1 class="center">WhaleMail</h1>
			
			<div class="form-login form-div">
				<form method="get" action="Authentication">
					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">account_circle</i>
						<input id="userlogin" type="text" name="userlogin">
						<label for="userlogin">Username</label>
					</div>

					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="passlogin" type="password" name="passlogin">
						<label for="passlogin">Senha</label>
					</div>

					<button class="btn waves-effect waves-light" type="submit">Entrar</button>
				</form>

				<div class="center opcoes">
					<a class='cadastro'>Ainda não possui uma conta?</a> | <a id='mudarsenha'>Esqueceu a senha?</a>
				</div>
			</div>

			<div class="form-cadastro form-div">
				<form method="post" action="Authentication">
					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">account_circle</i>
						<input id="usercadastro" type="text" name="usercadastro">
						<label for="usercadastro">Username</label>
					</div>

					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="passcadastro" type="password" name="passcadastro">
						<label for="passcadastro">Senha</label>
					</div>
					
					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="passconfcadastro" type="password" name="passconfcadastro">
						<label for="passconfcadastro">Confirmar senha</label>
					</div>

					<button class="btn waves-effect waves-light" type="submit">Cadastrar</button>
				</form>

				<div class="center opcoes">
					<a class='login'>Já possui uma conta?</a> | <a class='mudarsenha'>Esqueceu a senha?</a>
				</div>
			</div>
		</div>
		
		

	  
			
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/materialize.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript">
			
			$(document).ready(function(){
				$('.main').ready(function(){
					$('.form-login').show();
					$('.main').addClass('animated bounceInDown').one('webkitAnimationEnd', function(){
						$('.main').removeClass('animated bounceInDown');
					});
				});
				
				<%
					if (err != null)
						out.println("M.toast({html: '" + err + "'})");
					
					
					if (success != null)
						out.println("M.toast({html: '" + success + "'})");
				%>
			});

			$('.login').click(function () {
				changeForm('login');
			});
			
			$('.cadastro').click(function () {
				changeForm('cadastro');
			});

		</script>
	</body>
</html>