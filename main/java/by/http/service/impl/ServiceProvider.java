package by.http.service.impl;

import by.http.service.BillInterface;
import by.http.service.UserInterface;

public class ServiceProvider {
	private static ServiceProvider instance;
	
	static {
		instance = new ServiceProvider();
	}
	
	private final BillInterface  BillService = new BillServiceImpl();
	private final UserInterface UserService = new UserServiceImpl();
	
	private ServiceProvider() {}
	
	public static ServiceProvider getServiceProvider() {
		return instance;
	}
	
	public BillInterface getBillService() {
		return BillService;
	}
	
	public UserInterface getUserService() {
		return UserService;
	}
}
