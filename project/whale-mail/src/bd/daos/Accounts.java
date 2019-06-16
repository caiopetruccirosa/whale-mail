package bd.daos;

import java.sql.*;
import java.util.*;

import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Accounts {
    public static boolean existe(int id) throws Exception
    {
        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM ACCOUNTS WHERE [ID] = ?");
            BDSQLServer.COMANDO.setInt(1, id);
            MeuResultSet ret = (MeuResultSet)BDSQLServer.COMANDO.executeQuery();

            return ret.first();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao buscar conta!");
        }
    }

    public static void cadastrar(Account acc) throws Exception
    {
        if (acc == null)
            throw new Exception ("Conta nula!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("INSERT INTO ACCOUNT([USER], [PW], [HOST], [PROTOCOL], [PORT], [OWNER_ID]) VALUES (?, ?, ?, ?, ?, ?)");

            BDSQLServer.COMANDO.setString(1, acc.getUser());
            BDSQLServer.COMANDO.setString(2, acc.getPassword());
            BDSQLServer.COMANDO.setString(3, acc.getHost());
            BDSQLServer.COMANDO.setString(4, acc.getProtocol());
            BDSQLServer.COMANDO.setInt(5, acc.getPort());
            BDSQLServer.COMANDO.setInt(5, acc.getOwnerId()());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao cadastrar conta!");
        }
    }

    public static void excluir(int id) throws Exception
    {
        if (!existe(id))
            throw new Exception ("Conta não cadastrada!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("DELETE FROM ACCOUNTS WHERE [ID]=?");
            BDSQLServer.COMANDO.setInt(1, id);
            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao excluir conta!");
        }
    }

    public static void alterar(Account acc) throws Exception
    {
        if (acc == null)
            throw new Exception ("Conta nula!");

        if (!existe(acc.getId()))
            throw new Exception ("Conta não cadastrada!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("UPDATE ACCOUNT SET [USER] = ?, [PW] = ?, [HOST] = ?, [PROTOCOL] = ?, [PORT] = ?, [OWNER_ID] = ? WHERE ID = ?");

            BDSQLServer.COMANDO.setString(1, acc.getUser());
            BDSQLServer.COMANDO.setString(2, acc.getPassword());
            BDSQLServer.COMANDO.setString(3, acc.getHost());
            BDSQLServer.COMANDO.setInt(4, acc.getProtocol()());
            BDSQLServer.COMANDO.setInt(5, acc.getPort()());
            BDSQLServer.COMANDO.setInt(6, acc.getOwnerId()());
            BDSQLServer.COMANDO.setInt(7, acc.getId());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao alterar conta!");
        }
    }

    public static Account getAccount(int id) throws Exception
    {
        Account acc = null;

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM ACCOUNTS WHERE [ID] = ?");
            BDSQLServer.COMANDO.setInt(1, id);
            MeuResultSet ret = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!ret.first())
                throw new Exception ("Conta não cadastrada!");

            acc = new Account(ret.getInt("ID"), ret.getString("USER"), ret.getString("PW"), ret.getString("HOST"), ret.getString("PROTOCOL"), ret.getInt("PORT"), ret.getInt("OWNER_ID"));
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar conta!");
        }

        return acc;
    }

    public static ArrayList<Account> getAccounts(int ownerId) throws Exception
    {
        ArrayList<Account> accs = new ArrayList<>();

        try
        {
            BDSQLServer.COMANDO.prepareStatement("SELECT * FROM ACCOUNTS WHERE [OWNER_ID] = ?");

            MeuResultSet ret = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
            BDSQLServer.COMANDO.setInt(1, ownerId);
            
            while (ret.next()) {
            	Account aux = new Account(ret.getInt("ID"), ret.getString("USER"), ret.getString("PW"), ret.getString("HOST"), ret.getString("PROTOCOL"), ret.getInt("PORT"), ret.getInt("OWNER_ID"));
            	accs.add(aux);
            }
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar contas!");
        }

        return accs;
    }
}
