package controller;

import java.sql.ResultSet;
import java.util.Vector;

import utils.LoggedUser;
import utils.Response;
import database.Connect;
import model.User;

public class UserController {

	private static UserController instance;
	
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
	
    // getUsers
    // Retrieve all users
	private Vector<User> getUsers(){
		Vector<User> userList = new Vector<>();
		try {
			String query = "SELECT * FROM users";
			ResultSet rs = Connect.getInstance().execQuery(query);
			while (rs.next()) {
				String id = rs.getString("user_id");
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
	
	// generateNewUserId
	// Generate a new ID for a new user
	private String generateNewUserId(){
	    String query = "SELECT MAX(user_id) AS max_id FROM users"; 
	    try {
	    	ResultSet rs = Connect.getInstance().execQuery(query);
	        String maxID = "US000";
	        if (rs.next() && rs.getString("max_id") != null) {
	            maxID = rs.getString("max_id");
	        }

	        int numericPart = Integer.parseInt(maxID.substring(2));

	        return String.format("US%03d", numericPart + 1);
	    }catch (Exception e) {
			e.printStackTrace();
			return "Error user id";
		}
	}
	
	// getUser
	// Retrieve a user based on userId
	private User getUser(String userId) {
		try {
			String query = String.format("SELECT * FROM users WHERE user_id = '%s' LIMIT 1", userId);
			ResultSet rs = Connect.getInstance().execQuery(query);
			while (rs.next()) {
				String id = rs.getString("user_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String roles = rs.getString("roles");
				User user = new User(id, username, password, phoneNumber, address, roles);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	// login
	// Login based on username and password
	public Response<User> login(String username, String password) {
		try {
			String query = String.format("SELECT * FROM users WHERE username = '%s' AND password = '%s'", username,
					password);
			ResultSet rs = Connect.getInstance().execQuery(query);
			
			if (rs.next()) {
				String id = rs.getString("user_id");
				User user = getUser(id);
				LoggedUser.getInstance().setLoggedUser(user);
				return new Response<>(true, "User logged in", user);
			}
			return new Response<>(false, "User not found", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Error occurred", null);
		}
	}
	
	// register
	// Register a new user with username, password, phoneNumber, address, roles
	public Response<User> register(String username, String password, String phoneNumber, String address, String roles) {
		Response<User> validationResponse = checkAccountValidation(username, password, phoneNumber, address, roles);
		if(validationResponse.success) {
			try {
				String userId = generateNewUserId();
				String query = String.format("INSERT INTO USERS VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", userId, username, password, phoneNumber, address, roles);
				Connect.getInstance().execute(query);
				return validationResponse;
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(false, "An error occurred", null);
			}
		}
		return validationResponse;
	}
	
	// isUsernameUnique
	// Check if username is unique
	private boolean isUsernameUnique(String username) {
		Vector<User> userList = getUsers();
		 for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return false; 
            }
        }
		return true;
	}
	
	// isPasswordValid
	// Check if password meets the requirements
	private boolean isPasswordValid(String password) {
		char[] specialCharacters = {'!', '@', '#', '$', '%', '^', '&', '*'};
        for (char specialChar : specialCharacters) {
            if (password.contains(String.valueOf(specialChar))) {
                return true; 
            }
        }
        return false;
	}
	

	// isValidPhoneNumber
	// Check if phone number is valid
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
	
    // checkAccountValidation
    // Validate all inputs before registering
	public Response<User> checkAccountValidation(String username, String password, String phoneNumber, String address, String roles) {
		if(username.isEmpty()) {
			return new Response<>(false, "Username can't be empty", null);
		}
		else if (username.length() < 3) {
			return 	new Response<>(false, "Username must be at least 3 characters long", null);
		}
		else if (!isUsernameUnique(username)) {
			return new Response<>(false, "Username is not unique", null);
		}
		else if (password.isEmpty()) {
			return new Response<>(false, "Password can't be empty", null);
		}
		else if (password.length() < 8) {
			return new Response<>(false, "Password must be at least 8 characters long", null);
		}
		else if (!isPasswordValid(password)) {
			return new Response<>(false, "Password must include special characters (!, @, #, $, %, ^, &, *)", null);
		}
		else if(!isValidPhoneNumber(phoneNumber)) {
			return new Response<>(false,"Phone number must start with +62 and be at least 10 digits long", null);
		}
		else if(address.isEmpty()) {
			return new Response<>(false, "Address can't be empty", null);
		}
		else {
			return new Response<>(true, "User registered!", null);
		}
	}
}
