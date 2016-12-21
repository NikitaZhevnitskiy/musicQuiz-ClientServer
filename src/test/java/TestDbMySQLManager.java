import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.dbManagers.DbMySQLManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by NIK on 09/11/2016.
 */
public class TestDbMySQLManager
{
    private String rightProperties;
    private String wrongProperties;
    private DbMySQLManager dbM;

    private Connection con;

    @Before
    public void setUp() throws IOException
    {
        rightProperties="src/main/resources/musicQuiz.properties";

        wrongProperties="wrongProp";
        File file = new File(wrongProperties);
        file.createNewFile();
    }

    @After
    public void tearDown()
    {
        File file = new File(wrongProperties);
        file.delete();
    }

    @Test(expected = NumberFormatException.class)
    public void isNotConnectedToDatabase() throws FileNotFoundException, SQLException
    {
        //Arrange
        dbM = new DbMySQLManager(wrongProperties);
        con = dbM.getConnection();

        //Act
        boolean connected = con.isValid(5000);

        //Assert
        assertFalse(connected);
    }

    @Test
    public void isConnectedToDatabase() throws FileNotFoundException, SQLException
    {
        //Arrange
        dbM = new DbMySQLManager(rightProperties);
        con = dbM.getConnection();

        //Act
        boolean connected = con.isValid(5000);

        //Assert
        assertTrue(connected);
    }
}
