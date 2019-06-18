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

    public AccountManager(User user) throws Exception
    {
    	this.user = user;
    	
        //this.accounts = Accounts.getAccounts(user.getId());
        
    	// pra teste, porque o BD não ta funfando em casa
    	this.accounts = new ArrayList<Account>();
    	this.accounts.add(new Account(1, "littleheadfilms@gmail.com", "EmailLH12", "smtp.gmail.com", "IMAP", 587, 1));
    	
        if (this.accounts.size() > 0)
        	this.setCurrent(0);
        else
        	this.setCurrent(-1);
    }
    
    public Account getCurrentAccount() throws Exception {
    	if (this.current < 0)
    		throw new Exception("Usuário não possui contas!");
    	
    	return this.accounts.get(this.current);
    }
    
    public User getUser() {
    	return this.user;
    }
    
    public ArrayList<Account> getAccounts() {
    	return this.accounts;
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
        this.accounts.forEach(acc -> {
	    	if (acc.getId() == a.getId())
	    		acc = a;
    	});
        
        if (this.accounts.size() > 0)
        	this.setCurrent(0);
        else
        	this.setCurrent(-1);
    }
    
    public void deleteAccount(int id) throws Exception
    {
        Accounts.excluir(id);
        this.accounts.forEach(acc -> {
	    	if (acc.getId() == id)
	    		this.accounts.remove(acc);
    	});
        
        if (this.accounts.size() > 0)
        	this.setCurrent(0);
        else
        	this.setCurrent(-1);
    }
    
    public void setCurrent(int c) throws Exception {
    	if (c < -1 || c > this.accounts.size())
    		throw new Exception("Conta inválida!");
    	
    	this.current = c;
    	
    	if (c > -1 && c < this.accounts.size()) {
    		this.folders = new FolderHandler(this.accounts.get(this.current));
        	this.mailer = new MailHandler(this.accounts.get(this.current));
    	} else {
    		this.folders = null;
    		this.mailer = null;
    	}
    }
    
    public Mail[][] getCurrentMessages() throws Exception {
    	if (this.current < 0)
    		throw new Exception("Não foi possível listar as mensagens do e-mail atual!");
    	
    	Mail[][] ret = new Mail[this.accounts.size()][];
    	
    	Mail[] aux = null;
    	if (this.current == this.accounts.size()) {
    		for (int i = 0; i < ret.length; i++) {
        		this.folders = new FolderHandler(this.accounts.get(i));
        		aux = this.folders.getCurrentMails();
        		ret[i] = new Mail[aux.length];
        		ret[i] = aux;
        	}
    		
    		this.folders = null;
    		this.mailer = null;
    	} else {
    		aux = this.folders.getCurrentMails();
    		ret[0] = new Mail[aux.length];
    		ret[0] = aux;
    	}
    	
    	return ret;
    }
    
    public Folder[][] getCurrentFolders() throws Exception {
    	if (this.current < 0)
    		throw new Exception("Não foi possível listar as pastas do e-mail atual!");
    	
    	Folder[][] ret = new Folder[this.accounts.size()][];
    	
    	Folder[] aux = null;
    	if (this.current == this.accounts.size()) {
    		for (int i = 0; i < ret.length; i++) {
        		this.folders = new FolderHandler(this.accounts.get(i));
        		
        		aux = this.folders.getCurrentFolders();
        		ret[i] = new Folder[aux.length];
        		ret[i] = aux;
        	}
    		
    		this.folders = null;
    		this.mailer = null;
    	} else {
    		aux = this.folders.getCurrentFolders();
    		ret[0] = new Folder[aux.length];
    		ret[0] = aux;
    	}
    	
    	return ret;
    }
        
    public void deleteFolder(String folderName) throws Exception {
    	if (folderName == null)
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (this.folders != null)
    		this.folders.deleteFolder(folderName);
    }
    
    public void renameFolder(String name, String newName) throws Exception {
    	if (name == null)
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (newName == null)
    		throw new Exception("Novo nome de pasta inválido!");
    	
    	if (this.folders != null)
    		this.folders.renameFolder(name, newName);
    }
    
    public void createFolder(String folderName) throws Exception {
    	if (folderName == null)
    		throw new Exception("Nome de pasta inválido!");
    	
    	if (this.folders != null)
    		this.folders.createFolder(folderName);
    }
    
    public void moveFolder() throws Exception {}
    
    public void moveMail() throws Exception {}
    
    public void deleteMail() throws Exception {}

}