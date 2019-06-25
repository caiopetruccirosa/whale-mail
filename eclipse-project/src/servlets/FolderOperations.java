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
		HttpSession session = request.getSession();	
		AccountManager acc = (AccountManager) session.getAttribute("user");
		
		String action = request.getParameter("action");
		String folder = request.getParameter("folder");
		
		try {
			if (action.equals("go_forward")) {
				acc.nextPage();
			} else if (action.equals("go_backwards")) {
				acc.previousPage();
			} else if (action.equals("change_folder")) {
				acc.setCurrentFolder(folder);
			}
		} catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
		}
		
		response.sendRedirect("/whalemail/mail/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		AccountManager acc = (AccountManager) session.getAttribute("user");
		
		String action = request.getParameter("action");
		
		String folder = request.getParameter("folder");
		String newFolder = request.getParameter("new_folder");
		
		int message = -1;
		if (request.getParameter("message") != null)
			message = Integer.parseInt(request.getParameter("message"));

		try {
			if (action != null) {
				if (action.equals("create")) {
					acc.createFolder(folder);
					session.setAttribute("success", "Pasta criada com sucesso!");
					acc.setCurrentFolder(folder);
				} else if (action.equals("rename")) {
					acc.renameFolder(folder, newFolder);
					session.setAttribute("success", "Pasta renomeada com sucesso!");
					acc.setCurrentFolder(newFolder);
				} else if (action.equals("delete")) {
					acc.deleteFolder(folder);
					acc.setCurrentFolder("INBOX");
					session.setAttribute("success", "Pasta deletada com sucesso!");
				} else if (action.equals("delete_mail")) {
					acc.deleteMail(folder, message);
					session.setAttribute("success", "E-mail deletado com sucesso!");
					acc.setCurrentFolder(folder);
				} else if (action.equals("move_mail")) {
					acc.moveMail(acc.getCurrentFolder().getName(), newFolder, message);
					session.setAttribute("success", "E-mail movido com sucesso!");
					acc.setCurrentFolder(folder);
				}
			}
		} catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
		}
		
		session.setAttribute("user", acc);	
		response.sendRedirect("/whalemail/mail/");
	}

}
