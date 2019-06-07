public class Account
{
    protected String user;
    protected String pass;
    protected String host;

    public Account(String user, String pass, String host) throws Exception
    {
        if (user == null || user.trim() == "")
            throw new Exception("Usuário inválido!");

        if (pass == null || pass.trim() == "")
            throw new Exception("Senha inválida!");

        if (host == null || host.trim() == "")
            throw new Exception("Host inválido!");

        this.user = user;
        this.pass = pass;
        this.host = host;
    }

    public String getUser()
    {
        return this.user;
    }

    public String getPassword()
    {
        return this.pass;
    }

    public String getHost()
    {
        return this.host;
    }

    public void setUser(String u) throws Exception
    {
        if (u == null || u.trim() == "")
            throw new Exception("Usuário inválido!");

        this.user = u;
    }

    public void setPassword(String p) throws Exception
    {
        if (p == null || p.trim() == "")
            throw new Exception("Senha inválido!");

        this.pass = p;
    }

    public void setHost(String h) throws Exception
    {
        if (h == null || h.trim() == "")
            throw new Exception("Host inválido!");

        this.host = h;
    }

    public String toString()
    {
        return "{" + this.user + ":" + this.pass + ":" + this.host + "}";
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        Account m = (Account) obj;

        if (!this.user.equals(m.user))
            return false;

        if (!this.pass.equals(m.pass))
            return false;

        if (!this.host.equals(m.host))
            return false;
        
        return true;
    }

    public int hashCode()
    {
        int ret = 3;

        ret += this.user.hashCode() * 7;
        ret += this.pass.hashCode() * 7;
        ret += this.host.hashCode() * 7;

        return ret;
    }

    public Object clone()
    {
        Account ret = null;

        try
        {
            ret = new Account(this);
        }
        catch (Exception ex) {}

        return ret;
    }

    public Account(Account m) throws Exception
    {
        if (m == null)
            throw new Exception("Modelo nulo!");

        this.user = m.user;
        this.pass = m.pass;
        this.host = m.host;
    }
}