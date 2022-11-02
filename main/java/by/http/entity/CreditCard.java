package by.http.entity;

public class CreditCard {
		private int id;
		private int userID;
		private int billID;
		private String name;
		
		
		public CreditCard(int id, int userID, int billID, String name) {
			super();
			this.id = id;
			this.userID = userID;
			this.billID = billID;
			this.name = name;
		}


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public int getUserID() {
			return userID;
		}


		public void setUserID(int userID) {
			this.userID = userID;
		}


		public int getBillID() {
			return billID;
		}


		public void setBillID(int billID) {
			this.billID = billID;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}
		
		
		
}
