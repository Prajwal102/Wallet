package wallet;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DaoImpl d = new DaoImpl();
		
		System.out.println("Welcome");
		System.out.println("1.Register");
		System.out.println("2. Login");
		
		int c1 = sc.nextInt();
		switch(c1) {
		case 1:
			System.out.println("Enter Name");
			String name = sc.next();
			System.out.println("Enter phone");
			String phone = sc.next();
			d.addUser(name, phone);
			break;
		
		case 2:
			System.out.println("Enter mobile number");
			phone = sc.next();
			d.login(phone);
			break;
		default:
			System.out.println("Invalid choice");
			break;
		}
		
		while(true) {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
			int choice = 0;
			
			System.out.println("1. Check Balance");
			System.out.println("2. Add Money to Wallet");
			System.out.println("3. Send Money");
			System.out.println("4. Add Bank Account");
			System.out.println("5. Show Transactions");
			System.out.println("6. Exit");
			
			 choice = s.nextInt();
			
			switch(choice) {
			
			case 1:
				System.out.print("Balance: ");
				System.out.println(d.checkBalance());
				
				break;
			case 2:
				int amt = 0;
				System.out.println("Enter amount");
				amt = s.nextInt();
				d.addMoney(amt);
				
				break;
			case 3:
				System.out.println("Enter recepient mobile number");
				String mob = s.next();
				System.out.println("Enter amount");
				amt = s.nextInt();
				d.sendMoney(mob, amt);
				break;
			case 4:
				d.addAcc();
				
				break;
			case 5:
				ArrayList<Transactions> trans = new ArrayList<Transactions>();
				trans = d.showTransactions();
				System.out.println("All Transactions");
				System.out.println("-----------------------------");
				for(int i=0;i<trans.size();i++) {
					System.out.println(trans.get(i).getSender() + " -> " + trans.get(i).getReceiver() + " -> " + trans.get(i).getAmt());
				}
				break;
			case 6: System.exit(0);
			default: System.out.println("Invalid Entry");
			}
//			s.close();
			
		}
		
		
	
		
		
		
		
	}
	
	
	
	
}
