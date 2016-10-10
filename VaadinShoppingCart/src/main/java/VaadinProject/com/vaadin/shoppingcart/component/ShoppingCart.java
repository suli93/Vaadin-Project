package VaadinProject.com.vaadin.shoppingcart.component;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

/**
 * Created by keihell on 05/10/2016.
 */

//To create a new component we always have to extend CustomComponent Class
public class ShoppingCart extends com.vaadin.ui.CustomComponent {

    /*//We define a private class to represent the items that will be inside our ShoppingCart.
    private class ShoppingCartItem{
        //It's thought like a double-linked list, so we need pointers for previous and next item
        //for very item object we create.
        private ShoppingCartItem previousItem;
        private ShoppingCartItem nextItem;
        //Default attributes that our items will have
        private String name;
        private float price;
        private int quantity;

        @Override
        public String toString(){
            return this.getQuantity() + " " + this.getName() + " " + this.getPrice();
        }

        //Getters and Setters
        public ShoppingCartItem getPreviousItem() {
            return previousItem;
        }

        public void setPreviousItem(ShoppingCartItem previousItem) {
            this.previousItem = previousItem;
        }

        public ShoppingCartItem getNextItem() {
            return nextItem;
        }

        public void setNextItem(ShoppingCartItem nextItem) {
            this.nextItem = nextItem;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }*/

    //Attributes for our ShoppingCart.
    //Pointers to the first and the last item in our ShoppingCart
    private ShoppingCartItem firstItem;
    private ShoppingCartItem lastItem;

    //We define a default layout.
    private final FormLayout layout = new FormLayout();

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
        //TODO: Instead of showing the shopping cart, show a pop up.
        //Check if our shopping cart is not empty...
        if(this.getFirstItem()!=null && this.getLastItem()!=null){
            //We remove everything our ShoppingCart's layout is displaying ...
            this.layout.removeAllComponents();
            //We traverse the ShoppingCart's items starting on the first one
            ShoppingCartItem item = this.getFirstItem();
            while(item!=null){
                Label lname = new Label(item.getName());
                Label lprice = new Label(String.valueOf(item.getPrice()));
                Label lquantity = new Label(String.valueOf(item.getQuantity()));
                //With this line we add our item information to the layout and therefore is shown on the screen.
                this.layout.addComponents(lname, lprice, lquantity);
                //And we get the next item to start a new cycle.
                item = item.getNextItem();
            }
        }
    }

    private boolean addItemToNonEmptyCart(ShoppingCartItem item){
        this.getLastItem().setNextItem(item);
        item.setPreviousItem(this.getLastItem());
        this.setLastItem(item);
        return true;
    }

    private boolean addItemToEmptyCart(ShoppingCartItem item){
        this.setFirstItem(item);
        this.setLastItem(item);
        return true;
    }

    private boolean appendItemToAnotherItem(ShoppingCartItem newItem, ShoppingCartItem preexistingItem){
        preexistingItem.increaseQuantity(newItem.getQuantity());
        preexistingItem.increasePrice(newItem.getPrice());
        return true;
    }
}
