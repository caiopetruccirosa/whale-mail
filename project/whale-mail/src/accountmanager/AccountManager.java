package accountmanager;

import bd.daos.*;
import bd.dbos.*;
import java.util.*;
import handlers.*;

public class AccountManager
{
	protected User user;
    public ArrayList<Account> accounts;
    protected FolderHandler folders;
    protected MailHandler mailer;
    protected int current;

    public AccountManager(User user) throws Exception
    {
    	this.user = user;
        this.accounts = Accounts.getAccounts(user.getId());
        this.current = 0;
        
        this.folders = new FolderHandler(this.accounts.get(this.current));
        this.mailer = new MailHandler();
    }

    public void addAccount(Account a) throws Exception
    {
        if (a == null)
            throw new Exception("Conta nula!");

        Accounts.cadastrar(a);
        this.accounts.add(a);
    }
    
    public void deleteAccount(int id) throws Exception
    {
        Accounts.excluir(id);
        this.accounts.forEach(acc -> {
	    	if (acc.getId() == id)
	    		this.accounts.remove(acc);
    	});
    }
    
    public void setCurrent(int c) throws Exception {
    	this.current = c;
    	this.folders = new FolderHandler(this.accounts.get(this.current));
    	this.mailer = new MailHandler();
    }
}