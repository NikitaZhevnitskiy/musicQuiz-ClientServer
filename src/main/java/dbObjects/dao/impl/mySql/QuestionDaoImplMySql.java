package dbObjects.dao.impl.mySql;

import dbObjects.dao.QuestionDao;
import dbObjects.model.Question;
import server.dbManagers.DbMySQLManager;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * QuestionDaoImplMySql class
 *
 * impl serializable:
 *      need to send instances through Streams
 * impl QuestionDao
 *      need to impl exact methods from interface
 *
 * */
public class QuestionDaoImplMySql implements QuestionDao, Serializable
{
    DbMySQLManager dbMySQLManager;

    public QuestionDaoImplMySql(DbMySQLManager dbManager){
        dbMySQLManager=dbManager;
    }

    @Override
    public ArrayList<Question> getListOfQuestions()
    {
        ArrayList<Question> list = new ArrayList<>();
        String sql = "SELECT ID, TEXT, HIT, ANSWER FROM QUESTION";

        try (Connection connection = dbMySQLManager.getConnection();
             PreparedStatement selectAll = connection.prepareStatement(
                sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            ResultSet rs = selectAll.executeQuery();
            while(rs.next())
            {
                list.add(new Question(
                        rs.getInt("ID"),
                        rs.getString("TEXT"),
                        rs.getString("HIT"),
                        rs.getString("ANSWER")
                    )
                );
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list;
    }


}
