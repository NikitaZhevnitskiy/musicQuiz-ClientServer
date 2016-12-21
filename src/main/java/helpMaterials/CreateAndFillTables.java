package helpMaterials;

import dbObjects.model.PlayerScore;
import dbObjects.model.Question;
import server.dbManagers.DbMySQLManager;
import server.dbManagers.DbOrmManager;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Run class and it will create
 * in database 2 tables (Question and PlayerScore)
 * and fill them
 *
 * make corrections in db.properies
 *
 * src/main/java/musicQuiz.helpMaterials.helpMaterials/question.sql
 */
public class CreateAndFillTables
{
    public static void main(String[] args) throws SQLException, FileNotFoundException
    {
        String questionFilePath= "src/main/java/helpMaterials/helpMaterials/question.sql";
        String playerScoreFilePath= "src/main/java/helpMaterials/helpMaterials/playerScore.sql";
        String propertyFile="src/main/resources/musicQuiz.properties";

        DbOrmManager dbOrmManager = new DbOrmManager(propertyFile);

        // drop tables if exists
        TableUtils.dropTable(dbOrmManager.getConnection(),Question.class,true);
        TableUtils.dropTable(dbOrmManager.getConnection(),PlayerScore.class,true);
        System.out.println("### Tales are dropped if exists");

        // create tables using ORM
        int tableQuestionExists = TableUtils.createTableIfNotExists(dbOrmManager.getConnection(), Question.class);
        int tablePlayerScoreExists = TableUtils.createTableIfNotExists(dbOrmManager.getConnection(), PlayerScore.class);
        System.out.println("### Tables PlayerScore and Question were created");

        DbMySQLManager dbMySQLManager = new DbMySQLManager(propertyFile);
        // fill tables using plain SQL
        fillQuestionTable(dbMySQLManager, questionFilePath);
        fillPlayerScoreTable(dbMySQLManager, playerScoreFilePath);
        //System.out.println(getQuestionFillQuery(playerScoreFilePath));
        System.out.println("### Default data added");
    }

    private static void fillQuestionTable(DbMySQLManager dbMySQLManager, String fileName) throws FileNotFoundException
    {
        try(Connection connection = dbMySQLManager.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(getQuestionFillQuery(fileName)))
        {
            preparedStmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private static void fillPlayerScoreTable(DbMySQLManager dbMySQLManager, String fileName) throws FileNotFoundException
    {
        try(Connection connection = dbMySQLManager.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(getQuestionFillQuery(fileName)))
        {
            preparedStmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private static String getQuestionFillQuery(String fileName)throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(fileName));
        StringBuilder sql = new StringBuilder();

        while(scanner.hasNext())
        {
            sql.append(scanner.nextLine());
        }
        //System.out.println(sql.toString());
        return sql.toString();

    }

}
