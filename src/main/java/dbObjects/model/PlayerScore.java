package dbObjects.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

/**
 * Class presents table
 *
 * impl serializable:
 *      need to send instances through Streams
 *
 * */
@DatabaseTable(tableName = "playerscore")
public class PlayerScore implements Serializable
{
    public static final String ID_FIELD = "ID";
    public static final String NAME_FIELD = "NAME";
    public static final String SCORE_FIELD = "SCORE";

    @DatabaseField(columnName=ID_FIELD, generatedId = true)
    private int id;

    @DatabaseField(columnName = NAME_FIELD)
    private String player;

    @DatabaseField(columnName = SCORE_FIELD)
    private int score;

    public PlayerScore() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }
    public PlayerScore(int id, String playerName, int score)
    {
        this.id=id;
        this.player=playerName;
        this.score=score;
    }

    public PlayerScore(String player, int score)
    {
        this.player=player;
        this.score=score;
    }

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getPlayer() {return player;}

    public void setPlayer(String player) {this.player = player;}
    public String toString()
    {
        return (this.getId()+
                "\n"+this.getId()+
                "\n"+this.getPlayer()+
                "\n"+this.getScore()
        );
    }
}
