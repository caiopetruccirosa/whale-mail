package bd.daos;

import bd.BDSQLServer;
import bd.core.MeuResultSet;
import bd.dbos.Account;

import java.sql.SQLException;

public class Accounts {
    public static boolean existe(int id) throws Exception
    {
        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM ACCOUNTS WHERE ID = ?");
            BDSQLServer.COMANDO.setInt(1, id);
            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery();

            return resultado.first();
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
            BDSQLServer.COMANDO.prepareStatement("INSERT INTO ACCOUNT(USER, PW, HOST) VALUES (?, ?, ?)");

            BDSQLServer.COMANDO.setString(1, acc.getUser());
            BDSQLServer.COMANDO.setString(2, acc.getPassword());
            BDSQLServer.COMANDO.setString(3, acc.getHost());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao cadastrar conta!");
        }
    }

    public static void excluir(Account acc) throws Exception
    {
        if (!existe(acc.getId()))
            throw new Exception ("Conta não cadastrada!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("DELETE FROM ACCOUNTS WHERE ID=?");
            BDSQLServer.COMANDO.setInt(1, acc.getId());
            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao excluir usuário!");
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
            BDSQLServer.COMANDO.prepareStatement ("UPDATE ACCOUNT SET USER=?, PW=?, HOST=? WHERE ID = ?");

            BDSQLServer.COMANDO.setString(1, acc.getUser());
            BDSQLServer.COMANDO.setString(2, acc.getPassword());
            BDSQLServer.COMANDO.setString(3, acc.getHost());
            BDSQLServer.COMANDO.setInt(4, acc.getId());

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
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM ACCOUNTS WHERE ID = ?");
            BDSQLServer.COMANDO.setInt(1, id);
            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Conta não cadastrada!");

            acc = new Account(resultado.getInt("ID"), resultado.getString("USER"), resultado.getString("PW"), resultado.getString("HOST"));
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar usuário!");
        }

        return acc;
    }

    public static MeuResultSet getAccounts() throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            BDSQLServer.COMANDO.prepareStatement("SELECT * FROM ACCOUNTS");

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar contas!");
        }

        return resultado;
    }
}
