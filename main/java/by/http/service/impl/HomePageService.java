 package by.http.service.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.http.dal.dao.*;
import by.http.dal.daoexception.DaoException;
import by.http.dal.dao.daoimpl.DaoProvider;
import by.http.entity.Bill;
import by.http.entity.CreditCard;
import by.http.entity.User;
import by.http.service.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomePageService {
	private static DaoBill billDao = DaoProvider.getDaoProvider().getBillDao();
	private static DaoUser userDao = DaoProvider.getDaoProvider().getUserDao();
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static void executeMainPage(HttpServletRequest request) throws ServiceException{
    	User user = (User)request.getSession().getAttribute("user");
    	getBillsById(user.getId(),request);
    	getCreditCardsById(user.getId(),request);
    	getBillsID(user.getId(),request);
    	getBillNames(user.getId(),request);
    }
	
	private static void getBillsById(int id,HttpServletRequest request) throws ServiceException{
		try {
		ArrayList<Bill> billArray = billDao.getBillsByUserID(id);
		ArrayList<String> resultArrayList = new ArrayList<>();
			for (Bill b : billArray) {
				int i = 0;
				if (b.isBlocked()) {
					resultArrayList.add("Name - " + b.getName() + "; Balance - " + b.getBalance() + "; BLOCKED");
				} else {
					resultArrayList.add("Name - " + b.getName() + "; Balance - " + b.getBalance());
				}
			}
			request.getSession().setAttribute("result_bill_array_list", resultArrayList);
		} catch (DaoException e) {
			LOGGER.error("Error while retrieving String[] from Result set!", e);
			throw new ServiceException(e);
		}
		request.getSession().setAttribute("error", "something go wrong, please try again!");
		
	}

	private static void getCreditCardsById(int id,HttpServletRequest request) throws ServiceException{
		try {
		ArrayList<CreditCard> cardArray = billDao.getCreditCardByUserID(id);
		ArrayList<String> resultArrayList = new ArrayList<>();
			for (CreditCard c : cardArray) {
				int i = 0;
				String billName = billDao.getBillNameByID(c.getBillID());
				resultArrayList.add("Name - " + c.getName() + "; linked to - " + billName);
			}
			request.getSession().setAttribute("result_card_array_list", resultArrayList);
		} catch (DaoException e) {
			LOGGER.error("Error while retrieving String[] from Result set!", e);
			throw new ServiceException(e);
		}
	
	}
	private static void getBillsID(int userID,HttpServletRequest request) throws ServiceException{
		try {
		ArrayList<Integer> billArray = billDao.getBillsID(userID);
		request.getSession().setAttribute("bill_id_array", billArray);
		} catch (DaoException e) {
			LOGGER.error("Error getBillsID!" + e);
			throw new ServiceException(e);
		}}
	private static void getBillNames(int userID,HttpServletRequest request) throws ServiceException{
	try {
	ArrayList<Bill> billsName = billDao.getBillsByUserID(userID);
	ArrayList<String> resultArrayList = new ArrayList<>();
	for (Bill b : billsName) {
		int i = 0;
		String billName = b.getName();
		resultArrayList.add(billName);
	}
	request.getSession().setAttribute("bill_names_array", resultArrayList);
	} catch (DaoException e) {
		LOGGER.error("Error getBillsName!" + e);
		throw new ServiceException(e);
	}
}
	public static Boolean isActiveUser (HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		if ( user != null) {
			return true;
		}
		return false;
	}
	}
