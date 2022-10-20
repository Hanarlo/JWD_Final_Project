package by.http.dal.dao.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.http.dal.cp.ConnectionPool;
import by.http.dal.dao.DaoBill;
import by.http.dal.daoexception.DaoException;
import by.http.entity.Bill;
import by.http.entity.CreditCard;
import by.http.entity.History;

public class DAOBillImpl implements DaoBill {
	
	protected DAOBillImpl() {}
	
    private static final Logger LOGGER = LogManager.getLogger();

    
    
    //TODO string to private static final
	@Override
	public void createBill(int UserID, String name) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO bill(is_blocked, balance, user_id, name) VALUES(0,1000,?,?)");
			ps.setInt(1, UserID);
			ps.setString(2, name);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			LOGGER.error("Error while creating the bill! ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
		
	}
	
	@Override
	public void createCard(int userID, int billID,String name)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO credit_card(user_id, bill_id, name) VALUES(?,?,?)");
			ps.setInt(1, userID);
			ps.setInt(2, billID);
			ps.setString(3, name);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			LOGGER.error("Error add credit card ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
		
	}
	
	@Override
	public String sendMoney(String recieverLogin, String recieverBillName, int senderID, String senderBillName, int amount)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement ps;
		int recieverID = 0;
		int BillRecieverID = 0;
		int BillSenderID = 0;
		int Senderbalance = 0;
		int Recieverbalance = 0;
		ResultSet rs;
		Bill bill;
		try {
			ps = connection.prepareStatement("SELECT id FROM user WHERE username = ?");
			ps.setString(1, recieverLogin);
			rs = ps.executeQuery();
			rs.next();
			recieverID = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("Error retrieving reciever ID!: " + e.getMessage());
			return "User " + recieverLogin + " does not exists!";
		}

		try {
			bill = BillNameCheck(recieverID, recieverBillName);
			BillRecieverID = bill.getId();
			ps = connection.prepareStatement("SELECT is_blocked FROM bill WHERE id = ?");
			ps.setInt(1, BillRecieverID);
			rs = ps.executeQuery();
			rs.next();
			Boolean isBlockedBoolean = rs.getBoolean(1);
			if (isBlockedBoolean) {
				return "Reciever bill is blocked!";
			}
			Recieverbalance = getBalanceByBillID(BillRecieverID);
			Recieverbalance = Recieverbalance + amount;
		} catch (SQLException e) {
			System.out.println("Error retrieving reciever bill ID!: " + e.getMessage());
			return "Bill " + recieverBillName + " does not exists!";
		}

			bill = BillNameCheck(senderID, senderBillName);
			BillSenderID = bill.getId();
			Senderbalance = getBalanceByBillID(BillSenderID);
			if (Senderbalance < amount) {
				return "Not enough money!";
			}
			Senderbalance = Senderbalance - amount;
		
		try
		{
		  updateBalance(BillSenderID, Senderbalance);
		  updateBalance(BillRecieverID, Recieverbalance);
		  ps = connection.prepareStatement("INSERT INTO history(recieve_bill_id, reciever_id, sender_bill_id, sender_id, amount, date) VALUES (?,?,?,?,?,?)");
		  ps.setInt(1, BillRecieverID);
		  ps.setInt(2, recieverID);
		  ps.setInt(3, BillSenderID);
		  ps.setInt(4, senderID);
		  ps.setInt(5, amount);
		  java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		  ps.setDate(6, date);
		  ps.execute();
		}catch(Exception e) {
			LOGGER.error("Error balance update!: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
		return "All done!";
	}

	@Override
	public void updateBalance(int BillID, int newbalance) throws DaoException {
	      Connection connection = ConnectionPool.getInstance().takeConnection();
			PreparedStatement ps;
			try {
				ps = connection.prepareStatement("UPDATE bill SET balance = ? WHERE id = ?");
				ps.setInt(1, newbalance);
				ps.setInt(2, BillID);
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				LOGGER.error("Error retrieving  incoming bill's ID: ", e);
				throw new DaoException(e);
			}

			ConnectionPool.getInstance().closeConnection(connection);
	}
	
	@Override
	public ArrayList<Bill> getBillsByUserID(int userID)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rs;
		ArrayList<Bill> resultAL = new ArrayList<>();
		Bill bill;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM bill WHERE user_id=?");
			pStatement.setInt(1, userID);
			rs = pStatement.executeQuery();
			while (rs.next()) {
				bill = new Bill(rs.getInt(1), rs.getBoolean(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
				resultAL.add(bill);
			}
			ConnectionPool.getInstance().closePSRS(pStatement, rs);
			return resultAL;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving bill by name: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}

	}

	@Override
	public ArrayList<CreditCard> getCreditCardByUserID(int userID) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		ArrayList<CreditCard> resultAL = new ArrayList<>();
		CreditCard creditCard;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM credit_card WHERE user_id=?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
			while (rSet.next()){
				creditCard = new CreditCard(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getString(4));
				resultAL.add(creditCard);
			}
			ConnectionPool.getInstance().closePSRS(pStatement, rSet);
			return resultAL;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving credit card by name: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}

	}

	@Override
	public String getBillNameByID(int billID) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		String result;
		try {
			pStatement = connection.prepareStatement("SELECT name FROM bill WHERE id=?");
			pStatement.setInt(1, billID);
			rSet = pStatement.executeQuery();
			rSet.next();
			result = rSet.getString(1);
			ConnectionPool.getInstance().closePSRS(pStatement, rSet);
			return result;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving bill name by id: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}

	@Override
	public ArrayList<Integer> getBillsID(int userID) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		ArrayList<Integer> resultAL = new ArrayList<>();
		try {
			pStatement = connection.prepareStatement("SELECT id FROM bill  WHERE user_id=?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
			while (rSet.next()) {
				resultAL.add(rSet.getInt(1));
			}
			ConnectionPool.getInstance().closePSRS(pStatement, rSet);
			return resultAL;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving bill's ID: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}
	
	@Override
	public ArrayList<History> getIncomingHistoryByUserID(int userID) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		ArrayList<History> historyAL = new ArrayList<>();
		History history;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM history  WHERE reciever_id = ?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
			while (rSet.next()) {
				history = new History(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getInt(4), rSet.getInt(5), rSet.getInt(6), rSet.getDate(7));
				historyAL.add(history);
			}
			ConnectionPool.getInstance().closePSRS(pStatement, rSet);
			return historyAL;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving  incoming bill's ID: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}
	
	@Override
	public ArrayList<History> getOutgoingHistoryByUserID(int userID) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		ArrayList<History> historyAL = new ArrayList<>();
		History history;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM history  WHERE sender_id = ?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
			while (rSet.next()) {
				history = new History(rSet.getInt(1), rSet.getInt(2), rSet.getInt(3), rSet.getInt(4), rSet.getInt(5), rSet.getInt(6), rSet.getDate(7));
				historyAL.add(history);
			}
			ConnectionPool.getInstance().closePSRS(pStatement, rSet);
			return historyAL;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving outgoing bill's ID: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}
	
	@Override
	public Bill BillNameCheck(Integer userID, String bill_name) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rs;
		Bill bill = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM bill WHERE user_id = ? AND name = ?");
			pStatement.setInt(1, userID);
			pStatement.setString(2, bill_name);
			rs = pStatement.executeQuery();
			if(rs.next()) {
				bill = new Bill(rs.getInt(1), rs.getBoolean(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
				ConnectionPool.getInstance().closePSRS(pStatement, rs);
			}
			return bill;
		} catch (SQLException e) {
			LOGGER.error("Error BIllNameCheck!: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}
	
	@Override
	public void updateBillStatus(int billID, Boolean status) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		try {
			pStatement = connection.prepareStatement("UPDATE bill SET is_blocked = ? WHERE id = ?");
			pStatement.setBoolean(1, status);
			pStatement.setInt(2, billID);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			LOGGER.error("Error updateBIllStatus!: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}

	}

	@Override
	public int getBalanceByBillID(int billID) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement ps;
		ResultSet rs;
		int balance;
		try {
			ps = connection.prepareStatement("SELECT balance FROM BILL where id = ?");
			ps.setInt(1, billID);
			rs = ps.executeQuery();
			rs.next();
			balance = rs.getInt(1);
			ConnectionPool.getInstance().closePSRS(ps, rs);
			return balance;
		} catch (SQLException e) {
			LOGGER.error("Error get balance by bill id!: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}
}
