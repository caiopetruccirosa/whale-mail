package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Livros
{
    public static boolean cadastrado (int codigo) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LIVROS " +
                  "WHERE CODIGO = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            retorno = resultado.first();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar livro");
        }

        return retorno;
    }

    public static void incluir (Livro livro) throws Exception
    {
        if (livro==null)
            throw new Exception ("Livro nao fornecido");

        try
        {
            String sql;

            sql = "INSERT INTO LIVROS " +
                  "(CODIGO,NOME,PRECO) " +
                  "VALUES " +
                  "(?,?,?)";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt    (1, livro.getCodigo ());
            BDSQLServer.COMANDO.setString (2, livro.getNome ());
            BDSQLServer.COMANDO.setFloat  (3, livro.getPreco ());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao inserir livro");
        }
    }

    public static void excluir (int codigo) throws Exception
    {
        if (!cadastrado (codigo))
            throw new Exception ("Nao cadastrado");

        try
        {
            BDSQLServer.COMANDO.prepareStatement("DELETE FROM LIVROS WHERE CODIGO=?");

            BDSQLServer.COMANDO.setInt(1, codigo);

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao excluir livro");
        }
    }

    public static void alterar (Livro livro) throws Exception
    {
        if (livro==null)
            throw new Exception ("Livro nao fornecido");

        if (!cadastrado (livro.getCodigo()))
            throw new Exception ("Nao cadastrado");

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("UPDATE LIVROS SET NOME=? , PRECO=? WHERE CODIGO = ?");

            BDSQLServer.COMANDO.setString(1, livro.getNome ());
            BDSQLServer.COMANDO.setFloat(2, livro.getPreco ());
            BDSQLServer.COMANDO.setInt(3, livro.getCodigo ());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao atualizar dados de livro");
        }
    }

    public static Livro getLivro (int codigo) throws Exception
    {
        Livro livro = null;

        try
        {
            BDSQLServer.COMANDO.prepareStatement ("SELECT * FROM LIVROS WHERE CODIGO = ?");
            BDSQLServer.COMANDO.setInt (1, codigo);
            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            livro = new Livro (resultado.getInt("CODIGO"), resultado.getString("NOME"), resultado.getFloat("PRECO"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar livro");
        }

        return livro;
    }

    public static MeuResultSet getLivros () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            BDSQLServer.COMANDO.prepareStatement("SELECT * FROM LIVROS");

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar livros");
        }

        return resultado;
    }
}