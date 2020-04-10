package wallet;

public class BankAcc {
	
	private int bankid;
	private String BankName;
	private int BankAmt;
	
	public int getBankAmt() {
		return BankAmt;
	}
	public void setBankAmt(int bankAmt) {
		BankAmt = bankAmt;
	}
	public int getBankid() {
		return bankid;
	}
	public void setBankid(int bankid) {
		this.bankid = bankid;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	
}