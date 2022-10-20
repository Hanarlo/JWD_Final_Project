package by.http.dal.dao;

import java.io.ObjectInputStream.GetField;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.Date;

import org.apache.catalina.util.Introspection;

import by.http.dal.daoexception.DaoException;

public interface DaoUser {
	public void createUser(String login, String password) throws DaoException;
	public void deleteUser(int userId) throws DaoException;
	public int getUserIDByUsername(String username) throws DaoException;
	public String getUserNameByUserID(int id) throws DaoException;
	public Boolean userExists(String username, String password) throws DaoException;
	public void updateUserPassword(int userId, String newPassword) throws DaoException;
	public void updateUserName(int userId, String newUserName) throws DaoException;
	public int getUserRoleByUsername(String username) throws DaoException;
	public boolean usernameCheck(String username) throws DaoException;
	public void updateUserRole(int userID, int role) throws DaoException;
}
