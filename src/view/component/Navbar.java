package view.component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Navbar extends MenuBar {

	 public MenuBar createNavbar(String role) {
	        MenuBar navbar = new MenuBar();
	        Menu homeMenu = new Menu("Home");
	        navbar.getMenus().add(homeMenu);
	        if ("Admin".equals(role)) {	        	
	        	Menu adminMenu = new Menu("View Requested Item");
	        	navbar.getMenus().add(adminMenu);
	        }
	        else if("Seller".equals(role)) {
	        	Menu itemMenu = new Menu("Item");
	            MenuItem viewOfferedItem = new MenuItem("View Offered Item");
	            MenuItem uploadItem = new MenuItem("Upload Item");
	            MenuItem editItem = new MenuItem("Edit Item");
	            MenuItem deleteItem = new MenuItem("Delete Item");
	            MenuItem viewItemBySeller = new MenuItem("View Item");
	            itemMenu.getItems().addAll(viewOfferedItem, uploadItem, editItem, deleteItem, viewItemBySeller);
	            navbar.getMenus().add(itemMenu);
	        }
	        else if ("Buyer".equals(role)) {		        
		        Menu wishlistMenu = new Menu("Wishlist");
		        Menu historyMenu = new Menu("History");
		        Menu offerMenu = new Menu("Offer");

		        //Offer : Approved Offer, and All Offer
		        MenuItem approvedOffer = new MenuItem("Approved Offer");
		        MenuItem allOffer = new MenuItem("All Offer");
		        
		        offerMenu.getItems().addAll(approvedOffer, allOffer);
		        
		        navbar.getMenus().addAll(wishlistMenu, historyMenu, offerMenu);		        
	        }

	        return navbar;
	    }
}
