package utils;

import model.User;

public class LoggedUser {
	private static LoggedUser instance;  
    private User currentUser;

    private LoggedUser() {}

    public static LoggedUser getInstance() {
        if (instance == null) {
            instance = new LoggedUser();
        }
        return instance;
    }

    public void setLoggedUser(User user) {
        this.setCurrentUser(user);
    }
    
    public void logout() {
        this.setCurrentUser(null);
    }

	public User getCurrentUser() {
		return currentUser;
	}

	private void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
