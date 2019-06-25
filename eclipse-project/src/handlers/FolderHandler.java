package handlers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeMultipart;

import bd.dbos.*;
import mail.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class FolderHandler
{
	protected Account acc;
    protected Session session;
    protected Store store;
    protected String current;
    protected final int PAGE_LIMIT;

    public FolderHandler(Account acc) throws Exception
    {
        if (acc == null)
            throw new Exception("Conta nula!");

        this.acc = acc;
        
        this.PAGE_LIMIT = 10;
        
        Properties props = System.getProperties();
    	
    	props.put("mail.smtp.host", this.acc.getHost());
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.user", this.acc.getUser());
    	props.put("mail.smtp.password", this.acc.getPassword());
    	props.put("mail.smtp.port", "587");
    	props.put("mail.smtp.auth", "true");
    	
    	Authenticator auth = new Authenticator() {
	    	public PasswordAuthentication getPasswordAuthentication() {
		    	String username = acc.getUser();
		    	String password = acc.getPassword();
		    	
		    	if ((username != null) && (username.length() > 0) && (password != null) && (password.length () > 0))
		    		return new PasswordAuthentication(username, password);
		    	
	    		return null;
	    	}
    	};
        
        this.session = Session.getInstance(props, auth);
        this.store = this.session.getStore("imaps");
        this.store.connect("imap.gmail.com", this.acc.getUser(), this.acc.getPassword());
        
        this.current = "INBOX";
    }

    public void renameFolder(String foldername, String newName)  throws Exception
    {
    	if (foldername == null || foldername.trim() == "")
    		throw new Exception("Pasta inválido!");
    	
    	if (newName == null || newName.trim() == "")
    		throw new Exception("Novo nome de pasta inválido!");
    	
        Folder folder = this.store.getFolder(foldername);

        if (!folder.exists())
        	throw new Exception("Pasta não encontrada!");

        if (folder.isOpen())
            folder.close(false);

        folder.renameTo(this.store.getFolder(newName));
    }

    public boolean createFolder(String foldername)  throws Exception {
    	if (foldername == null || foldername.trim() == "")
    		throw new Exception("Nome de pasta inválido!");
    	
        Folder folder = this.store.getFolder(foldername);

        if (!folder.exists()) {
        	if (folder.create(Folder.HOLDS_MESSAGES))
                return true;
        } else
        	throw new Exception("Pasta já existente!");

        return false;
    }

    public boolean deleteFolder(String foldername) throws Exception {
    	if (foldername == null || foldername.trim() == "")
    		throw new Exception("Nome de pasta inválido!");
    	
        Folder folder = this.store.getFolder(foldername);

        if (!folder.exists())
            throw new Exception("Pasta não encontrada!");

        if (folder.isOpen())
            folder.close(false);

        return folder.delete(true);
    }
    
    public void setCurrent(String c) throws Exception {
    	if (c == null || c.trim() == "")
    		throw new Exception("Pasta inválida!");
    	
        if (!this.store.getFolder(c).exists())
        	throw new Exception("Pasta não encontrada!");
    	
    	this.current = c;
    }
    
    public Folder getCurrent() throws Exception {
    	return this.store.getFolder(this.current);
    }

    public Folder[] getFolders() throws Exception
    {
        return this.store.getDefaultFolder().list("*");
    }

    public Mail[] getCurrentMails(int page) throws Exception
    {
    	return this.getMails(this.current, page);
    }
    
    public Mail[] getMails(String foldername, int page) throws Exception
    {
    	if (foldername == null || foldername.trim() == "")
    		throw new Exception("Pasta inválido!");
    	
    	Folder folder = this.store.getFolder(foldername);
    	
    	if (!folder.exists())
    		throw new Exception("Pasta não encontrada!");
    	
    	folder.open(Folder.READ_ONLY);

    	int limit = folder.getMessageCount();
    	int max = limit - (page * this.PAGE_LIMIT);
    	int min = max - this.PAGE_LIMIT;
    	
    	if (limit < 1 || max < 1)
    		return null;
    	
    	if (min < 1)
    		min = 1;
    	
        Message[] messages = folder.getMessages(min, max);
        Mail[] mails = new Mail[messages.length];
        
        for (int i = 0; i < messages.length; i++) {
        	InternetAddress addressFrom = (InternetAddress) messages[i].getFrom()[0];
        	String from = addressFrom.getPersonal();
        	
        	Object msg = messages[i].getContent();
        	Date date = messages[i].getSentDate();
        	String to = this.acc.getUser();
        	String subject = messages[i].getSubject();
        	int id = messages[i].getMessageNumber();
        	
        	Mail aux = new Mail(id, from, to, null, null, subject, msg, date, null);
        	
        	mails[i] = aux;
        }
        
        return mails;
    }
    
    public void deleteMail(String foldername, int message) throws Exception {
    	if (foldername == null || foldername.trim() == "")
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (message < 0)
    		throw new Exception("Mensagem inválida!");
    	
    	Folder folder = this.store.getFolder(foldername);
    	folder.open(Folder.READ_WRITE);
    	
    	Message msg = folder.getMessage(message);
    	msg.setFlag(Flags.Flag.DELETED, true);
    }
    
    public ArrayList<File> getAttachments(Object msg) throws Exception
    {
    	if (!(msg instanceof Multipart))
    		return null;
    	
        Multipart multipart = (Multipart) msg;

        ArrayList<File> attachments = new ArrayList<File>();
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && (bodyPart.getFileName() == null || bodyPart.getFileName().isEmpty())) {
                continue; // dealing with attachments only
            }
            InputStream is = bodyPart.getInputStream();
            File f = new File(bodyPart.getFileName());
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[4096];
            int bytesRead;
            while((bytesRead = is.read(buf))!=-1) {
                fos.write(buf, 0, bytesRead);
            }
            fos.close();
            attachments.add(f);
        }

        return attachments;
    }
    
    public Mail getMail(String foldername, int message) throws Exception {
    	if (foldername == null || foldername.trim() == "")
    		throw new Exception("Pasta inválida");
    	
    	if (message < 1)
    		throw new Exception("Mensagem inválida!");
    	
    	Folder folder = this.store.getFolder(foldername);
    	
    	if (!folder.exists())
    		throw new Exception("Pasta não encontrada!");
    	
    	folder.open(Folder.READ_ONLY);
    	
        Message msg = folder.getMessage(message);
        
        InternetAddress addressFrom = (InternetAddress) msg.getFrom()[0];
    	String from = addressFrom.getPersonal();
    	   	
    	Object obj = msg.getContent();    	
    	Date date = msg.getSentDate();
    	String to = this.acc.getUser();
    	String subject = msg.getSubject();
    	int id = msg.getMessageNumber();
    	ArrayList<File> atts = this.getAttachments(obj);
    	
    	return new Mail(id, from, to, null, null, subject, obj, date, atts);
    }
}