package VaadinProject.com.vaadin.shoppingcart.component;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

/**
 * Created by keihell on 05/10/2016.
 */
public class ShoppingCart extends com.vaadin.ui.CustomComponent {

    private class ShoppingCartItem{
        private ShoppingCartItem previousItem;
        private ShoppingCartItem nextItem;
        private String name;
        private float price;
        private int quantity;

        @Override
        public String toString(){
            return this.getQuantity() + " " + this.getName() + " " + this.getPrice();
        }

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
    }

    private ShoppingCartItem firstItem;
    private ShoppingCartItem lastItem;

    private final FormLayout layout = new FormLayout();

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

    public boolean addItemToShoppingCart(String name, String price, String quantity){
        //TODO: Shpould check if item already exists in shopping cart and just increase quantity and price,
        //otherwise it should create a new item in the shopping cart.

        if(this.getLastItem()!=null){
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setName(name);
            newItem.setPrice(Float.parseFloat(price));
            newItem.setQuantity(Integer.parseInt(quantity));
            newItem.setPreviousItem(this.getLastItem());
            this.getLastItem().setNextItem(newItem);
            this.setLastItem(newItem);
        }else{
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setName(name);
            newItem.setPrice(Float.parseFloat(price));
            newItem.setQuantity(Integer.parseInt(quantity));
            newItem.setPreviousItem(this.getLastItem());
            this.setLastItem(newItem);
            this.setFirstItem(newItem);
        }

        renderShoppingCart();

        return true;
    }

    public void renderShoppingCart(){
        //TODO: Instead of showing the shopping cart, show a pop up.
        if(this.getFirstItem()!=null && this.getLastItem()!=null){
            this.layout.removeAllComponents();
            ShoppingCartItem item = this.getFirstItem();
            while(item!=null){
                Label lname = new Label(item.getName());
                Label lprice = new Label(String.valueOf(item.getPrice()));
                Label lquantity = new Label(String.valueOf(item.getQuantity()));
                this.layout.addComponents(lname, lprice, lquantity);
                item = item.getNextItem();
            }
        }
    }
}
