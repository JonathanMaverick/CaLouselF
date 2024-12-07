package model;

public class Offer {
	public String itemID;
	public String offerID;
	public String userID;
	public int offerPrice;
	public Offer(String itemID, String offerID, String userID, int offerPrice) {
		super();
		this.itemID = itemID;
		this.offerID = offerID;
		this.userID = userID;
		this.offerPrice = offerPrice;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getOfferID() {
		return offerID;
	}
	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getOfferPrice() {
		return offerPrice;
	}
	public void setOfferPrice(int offerPrice) {
		this.offerPrice = offerPrice;
	}
}
