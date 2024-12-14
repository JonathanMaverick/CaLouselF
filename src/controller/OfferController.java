package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Item;
import model.Offer;
import utils.Response;

public class OfferController {
    
    private static OfferController instance;
    
    public static OfferController getInstance() {
        if (instance == null) {
            instance = new OfferController();
        }
        return instance;
    }

    // getOffer
    // Retrieves an offer by its ID from the database
    private Offer getOffer(String offerId) {
        try {
            String query = String.format("SELECT * FROM Offers WHERE offer_id LIKE '%s'", offerId);
            ResultSet rs = Connect.getInstance().execQuery(query);
            while (rs.next()) {
                String itemId = rs.getString("item_id");
                String userId = rs.getString("user_id");
                int offerPrice = rs.getInt("offer_price");
                Offer offer = new Offer(offerId, itemId, userId, offerPrice);
                return offer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // generateNewOfferId
    // Generates a new offer ID by taking the maximum offer ID from the database
    private String generateNewOfferId() {
        String query = "SELECT MAX(offer_id) AS max_id FROM offers"; 
        try {
            ResultSet rs = Connect.getInstance().execQuery(query);
            String maxID = "OF000";
            if (rs.next() && rs.getString("max_id") != null) {
                maxID = rs.getString("max_id");
            }

            int numericPart = Integer.parseInt(maxID.substring(2));

            return String.format("OF%03d", numericPart + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error offer id";
        }
    }

    // checkOfferValidation
    // Validates the offer based on item ID, user ID, and offer price
    private Response<Offer> checkOfferValidation(String itemId, String userId, int price) {
        ItemController IC = new ItemController();
        Item item = IC.getItem(itemId);
        if(itemId.isEmpty() || userId.isEmpty()) {
            return new Response<>(false, "Item ID or User ID is empty", null);
        }
        else if(item.getPrice() <= price) {
            return new Response<>(false, "Offer price can't be bigger than current price", null);
        }
        Response<Offer> res = getOfferByItemId(itemId);
        if (res.success) {
            if(res.data.getOfferPrice() >= price) {
                return new Response<>(false, "Offer price can't be lower than current offer price", null);
            }
            deleteOffer(res.data.getOfferId());
        }
        return new Response<>(true, "Validation valid", null);        
    }
    
    private void deleteOffer(String offerId) {
    	 try {
             String query = String.format("DELETE FROM offers WHERE offer_id = '%s'", offerId);
             Connect.getInstance().execute(query);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    //createOffer
    // Creates a new offer for a specific item, user, and price
    public Response<Offer> createOffer(String itemId, String userId, int offerPrice) {
        Response<Offer> response = checkOfferValidation(itemId, userId, offerPrice);
        if(response.success) {
            try {
                String offerId = generateNewOfferId();
                String query = String.format("INSERT INTO offers VALUES "
                        + "('%s', '%s', '%s', '%d', '%s', '%s')",
                        offerId, itemId, userId, offerPrice, "", "Pending");
                Offer insertedOffer = new Offer(itemId, offerId, userId, offerPrice);
                Connect.getInstance().execute(query);
                return new Response<>(true, "Offer's successfully inserted", insertedOffer);
            } catch (Exception e) {
                e.printStackTrace();
                return new Response<>(false, "Offer's not inserted", null);
            }
        }
        else {
            return new Response<>(false, response.message, null);
        }
    }

    // acceptOffer
    // Accepts an offer by its ID, updates the item status and offer price in the database
    public Response<Offer> acceptOffer(String offerId) {
        Offer acceptedOffer = getOffer(offerId);
        try {
        	TransactionController.getInstance().createTransaction(acceptedOffer.getUserId(), acceptedOffer.getItemId());
        	deleteOffer(offerId);
        	return new Response<>(true, "Offer Accepted", acceptedOffer);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, "Error occurred", null);
        }
    }

    // declineOffer
    // Declines an offer by its ID and deletes it from the database
    public Response<Offer> declineOffer(String offerId, String reason) {
        try {
            Offer deletedOffer = getOffer(offerId);
            String query = String.format("UPDATE OFFERS SET offer_status = 'Decline', offer_reason = '%s'"
            		+ " WHERE offer_id = '%s'", reason, offerId);
            System.out.println(query);
            Connect.getInstance().execute(query);
            return new Response<>(true, "Offer declined", deletedOffer);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, "Error's occurred", null);
        }
    }

    // getOfferByItemId
    // Retrieves all offers associated with a specific item ID
    public Response<Offer> getOfferByItemId(String itemId) {
    	Offer offer = null;
        try {
            String query = String.format("SELECT * FROM Offers WHERE item_id = '%s' AND offer_status = 'Pending'"
            		+ " ORDER BY offer_price DESC LIMIT 1", itemId);
            ResultSet rs = Connect.getInstance().execQuery(query);
            while (rs.next()) {
                String offerId = rs.getString("offer_id");
                String userId = rs.getString("user_id");
                int offerPrice = rs.getInt("offer_price");
                offer = new Offer(itemId, offerId, userId, offerPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<Offer>(false, e.toString(), null);
        }
        if(offer == null) {
        	return new Response<Offer>(false, "", null);
        }
        return new Response<Offer>(true, "", offer);
    }
    
    public Response<Vector<Offer>> getOfferBySellerId(String sellerId){
    	Vector<Offer> offerList = new Vector<Offer>();
    	try {
    		String query = String.format("SELECT * FROM Offers o JOIN items i ON i.item_id = o.item_id WHERE i.seller_id = '%s' AND o.offer_status = 'Pending'", sellerId);
    		 ResultSet rs = Connect.getInstance().execQuery(query);
    		 while (rs.next()) {
                 String offerId = rs.getString("offer_id");
                 String itemId = rs.getString("item_id");
                 String userId = rs.getString("user_id");
                 int offerPrice = rs.getInt("offer_price");
                 String reason = rs.getString("offer_reason");
                 String name = rs.getString("name");
                 int size = rs.getInt("size");
                 int price = rs.getInt("price");
                 String category = rs.getString("category");
                 String status = rs.getString("offer_status");
                 Offer offer = new Offer(itemId, offerId, userId, offerPrice, name, category, price, size, reason, status);
                 offerList.add(offer);
             }
    	}catch (Exception e) {
            e.printStackTrace();
            return new Response<Vector<Offer>>(false, e.toString(), null);
        }
    	
    	return new Response<Vector<Offer>>(true, "Success", offerList);
    }
    
    //GetOfferHistoryBySellerID
    public Response<Vector<Offer>> getOfferHistoryBySellerId(String sellerId){
    	Vector<Offer> offerList = new Vector<Offer>();
    	try {
    		String query = String.format("SELECT * FROM Offers o JOIN items i ON i.item_id = o.item_id WHERE i.seller_id = '%s' AND o.offer_status = 'Decline'", sellerId);
    		 ResultSet rs = Connect.getInstance().execQuery(query);
    		 while (rs.next()) {
                 String offerId = rs.getString("offer_id");
                 String itemId = rs.getString("item_id");
                 String userId = rs.getString("user_id");
                 int offerPrice = rs.getInt("offer_price");
                 String reason = rs.getString("offer_reason");
                 String name = rs.getString("name");
                 int size = rs.getInt("size");
                 int price = rs.getInt("price");
                 String category = rs.getString("category");
                 String status = rs.getString("offer_status");
                 Offer offer = new Offer(itemId, offerId, userId, offerPrice, name, category, price, size, reason, status);
                 offerList.add(offer);
             }
    	}catch (Exception e) {
            e.printStackTrace();
            return new Response<Vector<Offer>>(false, e.toString(), null);
        }
    	
    	return new Response<Vector<Offer>>(true, "Success", offerList);
    }
}
