package servlets;

import java.io.*;
import java.io.IOException;

import javax.activation.FileDataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import accountmanager.AccountManager;
import mail.*;
import java.util.*;
import handlers.MailHandler;
import accountmanager.AccountManager;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Servlet implementation class MailOperations
 */
@WebServlet("/MailOperations")
public class MailOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailOperations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		AccountManager acc = (AccountManager) session.getAttribute("user");
		String from = acc.getUser().getUser();
		String[] to = request.getParameter("to").split(null);
		String subject = request.getParameter("Subject");
		String[] cc = request.getParameter("cc").split(null);
		String[] cco = request.getParameter("cco").split(null);
		Object message = request.getParameter("message_area");
		Date sentDate = new Date();	
		
		try {
		ArrayList<File> files = saveUploadedFiles(request);
		ArrayList<Attachment> at_array = new ArrayList<>();
		for(int i=0; i<files.size() ;i++) {
			FileDataSource fds = new FileDataSource(files.get(i));
			Attachment at = new Attachment(fds, fds.getName(), null);
			at_array.add(at);
		}
			Mail mail = new Mail(from, to, cc, cco, subject, message, sentDate, at_array);
			MailHandler handler = new MailHandler(acc.getCurrentAccount());
			handler.sendEmail(mail, "text/html");
		}
		catch(Exception ex){
			ex.getMessage();
		}
		
	}
	
	private ArrayList<File> saveUploadedFiles(HttpServletRequest request)
            throws IllegalStateException, IOException, ServletException {
        ArrayList<File> listFiles = new ArrayList<File>();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        Collection<Part> multiparts = request.getParts();
     
        if (multiparts.size() > 0) {
            for (Part part : request.getParts()) {
                // creates a file to be saved
                String fileName = extractFileName(part);
                if (fileName == null || fileName.equals("")) {
                    // not attachment part, continue
                    continue;
                }

                File saveFile = new File(fileName);
                System.out.println("saveFile: " + saveFile.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(saveFile);

                // saves uploaded file
                InputStream inputStream = part.getInputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();

                listFiles.add(saveFile);
            }
        }
        return listFiles;
    }

    /**
     * Retrieves file name of a upload part from its HTTP header
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    /**
     * Deletes all uploaded files, should be called after the e-mail was sent.
     */
    private void deleteUploadFiles(List<File> listFiles) {
        if (listFiles != null && listFiles.size() > 0) {
            for (File aFile : listFiles) {
                aFile.delete();
            }
        }
    }

}
