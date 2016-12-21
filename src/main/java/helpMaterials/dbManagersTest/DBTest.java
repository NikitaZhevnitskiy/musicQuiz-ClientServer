package helpMaterials.dbManagersTest;

import dbObjects.model.PlayerScore;

/**
 * DBTest class
 * for manual scenarios testing of
 * CRUD operations
 *
 * test
 *      1. dbObjects and their implementations
 *      2. dbManagers
 *          2.1 SQL
 *          2.2 ORM
 *
 * */
public class DBTest
{
    public static void main(String[] args)
    {
        PlayerScore player;
        String propertyFile = "src/main/resources/db.properties";

        System.out.println("### MYSQL TEST ");

        /** MySql test @see questionDaoImplMySql.java */
//        QuestionDaoImplMySql questionDaoImplMySql =
//                new QuestionDaoImplMySql(new DbMySQLManager(propertyFile));
//        ArrayList<Question> list = questionDaoImplMySql.getListOfQuestions();
//        System.out.println(list.size());
//        System.out.println(list.get(0).toString());


        /** MySql test @see PlayerScoreImplMySql.java */
//        System.out.println("### MYSQL TEST ");
//        PlayerScoreDaoImplMySql playerScoreDaoImplMySql=
//                new PlayerScoreDaoImplMySql(new DbMySQLManager(propertyFile));
//        ArrayList<PlayerScore> listTopTen = playerScoreDaoImplMySql.getTop10();
//        System.out.println(listTopTen.size());
//        System.out.println(listTopTen.get(0).toString());
//        player = new PlayerScore("playerFromMySQLImpl", 95);
//        System.out.println(player.toString());
//        playerScoreDaoImplMySql.createPlayer(player);


//___________________________________//
        System.out.println();        //
//___________________________________//


        System.out.println("### ORM TEST ");

//        /** Orm test @see questionDaoImplOrm.java */
//        QuestionDaoImplOrm questionDaoImplOrm =
//                new QuestionDaoImplOrm(new DbOrmManager(propertyFile));
//        ArrayList<Question> list1 = questionDaoImplOrm.getListOfQuestions();
//        System.out.println(list.size());
//        System.out.println(list1.get(1).toString());

        System.out.println();

//        /** Orm test @see PlayerScoreImplOrm.java */
//        PlayerScoreImplOrm orm = new PlayerScoreImplOrm(new DbOrmManager(propertyFile));
//        player = new PlayerScore("Nikitatshka", 95);
//        orm.createPlayer(player);
//        ArrayList<PlayerScore> listTopTen1 = orm.getTop10();
//        System.out.println(listTopTen1.size());
//        System.out.println(listTopTen1.get(0).toString());

    }
}
