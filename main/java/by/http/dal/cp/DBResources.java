package by.http.dal.cp;

public class DBResources {
	public DBResources() {};
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/payment?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "123456";
    public static final int DB_POOL_SIZE = 5;
}
