package dbObjects.dao.impl.orm;

import dbObjects.dao.PlayerScoreDao;
import dbObjects.model.PlayerScore;
import server.dbManagers.DbOrmManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * PlayerScoreImplOrm class
 *
 * impl serializable:
 *      need to send instances through Streams
 * impl QuestionDao
 *      need to impl exact methods from interface
 *
 * */
public class PlayerScoreImplOrm implements PlayerScoreDao, Serializable
{
    //private ConnectionSource connectionSource;
    private DbOrmManager dbOrmManager;
    private Dao<PlayerScore, Integer> playerScoreDao;


    public PlayerScoreImplOrm(DbOrmManager dbOrmManager)
    {
        this.dbOrmManager=dbOrmManager;
    }



    @Override
    public ArrayList<PlayerScore> getTop10()
    {
        ArrayList<PlayerScore> list = new ArrayList<>();
        try(ConnectionSource connectionSource = dbOrmManager.getConnection()){
            playerScoreDao = DaoManager.createDao(
                    connectionSource, PlayerScore.class);

            QueryBuilder<PlayerScore, Integer> qb = playerScoreDao.queryBuilder();
            // order by DESC
            qb.orderBy(PlayerScore.SCORE_FIELD, false);
            // limit 10
            qb.limit(10l);
            PreparedQuery<PlayerScore> preparedQuery = qb.prepare();
            list=(ArrayList<PlayerScore>)playerScoreDao.query(preparedQuery);

        }catch (Exception e){}
        return list;
    }

    @Override
    public void createPlayer(PlayerScore player)
    {
        try(ConnectionSource connectionSource = dbOrmManager.getConnection()){
            playerScoreDao = DaoManager.createDao(
                    connectionSource, PlayerScore.class);

            playerScoreDao.create(player);
        }catch (Exception e){}
    }
}
