package by.http.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import by.http.service.exception.ServiceException;

public interface BillInterface {
	public String blockBill(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String unblockBill(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String createBill(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String replenishBalance(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String makePayment(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
	public String isBlocked(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
