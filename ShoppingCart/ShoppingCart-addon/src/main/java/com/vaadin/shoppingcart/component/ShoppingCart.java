package com.vaadin.shoppingcart.component;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button;

import java.util.ArrayList;

/**
 * Created by keihell on 05/10/2016.
 */

//To create a new component we always have to extend CustomComponent Class
public class ShoppingCart extends com.vaadin.ui.CustomComponent {

    //Attributes for our ShoppingCart.

    //Styles to add the table of ShoppingCart
    private ArrayList<String> tableStyles;

    //Pointers to the first and the last item in our ShoppingCart
    private ShoppingCartItem firstItem;
    private ShoppingCartItem lastItem;

    //We define a default layout.
    private final FormLayout layout = new FormLayout();

    //Configurable attribute to show notifications when an item is added
    private boolean showAddedItemNotification = true;
    //Configurable attribute to show notifications when an item is added
    private boolean automaticSessionSaving = true;

    //Constructor method. We just call parent class constructor, and
    //set some default look & feel settings.
    public ShoppingCart(){
        super();
        this.setSizeFull();
        this.layout.setSizeFull();
        this.layout.setMargin(true);
        this.layout.setSpacing(true);
        this.setCompositionRoot(this.layout);
        this.firstItem = null;
        this.lastItem = null;
    }

    public void setFirstItem(ShoppingCartItem item){
        this.firstItem = item;
    }

    public ShoppingCartItem getFirstItem(){
        return this.firstItem;
    }

    public void setLastItem(ShoppingCartItem item){
        this.lastItem = item;
    }

    public ShoppingCartItem getLastItem(){
        return this.lastItem;
    }

    public boolean isShowAddedItemNotification() {
        return showAddedItemNotification;
    }

    public void setShowAddedItemNotification(boolean showAddedItemNotification) {
        this.showAddedItemNotification = showAddedItemNotification;
    }

    public boolean isAutomaticSessionSaving() {
        return automaticSessionSaving;
    }

    public void setAutomaticSessionSaving(boolean automaticSessionSaving) {
        this.automaticSessionSaving = automaticSessionSaving;
    }

    //Method to add an Item to our ShoppingCart.
    public boolean addItemToShoppingCart(ShoppingCartItem item){
        //Check if the Item has a valid Id
        if(item.getId()!=0){
            //Look into the ShoppingCart Items to check if it already exists...
            if(this.getFirstItem()!=null && this.getLastItem()!=null){
                //We traverse the ShoppingCart's items starting on the first one
                ShoppingCartItem indexItem = this.getFirstItem();
                while(indexItem!=null){
                    if(indexItem.equals(item)){
                        //If it's the same item, just increase the quantity and sum the price
                        return this.appendItemToAnotherItem(item, indexItem);
                    }
                    //Get the next item to start a new cycle.
                    indexItem = indexItem.getNextItem();
                }
                //If we didn't find the item in our ShoppingCart, we need to add it.
                return this.addItemToNonEmptyCart(item);
            }else{
                //The ShoppingCart is empty so we add the item
                return this.addItemToEmptyCart(item);
            }
        }
        //If the Item has no valid Id we don't add it to the ShoppingCart.
        return false;

    }

    public void renderShoppingCart(){
        //We remove everything our ShoppingCart's layout is displaying ...
        this.layout.removeAllComponents();
        //Check if our shopping cart is not empty...
        if(this.getFirstItem()!=null && this.getLastItem()!=null){
            float total = 0.0F;
            Table table = new Table();
            table.setSizeFull();

            // Define two columns for the built-in container
            table.addContainerProperty("Item", String.class, null);
            table.addContainerProperty("Qty.",  String.class, null);
            table.addContainerProperty("Price", Float.class, null);
            table.addContainerProperty("-",  com.vaadin.ui.Button.class, null);
            table.addContainerProperty("x", com.vaadin.ui.Button.class, null);

            //We traverse the ShoppingCart's items starting on the first one
            ShoppingCartItem item = this.getFirstItem();
            while(item!=null){
                //Create decrease and delete buttons
                Button decreaseQty = new Button();
                //decreaseQty.setIcon();
                decreaseQty.setId("decrease_"+item.getId());
                decreaseQty.addClickListener( e -> {
                    String[] data = decreaseQty.getId().split("_");
                    String itemId = data[1];
                    decreaseItemQuantity(Long.parseLong(itemId));
                    this.renderShoppingCart();
                });

                Button delete = new Button("X");
                delete.setId("del_"+item.getId());
                delete.addClickListener( e -> {
                    String[] data = delete.getId().split("_");
                    String itemId = data[1];
                    this.deleteItem(Long.parseLong(itemId));
                    this.renderShoppingCart();
                });

                // Add a new row
                table.addItem(new Object[]{item.getName(),String.valueOf(item.getQuantity()), item.getPrice(), decreaseQty, delete}, item.getId());
                total+=item.getPrice();
                //And we get the next item to start a new cycle.
                item = item.getNextItem();
            }
            //We add one last row with the Shopping cart total
            table.addItem(new Object[]{"TOTAL:","", total, null, null}, 0);
            // Show exactly the currently contained rows (items)
            table.setPageLength(table.size());
            if(tableStyles!=null && !tableStyles.isEmpty()){
                for(String style: tableStyles){
                    table.addStyleName(style);
                }
            }
            //With this line we add our item information to the layout and therefore is shown on the screen.
            this.layout.addComponent(table);
        }
    }

    //Method to add item when cart is not empty
    private boolean addItemToNonEmptyCart(ShoppingCartItem item){
        this.getLastItem().setNextItem(item);
        item.setPreviousItem(this.getLastItem());
        this.setLastItem(item);
        this.saveCartInSession();
        this.showNotification(item.getName());
        return true;
    }

    //Method to add item when cart is empty
    private boolean addItemToEmptyCart(ShoppingCartItem item){
        this.setFirstItem(item);
        this.setLastItem(item);
        this.saveCartInSession();
        this.showNotification(item.getName());
        return true;
    }

    //Method to sum item to another one existing in the Cart
    private boolean appendItemToAnotherItem(ShoppingCartItem newItem, ShoppingCartItem preexistingItem){
        preexistingItem.increaseQuantity(newItem.getQuantity());
        preexistingItem.increasePrice(newItem.getPrice());
        this.saveCartInSession();
        this.showNotification(newItem.getName());
        return true;
    }

    //Method to show notification
    private void showNotification(String itemName){
        if (this.isShowAddedItemNotification()){
            Notification notification = new Notification("New Item Added to Shopping Cart",
                    "<br/>Added Item: "+itemName,
                    Notification.TYPE_TRAY_NOTIFICATION, true);

            notification.setPosition(Position.TOP_RIGHT);
            notification.show(Page.getCurrent());
        }
    }

    //Method to decrease one item's quantity
    private void decreaseItemQuantity(long itemId){
        if(this.getFirstItem()!=null && this.getLastItem()!=null){
            ShoppingCartItem item = this.getFirstItem();
            while(item!=null){
                if(item.getId()==itemId){
                    float unitaryPrice = item.getPrice() / item.getQuantity();
                    item.decreaseQuantity(1);
                    if(item.getQuantity()==0){
                        this.deleteItem(item.getId());
                        break;
                    }
                    item.decreasePrice(unitaryPrice);
                    break;
                }
                item = item.getNextItem();
            }
        }
        this.saveCartInSession();
    }

    //Method to delete one item completely from Cart
    private boolean deleteItem(long itemId){
        if(this.getFirstItem()!=null && this.getLastItem()!=null){
            ShoppingCartItem item = this.getFirstItem();
            while(item!=null){
                if(item.getId()==itemId){
                    if(item.getPreviousItem()!=null){
                        item.getPreviousItem().setNextItem(item.getNextItem());
                    }else{
                        this.setFirstItem(item.getNextItem());
                    }
                    if(item.getNextItem()!=null){
                        item.getNextItem().setPreviousItem(item.getPreviousItem());
                    }else{
                        this.setLastItem(item.getPreviousItem());
                    }
                    break;
                }
                item = item.getNextItem();
            }
            this.saveCartInSession();
            return true;
        }
        this.saveCartInSession();
        return false;
    }

    //Method to save cart in session
    private void saveCartInSession(){
        if(this.isAutomaticSessionSaving()){
            VaadinSession.getCurrent().setAttribute("__VaadinShoppingCart__", this);
        }
    }

    //Method to retreive cart from session
    public static ShoppingCart recoverFromSession(){
        return (ShoppingCart) VaadinSession.getCurrent().getAttribute("__VaadinShoppingCart__");
    }

    public void setShoppingCartStyles(String... styles){
        if(tableStyles==null){
            tableStyles = new ArrayList();
        }
        for(String style : styles){
            tableStyles.add(style);
        }
    }

    public ArrayList<String> getShoppingCartStyles(){
        return (ArrayList<String>)this.tableStyles.clone();
    }

    public boolean removeShoppingCartStyles(String... styles){
        boolean removed = false;
        if(tableStyles==null || tableStyles.isEmpty()){
            return removed;
        }
        for(String style : styles){
            if(tableStyles.contains(style)){
                tableStyles.remove(style);
                removed = true;
            }
        }
        return removed;
    }
}
