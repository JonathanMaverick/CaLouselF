package model;

public class Transaction {
	private String TransactionID; 
	private String UserID;
	private String ItemID;
	
	public Transaction(String transactionID, String userID, String itemID) {
		super();
		TransactionID = transactionID;
		UserID = userID;
		ItemID = itemID;
	}
	
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getItemID() {
		return ItemID;
	}
	public void setItemID(String itemID) {
		ItemID = itemID;
	}
	
	
}
