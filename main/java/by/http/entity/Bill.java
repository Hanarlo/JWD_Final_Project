package by.http.entity;

public class Bill {
		private int id;
		private int balance;
		private String name;
		private boolean isBlocked;
		private int userID;
		
		public Bill() {}
		
		public Bill(String name) {
			super();
			this.name = name;
		}
		
		
		public Bill(int id, boolean isBlocked,int balance, int userID, String name) {
			super();
			this.id = id;
			this.balance = balance;
			this.name = name;
			this.isBlocked = isBlocked;
			this.userID = userID;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getBalance() {
			return balance;
		}

		public void setBalance(int balance) {
			this.balance = balance;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isBlocked() {
			return isBlocked;
		}

		public void setBlocked(boolean isBlocked) {
			this.isBlocked = isBlocked;
		}

		public int getUserID() {
			return userID;
		}

		public void setUserID(int userID) {
			this.userID = userID;
		}
		
		
}
