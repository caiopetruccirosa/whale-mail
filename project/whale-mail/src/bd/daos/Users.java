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
        try
        {

    		BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM USERS WHERE USER = ?");
            BDSQLServer.COMANDO.setString(1, user);
            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery();

            return resultado.first();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao buscar usuário!");
        }
    }
	
    public static boolean existe(String user, String pw) throws Exception
    {
        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM USERS WHERE USER = ? AND PW = ?");
            BDSQLServer.COMANDO.setString(1, user);
            BDSQLServer.COMANDO.setString(2, pw);
            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery();

            return resultado.first();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao buscar usuário!");
        }
    }

    public static void cadastrar(User user) throws Exception
    {
        if (user == null)
            throw new Exception ("Usuário nulo!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("INSERT INTO USERS(USER, PW) VALUES (?, ?)");

            BDSQLServer.COMANDO.setString(1, user.getUser());
            BDSQLServer.COMANDO.setString(2, user.getPassword());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao cadastrar usuário!");
        }
    }

    public static void excluir(User user) throws Exception
        {
        if (!existe(user.getUser(), user.getPassword()))
            throw new Exception ("Usuário não cadastrado!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("DELETE FROM USERS WHERE ID=?");
            BDSQLServer.COMANDO.setInt(1, user.getId());
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

        if (!existe(user.getUser(), user.getPassword()))
            throw new Exception ("Usuário não cadastrado!");

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("UPDATE USERS SET USER=?, PW=? WHERE ID = ?");

            BDSQLServer.COMANDO.setString(1, user.getUser());
            BDSQLServer.COMANDO.setString(2, user.getPassword());
            BDSQLServer.COMANDO.setInt(3, user.getId());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao alterar usuário!");
        }
    }

    public static User getUser(String username) throws Exception
    {
        User user = null;

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM USERS WHERE USER = ?");
            BDSQLServer.COMANDO.setString(1, username);
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

    public static List<User> getUsers() throws Exception
    {
        ArrayList<User> users = new ArrayList<>();

        try
        {
            BDSQLServer.COMANDO.prepareStatement("SELECT * FROM USERS");

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
            
            while (resultado.next()) {
            	User aux = new User(resultado.getInt("ID"), resultado.getString("USER"), resultado.getString("PW"));
            	users.add(aux);
            }
        }
        catch (SQLException ex)
        {
            throw new Exception ("Erro ao procurar usuários!");
        }

        return users;
    }
}