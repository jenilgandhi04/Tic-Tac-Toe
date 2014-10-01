package ttt.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.bean.UserBean;
import ttt.model.Game;
import ttt.model.User;
import ttt.model.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUser( Integer id )
    {
        return entityManager.find( User.class, id );
    }

    @Override
    public List<User> getUsers()
    {
        return entityManager.createQuery( "from User order by id", User.class )
            .getResultList();
    }

	@Override
	public User getUser(String username) {
		
		User user = entityManager.createQuery("from User where username = :uname",User.class).setParameter("uname", username).getSingleResult();
		return user;
	}

	@Override
	public List<Game> getGamesAgainstAI(User user) {
		List<Game> games = entityManager
				.createQuery(
						"from Game g where g.player1 = :userId and player2 = :aiUserId and g.gamestatus = 'OVER' ",Game.class)
				.setParameter("userId", user).setParameter("aiUserId", getUser("AI")).getResultList();
		return games;

	}

	@Override
	public List<Game> getSavedGames(User user) {
		List<Game> games = entityManager
				.createQuery(
						"from Game g where g.player1 = :userId and g.gamestatus = 'SAVED' ",Game.class)
				.setParameter("userId", user).getResultList();
		return games;
	}
	@Override
	public boolean authenticateUser(String username, String password){
		try {
			User user = entityManager
					.createQuery(
							"from User where username = :uname and password = :pwd",
							User.class).setParameter("uname", username)
					.setParameter("pwd", password).getSingleResult();
			if (user != null) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
	
	@Override
    @Transactional
	public User registerUser(User user){
		return entityManager.merge( user );
	}

}