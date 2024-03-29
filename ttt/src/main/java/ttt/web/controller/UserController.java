package ttt.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ttt.bean.GameBean;
import ttt.bean.UserBean;
import ttt.model.User;
import ttt.model.dao.GameDao;
import ttt.model.dao.UserDao;

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private GameDao gameDao;

	@RequestMapping("/users.html")
	public String users(ModelMap models) {
		models.put("users", userDao.getUsers());
		return "users";
	}

	@RequestMapping(value = "/registration.html", method = RequestMethod.GET)
	public String registration(ModelMap models) {
		models.put( "user", new User() );
		return "registration";
	}

	@RequestMapping(value = { "/login.html" }, method = RequestMethod.POST)
	public String login(HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (userDao.authenticateUser(username, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("userName", username);
			GameBean gameBean = null;
			try{
				//gameBean = gameDao.getLastInCompleteGame(username);
			}
			catch(Exception ex){
			}
			if (gameBean != null) {
				session.setAttribute("game", gameBean);
				request.setAttribute("gameBoard", gameBean.getBoard()
						.toString().replace('[', ' ').replace(']', ' ').trim());
			}
			return "newgame";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = { "/registration.html" }, method = RequestMethod.POST)
	public String registerUser(@ModelAttribute User user,
	        BindingResult bindingResult,HttpServletRequest request) {
		userDao.registerUser(user);
		HttpSession session = request.getSession();
		session.setAttribute("userName", user.getUsername());
		return "newgame";
	}
	@RequestMapping(value = { "/logout.html" }, method = RequestMethod.POST)
	public String logout(HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("userName");
		session.removeAttribute("game");
		session.invalidate();
		return "login";
	}
}