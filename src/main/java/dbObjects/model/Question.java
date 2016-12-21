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
@DatabaseTable(tableName = "question")
public class Question implements Serializable
{
    public static final String ID_FIELD = "id";
    public static final String QUESTION_TEXT_FIELD = "text";
    public static final String HIT_FIELD = "hit";
    public static final String ANSWER_FIELD = "answer";

    @DatabaseField(columnName=ID_FIELD, generatedId = true)
    private int id;

    @DatabaseField(columnName = QUESTION_TEXT_FIELD)
    private String questionText;

    @DatabaseField(columnName = HIT_FIELD)
    private String hit;

    @DatabaseField(columnName = ANSWER_FIELD)
    private String answer;


    public Question() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Question(int id, String questionText, String hit, String answer)
    {
        this.id = id;
        this.questionText = questionText;
        this.hit = hit;
        this.answer = answer;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getQuestionText() {return questionText;}

    public void setQuestionText(String questionText) {this.questionText = questionText;}

    public String getHint() {return hit;}

    public void setHit(String hit) {this.hit = hit;}

    public String getAnswer() {return answer;}

    public void setAnswer(String answer) {this.answer = answer;}

    public String toString()
    {
        return (this.getId()+
                "\n"+this.getQuestionText()+
                "\n"+this.getHint()+
                "\n"+this.getAnswer()
        );
    }




}
