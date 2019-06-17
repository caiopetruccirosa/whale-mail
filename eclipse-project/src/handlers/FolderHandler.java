package handlers;

import javax.mail.*;
import bd.dbos.*;
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
        
        this.current = "INBOX";
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
    	
    	// PODERIA HAVER UMA CHECAGEM SE A PASTA REALMENTE EXISTE, PORÉM JÁ TEM MUITA COISA PRA FAZER
    	
    	this.current = c;
    }

    public Folder[] getFolders() throws Exception
    {
        return this.store.getDefaultFolder().list("*");
    }

    public Message[] getCurrentMails() throws Exception
    {
    	return this.getMails(this.current);
    }
    
    public Message[] getMails(String folderName) throws Exception
    {
        Folder folder = this.store.getFolder(folderName);

        try {
            folder.open(Folder.READ_ONLY);

            return folder.getMessages();
        } finally {
            folder.close(false);
        }
    }
    
    public void moveMail() throws Exception {}
    
    public void deleteMail() throws Exception {}
    
    public void moveFolder() throws Exception {}
}