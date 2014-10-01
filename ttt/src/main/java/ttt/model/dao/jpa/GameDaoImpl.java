package ttt.model.dao.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import ttt.bean.GameBean;
import ttt.bean.GameBoard;
import ttt.model.Game;
import ttt.model.User;
import ttt.model.dao.GameDao;
import ttt.util.GameUtil;

@Repository
public class GameDaoImpl implements GameDao{
	 @PersistenceContext
	    private EntityManager entityManager;

	@Override
	@Transactional
	public void saveGame(GameBean gameBean) {
		Game game =null;
		if(gameBean.getGameId()==-1){
			game = new Game();
			game.setStarted(gameBean.getStartTime());
		}
		else
			game = entityManager.createQuery("from Game where id = :gameId",Game.class).setParameter("gameId", gameBean.getGameId()).getSingleResult();
		game.setGameboard(gameBean.getBoard().toString());
		game.setGamestatus(gameBean.isGameOver() ? "OVER" : "SAVED");
		User player1 = entityManager.createQuery("from User where username = :uname",User.class).setParameter("uname", gameBean.getPlayer1().getPlayerName()).getSingleResult();
		game.setPlayer1(player1);
		User player2 = entityManager.createQuery("from User where username = :uname",User.class).setParameter("uname", gameBean.getPlayer2().getPlayerName()).getSingleResult();
		game.setPlayer2(player2);
		try{
		User winner = entityManager.createQuery("from User where username = :uname",User.class).setParameter("uname", gameBean.getWinner().getPlayerName()).getSingleResult();
		game.setWinner(winner);
		}
		catch(Exception ex){}
		game.setSaved(new Date());
		if(gameBean.isGameOver())
			game.setEnded(new Date());
		entityManager.merge(game);
	}

	@Override
	public GameBean getLastInCompleteGame(String userName) throws Exception{
		Game game = entityManager
				.createQuery(
						"from Game where player1.id = :player1Id and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "SAVED").getSingleResult();
		if (game != null) {
			GameBean gameBean = new GameBean();
			gameBean.setGameId(game.getId());
			gameBean.setBoard(new GameBoard(GameUtil.covertGameBoard(game
					.getGameboard()))); // parse board
			gameBean.setGameOver(false);
			gameBean.getPlayer1().setPlayerName(userName);
			gameBean.getPlayer2().setPlayerName("AI");
			gameBean.setTurn(gameBean.getPlayer1());
			return gameBean;
		}
		return null;
	}
	@Override
	public List<GameBean> getListSavedGames(String userName) throws Exception{
		List<Game> listGamePOJO = entityManager
				.createQuery(
						"from Game where player1.id = :player1Id and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "SAVED").getResultList();
		List<GameBean> listGameBean = new ArrayList<GameBean>();
		for (Game game : listGamePOJO) {
			GameBean gameBean = new GameBean();
			gameBean.setGameId(game.getId());
			gameBean.setBoard(new GameBoard(GameUtil.covertGameBoard(game
					.getGameboard()))); // parse board
			gameBean.setGameOver(false);
			gameBean.getPlayer1().setPlayerName(userName);
			gameBean.getPlayer2().setPlayerName("AI");
			gameBean.setTurn(gameBean.getPlayer1());
			gameBean.setStartTime(game.getStarted());
			gameBean.setEndTime(game.getEnded());
			gameBean.setLastSaveTime(game.getSaved());
			listGameBean.add(gameBean);
			
		}
		return listGameBean;
	}
	@Override
	public GameBean getGame(String userName,String gameId)throws Exception{
		Game game = entityManager
				.createQuery(
						"from Game where player1.id = :player1Id and id = :gameId",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("gameId", Integer.parseInt(gameId)).getSingleResult();
		if (game != null) {
			GameBean gameBean = new GameBean();
			gameBean.setGameId(game.getId());
			gameBean.setBoard(new GameBoard(GameUtil.covertGameBoard(game
					.getGameboard()))); // parse board
			gameBean.setGameOver(false);
			gameBean.getPlayer1().setPlayerName(userName);
			gameBean.getPlayer2().setPlayerName("AI");
			gameBean.setTurn(gameBean.getPlayer1());
			return gameBean;
		}
		return null;
	}
	public int getNoOfOverGames(String userName) throws Exception{
		List<Game> listGamePOJO = entityManager
				.createQuery(
						"from Game where (player1.id = :player1Id or player2.id = :player1Id) and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").getResultList();
		
		return listGamePOJO.size();
	}
	public int getNoOfOverAsPlayer1Games(String userName)throws Exception{
		List<Game> listGamePOJO = entityManager
				.createQuery(
						"from Game where player1.id = :player1Id and player2.id = 3 and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").getResultList();
		
		return listGamePOJO.size();
	}
	public int getNoOfOverAsPlayer2Games(String userName)throws Exception{
		List<Game> listGamePOJO = entityManager
				.createQuery(
						"from Game where (player1.id = :player1Id or player2.id = :player1Id)  and player2.id != 3  and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").getResultList();
		
		return listGamePOJO.size();
	}
	public float getWinRateWithAI(String userName)throws Exception{
		List<Game> listWonGame = entityManager
				.createQuery(
						"from Game where (winner.id = :player1Id and player2.id = 3) and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").getResultList();
		List<Game> listTotalGame = entityManager
				.createQuery(
						"from Game where (player1.id = :player1Id and player2.id = 3) and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").getResultList();
		if(listTotalGame.size()==0)
			return Float.NaN;
		return ((float)listWonGame.size()/listTotalGame.size())*100;
	}
	public float getWinRateWithHuman(String userName)throws Exception{
		List<Game> listWonGame = entityManager
				.createQuery(
						"from Game where (winner.id = :player1Id and player2.id != 3) and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").getResultList();
		List<Game> listTotalGame = entityManager
				.createQuery(
						"from Game where (player1.id = :player1Id or player2.id = :player1Id)  and player2.id != 3 and gameStatus = :status",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").getResultList();
			if(listTotalGame.size()==0)
				return Float.NaN;
		return ((float)listWonGame.size()/listTotalGame.size())*100;
	}
	public List<GameBean> getLastMonthGames(String userName)throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		Date date = new Date(cal.getTimeInMillis());
		List<Game> listGamePOJO = entityManager
				.createQuery(
						"from Game where (player1.id = :player1Id or player2.id = :player1Id) and gameStatus = :status and started > :startDate",
						Game.class)
				.setParameter(
						"player1Id",
						entityManager
								.createQuery(
										"from User where username = :uname",
										User.class)
								.setParameter("uname", userName)
								.getSingleResult().getId())
				.setParameter("status", "OVER").setParameter("startDate",date).getResultList();
		List<GameBean> listGameBean = new ArrayList<GameBean>();
		for (Game game : listGamePOJO) {
			GameBean gameBean = new GameBean();
			gameBean.setGameId(game.getId());
			gameBean.setBoard(new GameBoard(GameUtil.covertGameBoard(game
					.getGameboard()))); // parse board
			gameBean.setGameOver(false);
			gameBean.getPlayer1().setPlayerName(game.getPlayer1().getUsername());
			gameBean.getPlayer2().setPlayerName(game.getPlayer2().getUsername().equals(userName)?game.getPlayer1().getUsername():game.getPlayer2().getUsername());
			if(game.getWinner()!=null)
				gameBean.getWinner().setPlayerName(game.getWinner().getUsername());
			else
				gameBean.getWinner().setPlayerName("DRAWN");
			gameBean.setTurn(gameBean.getPlayer1());
			gameBean.setStartTime(game.getStarted());
			gameBean.setEndTime(game.getEnded());
			gameBean.setGameLength((gameBean.getEndTime().getTime()-gameBean.getStartTime().getTime())/1000.0);
			listGameBean.add(gameBean);
			
		}
		return listGameBean;
	}
}
