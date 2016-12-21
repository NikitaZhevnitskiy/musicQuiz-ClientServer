

import dbObjects.dao.PlayerScoreDao;
import dbObjects.dao.QuestionDao;
import dbObjects.dao.impl.orm.QuestionDaoImplOrm;
import server.dbManagers.DbOrmManager;
import com.j256.ormlite.support.ConnectionSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by NIK on 09/11/2016.
 */
public class TestDbOrmManager
{
    private String rightProperties;
    private String wrongProperties;
    private DbOrmManager dbM;
    private ConnectionSource con;
    private String existingTableName;
    private String notExistingTableName;

    @Before
    public void setUp() throws IOException
    {
        existingTableName="PlayerScore";
        notExistingTableName="";
        rightProperties="src/main/resources/musicQuiz.properties";
        wrongProperties="wrongProp";
//        Properties prop = new Properties();
//        prop.load(new FileInputStream(rightProperties));

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
    public void isNotConnectedToDatabase_CheckOnQuestionDao() throws FileNotFoundException, SQLException
    {
        // Arrange
        // with wrongProperties file it will throw exception
        dbM = new DbOrmManager(wrongProperties);
//        dbM = new DbOrmManager(rightProperties);

        QuestionDao questionDao = mock(QuestionDaoImplOrm.class);
        // Act
        when(questionDao.getListOfQuestions()).thenCallRealMethod();
        // Assert
        questionDao.getListOfQuestions();

    }
    @Test(expected = NumberFormatException.class)
    public void isNotConnectedToDatabase_CheckOnPlayerScoreDao() throws FileNotFoundException, SQLException
    {
        // Arrange
        // with wrongProperties file it will throw exception
        dbM = new DbOrmManager(wrongProperties);
//        dbM = new DbOrmManager(rightProperties);
        PlayerScoreDao playerScoreDao = mock(PlayerScoreDao.class);
        // Act
        when(playerScoreDao.getTop10()).thenCallRealMethod();
        // Assert
        playerScoreDao.getTop10();

    }

}
