package by.http.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import by.http.service.exception.ServiceException;

public interface UserInterface {
	public String changePassword(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String changeUsername(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String createUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String loginUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String restoreUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	String deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
