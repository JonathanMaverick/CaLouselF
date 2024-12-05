package controller;

import java.sql.ResultSet;
import java.util.Vector;
import java.util.regex.Pattern;

import database.Connect;
import model.User;

public class UserController {
	
	public Vector<User> getUsers(){
		Vector<User> userList = new Vector<>();
		try {
			String query = "SELECT * FROM users";
			ResultSet rs = Connect.getInstance().execQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String roles = rs.getString("roles");
				User user = new User(id, username, password, phoneNumber, address, roles);
				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return userList;
	}
	
	
	public Boolean login(String username, String password) {
		try {
			String query = String.format("SELECT * FROM users WHERE username = '%s' AND password = '%s'", username,
					password);
			ResultSet rs = Connect.getInstance().execQuery(query);

			if (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean register(String username, String password, String phoneNumber, String address, String roles) {
		try {
			String query = String.format("INSERT INTO USERS VALUES (NULL, '%s', '%s', '%s', '%s')", username, password, phoneNumber, address, roles);
			Connect.getInstance().execute(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean isUsernameUnique(String username) {
		Vector<User> userList = getUsers();
		 for (User user : userList) {
            if (user.getUserName().equals(username)) {
                return false; 
            }
        }
		return true;
	}
	
	private boolean isPasswordValid(String password) {
		char[] specialCharacters = {'!', '@', '#', '$', '%', '^', '&', '*'};
        for (char specialChar : specialCharacters) {
            if (password.contains(String.valueOf(specialChar))) {
                return true; 
            }
        }
        return false;
	}
	

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        if (!phoneNumber.startsWith("+62")) {
            return false;
        }

        if (phoneNumber.length() < 10) {
            return false;
        }

        String remainingNumbers = phoneNumber.substring(3); 
        for (char c : remainingNumbers.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; 
            }
        }

        return true;
    }
	
	public String checkAccountValidation(String username, String password, String phoneNumber, String address, String roles) {
		if(username.isEmpty()) {
			return "Username not empty";
		}
		else if (username.length() < 3) {
			return "Username at least 3 character";
		}
		else if (!isUsernameUnique(username)) {
			return "Username not unique";
		}
		else if (password.isEmpty()) {
			return "Password not empty";
		}
		else if (password.length() < 8) {
			return "Password at least 8 character";
		}
		else if (isPasswordValid(password)) {
			return "Password valid";
		}
		else if(!isValidPhoneNumber(phoneNumber)) {
			return "Phone number valid";
		}
		else if(address.isEmpty()) {
			return "Address can't be empty";
		}
		else {
			register(username, password, phoneNumber, address, roles);
			return "Account created";
		}
	}
}
