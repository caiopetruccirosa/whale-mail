public class AccountManager 
{
    protected ArrayList<Account> accounts;
    protected int current;

    public AccountManager() 
    {
        this.accounts = new ArrayList<Account>();
        this.current = 0;
    }

    public void addAccount(Account a) throws Exception
    {
        if (a == null)
            throw new Exception("Conta inválido!");

        this.accounts.add(a);
    }
}