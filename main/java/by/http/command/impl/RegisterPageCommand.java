package by.http.command.impl;

import by.http.command.factory.CommandInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterPageCommand implements CommandInterface {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("error");
		return "Register.jsp";
	}

}
