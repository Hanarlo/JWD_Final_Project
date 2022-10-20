package by.http.command.factory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CommandInterface {
	public String execute(HttpServletRequest request, HttpServletResponse response);
}
