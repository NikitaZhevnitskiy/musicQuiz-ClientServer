package dbObjects.dao;

import dbObjects.model.Question;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interface
 *
 * extends serializable:
 *      need to send instances through Streams
 *
 * */
public interface QuestionDao extends Serializable
{

    ArrayList<Question> getListOfQuestions();
}
