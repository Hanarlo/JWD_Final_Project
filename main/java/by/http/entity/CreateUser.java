package by.http.entity;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.apache.catalina.connector.Response;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;

/**
 * Servlet implementation class mainpage
 */
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
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
		Dao dao = DAOImpl.getInstance();;
				
			String name = (String)request.getParameter("login");
			String password = (String)request.getParameter("password");
			int id = dao.getUserIDByUsername(name);
			if (name != "" && password != "") {
				try {
					dao.createUser(name, password);
					User user = new User(name,password,1, id);
					session.setAttribute("user", user);
					response.sendRedirect("/web/main");
				} catch (SQLException e) {
					request.setAttribute("exists", "asd");
					getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
					System.out.println("Error user check! " + e.getMessage());
				}
			}else {
				response.sendRedirect("Register.jsp");
			}
	}
	
}
