

import client.Client;
import dbObjects.model.PlayerScore;
import dbObjects.model.Question;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class TestClient
{
    private Client client;
    private String rightAnswer;
    private String wrongAnswer;
    private Question question;

    @Before
    public void setUp() throws IOException
    {
        client=new Client();
        rightAnswer="right";
        wrongAnswer="";
        question = new Question();
        question.setAnswer(rightAnswer);
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void isClient_hasName()
    {
        client.setClientName("clientName");
        assertEquals(client.getName(), "clientName");
    }

    @Test
    public void isClient_hasNoName()
    {
        assertNotEquals(client.getName(), "clientName");
    }

    @Test
    public void isAnswerCorrect() throws IOException
    {
        // Arrange
        boolean isAnswerCorrect;

        // Act

        isAnswerCorrect = client.checkAnswer(rightAnswer,question);
//        isAnswerCorrect = client.checkAnswer(wrongAnswer,question);

        // Assert
        assertTrue(isAnswerCorrect);
    }
    @Test
    public void isAnswerNotCorrect() throws IOException
    {
        boolean isAnswerCorrect;

        // Act

//        isAnswerCorrect = client.checkAnswer(rightAnswer,question);
        isAnswerCorrect = client.checkAnswer(wrongAnswer,question);

        // Assert
        assertFalse(isAnswerCorrect);
    }

    @Test
    public void isTopTenEmpty_DoNotThrowException()
    {
        ArrayList<PlayerScore> list = new ArrayList<>();
        client.printTopTen(list);
    }

    @Test (expected = NullPointerException.class)
    public void isTopTenEmpty_ThrowException()
    {
        ArrayList<PlayerScore> list = null;
        client.printTopTen(list);
    }

}
