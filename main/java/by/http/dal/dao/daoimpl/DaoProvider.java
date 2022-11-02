package by.http.dal.dao.daoimpl;

import by.http.dal.dao.DaoBill;
import by.http.dal.dao.DaoUser;
import by.http.dal.dao.daoimpl.DAOBillImpl;
import by.http.dal.dao.daoimpl.DAOUserImpl;

public class DaoProvider {
	private static DaoProvider instance;
	
	static {
		instance = new DaoProvider();
	}
	
	private final DaoBill  daoBill = new DAOBillImpl();
	private final DaoUser daoUser = new DAOUserImpl();
	
	private DaoProvider() {}
	
	public static DaoProvider getDaoProvider() {
		return instance;
	}
	
	public DaoBill getBillDao() {
		return daoBill;
	}
	
	public DaoUser getUserDao() {
		return daoUser;
	}
}
