package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import accountmanager.AccountManager;
import bd.dbos.Account;

/**
 * Servlet implementation class AccountOperations
 */
@WebServlet("/AccountOperations")
public class AccountOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountOperations() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/whalemail/mail/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		HttpSession session = request.getSession();	
		AccountManager acc = (AccountManager) session.getAttribute("user");
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String user = request.getParameter("user");
		    String pass = request.getParameter("pass");
		    String host = request.getParameter("host");
		    String protocol = request.getParameter("protocol");
		    int port = Integer.parseInt(request.getParameter("port"));
		    int owner_id = Integer.parseInt(request.getParameter("owner_id"));
			
		    if (action.equals("add")) {
		    	Account newAcc = new Account(id, user, pass, host, protocol, port, owner_id);
		    	acc.addAccount(newAcc);
		    	
		    	session.setAttribute("success", "Conta adicionada com sucesso!");
		    } else if (action.equals("update")) {
		    	Account newAcc = new Account(id, user, pass, host, protocol, port, owner_id);
		    	acc.updateAccount(newAcc);
		    	
		    	session.setAttribute("success", "Conta atualizada com sucesso!");
		    } else if (action.equals("delete")) {
		    	acc.deleteAccount(id);
		    	
		    	session.setAttribute("success", "Conta deletada com sucesso!");
		    } 
		} catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
		}
		
		session.setAttribute("user", acc);	
		response.sendRedirect("/whalemail/");
	}

}
