package controller;

import java.sql.ResultSet;
import java.util.Vector;

import database.Connect;
import model.Offer;
import model.Transaction;
import utils.Response;

public class TransactionController {

	private static TransactionController instance;
	
    public static TransactionController getInstance() {
        if (instance == null) {
            instance = new TransactionController();
        }
        return instance;
    }
    
    private String generateNewTransactionId(){
	    String query = "SELECT MAX(offer_id) AS max_id FROM transactions"; 
	    try {
	    	ResultSet rs = Connect.getInstance().execQuery(query);
	        String maxID = "TR000";
	        if (rs.next() && rs.getString("max_id") != null) {
	            maxID = rs.getString("max_id");
	        }

	        int numericPart = Integer.parseInt(maxID.substring(2));

	        return String.format("TR%03d", numericPart + 1);
	    }catch (Exception e) {
			e.printStackTrace();
			return "Error offer id";
		}
	}
    
	public Response<Transaction> createTransaction(String userId, String itemId){
		try {
			String transactionId = generateNewTransactionId();
			String query = String.format("INSERT INTO Transactions VALUES "
					+ "('%s', '%s', '%s')",
					transactionId, userId, itemId);
			Transaction insertedTransaction = new Transaction(transactionId, userId, itemId);
			Connect.getInstance().execute(query);
			return new Response<>(true, "Transaction's successfully inserted", insertedTransaction);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, "Transaction's not inserted", null);
		}
	}
    
    public Vector<Transaction> viewHistory(String userId){
    	Vector<Transaction> transactionList = new Vector<>();
    	try {
        	String query = String.format("SELECT * FROM transactions WHERE user_id ='%s'", userId);
	        ResultSet rs = Connect.getInstance().execQuery(query);
	        while (rs.next()) {
				String itemId = rs.getString("item_id");
				String transactionId = rs.getString("transaction_id");
				Transaction transaction = new Transaction(transactionId, userId, itemId);
				transactionList.add(transaction);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        return transactionList;
    }
    
    
}