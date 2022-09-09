package by.http.dao;

import java.io.ObjectInputStream.GetField;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.catalina.util.Introspection;

public interface Dao {
	public void createUser(String login, String password) throws SQLException;
	public void createCard(int userID, int BillID, String name);
	public void deleteUser(int userId);
	public int getUserIDByUsername(String username);
	public String getUserNameByUserID(int id);
	public ResultSet getBillsByUserID(int userID);
	public ResultSet getBillNameByID(int billID);
	public ResultSet getBillsID(int userID);
	public ResultSet getCreditCardByUserID(int userID);
	public Boolean userExists(String username, String password) throws SQLException;
	public void updateUserPassword(int userId, String newPassword);
	public void updateUserName(int userId, String newUserName);
	public String sendMoney(String recieverLogin, String recieverBillName,int senderId, String senderBillName, int amount);
	public void updateBalance(int BillID, int newbalance)  throws SQLException ;
	public int getUserRoleByUsername(String username);
	public ResultSet getIncomingHistoryByUserID(int userID);
	public ResultSet getOutgoingHistoryByUserID(int userID);
	public boolean usernameCheck(String username) throws SQLException;
	public ResultSet BillNameCheck(Integer userID, String bill_name);
	public void createBill(int UserID, String name);
	public void updateBillStatus(int billID, Boolean status);
	public int getBalanceByBillID(int billID);
	public void updateUserRole(int userID, int role);
}
