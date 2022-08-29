package by.http.logic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;
import by.http.entity.User;

/**
 * Servlet implementation class ChangePassword
 */
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oldPassword = (String)request.getParameter("old_password");
		String newPassword = (String)request.getParameter("new_password");
		User user = (User)request.getSession().getAttribute("user");
		Dao dao =  DAOImpl.getInstance();;
		if (oldPassword != "" && newPassword != "&&") {
			if (user.getPassword().equals(oldPassword)) {
				if (!oldPassword.equals(newPassword)) {
					System.out.println("Pass change!");
					dao.updateUserPassword(user.getId(), newPassword);
					user.setPassword(newPassword);
					request.getSession().setAttribute("user", user);
					request.setAttribute("pass_succes", "pass_succes");
					getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
				} else {
					getServletContext().getRequestDispatcher("/Mainpage.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("doesnt_match", "Passwords doesnt match!");
			}
		} else {
			request.setAttribute("empty_fields", "empty fields");
		}

		getServletContext().getRequestDispatcher("/Mainpage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
