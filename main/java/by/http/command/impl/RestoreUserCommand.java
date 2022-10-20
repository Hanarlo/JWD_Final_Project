package by.http.command.impl;

import by.http.command.factory.CommandInterface;
import by.http.service.UserInterface;
import by.http.service.exception.ServiceException;
import by.http.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestoreUserCommand implements CommandInterface {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		UserInterface userService = ServiceProvider.getServiceProvider().getUserService();
		try {
			return userService.restoreUser(request, response);
		} catch (ServiceException e) {
			return "AdminMainPage.jsp";
		}
	}

}
