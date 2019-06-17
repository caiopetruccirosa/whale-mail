package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import accountmanager.AccountManager;

/**
 * Servlet implementation class FolderOperations
 */
@WebServlet("/FolderOperations")
public class FolderOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FolderOperations() {
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
		
		String name = request.getParameter("name");
		String newName = request.getParameter("newName");
		
		HttpSession session = request.getSession();	
		AccountManager acc = (AccountManager) session.getAttribute("user");

		try {		
			if (action.equals("create")) {
				acc.createFolder(name);
			} else if (action.equals("rename")) {
				acc.renameFolder(name, newName);
			} else if (action.equals("delete")) {
				acc.deleteFolder(name);
			}
		} catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
		}
		
		session.setAttribute("user", acc);	
		response.sendRedirect("/whalemail/mail/");
	}

}
