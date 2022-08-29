package by.http.logic;

import java.io.IOException;
import java.lang.invoke.StringConcatFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;

/**
 * Servlet implementation class RestoreUser
 */
public class RestoreUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestoreUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getParameter("name");
		System.out.println(username + " username");
		Dao dao = DAOImpl.getInstance();
		int userId = dao.getUserIDByUsername(username);
		if (userId == 0 ) {
			request.setAttribute("user", "User is not exists!");
			getServletContext().getRequestDispatcher("/AdminMainPage.jsp").forward(request, response);
		} else {
			dao.updateUserRole(userId, 1);
			response.sendRedirect("/web/admin");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
