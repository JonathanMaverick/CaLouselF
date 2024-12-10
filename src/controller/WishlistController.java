package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Wishlist;
import utils.Response;

public class WishlistController {
	private static WishlistController instance;
	
    public static WishlistController getInstance() {
        if (instance == null) {
            instance = new WishlistController();
        }
        return instance;
    }
    
    private String generateNewWishlistId(){
	    String query = "SELECT MAX(wishlist_id) AS max_id FROM wishlists"; 
	    try {
	    	ResultSet rs = Connect.getInstance().execQuery(query);
	        String maxID = "WI000";
	        if (rs.next() && rs.getString("max_id") != null) {
	            maxID = rs.getString("max_id");
	        }

	        int numericPart = Integer.parseInt(maxID.substring(2));

	        return String.format("WI%03d", numericPart + 1);
	    }catch (Exception e) {
			e.printStackTrace();
			return "Error offer id";
		}
	}
    
	public Response<Wishlist> addWishlist(String userId, String itemId){
		try {
			String wishlistId = generateNewWishlistId();
			String query = String.format("INSERT INTO Transactions VALUES "
					+ "('%s', '%s', '%s')",
					wishlistId, userId, itemId);
			Wishlist insertedWishlist = new Wishlist(wishlistId, userId, itemId);
			Connect.getInstance().execute(query);
			return new Response<>(true, "Wishist's successfully inserted", insertedWishlist);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Transaction's not inserted", null);
		}
	}
    
    public Vector<Wishlist> viewWishlist(String userId){
    	Vector<Wishlist> wishlistList = new Vector<>();
    	try {
        	String query = String.format("SELECT * FROM wishlists WHERE user_id ='%s'", userId);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
				String itemId = rs.getString("item_id");
				String transactionId = rs.getString("wishlist_id");
				Wishlist wishlists = new Wishlist(transactionId, userId, itemId);
				wishlistList.add(wishlists);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        return wishlistList;
    }
    
	public Response<Wishlist> removeWishlist(String wishlistId) {
		try {
			String query = String.format("DELETE FROM wishlists WHERE wishlist_id = '%s'",wishlistId);
			
			Connect.getInstance().execute(query);
		
			return new Response<>(true, "Wishlist deleted", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Wishlist not deleted", null);
		}
	}
	
}
