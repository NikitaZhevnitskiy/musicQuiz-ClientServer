package client;


import dbObjects.model.PlayerScore;
import dbObjects.model.Question;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Client class
 *
 * last method create instance and run thread
 *
 * @author Nikita Zhevnitskiy
 */
public class Client implements Runnable
{
    private DataOutputStream output;
    private ObjectInputStream input;
    private Socket s;
    private BufferedReader console;
    private int totalQuestions;
    private int rightAnswers;
    private Question question;
    private String answer;
    private String name;


    public Client() {}

    public Client(String host, int port)throws IOException {
        s = new Socket(host, port);
        input = new ObjectInputStream(s.getInputStream());
        output = new DataOutputStream(s.getOutputStream());
        console=new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Two loops control interaction process between
     * SERVER and CLIENT
     *
     * Outer loop: check is client in game
     * Inner loop:
     *      Server send question, Client receive question
     *      Rub game logic
     *
     * After loops
     *      Client do(or not) send score to Server
     *      Server receive Name/Score
     *      Server send top10 players, Client receive it and print top10
     *
     * */
    public void run()
    {
        try
        {
            initClient();
            boolean inGame=true;
            // main GameLogic loop
            while(inGame)
            {
                sendRequestToServer(inGame);
                answer = "";
                while (!answer.equalsIgnoreCase("stop"))
                {
                    // send smth to server, for server keep going send question
                    // if sent string 'stop', server will stop send question
                    sendRequestToServer(answer);
                    question = (Question) getResponseFromServer();
                    ask(question);
                    answer=console.readLine().toUpperCase();
                    answer=checkAnswerForCommand(answer);
                    checkAnswer(answer, question);
                }
                saveScore();
                printTopTen((ArrayList<PlayerScore>) getResponseFromServer());
                inGame = false;
            }
        }catch (Exception e){}
    }


    public void initClient() throws IOException
    {
        System.out.println("### Write your name?");
        setClientName(console.readLine());
        totalQuestions=0;
        rightAnswers=0;
    }


    /************************************
     *                                  *
     *            GAME LOGIC            *
     *                                  *
     *                                  *
     ************************************/



    public void setClientName(String name)
    {

        //this.name=console.readLine();
        this.name=name;
        System.out.println("### Welcome to music quiz "+this.getName());
        System.out.println("### !!!Quiz starts!!! ###");
        System.out.println("### Console commands: " +
                "\n # hint - get hints for question" +
                "\n # stat - get current statistic" +
                "\n # stop - stop game"
        );
        System.out.println("####################################");
    }

    // print top 10 players
    public void printTopTen(ArrayList<PlayerScore> list)
    {
        System.out.println("################ TOP 10 ################");
        for (int i=0;i<list.size();i++)
        {
            System.out.println("# " +(i+1)+ " # " + list.get(i).getPlayer() + " # "+list.get(i).getScore());
        }
    }
    private void sendRequestToServer(Object object) throws IOException
    {
        if (object instanceof Boolean)
            output.writeBoolean((Boolean) object);
        if (object instanceof String)
            output.writeUTF((String)object);
    }
    private Object getResponseFromServer() throws IOException, ClassNotFoundException
    {
        Object object = input.readObject();
        return object;
    }
    private void ask(Question question)
    {
        System.out.println("### Question #" + (++totalQuestions));
        System.out.println(question.getQuestionText());
    }

    public boolean checkAnswer(String answer, Question question) throws IOException
    {
        boolean isAnswerRight=false;

        // check stop command
        if (answer.trim().equalsIgnoreCase("stop")) {
            sendRequestToServer("stop");
            return isAnswerRight;
        }

        if (answer.trim().equalsIgnoreCase(question.getAnswer().trim())) {
            System.out.println("### RIGHT!!!");
            rightAnswers++;
            isAnswerRight=true;
        } else {
            System.out.println("### WRONG!!!");
            isAnswerRight=false;
        }
        return isAnswerRight;
    }


    private void saveScore() throws IOException
    {
        System.out.println("### Your score: " + (int)getScore());
        System.out.println("### Would you like save your score: y/n");
        //String temp = readConsole();
        String temp = console.readLine().toUpperCase();
        if (temp.equalsIgnoreCase("Y"))
        {
            sendRequestToServer(getName()+"/"+(int)getScore());
        }
        else
        {
            sendRequestToServer("");
        }
    }
    /**
     * if command detected
     * read from console new answer
     * until command will not detected
     * returning new answer
     *
     * if command NOT detected
     * return answer (from parameters)
     * */
    private String checkAnswerForCommand(String answer) throws IOException
    {
        while (answer.equalsIgnoreCase("HINT") || answer.equalsIgnoreCase("STAT"))
        {
            if (answer.equalsIgnoreCase("HINT"))
            {
                System.out.println("### Hints");
                System.out.println(question.getHint());
                answer = console.readLine();
            }
            if (answer.equalsIgnoreCase("STAT"))
            {
                System.out.println("### Right answers/total questions : " + rightAnswers + "/" + totalQuestions);
                System.out.println("### Your score is: " + (int) getScore() + "%");
                System.out.println("### Your answer is for question# " + totalQuestions + " is:");
                answer = console.readLine();
            }
        }
        return answer;
    }

    // score depends on: total questions and right answers
    // work on it
    public double getScore()
    {
        double score=((rightAnswers*totalQuestions)/Math.pow(totalQuestions,2))*100;
        return score;
    }

    public String getName(){return name;}

    /**
     * create a client
     * */
    public static void main(String[] args)
    {
        String host="localhost";
        int port = 3333;
        Client client = null;
        try {
            client = new Client(host,port);
        } catch (IOException e) {e.printStackTrace();}

        new Thread(client).start();
    }
}
