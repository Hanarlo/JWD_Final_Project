package by.http.logic;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;

/**
 * Servlet implementation class UnblockBill
 */
public class UnblockBill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnblockBill() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String billName = (String)request.getParameter("bill");
		String username = (String)request.getParameter("username");
		Dao dao = DAOImpl.getInstance();;
		int userId = dao.getUserIDByUsername(username);
		if (userId == 0) {
			request.setAttribute("user", "User is not exists!");
			getServletContext().getRequestDispatcher("/AdminMainPage.jsp").forward(request, response);
		}
		ResultSet rSet = dao.BillNameCheck(userId, billName);
		try {
			rSet.next();
			int billId = rSet.getInt(1);
			dao.updateBillStatus(billId, false);
		} catch (SQLException e) {
			request.setAttribute("bill", "Bill is not Exists!");
			getServletContext().getRequestDispatcher("/AdminMainPage.jsp").forward(request, response);
		}
		response.sendRedirect("/web/admin");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
