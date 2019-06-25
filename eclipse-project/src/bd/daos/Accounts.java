package bd.daos;

import java.sql.*;
import java.util.*;

import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Accounts {	
    public static boolean existe(int id) throws Exception
    {
    	BDSQLServer bd = new BDSQLServer();
    	
        try
        {        	
            bd.getCmd().prepareStatement("SELECT * FROM [ACCOUNTS] WHERE [ID] = ?");
            bd.getCmd().setInt(1, id);
            MeuResultSet ret = (MeuResultSet)bd.getCmd().executeQuery();

            return ret.first();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao buscar conta!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static void cadastrar(Account acc) throws Exception
    {
        if (acc == null)
            throw new Exception ("Conta nula!");

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement("INSERT INTO [ACCOUNTS]([USER], [PW], [HOST], [PROTOCOL], [PORT], [OWNER_ID]) VALUES (?, ?, ?, ?, ?, ?)");

            bd.getCmd().setString(1, acc.getUser());
            bd.getCmd().setString(2, acc.getPassword());
            bd.getCmd().setString(3, acc.getHost());
            bd.getCmd().setString(4, acc.getProtocol());
            bd.getCmd().setInt(5, acc.getPort());
            bd.getCmd().setInt(6, acc.getOwnerId());

            bd.getCmd().executeUpdate();
            bd.getCmd().commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao cadastrar conta!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static void excluir(int id) throws Exception
    {
        if (!existe(id))
            throw new Exception ("Conta não cadastrada!");

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement("DELETE FROM [ACCOUNTS] WHERE [ID]=?");
            bd.getCmd().setInt(1, id);
            bd.getCmd().executeUpdate();
            bd.getCmd().commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao excluir conta!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static void alterar(Account acc) throws Exception
    {
        if (acc == null)
            throw new Exception ("Conta nula!");

        if (!existe(acc.getId()))
            throw new Exception ("Conta não cadastrada!");

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement ("UPDATE [ACCOUNTS] SET [USER] = ?, [PW] = ?, [HOST] = ?, [PROTOCOL] = ?, [PORT] = ?, [OWNER_ID] = ? WHERE [ID] = ?");

            bd.getCmd().setString(1, acc.getUser());
            bd.getCmd().setString(2, acc.getPassword());
            bd.getCmd().setString(3, acc.getHost());
            bd.getCmd().setString(4, acc.getProtocol());
            bd.getCmd().setInt(5, acc.getPort());
            bd.getCmd().setInt(6, acc.getOwnerId());
            bd.getCmd().setInt(7, acc.getId());

            bd.getCmd().executeUpdate();
            bd.getCmd().commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao alterar conta!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }
    }

    public static Account getAccount(int id) throws Exception
    {
        Account acc = null;

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement ("SELECT * FROM [ACCOUNTS] WHERE [ID] = ?");
            bd.getCmd().setInt(1, id);
            
            MeuResultSet ret = (MeuResultSet)bd.getCmd().executeQuery ();

            if (!ret.first())
                throw new Exception ("Conta não cadastrada!");

            acc = new Account(ret.getInt("ID"), ret.getString("USER"), ret.getString("PW"), ret.getString("HOST"), ret.getString("PROTOCOL"), ret.getInt("PORT"), ret.getInt("OWNER_ID"));
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar conta!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }

        return acc;
    }

    public static ArrayList<Account> getAccounts(int ownerId) throws Exception
    {
        ArrayList<Account> accs = new ArrayList<>();

        BDSQLServer bd = new BDSQLServer();
        
        try
        {
            bd.getCmd().prepareStatement("SELECT * FROM [ACCOUNTS] WHERE [OWNER_ID] = ?");
            bd.getCmd().setInt(1, ownerId);
            
            MeuResultSet ret = (MeuResultSet)bd.getCmd().executeQuery();
            
            while (ret.next()) {
            	Account aux = new Account(ret.getInt("ID"), ret.getString("USER"), ret.getString("PW"), ret.getString("HOST"), ret.getString("PROTOCOL"), ret.getInt("PORT"), ret.getInt("OWNER_ID"));
            	accs.add(aux);
            }
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar contas!");
        }
        finally
        {
        	if (bd != null)
        		bd.getCmd().close();
        }

        return accs;
    }
}
