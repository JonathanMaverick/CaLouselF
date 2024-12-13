package view.component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import utils.LoggedUser;
import view.Home;
import view.ViewManager;
import view.admin.ViewAllItem;
import view.admin.ViewItemPending;
import view.auth.Login;
import view.buyer.History;
import view.buyer.ViewItem;
import view.buyer.WishlistView;
import view.seller.AddItem;
import view.seller.ViewItemSeller;

public class Navbar extends MenuBar {

    public Navbar(String role) {
        createNavbar(role);
    }

    public MenuBar createNavbar( String role) {
        initHomeMenu();
        initRoleSpecificMenus(role);
        return this;
    }

    private void initHomeMenu() {
        Menu homeMenu = new Menu("Home");
        MenuItem homeMenuItem = new MenuItem("Home");
        MenuItem logOutItem = new MenuItem("Log Out");
        
        homeMenuItem.setOnAction(e -> home());
        logOutItem.setOnAction(e -> logOut());
        homeMenu.getItems().addAll(homeMenuItem, logOutItem);
        this.getMenus().add(homeMenu);
    }

    private void initRoleSpecificMenus(String role) {
        if ("Admin".equals(role)) {
            initAdminMenu();
        } else if ("Seller".equals(role)) {
            initSellerMenu();
        } else if ("Buyer".equals(role)) {
            initBuyerMenu();
        } 
    }

    private void initAdminMenu() {
        Menu adminMenu = new Menu("Admin");

        MenuItem viewRequestedItem = new MenuItem("View Item Pending");
        MenuItem viewAllItem = new MenuItem("View All Item");
        viewRequestedItem.setOnAction(e -> viewItemPending());
        viewAllItem.setOnAction(e -> viewAllItemAdmin());
        
        adminMenu.getItems().addAll(viewRequestedItem, viewAllItem); 
        this.getMenus().add(adminMenu); 
    }

    private void initSellerMenu() {
        Menu itemMenu = new Menu("Item");
        MenuItem viewOfferedItem = new MenuItem("View Offered Item");
        MenuItem addItem = new MenuItem("Add Item");
        MenuItem viewItemBySeller = new MenuItem("View Item");

        addItem.setOnAction(e -> showAddItemView());
        viewItemBySeller.setOnAction(e -> viewItemSeller());

        itemMenu.getItems().addAll(viewOfferedItem, addItem, viewItemBySeller);
        this.getMenus().add(itemMenu);
    }
    
    private void initBuyerMenu() {
        Menu wishlistMenu = new Menu("Wishlist");
        Menu historyMenu = new Menu("History");
        Menu offerMenu = new Menu("Offer");
        Menu itemMenu = new Menu("Item");

        MenuItem approvedOffer = new MenuItem("Approved Offer");
        MenuItem allOffer = new MenuItem("All Offer");
        MenuItem item = new MenuItem("View Menu");
        
        MenuItem wishlist = new MenuItem("View Wishlist");
        MenuItem history = new MenuItem("View History");
        
        item.setOnAction(e -> viewItemBuyer());
        wishlist.setOnAction(e -> viewWishlist());
        history.setOnAction(e -> viewHistory());
        
        wishlistMenu.getItems().addAll(wishlist);
        historyMenu.getItems().addAll(history);
        itemMenu.getItems().addAll(item);
        offerMenu.getItems().addAll(approvedOffer, allOffer);
        this.getMenus().addAll(itemMenu, wishlistMenu, historyMenu, offerMenu);
    }

    private void showAddItemView() {
        ViewManager.getInstance().switchTo(new AddItem());
    }
    
    private void home() {
        ViewManager.getInstance().switchTo(new Home());
    }
    
    private void viewItemSeller() {
        ViewManager.getInstance().switchTo(new ViewItemSeller());
    }
    
    private void viewItemPending() {
    	ViewManager.getInstance().switchTo(new ViewItemPending());
    }
    
    private void viewAllItemAdmin() {
    	ViewManager.getInstance().switchTo(new ViewAllItem());
    }
    
    private void viewItemBuyer() {
    	ViewManager.getInstance().switchTo(new ViewItem());
    }
    
    private void viewHistory() {
    	ViewManager.getInstance().switchTo(new History());
    }
    
    private void viewWishlist() {
    	ViewManager.getInstance().switchTo(new WishlistView());
    }
    
    private void logOut() {
    	LoggedUser.getInstance().logout();
    	ViewManager.getInstance().switchTo(new Login());
    }
}
