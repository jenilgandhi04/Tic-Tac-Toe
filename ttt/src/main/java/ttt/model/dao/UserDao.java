package ttt.model.dao;

import java.util.List;

import ttt.bean.UserBean;
import ttt.model.Game;
import ttt.model.User;

public interface UserDao {

    User getUser( Integer id );

    List<User> getUsers();
    User getUser( String username );
    List<Game> getGamesAgainstAI( User user );
    List<Game> getSavedGames( User user );
    boolean authenticateUser(String username, String password);
    User registerUser(User user);
}