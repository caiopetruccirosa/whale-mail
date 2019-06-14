package bd;

import bd.core.*;
import bd.daos.*;

public class BDSQLServer
{
    public static final MeuPreparedStatement COMANDO;

    static
    {
    	MeuPreparedStatement cmd = null;

    	try
        {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String con = "jdbc:sqlserver://regulus:1433;databasename=PR317167";
            String user = "PR317167";
            String pw = "ClinicaMedica164";

            cmd = new MeuPreparedStatement(driver, con, user, pw);
        }
        catch (Exception ex)
        {
            System.err.println ("Não foi possível conectar com o Banco de Dados");
            System.exit(0);
        }

        COMANDO = cmd;
    }
}