package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import accountmanager.AccountManager;
import handlers.MailHandler;
import mail.Mail;

/**
 * Servlet implementation class DownloadsAttachments
 */
@WebServlet("/DownloadsAttachments")
public class DownloadsAttachments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadsAttachments() {
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
		HttpSession session = request.getSession();
		AccountManager acc = (AccountManager) session.getAttribute("user");
		
		String action = request.getParameter("action");
		String folder = request.getParameter("folder");
		
		int message = -1;
		if (request.getParameter("message") != null)
			message = Integer.parseInt(request.getParameter("message"));
  		
  		try {
  			if (action != null) {
  				if (action.equals("download_attachments")) {
  					Mail mail = acc.getMail(folder, message);
  					
  					
  					// BAIXAR OS ARQUIVOS ANEXADOS
  					
  					
  		  			
  					session.setAttribute("success", "Anexos baixados com sucesso!");
  				}
  			}
		} catch (Exception ex) {
			session.setAttribute("err", ex.getMessage());
		}
		
		session.setAttribute("user", acc);	
		response.sendRedirect("/whalemail/mail/");
	}

}
