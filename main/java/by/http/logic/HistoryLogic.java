package by.http.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.catalina.connector.Response;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;
import by.http.entity.User;

public class HistoryLogic {
	Dao dao = DAOImpl.getInstance();;
	
	public ArrayList<String> getIncomingHistory(int id) {
		ResultSet rSet = dao.getIncomingHistoryByUserID(id);
		ArrayList<String> resultArrayList = new ArrayList<>();
		try {
			while(rSet.next()) {
				int i = 0;
				ResultSet recieveBillsName = dao.getBillNameByID(rSet.getInt(3));
				recieveBillsName.next();
				String recieveBillName = recieveBillsName.getString(1);
				ResultSet senderBillsName = dao.getBillNameByID(rSet.getInt(5));
				senderBillsName.next();
				String senderBillName = senderBillsName.getString(1);
				resultArrayList.add("To your bill - " + recieveBillName + " was sended from - " + senderBillName + " " + rSet.getInt(6) + " US dollars at " + rSet.getDate(7));
				}
			return resultArrayList;
		} catch (SQLException e) {
			System.out.println("Error while retrieving incoming history!: " + e.getMessage());
		}
			return null;
		
	}

	public ArrayList<String> getOutgoingHistory(int id) {
		ResultSet rSet = dao.getOutgoingHistoryByUserID(id);
		ArrayList<String> resultArrayList = new ArrayList<>();
		try {
			while(rSet.next()) {
				int i = 0;
				ResultSet recieveBillsName = dao.getBillNameByID(rSet.getInt(2));
				recieveBillsName.next();
				String recieveBillName = recieveBillsName.getString(1);
				ResultSet senderBillsName = dao.getBillNameByID(rSet.getInt(4));
				senderBillsName.next();
				String senderBillName = senderBillsName.getString(1);
				resultArrayList.add("From your bill - " + senderBillName + " was sended to - " + recieveBillName + " " + rSet.getInt(6) + " US dollars at " + rSet.getDate(7));
			}
			return resultArrayList;
		} catch (SQLException e) {
			System.out.println("Error while retrieving outgoing history!: " + e.getMessage());
		}
			return null;
	
	}
}
