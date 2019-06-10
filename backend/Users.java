package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Users
{
    public static boolean ehCadastrado(int id) throws Exception
    {
        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM USERS WHERE ID = ?");
            BDSQLServer.COMANDO.setInt (1, id);
            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery();

            return resultado.first();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao buscar usuário!");
        }

        return false;
    }

    public static void cadastrar(User user) throws Exception
    {
        if (user == null)
            throw new Exception ("Usuário nulo!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("INSERT INTO USERS(USER, PW) VALUES (?, ?)");

            BDSQLServer.COMANDO.setString(1, user.getUser());
            BDSQLServer.COMANDO.setString(2, livro.getPw());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao cadastrar usuário!");
        }
    }

    public static void excluir(int id) throws Exception
    {
        if (!ehCadastrado(id))
            throw new Exception ("Usuário não cadastrado!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("DELETE FROM USERS WHERE ID=?");
            BDSQLServer.COMANDO.setInt(1, id);
            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao excluir usuário!");
        }
    }

    public static void alterar(User user) throws Exception
    {
        if (user == null)
            throw new Exception ("Usuário nulo!");

        if (!ehCadastrado(user.getId()))
            throw new Exception ("Usuário não cadastrado!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("UPDATE USERS SET USER=?, PW=? WHERE ID = ?");

            BDSQLServer.COMANDO.setString(1, user.getUser());
            BDSQLServer.COMANDO.setString(2, user.getPw());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao alterar usuário!");
        }
    }

    public static User getUser(int id) throws Exception
    {
        User user = null;

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM USERS WHERE ID = ?");
            BDSQLServer.COMANDO.setInt (1, id);
            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Usuário não cadastrado!");

            user = new User(resultado.getInt("ID"), resultado.getString("USER"), resultado.getString("PW"));
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar usuário!");
        }

        return user;
    }

    public static MeuResultSet getUsers() throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            BDSQLServer.COMANDO.prepareStatement("SELECT * FROM USERS");

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar usuários!");
        }

        return resultado;
    }
}