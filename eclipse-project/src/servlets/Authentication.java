package servlets;

import java.io.*;
import java.util.Base64;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import accountmanager.AccountManager;
import bd.daos.Users;
import bd.dbos.User;

/**
 * Servlet implementation class Authentication
 */
@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authentication() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("userlogin").trim();
		String pass = request.getParameter("passlogin").trim();
		
		HttpSession session = request.getSession();	
		try {
			/*
			session.setAttribute("user", new AccountManager(new User(1, user, pass)));				
			response.sendRedirect("/whalemail/mail/");
			*/
			
			if (Users.existe(user, pass)) {
				session.setAttribute("user", new AccountManager(Users.getUser(user)));				
				response.sendRedirect("/whalemail/mail/");
			} else {
				throw new Exception("Usu�rio ou senha est�o incorretos!");
			}
		}
		catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
			response.sendRedirect("/whalemail/");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("usercadastro").trim();
		String pass = request.getParameter("passcadastro").trim();
		String passconf = request.getParameter("passconfcadastro").trim();
		
		HttpSession session = request.getSession();
		try {
			if (!pass.equals(passconf))
				throw new Exception("Senhas diferentes!");
			
			if (Users.existe(user))
				throw new Exception("Usu�rio j� existente!");
			
			String senhaCriptografada = Base64.getEncoder().encodeToString(pass.getBytes());
			Users.cadastrar(new User(0, user, senhaCriptografada));
			
			session.setAttribute("success", "Usu�rio cadastrado com sucesso!");
		}
		catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
		}
		
		response.sendRedirect("/whalemail/");
	}

}
