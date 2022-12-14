package by.http.command.impl;

import by.http.command.factory.CommandInterface;
import by.http.service.exception.ServiceException;
import by.http.service.impl.HistoryPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HistoryPageCommand implements CommandInterface {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HistoryPageService.executeHistoryPage(request);
			return "History.jsp";
		} catch (ServiceException e) {
			return "HomePage.jsp";
		}
	}

}
