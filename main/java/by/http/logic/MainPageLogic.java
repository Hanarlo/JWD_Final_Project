package by.http.logic;

import java.awt.dnd.DragSourceAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import by.http.dao.DAOImpl;
import by.http.dao.Dao;
import by.http.entity.User;

public class MainPageLogic {
	Dao dao = DAOImpl.getInstance();;
	
	public ArrayList<String> getBillsById(int id) {
		ResultSet rSet = dao.getBillsByUserID(id);
		ArrayList<String> resultArrayList = new ArrayList<>();
		try {
			while(rSet.next()) {
				int i = 0;
				if (rSet.getBoolean(2)) {
					resultArrayList.add("Name - " + rSet.getString(5) + "; Balance - " + rSet.getInt(3) + "; BLOCKED");
				} else {
					resultArrayList.add("Name - " + rSet.getString(5) + "; Balance - " + rSet.getInt(3));
				}
			}
			return resultArrayList;
		} catch (SQLException e) {
			System.out.println("Error while retrieving String[] from Result set!: " + e.getMessage());
		}
			return null;
		
	}

	public ArrayList<String> getCreditCardsById(int id) {
		ResultSet rSet = dao.getCreditCardByUserID(id);
		ArrayList<String> resultArrayList = new ArrayList<>();
		try {
			while(rSet.next()) {
				int i = 0;
				ResultSet billNameResultSet = dao.getBillNameByID(rSet.getInt(3));
				billNameResultSet.next();
				String billName = billNameResultSet.getString(1);
				resultArrayList.add("Name - " + rSet.getString(4) + "; linked to - " + billName);
			}
			return resultArrayList;
		} catch (SQLException e) {
			System.out.println("Error while retrieving String[] from Result set!: " + e.getMessage());
		}
			return null;
	
	}
	public ArrayList<Integer> getBillsID(int userID){
		Dao dao = DAOImpl.getInstance();;
		ResultSet rSet = dao.getBillsID(userID);
		ArrayList<Integer> billsID = new ArrayList<>();
		try {
			while (rSet.next()) {
				billsID.add(rSet.getInt(1));
			}
			return billsID;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
