package bd;

import bd.core.*;

public class BDSQLServer
{
    protected MeuPreparedStatement cmd;

    public BDSQLServer() throws Exception {
    	try
        {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String con = "jdbc:sqlserver://regulus:1433;databasename=PR317167";
            String user = "PR317167";
            String pw = "ClinicaMedica164";

            this.cmd = new MeuPreparedStatement(driver, con, user, pw);
        }
        catch (Exception ex)
        {
            throw new Exception("Não foi possível conectar com o Banco de Dados!");
        }
    }
    
    public MeuPreparedStatement getCmd() {
    	return this.cmd;
    }
}