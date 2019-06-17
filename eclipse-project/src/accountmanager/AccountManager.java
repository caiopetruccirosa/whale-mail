package accountmanager;

import bd.daos.*;
import bd.dbos.*;
import java.util.*;
import javax.mail.*;
import handlers.*;

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
    
    public ArrayList<Message> getCurrentMessages() throws Exception {
    	if (this.current < 0)
    		throw new Exception("Não foi possível listar as mensagens do e-mail atual!");
    	
    	ArrayList<Message> ret = new ArrayList<>();
    	
    	if (this.current == this.accounts.size()) {
    		// implementar pegar todos os emails de todos as contas
    		
    		this.folders = null;
    		this.mailer = null;
    	} else
    		ret.addAll(Arrays.asList(this.folders.getCurrentMails()));
    	
    	return ret;
    }
    
    public ArrayList<Folder> getCurrentFolders() throws Exception {
    	if (this.current < 0)
    		throw new Exception("Não foi possível listar as pastas do e-mail atual!");
    	
    	ArrayList<Folder> ret = new ArrayList<>();
    	
    	if (this.current == this.accounts.size()) {
    		// implementar pegar todas as pastas de todos as contas
    		
    		this.folders = null;
    		this.mailer = null;
    	} else
    		ret.addAll(Arrays.asList(this.folders.getFolders()));
    	
    	return ret;
    }
    
    public void moveMail() throws Exception {}
    
    public void deleteMail() throws Exception {}
    
    public void deleteFolder() throws Exception {}
    
    public void renameFolder() throws Exception {}
    
    public void createFolder() throws Exception {}
    
    public void moveFolder() throws Exception {}
}