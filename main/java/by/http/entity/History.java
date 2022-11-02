package by.http.entity;

import java.sql.Date;

public class History {
		private int id;
		private int recieverBillID;
		private int recieverID;
		private int senderBillID;
		private int senderID;
		private int amount;
		private Date date;
		
		
		
		public History() {
			super();
		}
		public History(int id, int recieverBillID, int recieverID, int senderBillID, int senderID, int amount,
				Date date) {
			super();
			this.id = id;
			this.recieverBillID = recieverBillID;
			this.recieverID = recieverID;
			this.senderBillID = senderBillID;
			this.senderID = senderID;
			this.amount = amount;
			this.date = date;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getRecieverBillID() {
			return recieverBillID;
		}
		public void setRecieverBillID(int recieverBillID) {
			this.recieverBillID = recieverBillID;
		}
		public int getRecieverID() {
			return recieverID;
		}
		public void setRecieverID(int recieverID) {
			this.recieverID = recieverID;
		}
		public int getSenderBillID() {
			return senderBillID;
		}
		public void setSenderBillID(int senderBillID) {
			this.senderBillID = senderBillID;
		}
		public int getSenderID() {
			return senderID;
		}
		public void setSenderID(int senderID) {
			this.senderID = senderID;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		
		
}
