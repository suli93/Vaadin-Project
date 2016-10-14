package VaadinProject;

import javax.servlet.annotation.WebServlet;

import VaadinProject.com.vaadin.shoppingcart.component.ShoppingCart;
import VaadinProject.com.vaadin.shoppingcart.dto.Clothings;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //We define default layout for our page
        final VerticalLayout layout = new VerticalLayout();

        //Define some textfields components with captions to ask the user to enter some info.
        final TextField name = new TextField();
        name.setCaption("Type item name here:");
        final TextField price = new TextField();
        price.setCaption("Type item price here:");
        final TextField quantity = new TextField();
        quantity.setCaption("Type item quantity here:");

        //Try to recover ShoppingCart from session.
        ShoppingCart myCart = ShoppingCart.recoverFromSession();
        if(myCart==null){
            //If there is no shoppingcart in my session, define a new component of type ShoppingCart
            myCart = new ShoppingCart();
        }
        ShoppingCart myCar = myCart;

        //Define a button component to submit the form request.
        Button button = new Button("Add Item to Cart");
        //Add a click listener to our button...
        button.addClickListener( e -> {
            //Validation to ensure all the information is provided by the user ...
            if(name.getValue().isEmpty() || price.getValue().isEmpty() || quantity.getValue().isEmpty()){
                layout.addComponent(new Label("There's information missing!"));
            }else{
                //If we have all the information, add the item to our ShoppingCart
                Clothings item = this.generateClothingItem(name.getValue(), price.getValue(), quantity.getValue());
                myCar.addItemToShoppingCart(item);

            }
        });

        //Define a button component to hide and show the ShoppingCart.
        Button showSC = new Button("Show My ShoppingCart");
        //Add a click listener to our button...
        showSC.addClickListener( e -> {
            myCar.renderShoppingCart();
        });


        //We add all the components define above to the UI. Without this line we wouldn't see anything on the screen.
        layout.addComponents(name, price, quantity, button, showSC, myCar);

        //Look & Feel stuff
        layout.setMargin(true);
        layout.setSpacing(true);

        //Not sure, but it seems this connect our Layout with the page displayed
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    //Method to generate new Clothings instance from the form.
    public Clothings generateClothingItem(String itemName, String itemPrice, String itemQuantity){
        Clothings item = new Clothings();
        item.setName(itemName);
        item.setPrice(Float.parseFloat(itemPrice));
        item.setQuantity(Integer.parseInt(itemQuantity));
        return item;
    }
}
