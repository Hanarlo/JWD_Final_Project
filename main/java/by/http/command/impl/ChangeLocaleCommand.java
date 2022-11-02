package by.http.command.impl;

import by.http.command.factory.CommandInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLocaleCommand implements CommandInterface{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("local", request.getParameter("local"));
		String lastCommand = (String)request.getParameter("last_command");
		return lastCommand;
	}
}
