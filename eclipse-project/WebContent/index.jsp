<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bd.dbos.User" %>

<%
    User user = (User)session.getAttribute("user");

    if (user != null)
        response.sendRedirect("mail/");
%>

<html>
	<head>

		<title>WhaleMail</title>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

		<link rel="stylesheet" type="text/css" href="css/fonts.css">
		<link rel="stylesheet" type="text/css" href="css/materialize.css">
		<link rel="stylesheet" type="text/css" href="css/animate.css">
		<link rel="stylesheet" type="text/css" href="css/newstyle.css">
	</head>

	<body>
		<div class="center-div main">
			<h1 class="center">WhaleMail</h1>
			<div class="form-login form-div">
				<form>
					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">account_circle</i>
						<input id="emailog" type="text">
						<label for="emailog">Username</label>
					</div>

					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="senhalog" type="password">
						<label for="senhalog">Senha</label>
					</div>

					<button class="btn waves-effect waves-light btnlogar" type="submit" name="logar">Entrar</button>
				</form>

				<div class="center opcoes">
					<a class='cadastro'>Ainda não possui uma conta?</a> | <a id='mudarsenha'>Esqueceu a senha?</a>
				</div>
			</div>

			<div class="form-cadastro form-div">
				<form action="login.jsp">
					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">account_circle</i>
						<input id="emailog" type="text">
						<label for="emailog">Username</label>
					</div>

					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="senhalog" type="password">
						<label for="senhalog">Senha</label>
					</div>
					
					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="senhalog" type="password">
						<label for="senhalog">Senha</label>
					</div>

					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="senhalog" type="password">
						<label for="senhalog">Senha</label>
					</div>

					<div class="input-field">
						<i class="material-icons prefix center icon-responsive">lock</i>
						<input id="senhalog" type="password">
						<label for="senhalog">Senha</label>
					</div>

					<button class="btn waves-effect waves-light btnlogar" type="submit" name="logar">Cadastrar</button>
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
						$('.main').removeClass('animated bounceInDown')
					});
				});
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