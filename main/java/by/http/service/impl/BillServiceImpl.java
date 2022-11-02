package by.http.service.impl;

import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import by.http.dal.dao.DaoBill;
import by.http.dal.dao.DaoUser;
import by.http.dal.daoexception.DaoException;
import by.http.dal.dao.daoimpl.DaoProvider;
import by.http.entity.Bill;
import by.http.entity.User;
import by.http.service.BillInterface;
import by.http.service.exception.ServiceException;

public class BillServiceImpl implements BillInterface{

	DaoUser daoUser = DaoProvider.getDaoProvider().getUserDao();
	DaoBill daoBill = DaoProvider.getDaoProvider().getBillDao();
	Bill bill;
	User user;
    private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public String blockBill(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		try {
		String asd =(String) request.getParameter("name");
	    Pattern pattern = Pattern.compile("\s[a-zA-Z]+");
	    Matcher matcher = pattern.matcher(asd);
	    matcher.find();
	    String bilNameString = asd.substring(matcher.start(), matcher.end()).trim();
	    User user = (User)request.getSession().getAttribute("user");
	    bill = daoBill.BillNameCheck(user.getId(), bilNameString);
		    daoBill.updateBillStatus(bill.getId(), true);
		} catch (DaoException e) {
			LOGGER.error("Error block bill DAO" + e);
			request.getSession().setAttribute("error", "Please try again!");
			throw new ServiceException(e);
		} catch (NullPointerException exception) {
			LOGGER.error("Error block bill NPE" + exception);
			//Человек просто не выбрал счет.
		}
		return "HomePage.jsp";
		
	}

	@Override
	public String unblockBill(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		String billName = (String)request.getParameter("bill");
		String username = (String)request.getParameter("username");
		try {
			int userId = daoUser.getUserIDByUsername(username);
			if (userId == 0) {
				request.getSession().setAttribute("errormp", "User is not exists!");
			}
			bill = daoBill.BillNameCheck(daoUser.getUserIDByUsername(username), billName);
			daoBill.updateBillStatus(bill.getId(), false);
			request.getSession().setAttribute("errormp", "Bill is unblocked!");
		} catch (DaoException e) {
			request.getSession().setAttribute("errormp", "Bill or User is not Exists!");
			throw new ServiceException(e);
		}
		return "AdminMainPage.jsp";
	
	}

	@Override
	public String createBill(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		String billName = (String)request.getParameter("bill_name");
		String cardName = (String)request.getParameter("card_name");
		User user = (User)request.getSession().getAttribute("user");
		if (billName != "" && cardName != "") {
			try {
				bill = daoBill.BillNameCheck(user.getId(), billName);
				if (bill == null) {
					daoBill.createBill(user.getId(),billName);
					bill = daoBill.BillNameCheck(user.getId(), billName);
					daoBill.createCard(user.getId(), bill.getId(), cardName);
					request.getSession().setAttribute("errorhp", "All done!");
				} else {
					request.setAttribute("errorhp", "Bill with this name is already exists!");
				}
			} catch (DaoException e) {
				LOGGER.error("Error create bill", e);
				request.getSession().setAttribute("errorhp", "Please try again!");
				throw new ServiceException(e);
			}
		} else {
			request.getSession().setAttribute("errorhp", "Please fill all fileds!");
		}
		return "HomePage.jsp";
	}

	@Override
	public String replenishBalance(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		String newBalance = (String)request.getParameter("balance");
	    user = (User)request.getSession().getAttribute("user");
	    if (newBalance != "") {
		    try {
			    bill = (Bill) session.getAttribute("bill");
			    daoBill.updateBalance(bill.getId(), bill.getBalance()+ Integer.valueOf(newBalance));
				session.setAttribute("errorhp", "Done!");
			} catch (DaoException e) {
				LOGGER.error("Error replenish balance", e);
				session.setAttribute("errorhp", "Please try again!");
				throw new ServiceException(e);
			}
		}
	    session.removeAttribute("bill");
	    return "HomePage.jsp";
		
	}

	@Override
	public String makePayment(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
	    user = (User)request.getSession().getAttribute("user");
	    String recieverBillNameString =  (String)request.getParameter("reciever_name");
	    String recieverLoginString =  (String)request.getParameter("reciever_login");
	    bill = (Bill)  session.getAttribute("bill");
	    try {
	    Integer amountInteger =  Integer.valueOf(request.getParameter("amount"));
	    String result = daoBill.sendMoney(recieverLoginString, recieverBillNameString, user.getId(), bill.getName(), amountInteger);
		request.setAttribute("errorhp", result);
	    } catch(NumberFormatException e) {
			LOGGER.error("error convert int from string", e);
			session.setAttribute("errorhp", "Please input amount!");
			throw new ServiceException(e);
	    }catch(DaoException e) {
			LOGGER.error("error make payment", e);
			session.setAttribute("errorhp", "Please try again!");
			throw new ServiceException(e);
	    }
	    return "HomePage.jsp";
		
	}
	
	public String isBlocked(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		HttpSession session = request.getSession();
		String asd =(String) request.getParameter("name");
		String newBalance = (String)request.getParameter("balance");
	    Pattern pattern = Pattern.compile("\s[a-zA-Z]+");
	    Matcher matcher = pattern.matcher(asd);
	    matcher.find();
	    String bilNameString = asd.substring(matcher.start(), matcher.end()).trim();
	    user = (User)request.getSession().getAttribute("user");
	    try {
			bill = daoBill.BillNameCheck(user.getId(), bilNameString);
		} catch (DaoException e) {
			LOGGER.error("Error balance block check" + e);
			session.setAttribute("errorhp", "Please try again!");
			throw new ServiceException(e);
		}
	    if (bill.isBlocked()) {
	    	request.getSession().setAttribute("errorhp", "Bill is blocked!");
	    	return "HomePage.jsp";
	    } else {
	    	session.setAttribute("bill", bill);
	    	switch (request.getParameter("command")) {
	    		case "FUND_PAGE":
	    			return "Replenish.jsp";
	    		default:
	    			return "Payment.jsp";
	    }}
	
	}
}
