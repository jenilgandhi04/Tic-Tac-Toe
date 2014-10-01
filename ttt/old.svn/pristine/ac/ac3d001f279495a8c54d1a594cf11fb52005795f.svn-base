package ttt.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;





import ttt.bean.GameBean;
import ttt.model.dao.GameDao;

@Controller
public class GameController {
	@Autowired
	private GameDao gameDao;
	@RequestMapping(value = { "/newGame.html" }, method = RequestMethod.POST)
	public String newGame(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(request.getParameter("save").equalsIgnoreCase("YES")){
			GameBean game= (GameBean) session.getAttribute("game");
			gameDao.saveGame(game);
		}
		session.removeAttribute("game");
			return "newgame";
	}
	@RequestMapping(value = { "/playGame.html" }, method = RequestMethod.POST)
	public void playGame(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		GameBean game= (GameBean) session.getAttribute("game");
		if(game==null){
			game= new GameBean();
			game.getPlayer1().setPlayerName(session.getAttribute("userName").toString());
			game.getPlayer2().setPlayerName("AI");
		}
		int cellIndex = Integer.parseInt(request.getParameter("cellIndex"));
		// mark user sign
		game.markSign(game.getPlayer1(), cellIndex);
		// mark Computer sign
		int aiCellIndex = game.getBestMove(game.getPlayer2());
		game.markSign(game.getPlayer2(), aiCellIndex);
		
		ttt.bean.Player winner = game.getWinner();
		String msg = "";
		if(winner!=null){
			if(winner.getPlayerSign() == 0){
				msg="Congratulations! You won.";
				game.setGameOver(true);
				gameDao.saveGame(game);
			}
			else if(winner.getPlayerSign() == 1){
				msg="Better luck next time...";
				game.setGameOver(true);
				gameDao.saveGame(game);
			}
			else{
				msg="Game drawn !";
				game.setGameOver(true);
				gameDao.saveGame(game);
			}
		}
		session.setAttribute("game",game);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(aiCellIndex+"~"+msg);
	}
	@RequestMapping(value = { "/saveGame.html" }, method = RequestMethod.POST)
	public void saveGame(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		GameBean game= (GameBean) session.getAttribute("game");
		if(game!=null)
			gameDao.saveGame(game);
	}
	@RequestMapping(value = { "/viewSavedGames.html" }, method = RequestMethod.POST)
	public String viewSavedGames(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		session.removeAttribute("game");
		try{
		request.setAttribute("listSavedGames",gameDao.getListSavedGames(session.getAttribute("userName").toString()));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return "viewSavedGames";
	}
	@RequestMapping(value = { "/resumeGame.html" }, method = RequestMethod.GET)
	public String resumeGame(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		GameBean gameBean = null;
	try{
		gameBean = gameDao.getGame(session.getAttribute("userName").toString(),request.getParameter("gameId"));
	}
	catch(Exception ex){
	}
	if (gameBean != null) {
		session.setAttribute("game", gameBean);
		request.setAttribute("gameBoard", gameBean.getBoard()
				.toString().replace('[', ' ').replace(']', ' ').trim());
	}
	return "newgame";
	}
	@RequestMapping(value = { "/viewHistory.html" }, method = RequestMethod.POST)
	public String viewHistory(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		session.removeAttribute("game");
		try{
			/*
			 * The number of games completed (i.e. excluding the saved games).
			 * The number of 1-player games completed. The number of 2-player
			 * games completed. The win rate against AI. The win rate against
			 * human players. The list of games played this month. For each
			 * game, show the opponent's name (username for a human player, or
			 * "AI" for the AI player), game length (i.e. end time - start
			 * time), and the outcome.
			 */
		request.setAttribute("noOfComp",gameDao.getNoOfOverGames(session.getAttribute("userName").toString()));
		request.setAttribute("noOfPlayer1",gameDao.getNoOfOverAsPlayer1Games(session.getAttribute("userName").toString()));
		request.setAttribute("noOfPlayer2",gameDao.getNoOfOverAsPlayer2Games(session.getAttribute("userName").toString()));
		request.setAttribute("winRateAI",gameDao.getWinRateWithAI(session.getAttribute("userName").toString()));
		request.setAttribute("winRateHuman",gameDao.getWinRateWithHuman(session.getAttribute("userName").toString()));
		request.setAttribute("listOfGames",gameDao.getLastMonthGames(session.getAttribute("userName").toString()));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return "viewHistory";
	}
}
