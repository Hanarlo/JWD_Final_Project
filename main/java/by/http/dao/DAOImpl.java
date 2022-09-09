package by.http.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression;

import by.http.ConnectionPool.ConnectionPool;

public class DAOImpl implements Dao{
	
	ConnectionPool connectionPool;
	Connection connection;
	
	   private static DAOImpl dao;

	   private DAOImpl() {
			if (connectionPool == null) {
				connectionPool = new ConnectionPool();
				try {
					connectionPool.init_pool();
				} catch (SQLException e) {
					System.out.println("Error initializing CP!: " + e.getMessage());
				}
			}
	   }

	   public static DAOImpl getInstance() {
	      if(dao == null) {
	         dao = new DAOImpl();
	      }
	       return dao;
	   }
	   

	   public void getConnection() {
	       connection = connectionPool.takeConnection();
	   }
	
	@Override
	public void createUser(String login, String password) throws SQLException {
			dao.getConnection();
			PreparedStatement ps = connection.prepareStatement("INSERT INTO user (username,password, role_id) VALUES(?,?,'1')\r\n");
			ps.setString(1, login);
			ps.setString(2, password);
			ps.execute();
			connectionPool.closeConnection(connection,ps);
	}
	
	@Override
	public void createBill(int UserID, String name) {
		dao.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO bill(is_blocked, balance, user_id, name) VALUES(0,1000,?,?)");
			ps.setInt(1, UserID);
			ps.setString(2, name);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error while creating the bill! " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		
	}
	@Override
	public void deleteUser(int userId) {
		dao.getConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement("UPDATE user SET role = 2 WHERE id = " + userId);
			pStatement.close();
		} catch (SQLException e) {
			System.out.println("Error delete user! " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		
	}
	@Override
	public void updateUserPassword(int userId, String pass) {
		dao.getConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
			pStatement.setString(1, pass);
			pStatement.setInt(2, userId);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			System.out.println("Error password update! " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		
	}
	@Override
	public void updateUserName(int userId, String name) {
		dao.getConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement("UPDATE user SET username = ? WHERE id = ? ");
			pStatement.setString(1, name);
			pStatement.setInt(2, userId);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			System.out.println("Error username change! " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		
	}

	@Override
	public void createCard(int userID, int billID,String name) {
		dao.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO credit_card(user_id, bill_id, name) VALUES(?,?,?)");
			ps.setInt(1, userID);
			ps.setInt(2, billID);
			ps.setString(3, name);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error add credit card " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		
	}

	@Override
	public String sendMoney(String recieverLogin, String recieverBillName, int senderID, String senderBillName, int amount) {
		dao.getConnection();
		PreparedStatement ps = null;
		int recieverID = 0;
		int BillRecieverID = 0;
		int BillSenderID = 0;
		int Senderbalance = 0;
		int Recieverbalance = 0;
		ResultSet rs = null ;
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
			rs = BillNameCheck(recieverID, recieverBillName);
			rs.next();
			BillRecieverID = rs.getInt(1);
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

		try {
			rs = BillNameCheck(senderID, senderBillName);
			rs.next();
			BillSenderID = rs.getInt(1);
			Senderbalance = getBalanceByBillID(BillSenderID);
			if (Senderbalance < amount) {
				return "Not enough money!";
			}
			Senderbalance = Senderbalance - amount;
		} catch (SQLException e) {
			System.out.println("Error retrieving sender bill ID!: " + e.getMessage());
		}
		
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
				System.out.println("Error balance update!: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection,ps,rs);
		}


		return "All done!";
	}

	@Override
	public void updateBalance(int BillID, int newbalance) throws SQLException {
		dao.getConnection();
			PreparedStatement ps = connection.prepareStatement("UPDATE bill SET balance = ? WHERE id = ?");
			ps.setInt(1, newbalance);
			ps.setInt(2, BillID);
			ps.execute();
			ps.close();
					connectionPool.closeConnection(connection,ps);
	}

	@Override
	public Boolean userExists(String username, String password) throws SQLException {
		dao.getConnection();
			PreparedStatement pStatement;
			ResultSet rs;
				pStatement = connection.prepareStatement("SELECT 1 FROM user WHERE username=? AND password =?");
				pStatement.setString(1, username);
				pStatement.setString(2, password);
				rs = pStatement.executeQuery();
				connectionPool.closeConnection(connection);
				return rs.next();


				
	}

	@Override
	public int getUserIDByUsername(String username) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		try {
			pStatement = connection.prepareStatement("SELECT id FROM user WHERE username=?");
			pStatement.setString(1, username);
			rSet = pStatement.executeQuery();
			rSet.next();
			return rSet.getInt(1);
		} catch (SQLException e) {
			System.out.println("Error retrieving user id by name: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return 0;
	}

	@Override
	public ResultSet getBillsByUserID(int userID) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM bill WHERE user_id=?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
		} catch (SQLException e) {
			System.out.println("Error retrieving bill by name: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
			return rSet;

		}

	}

	@Override
	public ResultSet getCreditCardByUserID(int userID) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM credit_card WHERE user_id=?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
		} catch (SQLException e) {
			System.out.println("Error retrieving credit card by name: " + e.getMessage());
		} finally {
					connectionPool.closeConnection(connection);
			return rSet;
		}

	}

	@Override
	public ResultSet getBillNameByID(int billID) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet = null;
		try {
			pStatement = connection.prepareStatement("SELECT name FROM bill WHERE id=?");
			pStatement.setInt(1, billID);
			rSet = pStatement.executeQuery();
			return rSet;
		} catch (SQLException e) {
			System.out.println("Error retrieving bill name by id: " + e.getMessage());
		} finally {
					connectionPool.closeConnection(connection);
		}
		return null;
	}

	@Override
	public ResultSet getBillsID(int userID) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet = null;
		try {
			pStatement = connection.prepareStatement("SELECT id FROM bill  WHERE user_id=?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
			return rSet;
		} catch (SQLException e) {
			System.out.println("Error retrieving bill's ID: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return null;
	}

	@Override
	public int getUserRoleByUsername(String username) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet = null;
		int role = 1;
		try {
			pStatement = connection.prepareStatement("SELECT role_id FROM user  WHERE username=?");
			pStatement.setString(1, username);
			rSet = pStatement.executeQuery();
			rSet.next();
			role = rSet.getInt(1);
			return role;
		} catch (SQLException e) {
			System.out.println("Error retrieving bill's ID: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return role;
	}

	@Override
	public ResultSet getIncomingHistoryByUserID(int userID) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM history  WHERE reciever_id = ?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
			return rSet;
		} catch (SQLException e) {
			System.out.println("Error retrieving  incoming bill's ID: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return null;
	}
	
	@Override
	public ResultSet getOutgoingHistoryByUserID(int userID) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM history  WHERE sender_id = ?");
			pStatement.setInt(1, userID);
			rSet = pStatement.executeQuery();
			return rSet;
		} catch (SQLException e) {
			System.out.println("Error retrieving outgoing bill's ID: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return null;
	}

	@Override
	public boolean usernameCheck(String username) throws SQLException {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rs;
				pStatement = connection.prepareStatement("SELECT 1 FROM user WHERE username=?");
			pStatement.setString(1, username);
			rs = pStatement.executeQuery();
					connectionPool.closeConnection(connection);
			return !rs.next();
	}

	@Override
	public ResultSet BillNameCheck(Integer userID, String bill_name) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rs;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM bill WHERE user_id = ? AND name = ?");
			pStatement.setInt(1, userID);
			pStatement.setString(2, bill_name);
			rs = pStatement.executeQuery();
			return rs;
		} catch (SQLException e) {
			System.out.println("Error BIllNameCheck!: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void updateBillStatus(int billID, Boolean status) {
		dao.getConnection();
		PreparedStatement pStatement;
		try {
			pStatement = connection.prepareStatement("UPDATE bill SET is_blocked = ? WHERE id = ?");
			pStatement.setBoolean(1, status);
			pStatement.setInt(2, billID);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			System.out.println("Error updateBIllStatus!: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}

	}

	@Override
	public int getBalanceByBillID(int billID) {
		dao.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		int balance;
		try {
			ps = connection.prepareStatement("SELECT balance FROM BILL where id = ?");
			ps.setInt(1, billID);
			rs = ps.executeQuery();
			rs.next();
			balance = rs.getInt(1);
			return balance;
		} catch (SQLException e) {
			System.out.println("Error get balance by bill id!: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return 0;
	}


	@Override
	public void updateUserRole(int userID, int role) {
		dao.getConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement("UPDATE user SET role_id = ? WHERE id = ?");
			pStatement.setInt(1, role);
			pStatement.setInt(2, userID);
			pStatement.execute();
		} catch (SQLException e) {
			System.out.println("Error update user role!: " + e.getMessage());
		}
		
	}

	@Override
	public String getUserNameByUserID(int id) {
		dao.getConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		try {
			pStatement = connection.prepareStatement("SELECT username FROM user WHERE id=?");
			pStatement.setInt(1, id);
			rSet = pStatement.executeQuery();
			rSet.next();
			return rSet.getString(1);
		} catch (SQLException e) {
			System.out.println("Error retrieving user name by id: " + e.getMessage());
		} finally {
			connectionPool.closeConnection(connection);
		}
		return null;
	}
	
	
}
