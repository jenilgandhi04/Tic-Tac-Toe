package ttt.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import ttt.bean.GameBean;
import ttt.model.dao.GameDao;
import ttt.model.dao.UserDao;

@Controller
public class HumanGameController {
	@Autowired
	private GameDao gameDao;
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = { "/newHumanGame.html" }, method = RequestMethod.POST)
	public String newHumanGame(HttpServletRequest request) {
		Map<String,GameBean> hostedGames = (Map<String,GameBean>)request.getServletContext().getAttribute("hostedGames");
		if(hostedGames==null){
			hostedGames = new HashMap<String, GameBean>();
		}
		HttpSession session = request.getSession();
		String hostUserName = session.getAttribute("userName").toString();
		GameBean gameBean = new GameBean();
		gameBean.getPlayer1().setPlayerName(hostUserName);
		hostedGames.put(hostUserName, gameBean);
		request.getServletContext().setAttribute("hostedGames",hostedGames);
		request.setAttribute("isHost","YES");
		return "newHumanGame";
	}
	/*@RequestMapping(value = { "/viewHostedGames.html" }, method = RequestMethod.POST)
	public String viewHostedGames(HttpServletRequest request) {
		Map<String,GameBean> hostedGames = (Map<String,GameBean>)request.getServletContext().getAttribute("hostedGames");
		if(hostedGames!=null)
			request.setAttribute("listHostedGames",hostedGames.keySet());
		return "viewHostedGames";
	}*/
	@RequestMapping(value = { "/joinHostedGame.html" }, method = RequestMethod.POST)
	public String joinHostedGame(HttpServletRequest request) {
		try{
		Map<String,GameBean> hostedGames = (Map<String,GameBean>)request.getServletContext().getAttribute("hostedGames");
		//System.out.println("************************"+hostedGames.keySet());
		String hostUserName = hostedGames.keySet().iterator().next();
		GameBean gameBean = hostedGames.get(hostUserName);
		HttpSession session = request.getSession();
		String userName = session.getAttribute("userName").toString();
		gameBean.getPlayer2().setPlayerName(userName);
		hostedGames.remove(hostUserName);
		Map<String,GameBean> runningGames = (Map<String,GameBean>)request.getServletContext().getAttribute("runningGames");
		if(runningGames==null){
			runningGames = new HashMap<String, GameBean>();
		}
		gameBean.setStartTime(new Date());
		runningGames.put(hostUserName, gameBean);
		request.getServletContext().setAttribute("hostedGames",hostedGames);
		request.getServletContext().setAttribute("runningGames",runningGames);
		session.setAttribute("hostUserName", hostUserName);
		return "newHumanGame";
		}
		catch(Exception ex){
			request.setAttribute("errMsg", "No Game is Hosted.. Try With AI..");
			return "newgame";
		}
	}
	@RequestMapping(value = { "/whosonline.deferred.json" }, method = RequestMethod.GET)
	@ResponseBody
	public Object userHasJoined(HttpServletRequest request) {
		Map<String,GameBean> runningGames = (Map<String,GameBean>)request.getServletContext().getAttribute("runningGames");
		DeferredResult deferredResult = new DeferredResult();
		if(runningGames!=null){
		GameBean gameBean = runningGames.get(request.getSession().getAttribute("userName").toString());
		//DeferredResult deferredResult = new DeferredResult();
		String messages = gameBean.isGameStarted(deferredResult);
		return (messages != null) ? messages : deferredResult;
		}
		return deferredResult;
	}
	@RequestMapping(value = { "/playHumanGame.html" }, method = RequestMethod.POST)
	public void playHumanGame(HttpServletRequest request,javax.servlet.http.HttpServletResponse response) throws IOException {
		Map<String,GameBean> runningGames = (Map<String,GameBean>)request.getServletContext().getAttribute("runningGames");
		if(runningGames!=null){
			GameBean gameBean = null;
			HttpSession session =request.getSession();
			String userName= session.getAttribute("userName").toString();
			if(session.getAttribute("hostUserName")==null)
				gameBean=runningGames.get(userName);
			else
				gameBean=runningGames.get(session.getAttribute("hostUserName").toString());
			
			if(gameBean!= null && gameBean.getTurn().getPlayerName().equals(userName)){
				int cellIndex = Integer.parseInt(request.getParameter("cellIndex"));
				// mark user sign
				gameBean.markSign(gameBean.getTurn(), cellIndex);
				ttt.bean.Player winner = gameBean.getWinner();
				String msg = "";
				if(winner!=null){
					if(winner.getPlayerName().equals(userName)){
						msg="Congratulations! You won.";
						gameBean.setGameOver(true);
						gameDao.saveGame(gameBean);
					}
					else if(winner.getPlayerSign() == 1 || winner.getPlayerSign() == 0){
						msg="Better luck next time...";
						gameBean.setGameOver(true);
						gameDao.saveGame(gameBean);
					}
					else{
						msg="Game drawn !";
						gameBean.setGameOver(true);
						gameDao.saveGame(gameBean);
					}
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(msg);
				}
			}
		}
	}
	@RequestMapping(value = { "/isMyTurn.deferred.json" }, method = RequestMethod.GET)
	@ResponseBody
	public Object isMyTurn(HttpServletRequest request) throws IOException {
		Map<String,GameBean> runningGames = (Map<String,GameBean>)request.getServletContext().getAttribute("runningGames");
		DeferredResult deferredResult = new DeferredResult();
		if(runningGames!=null){
			GameBean gameBean = null;
			HttpSession session =request.getSession();
			String userName= session.getAttribute("userName").toString();
			if(session.getAttribute("hostUserName")==null)
				gameBean=runningGames.get(userName);
			else
				gameBean=runningGames.get(session.getAttribute("hostUserName").toString());
			if(gameBean!=null){
				String msg = gameBean.isMyTurn(deferredResult,userName);
				return (msg != null) ? msg : deferredResult;
			}
		}
		return deferredResult;
	}
	/*@RequestMapping(value = { "/playHumanGame.html" }, method = RequestMethod.POST)
	 *
	public Object playGame(HttpServletRequest request) throws IOException {
		Map<String,GameBean> runningGames = (Map<String,GameBean>)request.getServletContext().getAttribute("runningGames");
		DeferredResult deferredResult = new DeferredResult();
		if(runningGames!=null){
			GameBean gameBean = null;
			HttpSession session =request.getSession();
			String userName= session.getAttribute("userName").toString();
			if(session.getAttribute("hostUserName")==null)
				gameBean=runningGames.get(userName);
			else
				gameBean=runningGames.get(session.getAttribute("hostUserName").toString());
			
			if(gameBean!= null && gameBean.getTurn().getPlayerName().equals(userName)){
				int cellIndex = Integer.parseInt(request.getParameter("cellIndex"));
				// mark user sign
				gameBean.markSign(gameBean.getTurn(), cellIndex);
				ttt.bean.Player winner = gameBean.getWinner();
				String msg = "";
				if(winner!=null){
					if(winner.getPlayerName().equals(userName)){
						msg="Congratulations! You won.";
						gameBean.setGameOver(true);
						gameDao.saveGame(gameBean);
					}
					else if(winner.getPlayerSign() == 1 || winner.getPlayerSign() == 0){
						msg="Better luck next time...";
						gameBean.setGameOver(true);
						gameDao.saveGame(gameBean);
					}
					else{
						msg="Game drawn !";
						gameBean.setGameOver(true);
						gameDao.saveGame(gameBean);
					}
					return msg;
				}
				else{
					msg = gameBean.isMyTurn(deferredResult,userName);
					return (msg != null) ? msg : deferredResult;
				}
			}
		}
		return deferredResult;
	}*/
		/*HttpSession session = request.getSession();
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
		response.getWriter().write(aiCellIndex+"~"+msg);*/

	/*public void userHasJoined(HttpServletRequest request,javax.servlet.http.HttpServletResponse response) throws IOException {
		Map<String,GameBean> runningGames = (Map<String,GameBean>) request.getServletContext().getAttribute("runningGames");
		if(runningGames!=null){
			if(runningGames.containsKey(request.getSession().getAttribute("userName").toString()))
				response.getWriter().write("YES");
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("NO");
	}*/
	
}
