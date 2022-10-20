package by.http.command.impl;

import by.http.command.factory.CommandInterface;
import by.http.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainPageCommand implements CommandInterface {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
			return "AdminMainPage.jsp";
	}

}
