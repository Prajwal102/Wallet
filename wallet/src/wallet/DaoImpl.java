package wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public  class DaoImpl implements UserDao{
	User current = new User();
	Connection conn = Conne.getcon();
	
	public void login(String phone) {
		try {
			String query  = "SELECT * FROM USERS WHERE phone=(?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phone);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
			current.setPhone(rs.getString("phone"));
			current.setName(rs.getString("name"));
			current.setBalance(rs.getInt("balance"));
			}
			System.out.println("Hello "+current.getName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void addUser(String name,String phone) {
//		User us = new User();
		current.setPhone(phone);	
		current.setName(name);
		try {
			String query = "INSERT INTO USERS(phone,name) VALUES(?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1,current.getPhone());
			pstmt.setString(2,current.getName());
			pstmt.execute();
			addAcc();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public int checkBalance() {
		try {
			int amt=-1;
			String query = "SELECT balance FROM USERS WHERE phone=(?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1,current.getPhone());
		    ResultSet rs = pstmt.executeQuery();
		    while(rs.next()) {
		    amt = rs.getInt("balance");
		    }
		    return(amt);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	    
		return -1;
		
	}

	@Override
	public void addMoney(int amt) {
		Scanner sc = new Scanner(System.in);
		try {
			ArrayList<BankAcc> bankarr = new ArrayList<BankAcc>();
			System.out.println("Select bank");
			String query1 = "SELECT * FROM ACCOUNT WHERE phone=(?)";
			PreparedStatement pstmt1 = conn.prepareStatement(query1);
			pstmt1.setString(1,current.getPhone());
			ResultSet rs = pstmt1.executeQuery();
			while(rs.next())
			{
				BankAcc bacc = new BankAcc();
				bacc.setBankid(rs.getInt("bankid"));
				bacc.setBankName(rs.getString("accname"));
				bacc.setBankAmt(rs.getInt("bankamt"));
				bankarr.add(bacc);
			}
			for(int i = 0;i<bankarr.size();i++) {
				System.out.println(i+ ". "+bankarr.get(i).getBankName() + " (" + bankarr.get(i).getBankAmt() + ")");
			}
			System.out.println("Enter choice");
			int choice = sc.nextInt();
			
			int newbankid = bankarr.get(choice).getBankid();
			String query0 = "UPDATE ACCOUNT SET bankamt=bankamt-(?) where bankid=(?)";
			PreparedStatement pstmt0 = conn.prepareStatement(query0);
			pstmt0.setInt(1,amt);
			pstmt0.setInt(2,newbankid);	
		    int affected0 = pstmt0.executeUpdate();
		    if(affected0 > 0)
		    	System.out.println("Amount deducted from bank");
					
			String query = "UPDATE USERS SET balance=balance+(?) where phone=(?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,amt);
			pstmt.setString(2,current.getPhone());
		    int affected = pstmt.executeUpdate();
		    if(affected > 0) {
		    	System.out.println("Money Added to wallet");
		    	System.out.print("Updated wallet balance: ");
		    	System.out.println(checkBalance());
		    } 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
// 		finally {
// 			sc.close();
// 		}

	    
	}

	@Override
	public ArrayList<Transactions> showTransactions() {
		
		try {
			ArrayList<Transactions> banktrans = new ArrayList<Transactions>();
			String query = "SELECT *  FROM TRANSACTIONS WHERE sender = (?) OR receiver=(?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1,current.getPhone());
			pstmt.setString(2, current.getPhone());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Transactions trans = new Transactions();
				trans.setSender(rs.getString("sender"));
				trans.setReceiver(rs.getString("receiver"));
				trans.setAmt(rs.getInt("amt"));
				banktrans.add(trans);
			}
			return(banktrans);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}



	public void sendMoney(String recv_phone, int amt) {
		if(amt > current.getBalance() ) {
			System.out.println("Low Wallet Balance! Add money from bank");
			addMoney(amt);
		}
		try {
			String query = "UPDATE USERS SET balance=balance-(?) where phone=(?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amt);
			pstmt.setString(2,current.getPhone());
		    int affected = pstmt.executeUpdate();
		    if(affected > 0)
		    	System.out.println("Amount Sent");
		    String query2 = "UPDATE USERS SET balance=balance+(?) where phone=(?)";
		    PreparedStatement pstmt2 = conn.prepareStatement(query2);
			pstmt2.setInt(1, amt);
			pstmt2.setString(2,recv_phone);
		    int affected2 = pstmt2.executeUpdate();
		    if(affected2>0) {
		    	System.out.println("Amount Received by "+recv_phone);
	    		System.out.print("Updated wallet balance: ");
	    		System.out.println(checkBalance());
		    }
		    String query3 = "INSERT INTO TRANSACTIONS VALUES(?,?,?)";
			PreparedStatement pstmt3 = conn.prepareStatement(query3);
			pstmt3.setString(1,current.getPhone());
			pstmt3.setString(2,recv_phone);
			pstmt3.setInt(3, amt);
			pstmt3.execute();

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAcc() {
		System.out.println("Add a bank account");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter bankname");
		String name = sc.next();
		System.out.println("Enter bank balance");
		int amt = sc.nextInt();

		try {
			String query = "INSERT INTO ACCOUNT(phone,accname,bankamt) VALUES(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1,current.getPhone());
			pstmt.setString(2,name);
			pstmt.setInt(3, amt);
			pstmt.execute();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
// 		finally {
// 			sc.close();
// 		}
	}

	
}
