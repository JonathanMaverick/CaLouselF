package model;

public class Offer {
	public String itemId;
	public String offerId;
	public String userId;
	public int offerPrice;
	public Offer(String itemId, String offerId, String userId, int offerPrice) {
		super();
		this.itemId = itemId;
		this.offerId = offerId;
		this.userId = userId;
		this.offerPrice = offerPrice;
	}
	public String getitemId() {
		return itemId;
	}
	public void setitemId(String itemId) {
		this.itemId = itemId;
	}
	public String getofferId() {
		return offerId;
	}
	public void setofferId(String offerId) {
		this.offerId = offerId;
	}
	public String getuserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
	}
	public int getOfferPrice() {
		return offerPrice;
	}
	public void setOfferPrice(int offerPrice) {
		this.offerPrice = offerPrice;
	}
}
