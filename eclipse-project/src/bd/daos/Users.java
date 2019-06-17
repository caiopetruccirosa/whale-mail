package bd.daos;

import java.sql.*;
import java.util.*;

import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Users
{
	public static boolean existe(String user) throws Exception
    {
		BDSQLServer bd = new BDSQLServer();
		
		if (user == null || user.trim() == "")
			throw new Exception("Usu�rio nulo!");
		
        try
        {
    		bd.getCmd().prepareStatement ("SELECT * FROM [USERS] WHERE [USER] = ?");
            bd.getCmd().setString(1, user);
            MeuResultSet ret = (MeuResultSet)bd.getCmd().executeQuery();

            return ret.first();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao buscar usu�rio!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }
	
    public static boolean existe(String user, String pw) throws Exception
    {
    	if (user == null || user.trim() == "")
			throw new Exception("Usu�rio nulo!");
    	
    	if (pw == null || pw.trim() == "")
			throw new Exception("Senha nula!");
    	
    	BDSQLServer bd = new BDSQLServer();
    	
        try
        {
            bd.getCmd().prepareStatement ("SELECT * FROM [USERS] WHERE [USER] = ? AND [PW] = ?");
            bd.getCmd().setString(1, user);
            bd.getCmd().setString(2, pw);
            MeuResultSet ret = (MeuResultSet)bd.getCmd().executeQuery();

            return ret.first();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao buscar usu�rio!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static void cadastrar(User user) throws Exception
    {
        if (user == null)
            throw new Exception ("Usu�rio nulo!");

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement("INSERT INTO [USERS]([USER], [PW]) VALUES (?, ?)");

            bd.getCmd().setString(1, user.getUser());
            bd.getCmd().setString(2, user.getPassword());

            bd.getCmd().executeUpdate();
            bd.getCmd().commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao cadastrar usu�rio!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static void excluir(String user, int id) throws Exception
	{
        if (!existe(user))
            throw new Exception ("Usu�rio n�o cadastrado!");

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement("DELETE FROM [USERS] WHERE [ID]=?");
            bd.getCmd().setInt(1, id);
            bd.getCmd().executeUpdate();
            bd.getCmd().commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao excluir usu�rio!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static void alterar(User user) throws Exception
    {
        if (user == null)
            throw new Exception ("Usu�rio nulo!");

        if (!existe(user.getUser()))
            throw new Exception ("Usu�rio n�o cadastrado!");

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement ("UPDATE [USERS] SET [USER]=?, [PW]=? WHERE [ID] = ?");

            bd.getCmd().setString(1, user.getUser());
            bd.getCmd().setString(2, user.getPassword());
            bd.getCmd().setInt(3, user.getId());

            bd.getCmd().executeUpdate();
            bd.getCmd().commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao alterar usu�rio!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static User getUser(String username) throws Exception
    {
    	if (username == null || username.trim() == "")
			throw new Exception("Usu�rio nulo!");
    	
        User user = null;

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement ("SELECT * FROM [USERS] WHERE [USER] = ?");
            bd.getCmd().setString(1, username);
            MeuResultSet ret = (MeuResultSet)bd.getCmd().executeQuery ();

            if (!ret.first())
                throw new Exception ("Usu�rio n�o cadastrado!");

            user = new User(ret.getInt("ID"), ret.getString("USER"), ret.getString("PW"));
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar usu�rio!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }

        return user;
    }

    public static List<User> getUsers() throws Exception
    {
        ArrayList<User> users = new ArrayList<>();

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement("SELECT * FROM [USERS]");

            MeuResultSet ret = (MeuResultSet)bd.getCmd().executeQuery ();
            
            while (ret.next()) {
            	User aux = new User(ret.getInt("ID"), ret.getString("USER"), ret.getString("PW"));
            	users.add(aux);
            }
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar usu�rios!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }

        return users;
    }
}