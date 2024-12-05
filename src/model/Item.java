package model;

public class Item {
	
	private int id;
	private String name;
	private int size;
	private int price;
	private String category;
	private String status;
	private String wishlist;
	private String offerStatus;
	private String itemOfferPrice;
	
	public Item(int id, String name, int size, int price, String category, String status, String wishlist,
			String offerStatus) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.price = price;
		this.category = category;
		this.status = status;
		this.wishlist = wishlist;
		this.offerStatus = offerStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getWishlist() {
		return wishlist;
	}
	public void setWishlist(String wishlist) {
		this.wishlist = wishlist;
	}
	public String getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}
	
}
