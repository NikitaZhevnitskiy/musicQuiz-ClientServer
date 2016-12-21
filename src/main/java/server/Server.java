package server;

import dbObjects.dao.*;
import dbObjects.dao.impl.mySql.*;
import dbObjects.dao.impl.orm.*;
import dbObjects.model.*;
import server.dbManagers.*;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;




/**
 * Class Server
 * Multi-client server
 *
 * functions:
 *      extract info from db
 *      send question(s) to client(s)
 *      sent list of top10 players score
 *
 * last method create instance of server and run thread with it
 *
 * http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
 * http://www.javaworld.com/article/2078809/java-concurrency/java-concurrency-java-101-the-next-generation-java-concurrency-without-the-pain-part-1.html
 * */
public class Server implements Runnable
{

    //private BufferedReader console;
    private ServerSocket serverSocket;
    private ArrayList<Socket> listOfClients;
    private Random random;
    private ArrayList<Question> listWithQuestions;
    private Question question;
    private QuestionDao questionDao;
    private PlayerScoreDao playerScoreDao;
    private DbMySQLManager dbManagerSql;
    private DbOrmManager dbOrmManager;


    // to avoid method factory, just create 2 different constructors
    // for sql
    public Server(DbMySQLManager sqlManager) {
        dbManagerSql=sqlManager;
        questionDao = new QuestionDaoImplMySql(dbManagerSql);
        playerScoreDao = new PlayerScoreDaoImplMySql(dbManagerSql);
    }

    // for orm
    public Server(DbOrmManager ormManager){
        dbOrmManager=ormManager;
        questionDao=new QuestionDaoImplOrm(dbOrmManager);
        playerScoreDao=new PlayerScoreImplOrm(dbOrmManager);
    }

    public Server(){}

    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }

    @Override
    public void run()
    {

        try
        {
            initServer();
            while (true)
            {
                final Socket s = serverSocket.accept();
                listOfClients.add(s);
                System.out.println("\n### New client "+listOfClients.size());
                // replaced with lambda
                Runnable r = () -> play(s);
                new Thread(r).start();
            }

        } catch (Exception e) {}
    }

    private void play(Socket client)
    {
        try (
                //init streams for THIS client
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                DataInputStream input = new DataInputStream(client.getInputStream())
            )
        {
                while (input.readBoolean())
                {
                    while (!input.readUTF().equalsIgnoreCase("stop")){
                        question = getRandomQuestion(listWithQuestions);
                        sendQuestion(outputStream, question);
                    }
                    savePlayer(playerScoreDao,input);
                    sendTopTen(outputStream);
                }
        }catch (IOException e){}
    }

    //
    public void initServer() throws IOException
    {
        System.out.println("### Server starts ###");
        listWithQuestions= questionDao.getListOfQuestions();
        listOfClients = new ArrayList<>();
        random=new Random();
        serverSocket = new ServerSocket(3333);
        //console = new BufferedReader(new InputStreamReader(System.in));
    }

    private Question getRandomQuestion(ArrayList<Question> listOfQuestions)
    {
        return listOfQuestions.get(random.nextInt(listWithQuestions.size()));
    }

    private void sendQuestion(ObjectOutputStream out, Question q) throws IOException
    {
        out.writeObject(q);
        // server log
        System.out.println("\n#####\nQuestion ID#"+q.getId()+" has been send ");
        System.out.println("#TEXT: "+q.getQuestionText());
        System.out.println("#ANSWER: "+q.getAnswer());
        System.out.println("#####");
    }

    /**
     * Receive String object from client
     * Contain data in MINE's predefined format (ClientName/Score)
     *
     *
     * */
    private void savePlayer(PlayerScoreDao playerScoreDao, DataInputStream inputStream) throws IOException
    {
        String clientData=inputStream.readUTF();
        if (!clientData.isEmpty())
        {
            String[] temp=clientData.split("/");
            PlayerScore player = new PlayerScore(temp[0],Integer.parseInt(temp[1]));
            playerScoreDao.createPlayer(player);
            // write PlayerName in console
            System.out.println("\n### Client: "+temp[0] +"\n### Score: "+temp[1]);
            System.out.println("### SAVED");
        }
    }

    private void sendTopTen(ObjectOutputStream out) throws IOException
    {
        out.writeObject(playerScoreDao.getTop10());
    }

    /**
     * change server parameter to change sql or orm
     * constructor parameters:
     *
     *      DbMySQLManager      ORM
     *      DbOrmManager        MySQL
     *
     * */
    public static void main(String[] args)
    {
        String propertyFile = "src/main/resources/musicQuiz.properties";
        new Server(new DbOrmManager(propertyFile)).run();
        //new Server(new DbMySQLManager(propertyFile)).run();
    }
}