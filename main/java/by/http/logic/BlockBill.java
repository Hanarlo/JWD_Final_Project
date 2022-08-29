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

import by.http.dao.DAOImpl;
import by.http.dao.Dao;
import by.http.entity.User;

/**
 * Servlet implementation class BlockBill
 */
public class BlockBill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlockBill() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		Dao dao = DAOImpl.getInstance();;
		String asd =(String) request.getParameter("name");
	    Pattern pattern = Pattern.compile("\s[a-zA-Z]+");
	    Matcher matcher = pattern.matcher(asd);
	    matcher.find();
	    String bilNameString = asd.substring(matcher.start(), matcher.end()).trim();
	    User user = (User)request.getSession().getAttribute("user");
	    ResultSet rSet = dao.BillNameCheck(user.getId(), bilNameString);
			rSet.next();
		    int billID = rSet.getInt(1);
		    dao.updateBillStatus(billID, true);
		} catch (SQLException e) {
			System.out.println("Error blockBill!: " + e.getMessage());
		} catch (NullPointerException exception) {
		} finally {
		    response.sendRedirect("/web/main");
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
