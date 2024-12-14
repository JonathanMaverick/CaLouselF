package model;

public class Transaction {
	private String transactionId; 
	private String userId;
	private String itemId;
	private String itemName;
	private int itemPrice;
	private String itemCategory;
	private int itemSize;
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Transaction(String transactionId, String userId, String itemId, String itemName, int itemPrice, String itemCategory, int itemSize) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemSize = itemSize;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getItemSize() {
		return itemSize;
	}

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}

	public Transaction(String transactionId, String userId, String itemId) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.itemId = itemId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
