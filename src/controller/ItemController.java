package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Item;
import utils.LoggedUser;
import utils.Response;

public class ItemController {
    
    private static ItemController instance;
    
    public static ItemController getInstance() {
        if (instance == null) {
            instance = new ItemController();
        }
        return instance;
    }

    // viewItem
    // Get all items, regardless of their status
    public Response<Vector<Item>> viewItem() {
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
                String status = rs.getString("status"); // Whether approved by admin or not
                int wishlist = rs.getInt("wishlist"); // Count of people who like this product
                String offerStatus = rs.getString("offer_status"); // Whether the item has been offered or not
                int itemOfferPrice = rs.getInt("item_offer_price"); // Latest offer price
                String sellerId = rs.getString("seller_id");
                String reason = rs.getString("reason");
                Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice, sellerId, reason);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<Vector<Item>>(false, e.toString(), null);
        }
        return new Response<Vector<Item>>(true, "View all items", itemList);
    }
    
    // viewItemPending
    // Get all items with 'Pending' status
    public Response<Vector<Item>> viewItemPending() {
        Vector<Item> itemList = new Vector<>();
        try {
            String query = "SELECT * FROM items WHERE status = 'Pending'";
            
            ResultSet rs = Connect.getInstance().execQuery(query);
            while (rs.next()) {
                String itemId = rs.getString("item_id");
                String name = rs.getString("name");
                int size = rs.getInt("size");
                int price = rs.getInt("price");
                String category = rs.getString("category");
                String status = rs.getString("status"); // Whether approved by admin or not
                int wishlist = rs.getInt("wishlist"); // Count of people who like this product
                String offerStatus = rs.getString("offer_status"); // Whether the item has been offered or not
                int itemOfferPrice = rs.getInt("item_offer_price"); // Latest offer price
                String sellerId = rs.getString("seller_id");
                String reason = rs.getString("reason");
                Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice, sellerId, reason);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, "Fail", null);
        }
        Response<Vector<Item>> res = new Response<>(true, "Success", itemList);
        return res;
    }
    
    // getItem
    // Get item by itemId
    public Item getItem(String itemId) {
        try {
            String query = String.format("SELECT * FROM ITEMS WHERE status LIKE 'Pending'");
            ResultSet rs = Connect.getInstance().execQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                int size = rs.getInt("size");
                int price = rs.getInt("price");
                String category = rs.getString("category");
                String status = rs.getString("status"); // Whether approved by admin or not
                int wishlist = rs.getInt("wishlist"); // Count of people who like this product
                String offerStatus = rs.getString("offer_status"); // Whether the item has been offered or not
                int itemOfferPrice = rs.getInt("item_offer_price"); // Latest offer price
                String sellerId = rs.getString("seller_id");
                String reason = rs.getString("reason");
                Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice, sellerId, reason);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // generateNewitemId
    // Generate new item_id
    private String generateNewitemId() {
        String query = "SELECT MAX(item_id) AS max_id FROM items"; 
        try {
            ResultSet rs = Connect.getInstance().execQuery(query);
            String maxID = "IT000";
            if (rs.next() && rs.getString("max_id") != null) {
                maxID = rs.getString("max_id");
            }

            int numericPart = Integer.parseInt(maxID.substring(2));

            return String.format("IT%03d", numericPart + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error item id";
        }
    }
    
    // uploadItem
    // Create a new item
    public Response<Item> uploadItem(String name, int size, int price, String category) {
        Response<Item> response = checkItemValidation(name, category, size, price);
        if (response.success) {
            try {
                String itemId = generateNewitemId();
                String query = String.format("INSERT INTO ITEMS VALUES "
                        + "('%s', '%s', '%d', '%d', '%s', '%s', '%d', '%s','%d', '%s', '%s')",
                        itemId, name, size, price, category, "Pending", 0, "No Offer", 0, LoggedUser.getInstance().getCurrentUser().getUserId(), "No Reason");
                Item insertedItem = new Item(itemId, name, size, price, category, LoggedUser.getInstance().getCurrentUser().getUserId());
                Connect.getInstance().execute(query);
                return new Response<>(true, "Item's successfully inserted", insertedItem);
            } catch (Exception e) {
                e.printStackTrace();
                return new Response<>(false, "Item's not inserted", null);
            }
        } else {
            return new Response<>(false, response.message, null);
        }
    }
    
    // editItem
    // Edit item details
    public Response<Item> editItem(String itemId, String name, int size, int price, String category) {
        Response<Item> res = checkItemValidation(name, category, size, price);
        if (!res.success) {
            return res;
        }
        try {
            String query = String.format("UPDATE ITEMS SET " +
                        "name = '%s', " +
                        "size = %d, " +
                        "price = %d, " +
                        "category = '%s' " +
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
    
    // deleteItem
    // Delete item from the system
    public Response<Item> deleteItem(String itemId) {
        try {
            Item deletedItem = getItem(itemId);
            String query = String.format("DELETE FROM ITEMS WHERE item_id = '%s'", itemId);
            
            Connect.getInstance().execute(query);
        
            return new Response<>(true, "Item's deleted", deletedItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, "Item's not deleted", null);
        }
    }
    
    // browseItem
    // Search items based on a keyword
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
                String status = rs.getString("status"); // Whether approved by admin or not
                int wishlist = rs.getInt("wishlist"); // Count of people who like this product
                String offerStatus = rs.getString("offer_status"); // Whether the item has been offered or not
                int itemOfferPrice = rs.getInt("item_offer_price"); // Latest offer price
                String sellerId = rs.getString("seller_id");
                String reason = rs.getString("reason");
                Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice, sellerId, reason);
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    
    // checkItemValidation
    // Validate item data before upload or edit
    public Response<Item> checkItemValidation(String name, String category, int size, int price) {
        if (name.isEmpty()) {
            return new Response<>(false, "Name is empty", null);
        } else if (name.length() < 3) {
            return new Response<>(false, "Name must be at least 3 characters", null);
        } else if (category.isEmpty()) {
            return new Response<>(false, "Category is empty", null);
        } else if (category.length() < 3) {
            return new Response<>(false, "Category must be at least 3 characters", null);
        } else if (price == 0) {
            return new Response<>(false, "Price is 0", null);
        }
        return new Response<>(true, "Item validation is true", null);
    }
    
    // approveItem
    // Approve an item for sale by admin
    public Response<Item> approveItem(String itemId) {
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
    
    // declineItem
    // Decline an item and prevent it from being sold
    public Response<Item> declineItem(String itemId, String reason) {
        if (reason.isEmpty()) {
            return new Response<>(false, "Reason is empty", null);
        }
        try {
            String query = String.format("UPDATE ITEMS SET " +
                        "status = '%s', " +
                        "reason = '%s' " +
                        "WHERE item_id = '%s'",
                        "Declined", reason, itemId);
             
            Connect.getInstance().execute(query);
            Item updatedItem = getItem(itemId);
            return new Response<>(true, "Item's Declined", updatedItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, "Item's not updated", null);
        }
    }
    
    // viewApprovedStatusItem
    // Get all items with 'Approved' status
    public Response<Vector<Item>> viewApprovedStatusItem() {
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
                String status = rs.getString("status"); // Whether approved by admin or not
                int wishlist = rs.getInt("wishlist"); // Count of people who like this product
                String offerStatus = rs.getString("offer_status"); // Whether the item has been offered or not
                int itemOfferPrice = rs.getInt("item_offer_price"); // Latest offer price
                String reason = rs.getString("reason");
                String sellerId = rs.getString("seller_id");
                Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice, sellerId, reason);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, "Error occurred", null);
        }
        return new Response<>(true, "Items with approved status", itemList);
    }
    
    public Response<Vector<Item>> viewItemBySellerId(String sellerId) {
        Vector<Item> itemList = new Vector<>();
        try {
            String query = String.format("SELECT * FROM items WHERE seller_id = '%s' AND status = 'Approved'", sellerId);
            
            ResultSet rs = Connect.getInstance().execQuery(query);
            while (rs.next()) {
                String itemId = rs.getString("item_id");
                String name = rs.getString("name");
                int size = rs.getInt("size");
                int price = rs.getInt("price");
                String category = rs.getString("category");
                String status = rs.getString("status"); // Whether approved by admin or not
                int wishlist = rs.getInt("wishlist"); // Count of people who like this product
                String offerStatus = rs.getString("offer_status"); // Whether the item has been offered or not
                int itemOfferPrice = rs.getInt("item_offer_price"); // Latest offer price
                String reason = rs.getString("reason");
                Item item = new Item(itemId, name, size, price, category, status, wishlist, offerStatus, itemOfferPrice, sellerId, reason);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, "Error occurred", null);
        }
        return new Response<>(true, "Items with seller Id status", itemList);
    }
}
