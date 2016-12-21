package server.dbManagers;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


/**
 * Class DbMySQLManager
 * Interaction with Database
 * Contains connection-metadata (database, users, etc...)
 *
 * @version 1.0
 */
public class DbMySQLManager
{
    /**
     * Connection metadata
     */
    private static  String hostName;
    private static  String dbName;
    private static  String userName;
    private static  String password;
    private static  int port;
    private Properties props;

    public DbMySQLManager(String properties) {

        try
        {
            props=new Properties();
            FileInputStream fileInputStream = new FileInputStream(properties);
            props.load(fileInputStream);
            userName = props.getProperty("userName");
            hostName=props.getProperty("hostName");
            dbName=props.getProperty("dbName");
            password=props.getProperty("password");
            port=Integer.parseInt(props.getProperty("port"));
            fileInputStream.close();
        }
        catch (IOException e)
        {

        }
    }

    public Connection getConnection()
    {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setDatabaseName(dbName);
        ds.setServerName(hostName);
        ds.setUser(userName);
        ds.setPassword(password);
        ds.setPort(port);
        Connection con = null;
        try
        {
            con = ds.getConnection();

        } catch (SQLException e)
        {
            System.err.println("### Connection error ###");
            System.err.println(e.getMessage());
        }
        return con;
    }

}