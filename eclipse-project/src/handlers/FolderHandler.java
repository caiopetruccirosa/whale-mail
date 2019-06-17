package handlers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import bd.dbos.*;
import mail.*;

import java.util.*;

public class FolderHandler
{
	protected Account acc;
    protected Session session;
    protected Store store;
    protected String current;

    public FolderHandler(Account acc) throws Exception
    {
        if (acc == null)
            throw new Exception("Conta nula!");

        this.acc = acc;
        
        Properties prop = new Properties();        
        prop.put("mail.imap.host", "imap.gmail.com");
        prop.put("mail.imap.port", "imap");
        prop.put("mail.imap.starttls.enable", "true");

        this.session = Session.getDefaultInstance(prop);
        this.store = this.session.getStore("imaps");
        this.store.connect("imap.gmail.com", acc.getUser(), acc.getPassword());
        
        this.current = "";
    }

    public void renameFolder(String name, String newName)  throws Exception
    {
        Folder folder = this.store.getFolder(name);

        if (!folder.exists())
        	throw new Exception("Pasta não encontrada!");

        if (folder.isOpen())
            folder.close(false);

        folder.renameTo(this.store.getFolder(newName));
    }

    public boolean createFolder(String name)  throws Exception {
        Folder folder = this.store.getFolder(name);

        if (!folder.exists()) {
        	if (folder.create(Folder.HOLDS_MESSAGES))
                return true;
        } else
        	throw new Exception("Pasta já existente!");

        return false;
    }

    public boolean deleteFolder(String name) throws Exception {
        Folder folder = this.store.getFolder(name);

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

    public Folder[] getCurrentFolders() throws Exception
    {
        return this.store.getFolder(this.current).list("*");
    }

    public Mail[] getCurrentMails() throws Exception
    {
    	return this.getMails(this.current);
    }
    
    public Mail[] getMails(String folderName) throws Exception
    {
    	if (folderName.equals(""))
    		folderName = "INBOX";
    	
    	Folder folder = this.store.getFolder(folderName);
    	folder.open(Folder.READ_ONLY);

        Message[] messages = folder.getMessages();
        Mail[] mails = new Mail[messages.length];
        
        int limit = messages.length;
        if (limit > 50)
        	limit = 50;
        
        for (int i = 0; i < limit; i++) {
        	InternetAddress addressFrom = (InternetAddress) messages[i].getFrom()[0];
        	String from = addressFrom.getPersonal();
        	
        	Object msg = messages[i].getContent();
        	Date date = messages[i].getSentDate();
        	
        	String subject = messages[i].getSubject();
        	
        	Mail aux = new Mail(from, this.acc.getUser(), null, null, subject, msg, date);
        	
        	mails[i] = aux;
        }
        
        return mails;
    }
    
    public void moveMail() throws Exception {}
    
    public void deleteMail() throws Exception {}
    
    public void moveFolder(String origin, String dest) throws Exception {}
}