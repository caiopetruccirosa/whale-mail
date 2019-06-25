package handlers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;

import bd.dbos.*;
import mail.*;

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
        
        Properties prop = new Properties();        
        prop.put("mail.imap.host", "imap.gmail.com");
        prop.put("mail.imap.port", "imap");
        prop.put("mail.imap.starttls.enable", "true");

        this.session = Session.getDefaultInstance(prop);
        this.store = this.session.getStore("imaps");
        this.store.connect("imap.gmail.com", acc.getUser(), acc.getPassword());
        
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
        	String[] to = new String[1];
        	to[0] = this.acc.getUser();
        	String subject = messages[i].getSubject();
        	int id = messages[i].getMessageNumber();
        	
        	
        	Mail aux = new Mail(id, from, to, null, null, subject, msg, date, null, foldername);
        	
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
    
    public Mail getMail(String foldername, int message) throws Exception {
    	if (foldername == null || foldername.trim() == "")
    		throw new Exception("Pasta inválida");
    	
    	if (message < 1)
    		throw new Exception("Mensagem inválida!");
    	
    	Folder folder = this.store.getFolder(foldername);
    	
    	if (!folder.exists())
    		throw new Exception("Pasta não encontrada!");
    	
    	folder.open(Folder.READ_ONLY);
    	
        Message msgObj = folder.getMessage(message);
        
        InternetAddress addressFrom = (InternetAddress) msgObj.getFrom()[0];
    	String from = addressFrom.getPersonal();
    	
    	Object msg = msgObj.getContent();
    	Date date = msgObj.getSentDate();
    	String[] to = new String[1];
    	to[0] = this.acc.getUser();
    	String subject = msgObj.getSubject();
    	int id = msgObj.getMessageNumber();
    	
    	return new Mail(id, from, to, null, null, subject, msg, date, null, foldername);
    }
}