package wallet;

import java.util.ArrayList;

public interface UserDao {
	public void login(String phone);
	public void addUser(String name,String phone);
	public void addMoney(int amt);
	public int checkBalance();
	public ArrayList<Transactions> showTransactions();
	public void sendMoney(String recv_phone,int amt);
	public void addAcc();
	
	
}
