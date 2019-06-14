package folderhandler;

import javax.mail.*;

import bd.dbos.Account;

import java.util.*;

public class FolderHandler
{
	protected Account acc;
    protected Session session;
    protected Store store;

    public FolderHandler(Account acc) throws Exception
    {
        if (acc == null)
            throw new Exception("Conta nula!");

        Properties prop = new Properties();

        this.acc = acc;
        
        if (acc.getHost().equals("IMAP")) {
            prop.put("mail.imap.host", "imap.gmail.com");
            prop.put("mail.imap.port", "imap");
            prop.put("mail.imap.starttls.enable", "true");

            this.session = Session.getDefaultInstance(prop);
            this.store = this.session.getStore("imaps");
            this.store.connect("imap.gmail.com", acc.getUser(), acc.getPassword());
        }
        else if (acc.getHost().equals("POP3")) {
            prop = System.getProperties();

            this.session = Session.getDefaultInstance(prop);
            this.store = this.session.getStore("pop3s");
            this.store.connect("pop.gmail.com", acc.getUser(), acc.getPassword());
        }
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

    public Folder[] getFolders() throws Exception
    {
        return this.store.getDefaultFolder().list("*");
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
}