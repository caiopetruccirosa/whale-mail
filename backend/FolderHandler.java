public class FolderHandler 
{
    protected Session session;
    protected Store store;

    public FolderHandler(String protocol)
    {
        if (protocol == null || protocol.trim() == "")
            throw new Exception("Protocolo nulo");

        Properties prop = new Properties()

        if (protocol.equals("IMAP")) {
            prop.put("mail.imap.host", "imap.gmail.com");
            prop.put("mail.imap.port", "imap");
            prop.put("mail.imap.starttls.enable", "true");

            this.session = Session.getDefaultInstance(prop);
            this.store = this.session.getStore("imaps");
            this.store.connect("imap.gmail.com", user, pass);
        }
        else if (protocol.equals("POP3")) {
            prop = System.getProperties();

            this.session = Session.getDefaultInstance(prop);
            this.store = this.session.getStore("pop3s");
            this.store.connect("pop.gmail.com", user, pass);
        }
        else
            throw new Exception("Protocolo inválido");
    }

    public void renameFolder(String name, String newName)
    {
        Folder folder = this.store.getFolder(name);

        if (!folder.exists())
            throw new FolderNotFoundException();

        if (folder.isOpen())
            folder.close();

        folder.renameTo(this.store.getFolder(newName));
    }

    public boolean createFolder() {
        Folder folder = this.store.getFolder(name);

        if (!folder.exists())
            if (folder.create(Folder.HOLDS_MESSAGES))
                return true;

        return false;
    }

    public boolean deleteFolder() {
        Folder folder = this.store.getFolder(name);

        if (!folder.exists())
            throw new FolderNotFoundException();

        if (folder.isOpen())
            folder.close();

        return folder.delete(true);
    }

    public Folder[] getFolders()
    {
        return this.store.getDefaultFolder().list("*");
        String[] foldersNames = new String[folder.length];
    }

    public Message[] getMails(String folderName)
    {
        Folder folder = this.store.getFolder(folderName);

        try {
            folder.open(Folder.READ_ONLY);

            return folder.getMessages();
        } finally {
            folder.close();
        }
    }
}