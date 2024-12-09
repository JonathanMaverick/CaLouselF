package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Item;
import utils.Response;

public class ItemController {
	
	private static ItemController instance;
	
    public static ItemController getInstance() {
        if (instance == null) {
            instance = new ItemController();
        }
        return instance;
    }

	//viewItem
	//Ambil semua item, tanpa peduli status
	public Vector<Item> viewItem(){
		Vector<Item> itemList = new Vector<>();
		try {
			String query = "SELECT * FROM items";
			
			ResultSet rs = Connect.getInstance().execQuery(query);
			while (rs.next()) {
				String itemId = rs.getString("item_id");
				String name = rs.getString("name");
				int size = rs.getInt("size");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String status = rs.getString("status"); //Di acc admin atau enggak
				int wishlist = rs.getInt("wishlist"); //Wishlist itung berapa orang yang suka produk ini
				String offerStatus = rs.getString("offer_status"); //Apakah sudah pernah di offer atau enggak
				int itemOfferPrice = rs.getInt("item_offer_price"); //Harga offer terbaru
				Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice);
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return itemList;
	}
	
	//getItem
	//Ambil item berdasarkan itemId yang ada
	public Item getItem(String itemId) {
        try {
        	String query = String.format("SELECT * FROM ITEMS WHERE item_id LIKE '%s%'", itemId);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
				String name = rs.getString("name");
				int size = rs.getInt("size");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String status = rs.getString("status"); //Di acc admin atau enggak
				int wishlist = rs.getInt("wishlist"); //Wishlist itung berapa orang yang suka produk ini
				String offerStatus = rs.getString("offer_status"); //Apakah sudah pernah di offer atau enggak
				int itemOfferPrice = rs.getInt("item_offer_price"); //Harga offer terbaru
				Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice);
				return item;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        return null;
	}
	
	//generateNewitemId
	//Buat generate item_id
	private String generateNewitemId(){
	    String query = "SELECT MAX(item_id) AS max_id FROM items"; 
	    try {
	    	ResultSet rs = Connect.getInstance().execQuery(query);
	        String maxID = "IT000";
	        if (rs.next() && rs.getString("max_id") != null) {
	            maxID = rs.getString("max_id");
	        }

	        int numericPart = Integer.parseInt(maxID.substring(2));

	        return String.format("IT%03d", numericPart + 1);
	    }catch (Exception e) {
			e.printStackTrace();
			return "Error item id";
		}
	}
	
	//uploadItem
	//Membuat item
	public Response<Item> uploadItem(String name, int size, int price, String category) {
		Response<Item> response = checkItemValidation(name, category, size, price);
		if(response.success) {
			try {
				String itemId = generateNewitemId();
				String query = String.format("INSERT INTO ITEMS VALUES "
						+ "('%s', '%s', '%d', '%d', '%s', '%s', '%d', '%s','%d')",
						itemId, name, size, price, category, "Pending", 0, "No Offer", 0);
				Item insertedItem = new Item(itemId, name, size, price, category);
				Connect.getInstance().execute(query);
				return new Response<>(true, "Item's successfully inserted", insertedItem);
			} catch (Exception e) {
				e.printStackTrace();
				return new Response<>(false, "Item's not inserted", null);
			}
		}
		else {
			return new Response<>(false, response.message, null);
		}
	}
	
	//editItem
	//Edit Item
	public Response<Item> editItem(String itemId, String name, int size, int price, String category) {
		try {
			 String query = String.format("UPDATE ITEMS SET " +
		                "name = '%s', " +
		                "size = '%d', " +
		                "price = '%d', " +
		                "category = '%s', " +
		                "WHERE item_id = '%s'",
		                name, size, price, category, itemId);
			 
			Connect.getInstance().execute(query);
			Item updatedItem = getItem(itemId);
			return new Response<>(true, "Item's successfully updated", updatedItem);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Item's not updated", null);
		}
	}
	
	//deleteItem
	//Delete Item
	public Response<Item> deleteItem(String itemId) {
		try {
			Item deletedItem = getItem(itemId);
			String query = String.format("DELETE FROM ITEMS WHERE item_id = '%s'",itemId);
			
			Connect.getInstance().execute(query);
		
			return new Response<>(true, "Item's deleted", deletedItem);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Item's not deleted", null);
		}
	}
	
	//browseItem
	//Mencari item sesuai dengan keyword yang ada
	public Vector<Item> browseItem(String keyword) {
	    Vector<Item> items = new Vector<>();
	    try {
	        String query = String.format("SELECT * FROM ITEMS WHERE name LIKE '%%%s%%'", keyword);

	        ResultSet rs = Connect.getInstance().execQuery(query);

	        while (rs.next()) {
	        	String itemId = rs.getString("item_id");
				String name = rs.getString("name");
				int size = rs.getInt("size");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String status = rs.getString("status"); //Di acc admin atau enggak
				int wishlist = rs.getInt("wishlist"); //Wishlist itung berapa orang yang suka produk ini
				String offerStatus = rs.getString("offer_status"); //Apakah sudah pernah di offer atau enggak
				int itemOfferPrice = rs.getInt("item_offer_price"); //Harga offer terbaru
				Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice);
				items.add(item);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return items;
	}
	
	//checkItemValidation
	//Digunakan untuk melakukan validasi sebelum upload / edit sebuah item
	//!!
	public Response<Item> checkItemValidation(String name, String category, int size, int price) {
		//Size is empty di frontend
		//Price is empty di frontend
		//Belum semua ya, tunggu konfirmasi dari frontend
		if(name.isEmpty()) {
			return new Response<>(false, "Name is empty", null);
		}
		else if (name.length() < 3) {
			return new Response<>(false, "Name must at least 3 character", null);
		}
		else if (category.isEmpty()) {
			return new Response<>(false, "Category is empty", null);
		}
		else if (category.length() < 3) {
			return new Response<>(false, "Category must at least 3 character", null);
		}
		else if (price == 0) {
			return new Response<>(false, "Price is 0", null);
		}
		return new Response<>(true, "Item validation is true", null);
	}
	
	//approveItem
	//Item sudah di approve oleh admin dan boleh diperjualkan
	public Response<Item> approveItem(String itemId) {
		//Idea:
		//Status diubah menjadi approved. Agar bisa dijual nantinya
		try {
			 String query = String.format("UPDATE ITEMS SET " +
		                "status = '%s'" +
		                "WHERE item_id = '%s'",
		                "Approved", itemId);
			 
			Connect.getInstance().execute(query);
			Item updatedItem = getItem(itemId);
			return new Response<>(true, "Item's Approved", updatedItem);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Item's not updated", null);
		}
	}
	
	//declineItem
	//Item di decline oleh admin dan tidak boleh diperjualkan
	public Response<Item> declineItem(String itemId) {
		//Idea:
		//Status diubah menjadi decline.
		try {
			 String query = String.format("UPDATE ITEMS SET " +
		                "status = '%s'" +
		                "WHERE item_id = '%s'",
		                "Declined", itemId);
			 
			Connect.getInstance().execute(query);
			Item updatedItem = getItem(itemId);
			return new Response<>(true, "Item's Declined", updatedItem);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Item's not updated", null);
		}
	}
	
	//viewApprovedStatusItem
	//Mengambil semua item yang memiliki status approved
	public Response<Vector<Item>> viewApprovedStatusItem() {
		//Idea:
		//Ambil semua item yang statusnya approved?
		Vector<Item> itemList = new Vector<>();
		try {
			String query = "SELECT * FROM items WHERE status = 'Approved'";
			
			ResultSet rs = Connect.getInstance().execQuery(query);
			while (rs.next()) {
				String itemId = rs.getString("item_id");
				String name = rs.getString("name");
				int size = rs.getInt("size");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String status = rs.getString("status"); //Di acc admin atau enggak
				int wishlist = rs.getInt("wishlist"); //Wishlist itung berapa orang yang suka produk ini
				String offerStatus = rs.getString("offer_status"); //Apakah sudah pernah di offer atau enggak
				int itemOfferPrice = rs.getInt("item_offer_price"); //Harga offer terbaru
				Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice);
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Error occured", null);
		}
		return new Response<>(true, "Item with approved status", itemList);
	}
	
	
}
