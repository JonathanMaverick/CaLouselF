package model;

public class Item {
	
	private String itemID;
	private String name;
	private int size;
	private int price;
	private String category;
	private String status;
	private int wishlist;
	private String offerStatus;
	private int itemOfferPrice;
	
	public Item(String itemID, String name, int size, int price, String category) {
		super();
		this.itemID = itemID;
		this.name = name;
		this.size = size;
		this.price = price;
		this.category = category;
	}
	
	public Item(String itemID, String name, int size, int price, String category, String status, int wishlist,
			String offerStatus, int itemOfferPrice) {
		super();
		this.itemID = itemID;
		this.name = name;
		this.size = size;
		this.price = price;
		this.category = category;
		this.status = status;
		this.wishlist = wishlist;
		this.offerStatus = offerStatus;
		this.itemOfferPrice = itemOfferPrice;
	}
	public String getId() {
		return itemID;
	}
	public void setId(String itemID) {
		this.itemID = itemID;
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
	public int getWishlist() {
		return wishlist;
	}
	public void setWishlist(int wishlist) {
		this.wishlist = wishlist;
	}
	public String getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}
	public int getItemOfferPrice() {
		return itemOfferPrice;
	}
	public void setItemOfferPrice(int itemOfferPrice) {
		this.itemOfferPrice = itemOfferPrice;
	}
	
}
