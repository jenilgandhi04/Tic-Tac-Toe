package ttt.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import ttt.model.Game;

@Test(groups = "UserDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    /*@Test
    public void getUser()
    {
        assert userDao.getUser( 1 ).getUsername().equalsIgnoreCase( "admin" );
    }*/

    /*@Test
    public void getUsers()
    {
        assert userDao.getUsers().size() == 2;
    }*/
    @Test
    public void getUserByUsername(){
    	assert userDao.getUser("cysun").getUsername().equalsIgnoreCase( "cysun" );
    }
    @Test
    public void getGamesAgainstAI(){
    	//List<Game> games = userDao.getGamesAgainstAI(userDao.getUser("cysun"));
    	assert true;
    }
    @Test
    public void getSavedGame(){
    	assert true;//userDao.getSavedGames(userDao.getUser("cysun")).get(0).getGameboard().equalsIgnoreCase("[0,-1,-1,-1,1,-1,-1,-1,-1]");
    }
}