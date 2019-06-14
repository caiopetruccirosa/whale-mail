package bd.dbos;

public class User implements Cloneable
{
    protected int id;
    protected String user;
    protected String pass;

    public User(int id, String user, String pass) throws Exception
    {
        if (user == null || user.trim() == "")
            throw new Exception("Usuário inválido!");

        if (pass == null || pass.trim() == "")
            throw new Exception("Senha inválida!");

        this.id = id;
        this.user = user;
        this.pass = pass;
    }

    public int getId()
    {
        return this.id;
    }

    public String getUser()
    {
        return this.user;
    }

    public String getPassword()
    {
        return this.pass;
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

    public String toString()
    {
        return "{" + this.id + ":" + this.user + ":" + this.pass + "}";
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        User u = (User) obj;

        if (this.id != u.id)
            return false;

        if (!this.user.equals(u.user))
            return false;

        if (!this.pass.equals(u.pass))
            return false;

        return true;
    }

    public int hashCode()
    {
        int ret = 3;

        ret += new Integer(this.id).hashCode() * 7;
        ret += this.user.hashCode() * 7;
        ret += this.pass.hashCode() * 7;

        return ret;
    }

    public Object clone()
    {
        User ret = null;

        try
        {
            ret = new User(this);
        }
        catch (Exception ex) {}

        return ret;
    }

    public User(User m) throws Exception
    {
        if (m == null)
            throw new Exception("Modelo nulo!");

        this.id = m.id;
        this.user = m.user;
        this.pass = m.pass;
    }
}