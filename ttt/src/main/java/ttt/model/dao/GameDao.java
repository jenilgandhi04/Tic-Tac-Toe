package ttt.model.dao;

import java.util.List;

import ttt.bean.GameBean;

public interface GameDao {

	public void saveGame(GameBean gameBean);
	public GameBean getLastInCompleteGame(String userName) throws Exception;
	public List<GameBean> getListSavedGames(String userName) throws Exception;
	public GameBean getGame(String userName,String gameId)throws Exception;
	public int getNoOfOverGames(String userName) throws Exception;
	public int getNoOfOverAsPlayer1Games(String userName)throws Exception;
	public int getNoOfOverAsPlayer2Games(String userName)throws Exception;
	public float getWinRateWithAI(String userName)throws Exception;
	public float getWinRateWithHuman(String userName)throws Exception;
	public List<GameBean> getLastMonthGames(String userName)throws Exception;

}