package by.http.ConnectionPool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class ConnectionPool{
		
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConnections;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

	public ConnectionPool() {
		driverName = DBResources.DB_DRIVER;
		url = DBResources.DB_URL;
		user = DBResources.DB_USER;
		password = DBResources.DB_PASSWORD;
		poolSize = DBResources.DB_POOL_SIZE;
	}
	
	public void init_pool() throws SQLException {
			try {
				Class.forName(driverName);
				givenAwayConnections = new ArrayBlockingQueue<Connection>(poolSize);
				connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
				for (int i = 0; i < poolSize; i++) {
					Connection connection = DriverManager.getConnection(url, user, password);
					PooledConnection pooledConnection = new PooledConnection(connection);
					connectionQueue.add(pooledConnection);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
    private void clearConnectionQueue(){
        try{
            CloseConnectionsQueue(givenAwayConnections);
            CloseConnectionsQueue(connectionQueue);
        } catch (SQLException e){

        }
    }
    public Connection takeConnection() {
        Connection connection = null;
        try{
            connection = connectionQueue.take();
            givenAwayConnections.add(connection);
        } catch (InterruptedException e){
			System.out.println("Error take connetcion! " + e.getMessage());
        }
        return connection;
    }
    public void closeConnection(Connection con, PreparedStatement st, ResultSet rs){
        try {
            con.close();
        } catch (SQLException e){

        }
        try {
            rs.close();
        } catch (SQLException e){

        }
        try {
            st.close();
        } catch (SQLException e){

        }
    }
    public void closeConnection(Connection con, PreparedStatement st){
        try {
            con.close();
        } catch (SQLException e){

        }
        try {
            st.close();
        } catch (SQLException e){

        }
    }
    public void closeConnection(Connection con){
        try {
            con.close();
        } catch (SQLException e){

        }
    }
    private void CloseConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException{
        Connection connection;
        while ((connection = queue.poll()) != null){
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }
    private class PooledConnection implements Connection{
        private Connection connection;

        public PooledConnection(Connection connection) throws SQLException{
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        @Override
        public void clearWarnings()throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return null;
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

        }

        @Override
        public void setHoldability(int holdability) throws SQLException {

        }

        @Override
        public int getHoldability() throws SQLException {
            return 0;
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return null;
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return null;
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {

        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return null;
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return null;
        }

        @Override
        public Clob createClob() throws SQLException {
            return null;
        }

        @Override
        public Blob createBlob() throws SQLException {
            return null;
        }

        @Override
        public NClob createNClob() throws SQLException {
            return null;
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return null;
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return false;
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {

        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {

        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return null;
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return null;
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return null;
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return null;
        }

        @Override
        public void setSchema(String schema) throws SQLException {

        }

        @Override
        public String getSchema() throws SQLException {
            return null;
        }

        @Override
        public void abort(Executor executor) throws SQLException {

        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return null;
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return null;
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {

        }

        @Override
        public void rollback() throws SQLException {

        }

        @Override
        public void close() throws SQLException{
            if (connection.isClosed()){
                throw new SQLException("Alredy closed");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConnections.remove(this)){
                throw new SQLException("Error deleting");
            }
            if (!connectionQueue.offer(this)){
                throw new SQLException("Error allocating connection in the pool");
            }
        }

        @Override
        public boolean isClosed() throws SQLException {
            return false;
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return null;
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {

        }

        @Override
        public String getCatalog() throws SQLException {
            return null;
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {

        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return 0;
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }
}