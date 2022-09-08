package by.http.logic;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;
import by.http.entity.User;

/**
 * Servlet implementation class ChangeUsername
 */
public class ChangeUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeUsername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Dao dao =  DAOImpl.getInstance();;
		User user = (User)session.getAttribute("user");
		String newUsername = (String)request.getParameter("new_username");
		if (newUsername != "") {
			if (user.getUsername() != newUsername) {
				try {
					if (dao.usernameCheck(newUsername)) {
						dao.updateUserName(user.getId(), newUsername);
						user.setUsername(newUsername);
						session.setAttribute("user", user);
					} else {
						request.setAttribute("zanyato", "username zanyat");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				request.setAttribute("equal_username", "equal username");
			}
		} else {
			request.setAttribute("empty_fields", "empty fields");
		}

		getServletContext().getRequestDispatcher("/Mainpage.jsp").forward(request, response);
	}

}
