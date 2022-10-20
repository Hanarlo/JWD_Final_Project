package by.http.dal.dao.daoimpl;

import java.nio.channels.NonWritableChannelException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression;

import by.http.dal.cp.ConnectionPool;
import by.http.dal.dao.DaoUser;
import by.http.dal.daoexception.DaoException;

public class DAOUserImpl implements DaoUser{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String createUserSQL = "INSERT INTO user (username,password, role_id) VALUES(?,?,'1')";
    private static final String deleteUserSQL = "UPDATE user SET role = 2 WHERE id = ?";
    private static final String getUserIDByUsernameSQL = "SELECT id FROM user WHERE username=?";
    private static final String getUserNameByUserIDSQL = "SELECT username FROM user WHERE id=?";
    private static final String userExistsSQL = "SELECT 1 FROM user WHERE username=? AND password =?";
    private static final String updateUserPasswordSQL = "UPDATE user SET password = ? WHERE id = ?";
    private static final String updateUserNameSQL = "UPDATE user SET username = ? WHERE id = ? ";
    private static final String getUserRoleByUsernameSQL = "SELECT role_id FROM user  WHERE username=?";
    private static final String usernameCheckSQL = "SELECT 1 FROM user WHERE username=?";
    private static final String updateUserRoleSQL = "UPDATE user SET role_id = ? WHERE id = ?";

	   protected DAOUserImpl() {}
	  
	@Override
	public void createUser(String login, String password) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
			try {
				PreparedStatement ps = connection.prepareStatement(createUserSQL);
				ps.setString(1, login);
				ps.setString(2, password);
				ps.execute();
				ps.close();
			} catch (SQLException e) {
				LOGGER.error("Error createUser!: ", e);
				throw new DaoException(e);
			} finally {
				ConnectionPool.getInstance().closeConnection(connection);
			}
	}
	

	@Override
	public void deleteUser(int userId)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement(deleteUserSQL);
			pStatement.setInt(1, userId);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			LOGGER.error("Error delete user! ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
		
	}
	@Override
	public void updateUserPassword(int userId, String pass)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement(updateUserPasswordSQL);
			pStatement.setString(1, pass);
			pStatement.setInt(2, userId);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			LOGGER.error("Error password update! ",e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
		
	}
	@Override
	public void updateUserName(int userId, String name)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement(updateUserNameSQL);
			pStatement.setString(1, name);
			pStatement.setInt(2, userId);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			LOGGER.error("Error username change! ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
		
	}

	@Override
	public Boolean userExists(String username, String password) throws DaoException {
	      Connection connection = ConnectionPool.getInstance().takeConnection();
			PreparedStatement pStatement;
			ResultSet rs;
				try {
					pStatement = connection.prepareStatement(userExistsSQL);
					pStatement.setString(1, username);
					pStatement.setString(2, password);
					rs = pStatement.executeQuery();
					Boolean result = rs.next();
					ConnectionPool.getInstance().closePSRS(pStatement, rs);
					return result;
				} catch (SQLException e) {
					LOGGER.error("Error user exist check ", e);
					throw new DaoException(e);
				} finally {
					ConnectionPool.getInstance().closeConnection(connection);
				}
				
	}

	@Override
	public int getUserIDByUsername(String username)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		try {
			pStatement = connection.prepareStatement(getUserIDByUsernameSQL);
			pStatement.setString(1, username);
			rSet = pStatement.executeQuery();
			rSet.next();
			int result = rSet.getInt(1);
			ConnectionPool.getInstance().closePSRS(pStatement, rSet);
			return result;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving user id by name: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}

	@Override
	public int getUserRoleByUsername(String username) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		int role = 1;
		try {
			pStatement = connection.prepareStatement(getUserRoleByUsernameSQL);
			pStatement.setString(1, username);
			rSet = pStatement.executeQuery();
			rSet.next();
			role = rSet.getInt(1);
			ConnectionPool.getInstance().closePSRS(pStatement, rSet);
			return role;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving bill's ID: " + e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}



	@Override
	public boolean usernameCheck(String username) throws DaoException {
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rs;
			try {
				pStatement = connection.prepareStatement(usernameCheckSQL);
				pStatement.setString(1, username);
				rs = pStatement.executeQuery();
				Boolean b = !rs.next();
				ConnectionPool.getInstance().closePSRS(pStatement, rs);
				return b;
			} catch (SQLException e) {
				LOGGER.error("Error username check!: " + e);
				throw new DaoException(e);
			} finally{
				ConnectionPool.getInstance().closeConnection(connection);
			}


	}

	@Override
	public void updateUserRole(int userID, int role)  throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		try {
			PreparedStatement pStatement = connection.prepareStatement(updateUserRoleSQL);
			pStatement.setInt(1, role);
			pStatement.setInt(2, userID);
			pStatement.execute();
			pStatement.close();
		} catch (SQLException e) {
			LOGGER.error("Error update user role!: " + e.getMessage());
			throw new DaoException(e);
		}  finally{
			ConnectionPool.getInstance().closeConnection(connection);
		}
		
	}

	@Override
	public String getUserNameByUserID(int id) throws DaoException{
	      Connection connection = ConnectionPool.getInstance().takeConnection();
		PreparedStatement pStatement;
		ResultSet rSet;
		try {
			pStatement = connection.prepareStatement(getUserNameByUserIDSQL);
			pStatement.setInt(1, id);
			rSet = pStatement.executeQuery();
			rSet.next();
			String result = rSet.getString(1);
			ConnectionPool.getInstance().closePSRS(pStatement,rSet);
			return result;
		} catch (SQLException e) {
			LOGGER.error("Error retrieving user name by id: ", e);
			throw new DaoException(e);
		} finally {
			ConnectionPool.getInstance().closeConnection(connection);
		}
	}
	
	
}
