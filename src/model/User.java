package model;

public class User {
	private String userID;
	private String userName;
	private String userPassword;
	private String userphoneNumber;
	private String userAddress;
	private String userRoles;
	
	public User(String userID, String userName, String userPassword, String userphoneNumber, String userAddress,
			String userRoles) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userphoneNumber = userphoneNumber;
		this.userAddress = userAddress;
		this.userRoles = userRoles;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserphoneNumber() {
		return userphoneNumber;
	}
	public void setUserphoneNumber(String userphoneNumber) {
		this.userphoneNumber = userphoneNumber;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

}
