package model;

public class Offer {
	public String itemId;
	public String offerId;
	public String userId;
	public int offerPrice;
	public String name;
	public String category;
	public int price;
	public int size;
	private String reason;
	private String status;
	
	public Offer(String itemId, String offerId, String userId, int offerPrice, String name, String category, int price,
			int size, String reason, String status) {
		super();
		this.itemId = itemId;
		this.offerId = offerId;
		this.userId = userId;
		this.offerPrice = offerPrice;
		this.name = name;
		this.category = category;
		this.price = price;
		this.size = size;
		this.reason = reason;
		this.status = status;
	}

	public Offer(String itemId, String offerId, String userId, int offerPrice, String reason, String status) {
		super();
		this.itemId = itemId;
		this.offerId = offerId;
		this.userId = userId;
		this.offerPrice = offerPrice;
		this.reason = reason;
		this.status = status;
	}
	
	public Offer(String itemId, String offerId, String userId, int offerPrice) {
		super();
		this.itemId = itemId;
		this.offerId = offerId;
		this.userId = userId;
		this.offerPrice = offerPrice;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(int offerPrice) {
		this.offerPrice = offerPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
