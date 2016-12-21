package dbObjects.dao.impl.orm;

import dbObjects.dao.QuestionDao;
import dbObjects.model.Question;
import server.dbManagers.DbOrmManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * QuestionDaoImplOrm class
 *
 * impl serializable:
 *      need to send instances through Streams
 * impl QuestionDao
 *      need to impl exact methods from interface
 *
 * */
public class QuestionDaoImplOrm implements QuestionDao, Serializable
{
    //private ConnectionSource connectionSource;
    private DbOrmManager dbOrmManager;
    private Dao<Question, String> questionDao;


    public QuestionDaoImplOrm(DbOrmManager dbOrmManager)
    {
        this.dbOrmManager=dbOrmManager;
    }

    @Override
    public ArrayList<Question> getListOfQuestions()
    {
        ArrayList<Question> listOfQuestions = new ArrayList<>();
        try(ConnectionSource connectionSource = dbOrmManager.getConnection()){
            questionDao = DaoManager.createDao(
                    connectionSource, Question.class);


            listOfQuestions=(ArrayList<Question>)questionDao.queryForAll();
        }catch (Exception e){}
        return listOfQuestions;
    }
}
