package model;

public class Item {
	
	private String itemId;
	private String name;
	private int size;
	private int price;
	private String category;
	private String status;
	private String sellerId;
	private String reason;
	
	public Item(String itemId, String name, int size, int price, String category, String sellerId) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.size = size;
		this.price = price;
		this.category = category;
		this.setSellerId(sellerId);
	}
	
	public Item(String itemId, String name, int size, int price, String category, String status,
			String sellerId, String reason) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.size = size;
		this.price = price;
		this.category = category;
		this.status = status;
		this.reason = reason;
		this.setSellerId(sellerId);
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
}
