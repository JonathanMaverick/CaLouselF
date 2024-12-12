package view.component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import view.ViewManager;
import view.admin.ViewItemPending;
import view.seller.AddItem;
import view.seller.ViewItemSeller;

public class Navbar extends MenuBar {

    private static Navbar instance;

    private Navbar(ViewManager vm, String role) {
        createNavbar(vm, role);
    }

    public static Navbar getInstance(ViewManager vm, String role) {
        if (instance == null) {
            instance = new Navbar(vm, role);
        }
        return instance;
    }

    public MenuBar createNavbar(ViewManager vm, String role) {
        this.getMenus().clear(); 
        initHomeMenu(); 
        initRoleSpecificMenus(vm, role); 
        return this;
    }

    private void initHomeMenu() {
        Menu homeMenu = new Menu("Home");
        this.getMenus().add(homeMenu);
    }

    private void initRoleSpecificMenus(ViewManager vm, String role) {
        if ("Admin".equals(role)) {
            initAdminMenu(vm);
        } else if ("Seller".equals(role)) {
            initSellerMenu(vm);
        } else if ("Buyer".equals(role)) {
            initBuyerMenu();
        }
    }

    private void initAdminMenu(ViewManager vm) {
        Menu adminMenu = new Menu("Admin");

        MenuItem viewRequestedItem = new MenuItem("View Item Pending");
        viewRequestedItem.setOnAction(e -> viewItemAdmin(vm));
        
        adminMenu.getItems().add(viewRequestedItem); 
        this.getMenus().add(adminMenu); 
    }

    private void initSellerMenu(ViewManager vm) {
        Menu itemMenu = new Menu("Item");
        MenuItem viewOfferedItem = new MenuItem("View Offered Item");
        MenuItem addItem = new MenuItem("Add Item");
        MenuItem viewItemBySeller = new MenuItem("View Item");

        addItem.setOnAction(e -> showAddItemView(vm));
        viewItemBySeller.setOnAction(e -> viewItemBySeller(vm));

        itemMenu.getItems().addAll(viewOfferedItem, addItem, viewItemBySeller);
        this.getMenus().add(itemMenu);
    }
    
    private void initBuyerMenu() {
        Menu wishlistMenu = new Menu("Wishlist");
        Menu historyMenu = new Menu("History");
        Menu offerMenu = new Menu("Offer");

        MenuItem approvedOffer = new MenuItem("Approved Offer");
        MenuItem allOffer = new MenuItem("All Offer");

        offerMenu.getItems().addAll(approvedOffer, allOffer);
        this.getMenus().addAll(wishlistMenu, historyMenu, offerMenu);
    }

    private void showAddItemView(ViewManager vm) {
        if (vm.getView("add_item") == null) {
            AddItem addItemView = new AddItem(vm);
            vm.registerView("add_item", addItemView.getView());
        }
        vm.showView("add_item");
    }
    
    private void viewItemBySeller(ViewManager vm) {
    	if (vm.getView("view_item_by_seller") == null) {
            ViewItemSeller viewItemSellerView = new ViewItemSeller(vm);
            vm.registerView("view_item_by_seller", viewItemSellerView.getView());
        }
        vm.showView("view_item_by_seller");
    }
    
    private void viewItemAdmin(ViewManager vm) {
    	if(vm.getView("view_item") == null) {
    		ViewItemPending viewItem = new ViewItemPending(vm);
    		vm.registerView("view_item", viewItem.getView());
    	}
    	vm.showView("view_item");
    }
}
