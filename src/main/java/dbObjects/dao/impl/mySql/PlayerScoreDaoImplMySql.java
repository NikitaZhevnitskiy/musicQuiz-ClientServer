package dbObjects.dao.impl.mySql;

import dbObjects.dao.PlayerScoreDao;
import dbObjects.model.PlayerScore;
import server.dbManagers.DbMySQLManager;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * PlayerScoreDaoImplMySql class
 *
 * impl serializable:
 *      need to send instances through Streams
 * impl QuestionDao
 *      need to impl exact methods from interface
 *
 * */
public class PlayerScoreDaoImplMySql implements PlayerScoreDao, Serializable
{
    DbMySQLManager dbMySQLManager;

    public PlayerScoreDaoImplMySql(DbMySQLManager dbManager){
        dbMySQLManager=dbManager;
    }

    @Override
    public ArrayList<PlayerScore> getTop10()
    {
        ArrayList<PlayerScore> list = new ArrayList<>();

        // starts with biggest score
        String sql = "select id, name, score from PlayerScore " +
                "order by score DESC " +
                "limit 10;";

        try (Connection connection = dbMySQLManager.getConnection();
             PreparedStatement selectTopTen = connection.prepareStatement(
                     sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            ResultSet rs = selectTopTen.executeQuery();
            while(rs.next())
            {
                list.add(new PlayerScore(
                                rs.getInt("ID"),
                                rs.getString("NAME"),
                                rs.getInt("SCORE")
                        )
                );
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void createPlayer(PlayerScore player)
    {
        String sql = "INSERT INTO PlayerScore (NAME,SCORE) VALUES (?, ?)";
        try(Connection connection = dbMySQLManager.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql))
        {
            preparedStmt.setString(1, player.getPlayer());
            preparedStmt.setInt(2, player.getScore());
            preparedStmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
