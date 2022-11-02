package by.http.command.factory;

import java.util.HashMap;

import by.http.command.impl.*;
import jakarta.servlet.http.HttpServletRequest;

public class CommandFactory {

	private static HashMap<String, CommandInterface> command;
	static {
	    command = new HashMap<>();
	    command.put("HISTORY_PAGE", new HistoryPageCommand());
	    command.put("LOGIN_PAGE", new LoginPageCommand());
	    command.put("REGISTER_PAGE", new RegisterPageCommand());
	    command.put("REGISTER", new RegisterCommand());
	    command.put("LOGIN", new LoginCommand());
	    command.put("HOME_PAGE", new HomePageCommand());
	    command.put("CHANGE_LOCALE", new ChangeLocaleCommand());
	    command.put("CHANGE_USERNAME", new ChangeUsernameCommand());
	    command.put("CHANGE_PASSWORD", new ChangePasswordCommand());
	    command.put("LOGOUT", new LogOutCommand());
	    command.put("DELETE_ACCOUNT", new DeleteUserCommand());
	    command.put("BLOCK", new BlockCommand());
	    command.put("CREATE_BILL", new CreateBillCommand());
	    command.put("FUND_PAGE", new FundPageCommand());
	    command.put("FUND", new FundCommand());
	    command.put("PAYMENT", new PaymentCommand());
	    command.put("PAYMENT_PAGE", new PaymentPageCommand());
	    command.put("DEFAULT", new LoginPageCommand());
	    command.put("RESTORE_USER", new RestoreUserCommand());
	    command.put("RESTORE_BILL", new RestoreBillCommand());
	    command.put("MAIN_PAGE", new MainPageCommand());
	}
	
	private CommandFactory() {
		
	}
	
	public static CommandInterface getCommand(HttpServletRequest request) {
		String commandString = (String)request.getParameter("command");
		CommandInterface commandInterface = null;
		
		
		if(commandString != null) {
			commandInterface = command.getOrDefault(commandString, new LoginPageCommand());
		} else {
			return commandInterface =  command.get("DEFAULT");
		}
		return commandInterface;
		
	}
	
}
