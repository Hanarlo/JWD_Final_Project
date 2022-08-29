package by.http.logic;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;
import by.http.entity.User;

public class MakePayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MakePayment() {
        super();
		//TODO 
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dao dao = DAOImpl.getInstance();
		HttpSession session = request.getSession();
		String asd =(String) session.getAttribute("name");
	    Pattern pattern = Pattern.compile("\s[a-zA-Z]+");
	    Matcher matcher = pattern.matcher(asd);
	    matcher.find();
	    String SenderbilNameString = asd.substring(matcher.start(), matcher.end()).trim();
	    User user = (User)request.getSession().getAttribute("user");
	    String recieverBillNameString =  (String)request.getParameter("reciever_name");
	    String recieverLoginString =  (String)request.getParameter("reciever_login");
	    try {
	    Integer amountInteger =  Integer.valueOf(request.getParameter("amount"));
	    String result = dao.sendMoney(recieverLoginString, recieverBillNameString, user.getId(), SenderbilNameString, amountInteger);
		request.setAttribute("result", result);
	    } catch(NumberFormatException e) {
			
	    } finally {
			getServletContext().getRequestDispatcher("/Mainpage.jsp").forward(request, response);
	    }

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
