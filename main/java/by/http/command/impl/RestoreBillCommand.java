package by.http.command.impl;

import by.http.command.factory.CommandInterface;
import by.http.service.BillInterface;
import by.http.service.exception.ServiceException;
import by.http.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestoreBillCommand implements CommandInterface {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		BillInterface billService = ServiceProvider.getServiceProvider().getBillService();
		try {
			return billService.unblockBill(request, response);
		} catch (ServiceException e) {
			return "AdminMainPage.jsp";
		}
	}

}
