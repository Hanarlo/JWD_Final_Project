package by.http.service.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.http.dal.dao.DaoBill;
import by.http.dal.dao.DaoUser;
import by.http.dal.dao.daoimpl.DaoProvider;
import by.http.dal.daoexception.DaoException;
import by.http.entity.History;
import by.http.entity.User;
import by.http.service.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

public class HistoryPageService {
	private static DaoBill billDao = DaoProvider.getDaoProvider().getBillDao();
	private static DaoUser userDao = DaoProvider.getDaoProvider().getUserDao();
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static void executeHistoryPage(HttpServletRequest request) throws ServiceException {
    	User user =(User) request.getSession().getAttribute("user");
    	try {
			ArrayList<History> incoming = billDao.getIncomingHistoryByUserID(user.getId());
			ArrayList<History> outgoing = billDao.getOutgoingHistoryByUserID(user.getId());
			ArrayList<String> incomingru = new ArrayList<String>();
			ArrayList<String> outgoingru = new ArrayList<String>();
			for (History h : incoming) {
				incomingru.add("На ваш счет " + billDao.getBillNameByID(h.getRecieverBillID()).toUpperCase() + " от счета " + billDao.getBillNameByID(h.getSenderBillID()).toUpperCase() + " пользователя " +
						userDao.getUserNameByUserID(h.getSenderID()).toUpperCase() + " поступило " + h.getAmount() + " дата " + h.getDate());
			}
			for (History h : outgoing) {
				outgoingru.add("с вашего счета " + billDao.getBillNameByID(h.getSenderBillID()).toUpperCase() + " на счет " + billDao.getBillNameByID(h.getRecieverBillID()).toUpperCase() + " пользователя " +
						userDao.getUserNameByUserID(h.getRecieverID()).toUpperCase() + " отправлено " + h.getAmount() + " дата " + h.getDate());
			}
			request.getSession().setAttribute("incomingHistoryru", incomingru);
			request.getSession().setAttribute("outgoingHistoryru", outgoingru);
			
			ArrayList<String> incomingeng = new ArrayList<String>();
			ArrayList<String> outgoingeng = new ArrayList<String>();
			for (History h : incoming) {
				incomingeng.add("To your bill " + billDao.getBillNameByID(h.getRecieverBillID()).toUpperCase() + " from bill " + billDao.getBillNameByID(h.getSenderBillID()).toUpperCase() + " by user " +
						userDao.getUserNameByUserID(h.getSenderID()).toUpperCase() + " arrived " + h.getAmount() + " on " + h.getDate());
			}
			for (History h : outgoing) {
				outgoingeng.add("from your bill " + billDao.getBillNameByID(h.getSenderBillID()).toUpperCase() + " on bill " + billDao.getBillNameByID(h.getRecieverBillID()).toUpperCase() + " to user " +
						userDao.getUserNameByUserID(h.getRecieverID()).toUpperCase() + " sended " + h.getAmount() + " on " + h.getDate());
			}
			request.getSession().setAttribute("incomingHistoryeng", incomingeng);
			request.getSession().setAttribute("outgoingHistoryeng", outgoingeng);
		} catch (DaoException e) {
			LOGGER.error("Error ExecuteHistoryPage!" + e);
			throw new ServiceException(e);
		}
    }
}
