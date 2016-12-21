package server.dbManagers;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class DbOrmManager
 * Interaction with Database
 * Contains connection-metadata (database, users, etc...)
 *
 * @version 1.0
 */
public class DbOrmManager
{
    private static  String hostName;
    private static  String dbName;
    private static  String userName;
    private static  String password;
    private static  int port;
    private Properties props;
    private String url;

    public DbOrmManager(String properties) {

        try
        {
            props=new Properties();
            props.load(new FileInputStream(properties));
            userName = props.getProperty("userName");
            hostName=props.getProperty("hostName");
            dbName=props.getProperty("dbName");
            password=props.getProperty("password");
            port=Integer.parseInt(props.getProperty("port"));

        } catch (IOException e)
        {

        }
    }

    public ConnectionSource getConnection()
    {
        url = "jdbc:mysql://"+hostName+":"+port+"/"+dbName;
        ConnectionSource connectionSource = null;
        try {
            connectionSource=new JdbcConnectionSource(url,userName, password);
        } catch (SQLException e) {e.printStackTrace();}
        return connectionSource;
    }
}
