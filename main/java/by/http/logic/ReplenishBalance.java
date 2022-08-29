package by.http.logic;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Servlet implementation class ReplenishBalance
 */
public class ReplenishBalance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplenishBalance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dao dao = DAOImpl.getInstance();;
		HttpSession session = request.getSession();
		String asd =(String) session.getAttribute("name");
		String newBalance = (String)request.getParameter("balance");
	    Pattern pattern = Pattern.compile("\s[a-zA-Z]+");
	    Matcher matcher = pattern.matcher(asd);
	    matcher.find();
	    String bilNameString = asd.substring(matcher.start(), matcher.end()).trim();
	    User user = (User)request.getSession().getAttribute("user");
	    ResultSet rSet = dao.BillNameCheck(user.getId(), bilNameString);
	    if (newBalance != "") {
		    try {
				rSet.next();
			    int billID = rSet.getInt(1);
			    int oldBalance = rSet.getInt(3);
			    dao.updateBalance(billID, oldBalance+ Integer.valueOf(newBalance));
			} catch (SQLException e) {
				System.out.println("Error ReplenishBalance!: " + e.getMessage());
			}
		}
	    session.removeAttribute("name");
	    response.sendRedirect("Mainpage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
