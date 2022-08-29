package by.http.entity;

import java.awt.dnd.DragSourceAdapter;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;

/**
 * Servlet implementation class LoginUser
 */
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Dao dao =  DAOImpl.getInstance();;
			String name = (String)request.getParameter("login");
			String password = (String)request.getParameter("password");
			int id = dao.getUserIDByUsername(name);
					try {
						if (dao.userExists(name, password)) {
							if (dao.getUserRoleByUsername(name) == 1 ) {
								User user = new User(name,password,1, id);
								session.setAttribute("user", user);
								response.sendRedirect("/web/main");
							}else if (dao.getUserRoleByUsername(name) == 3 ) {
								User user = new User(name,password,3, id);
								session.setAttribute("user", user);
								response.sendRedirect("/web/admin");
							}  else {
								request.setAttribute("inactive", "inactive");
								getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
							}
						} else {
							request.setAttribute("notexists", "notexists");
							getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
						}
					} catch (SQLException e) {
						System.out.println("Error user check! " + e.getMessage());
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
