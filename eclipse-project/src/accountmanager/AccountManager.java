package accountmanager;

import bd.daos.*;
import bd.dbos.*;
import java.util.*;
import javax.mail.*;
import handlers.*;
import mail.*;

public class AccountManager
{
	protected User user;
	protected ArrayList<Account> accounts;
    protected FolderHandler folders;
    protected MailHandler mailer;
    protected int current;
    protected int page;

    public AccountManager(User user) throws Exception
    {
    	this.user = user;
    	
        this.accounts = Accounts.getAccounts(user.getId());
        
    	// pra teste, porque o BD não ta funfando em casa
    	//this.accounts = new ArrayList<Account>();
    	//this.accounts.add(new Account(1, "littleheadfilms@gmail.com", "EmailLH12", "smtp.gmail.com", "IMAP", 587, 1));
    	//this.accounts.add(new Account(2, "caiorosa123@gmail.com", "SenhaForte123", "smtp.gmail.com", "IMAP", 587, 1));
    	
    	
        if (this.accounts.size() > 0)
        	this.setCurrent(0);
        else
        	this.setCurrent(-1);
    }
    
    public boolean isValid() {
    	if (this.user == null)
    		return false;
    	
    	if (this.folders  == null || this.mailer == null)
    		return false;
    	
    	return true;
    }
    
    public Account getCurrentAccount() {
    	if (this.current < 0 || this.current >= this.accounts.size())
    		return null;
    	
    	return this.accounts.get(this.current);
    }
    
    public User getUser() {
    	return this.user;
    }
    
    public ArrayList<Account> getAccounts() {
    	return this.accounts;
    }
    
    public int getPage() {
    	return this.page;
    }

    public void addAccount(Account a) throws Exception
    {
        if (a == null)
            throw new Exception("Conta nula!");

        Accounts.cadastrar(a);
        this.accounts.add(a);
        
        if (this.accounts.size() > 0)
        	this.setCurrent(0);
        else
        	this.setCurrent(-1);
    }
    
    public void updateAccount(Account a) throws Exception {
    	if (a == null)
            throw new Exception("Conta nula!");
    	
    	Accounts.alterar(a);
    	
    	for (int i = 0; i < this.accounts.size(); i++) {
    		if (this.accounts.get(i).getId() == a.getId())
    			this.accounts.set(i, a);
    	}
        
        if (this.accounts.size() > 0)
        	this.setCurrent(0);
        else
        	this.setCurrent(-1);
    }
    
    public void deleteAccount(int id) throws Exception
    {
        Accounts.excluir(id);
    	
    	for (int i = 0; i < this.accounts.size(); i++) {
    		if (this.accounts.get(i).getId() == id)
    			this.accounts.remove(i);
    	}
        
        if (this.accounts.size() > 0)
        	this.setCurrent(0);
        else
        	this.setCurrent(-1);
    }
    
    public void setCurrent(int c) throws Exception {
    	if (c < -1 || c >= this.accounts.size())
    		throw new Exception("Conta inválida!");
    	
    	this.current = c;
    	this.page = 0;
    	
    	this.folders = null;
		this.mailer = null;
    	
    	if (c > -1) {
    		this.folders = new FolderHandler(this.accounts.get(this.current));
        	this.mailer = new MailHandler(this.accounts.get(this.current));
    	}
    }
    
    public void nextPage() {
    	if (this.page < 99)
    		this.page++;
    	else
    		this.page = 99;
    }
    
    public void previousPage() {
    	if (this.page > 0)
    		this.page--;
    	else
    		this.page = 0;
    }
    
    public void setCurrentFolder(String c) throws Exception {
    	if (c == null || c.trim() == "")
    		throw new Exception("Pasta inválida!");
    	
    	if (this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	this.folders.setCurrent(c);
    }
    
    public Mail[] getCurrentMails() throws Exception {
    	if (this.current < 0 || this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	return this.folders.getCurrentMails(this.page);
    }
    
    public Folder getCurrentFolder() throws Exception {
    	if (this.folders == null)
    		return null;
    	
    	return this.folders.getCurrent();
    }
    
    public Folder[] getCurrentFolders() throws Exception {
    	if (this.current < 0 || this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	return this.folders.getFolders();
    }
        
    public void deleteFolder(String folder) throws Exception {
    	if (folder == null || folder.trim() == "")
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	this.folders.deleteFolder(folder);
    }
    
    public void renameFolder(String name, String newName) throws Exception {
    	if (name == null || name.trim() == "")
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (newName == null || newName.trim() == "")
    		throw new Exception("Novo nome de pasta inválido!");
    	
    	if (this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	this.folders.renameFolder(name, newName);
    }
    
    public void createFolder(String folder) throws Exception {
    	if (folder == null || folder.trim() == "")
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	this.folders.createFolder(folder);
    }
    
    public void deleteMail(String folder, int message) throws Exception {
    	if (folder == null)
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (message < 0)
    		throw new Exception("Mensagem inválida!");
    	
    	if (this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	this.folders.deleteMail(folder, message);
    }

    public Mail getMail(String folder, int message) throws Exception {
    	if (folder == null || folder.trim() == "")
    		throw new Exception("Pasta inválida");
    	
    	if (message < 1)
    		throw new Exception("Mensagem inválida!");

    	if (this.folders == null)
    		throw new Exception("Selecione uma conta!");
    	
    	return this.folders.getMail(folder, message);
    }
}