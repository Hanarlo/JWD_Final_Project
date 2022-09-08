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
import by.http.entity.User;

/**
 * Servlet implementation class CreateBill
 */
public class CreateBill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateBill() {
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
		String billName = (String)request.getParameter("bill_name");
		String cardName = (String)request.getParameter("card_name");
		Dao dao = DAOImpl.getInstance();;
		User user = (User)request.getSession().getAttribute("user");
		if (billName != "" && cardName != "") {
			ResultSet rs = dao.BillNameCheck(user.getId(), billName);
			try {
				if (!rs.next()) {
					dao.createBill(user.getId(),billName);
					rs = dao.BillNameCheck(user.getId(), billName);
					rs.next();
					dao.createCard(user.getId(), rs.getInt(1), cardName);
				} else {
					request.setAttribute("bill_check", "Bill");
				}
			} catch (SQLException e) {
				System.out.println("Error CreateBill!: " + e.getMessage());
			}
		} else {
			request.setAttribute("empty_fields", "empty");
		}
			


		getServletContext().getRequestDispatcher("/Mainpage.jsp").forward(request, response);
	}

}
