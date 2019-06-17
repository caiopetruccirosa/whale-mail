package handlers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
            throw new FolderNotFoundException();

        if (folder.isOpen())
            folder.close(false);

        folder.renameTo(this.store.getFolder(newName));
    }

    public boolean createFolder(String name)  throws Exception {
        Folder folder = this.store.getFolder(name);

        if (!folder.exists())
            if (folder.create(Folder.HOLDS_MESSAGES))
                return true;

        return false;
    }

    public boolean deleteFolder(String name) throws Exception {
        Folder folder = this.store.getFolder(name);

        if (!folder.exists())
            throw new FolderNotFoundException();

        if (folder.isOpen())
            folder.close(false);

        return folder.delete(true);
    }
    
    public void setCurrent(String c) throws Exception {
    	if (c == null || c.trim() == "")
    		throw new Exception("Pasta inválida!");
    	
        if (!this.store.getFolder(c).exists())
            throw new FolderNotFoundException();
    	
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
        Folder folder = this.store.getFolder(folderName);

        if (!folder.isOpen())
        	folder.open(Folder.READ_ONLY);
        
        Message[] messages = folder.getMessages();
        Mail[] mails = new Mail[messages.length];
        
        for (int i = 0; i < messages.length; i++) {
        	InternetAddress addressFrom = (InternetAddress) messages[i].getFrom()[0];
        	String from = addressFrom.getPersonal();
        	
        	InternetAddress addressReplyTo = (InternetAddress) messages[i].getReplyTo()[0];
        	String replyTo = addressReplyTo.getPersonal();
        	
        	MimeMessage msg = (MimeMessage) messages[i].getContent();
        	Date date = messages[i].getSentDate();
        	
        	String subject = messages[i].getSubject();
        	
        	Mail aux = new Mail(from, replyTo, null, null, subject, msg, date);
        	
        	mails[i] = aux;
        }
        
        return mails;
    }
    
    public void moveMail() throws Exception {}
    
    public void deleteMail() throws Exception {}
    
    public void moveFolder() throws Exception {}
}