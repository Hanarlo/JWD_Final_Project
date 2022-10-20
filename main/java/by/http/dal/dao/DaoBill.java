package by.http.dal.dao;

import java.util.ArrayList;

import by.http.dal.daoexception.DaoException;
import by.http.entity.Bill;
import by.http.entity.CreditCard;
import by.http.entity.History;

public interface DaoBill {
	public void createCard(int userID, int BillID, String name) throws DaoException;
	public ArrayList<Bill> getBillsByUserID(int userID) throws DaoException;
	public String getBillNameByID(int billID) throws DaoException;
	public ArrayList<Integer> getBillsID(int userID) throws DaoException;
	public ArrayList<CreditCard> getCreditCardByUserID(int userID) throws DaoException;
	public String sendMoney(String recieverLogin, String recieverBillName,int senderId, String senderBillName, int amount) throws DaoException;
	public void updateBalance(int BillID, int newbalance)  throws DaoException ;
	public ArrayList<History> getIncomingHistoryByUserID(int userID) throws DaoException;
	public ArrayList<History> getOutgoingHistoryByUserID(int userID) throws DaoException;
	public Bill BillNameCheck(Integer userID, String bill_name) throws DaoException;
	public void createBill(int UserID, String name) throws DaoException;
	public void updateBillStatus(int billID, Boolean status) throws DaoException;
	public int getBalanceByBillID(int billID) throws DaoException;
}
