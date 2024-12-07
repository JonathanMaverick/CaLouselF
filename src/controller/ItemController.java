package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Item;
import model.User;

public class ItemController {

	public Vector<Item> viewItem(){
		Vector<Item> itemList = new Vector<>();
		try {
			String query = "SELECT * FROM items";
			
			ResultSet rs = Connect.getInstance().execQuery(query);
			while (rs.next()) {
				String itemID = rs.getString("item_id");
				String name = rs.getString("name");
				int size = rs.getInt("size");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String status = rs.getString("status"); //Di acc admin atau enggak
				int wishlist = rs.getInt("wishlist"); //Wishlist itung berapa orang yang suka produk ini
				String offerStatus = rs.getString("offer_status"); //Apakah sudah pernah di offer atau enggak
				int itemOfferPrice = rs.getInt("item_offer_price"); //Harga offer terbaru
				Item item = new Item(itemID, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice);
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return itemList;
	}
	
	public Boolean uploadItem(String name, int size, int price, String category) {
		try {
			String query = String.format("INSERT INTO ITEMS VALUES "
					+ "(NULL, '%s', '%d', '%d', '%s', '%s', '%d', '%s','%d')",
					name, size, price, category, "Pending", 0, "No Offer", 0);
			Connect.getInstance().execute(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean editItem(String itemId, String name, int size, int price, String category) {
		try {
			 String query = String.format("UPDATE ITEMS SET " +
		                "name = '%s', " +
		                "size = '%d', " +
		                "price = '%d', " +
		                "category = '%s', " +
		                "WHERE id = '%s'",
		                name, size, price, category, itemId);

			Connect.getInstance().execute(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean deleteItem(String itemId) {
		try {
			String query = String.format("DELETE FROM ITEMS WHERE id = '%s'",itemId);

			Connect.getInstance().execute(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Vector<Item> browseItem(String keyword) {
	    Vector<Item> items = new Vector<>();
	    try {
	        String query = String.format("SELECT * FROM ITEMS WHERE name LIKE '%%%s%%'", keyword);

	        ResultSet rs = Connect.getInstance().execQuery(query);

	        while (rs.next()) {
	        	String itemID = rs.getString("item_id");
				String name = rs.getString("name");
				int size = rs.getInt("size");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String status = rs.getString("status"); //Di acc admin atau enggak
				int wishlist = rs.getInt("wishlist"); //Wishlist itung berapa orang yang suka produk ini
				String offerStatus = rs.getString("offer_status"); //Apakah sudah pernah di offer atau enggak
				int itemOfferPrice = rs.getInt("item_offer_price"); //Harga offer terbaru
				Item item = new Item(itemID, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice);
				items.add(item);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return items;
	}
	
	public String checkItemValidation(String name, String category, int size, int price) {
		//Size is empty di frontend
		//Price is empty di frontend
		//Belum semua ya, tunggu konfirmasi dari frontend
		if(name.isEmpty()) {
			return "Name is empty";
		}
		else if (name.length() < 3) {
			return "Name must at least 3 character";
		}
		else if (category.isEmpty()) {
			return "Category is empty";
		}
		else if (category.length() < 3) {
			return "Category must at least 3 character";
		}
		else if (price == 0) {
			return "Price is 0";
		}
		return "Valid";
	}

	public Item getItem(String itemId) {
        try {
        	String query = String.format("SELECT * FROM ITEMS WHERE item_id LIKE '%s%'", itemId);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
	        	String itemID = rs.getString("item_id");
				String name = rs.getString("name");
				int size = rs.getInt("size");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String status = rs.getString("status"); //Di acc admin atau enggak
				int wishlist = rs.getInt("wishlist"); //Wishlist itung berapa orang yang suka produk ini
				String offerStatus = rs.getString("offer_status"); //Apakah sudah pernah di offer atau enggak
				int itemOfferPrice = rs.getInt("item_offer_price"); //Harga offer terbaru
				Item item = new Item(itemID, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice);
				return item;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        return null;
	}
	
	public Boolean acceptOffer(String itemId) {
		try {
			 String query = String.format("UPDATE ITEMS SET " +
		                "name = '%s', " +
		                "size = '%d', " +
		                "price = '%d', " +
		                "category = '%s', " +
		                "WHERE id = '%s'",
		                name, size, price, category, itemId);

			Connect.getInstance().execute(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
