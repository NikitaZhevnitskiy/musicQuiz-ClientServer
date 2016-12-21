package dbObjects.dao;

import dbObjects.model.PlayerScore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interface
 *
 * extends serializable:
 *      need to send instances through Streams
 *
 * */
public interface PlayerScoreDao extends Serializable
{
    ArrayList<PlayerScore> getTop10();
    void createPlayer(PlayerScore player);
}
