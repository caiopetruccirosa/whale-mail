package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bd.daos.Users;
import bd.dbos.User;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/Auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user").trim();
		String pass = request.getParameter("pass").trim();
		
		HttpSession session = request.getSession();
		try {
			session.setAttribute("user", new User(1, user, pass));	
			response.sendRedirect("mail/");
			
			/*
			if (Users.existe(user, pass)) {
				session.setAttribute("user", Users.getUser(user));				
				response.sendRedirect("mail/");
			} else 
				throw new Exception("Usuário ou senha estão incorretos!");
			*/
		}
		catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
			response.sendRedirect("/");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user").trim();
		String pass = request.getParameter("pass").trim();
		String pass_conf = request.getParameter("pass_conf").trim();
		
		HttpSession session = request.getSession();
		try {
			if (!pass.equals(pass_conf))
				throw new Exception("Senhas diferentes!");
			
			if (Users.existe(user))
				throw new Exception("Usuário já existente!");
			
			Users.cadastrar(new User(0, user, pass));
			
			session.setAttribute("success", "Usuário cadastrado com sucesso!");
		}
		catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
		}
		
		response.sendRedirect("/");
	}

}
