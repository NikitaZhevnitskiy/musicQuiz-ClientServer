

import server.dbManagers.DbMySQLManager;
import server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static junit.framework.Assert.assertTrue;

/**
 * Created by NIK on 09/11/2016.
 */
public class TestServer
{
    private Server server;
    private String propertyFile;
    @Before
    public void setUp() throws IOException
    {
        propertyFile="src/main/resources/musicQuiz.properties";
        server=new Server(new DbMySQLManager(propertyFile));
    }

    @After
    public void tearDown()
    {
        server=null;
    }

    @Test//(expected = NullPointerException.class)
    public void serverInit_checkPortSuccess() throws IOException
    {
        // Arrange
        ServerSocket sSocket;
        int localPort;

        // Act
        server.initServer();
        sSocket = server.getServerSocket();
        localPort = sSocket.getLocalPort();

        // Assert
        assertTrue(localPort==3333);
    }

}
