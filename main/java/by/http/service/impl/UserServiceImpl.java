package by.http.service.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import by.http.dal.dao.DaoUser;
import by.http.dal.daoexception.DaoException;
import by.http.dal.dao.daoimpl.DaoProvider;
import by.http.entity.User;
import by.http.service.UserInterface;
import by.http.service.exception.ServiceException;

public class UserServiceImpl implements UserInterface{
	User user = null;
	DaoUser dao = DaoProvider.getDaoProvider().getUserDao();
    private static final Logger LOGGER = LogManager.getLogger();
	@Override
	public String changePassword(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		try {
		String oldPassword = (String)request.getParameter("old_password");
		String newPassword = (String)request.getParameter("new_password");
		if (oldPassword != "" && newPassword != "&&") {
			if (user.getPassword().equals(oldPassword)) {
				if (!oldPassword.equals(newPassword)) {
					dao.updateUserPassword(user.getId(), newPassword);
					session.setAttribute("error", "Please log in with a new password!");
					return "Controller?command=LOGIN_PAGE";
				} else {
					session.setAttribute("errorhp", "Please change password to a new one!");
				}
			} else {
				session.setAttribute("errorhp", "Passwords doesn't match!");
			}
		} else {
			session.setAttribute("errorhp", "Please fill all fileds!");
		}
		} catch (DaoException e) {
			session.setAttribute("errorhp", "Something go wrong");
			LOGGER.error("Error password change: " + e);
			throw new ServiceException(e);
		}
		return "Controller?command=HOME_PAGE";
	}

	@Override
	public String changeUsername(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String newUsername = (String)request.getParameter("new_username");
		if (newUsername != "") {
			if (user.getUsername() != newUsername) {
				try {
					if (dao.usernameCheck(newUsername)) {
						dao.updateUserName(user.getId(), newUsername);
						user.setUsername(newUsername);
						session.setAttribute("user", user);
						session.setAttribute("errorhp", "All set!");
						session.setAttribute("username", user.getUsername());
					} else {
						session.setAttribute("errorhp", "Username is unavailable!");
					}
				} catch (DaoException e) {
					session.setAttribute("errorhp", "Something go wrong");
					LOGGER.error("Error username change: " + e);
					throw new ServiceException(e);
				}
			} else {
				session.setAttribute("errorhp", "Change username to a new one!");
			}
		} else {
			session.setAttribute("errorhp", "Please fill all fileds!");
		}
		return "HomePage.jsp";
	}

	@Override
	public String createUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		//HomePageService hpService = new HomePageService();
			String name = (String)request.getParameter("login");
			String password = (String)request.getParameter("password");
			if (name != "" && password != "") {
				try {
					dao.createUser(name, password);
					int id = dao.getUserIDByUsername(name);
					User user = new User(name,password,1, id);
					session.setAttribute("user", user);
					session.setAttribute("username", user.getUsername());
					//hpService.executeMainPage(request);
					session.removeAttribute("errorhp");
					return "Controller?command=HOME_PAGE";
				} catch (DaoException e) {
					session.setAttribute("error", "Profile with username already exists!");
					LOGGER.error("Error user create: " + e);
					throw new ServiceException(e);
				}
			}else {
				session.setAttribute("error", "Please fill all forms!");
				return "Register.jsp";
			}
		
	}

	@Override
	public String deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		User user = (User)request.getSession().getAttribute("user");
		try {
			dao.updateUserRole(user.getId(), 2);
			session.setAttribute("error", "Your profile is succsesfully deleted!");
			return "Login.jsp";
		} catch (DaoException e) {
			session.setAttribute("errorhp", "Please try again!");
			LOGGER.error("Error user delete: " + e);
			throw new ServiceException(e);
		}

		
	}

	@Override
	public String loginUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		String name = (String)request.getParameter("login");
		String password = (String)request.getParameter("password");
		//HomePageService hpService = new HomePageService();
					try {
						int id = dao.getUserIDByUsername(name);
						if (dao.userExists(name, password)) {
							if (dao.getUserRoleByUsername(name) == 1 ) {
								User user = new User(name,password,1, id);
								session.setAttribute("user", user);
								session.setAttribute("username", user.getUsername());
								//hpService.executeMainPage(request);
								session.removeAttribute("errorhp");
								return "Controller?command=HOME_PAGE";
							}else if (dao.getUserRoleByUsername(name) == 3 ) {
								User user = new User(name,password,3, id);
								session.setAttribute("user", user);
								session.setAttribute("username", user.getUsername());
								return "Controller?command=MAIN_PAGE";
							}  else {
								session.setAttribute("error", "Your profile is inactive!");
								return "Login.jsp";
							}
						} else {
							session.setAttribute("error", "Username or password is incorrect!");
							return "Login.jsp";
						}
					} catch (DaoException e) {
						LOGGER.error("Error login User!", e);
						session.setAttribute("error", "Username or password is incorrect!");
						throw new ServiceException(e);
					}
					 //catch (ServiceException e) {
					//		LOGGER.error("Error login User!", e);
					//		session.setAttribute("error", "Username or password is incorrect!");
					//		throw new ServiceException(e);
					//	}
	}

	@Override
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.setAttribute("error", "You succsesfully log out!");
		return "Login.jsp";
		
	}

	@Override
	public String restoreUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
			try {
				String username = (String)request.getParameter("name");
				int userId = dao.getUserIDByUsername(username);
				if (userId == 0 ) {
					request.setAttribute("errormp", "User is not exists!");
				} else {
				dao.updateUserRole(userId, 1);
			}
				} catch (DaoException e) {
				LOGGER.error("Error restore user" + e);
				request.setAttribute("errormp", "Please try again!");
				throw new ServiceException(e);
			}
			return "AdminMainPage.jsp";
		}
		
	}
