package VaadinProject;

import javax.servlet.annotation.WebServlet;

import VaadinProject.com.vaadin.shoppingcart.component.ShoppingCart;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
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
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type item name here:");

        final TextField price = new TextField();
        price.setCaption("Type item price here:");

        final TextField quantity = new TextField();
        quantity.setCaption("Type item quantity here:");

        final Table table = new Table();

        ShoppingCart myCar = new ShoppingCart();

        Button button = new Button("Add Item to Cart");
        button.addClickListener( e -> {
//            layout.addComponent(new Label("Thanks " + name.getValue()
//                    + ", it works!"));
            if(name.getValue().isEmpty() || price.getValue().isEmpty() || quantity.getValue().isEmpty()){
                layout.addComponent(new Label("There's information missing!"));
            }else{
                //TODO: Send objects and no strings.
                //Every object must have id and name.
                myCar.addItemToShoppingCart(name.getValue(), price.getValue(), quantity.getValue());
            }
        });
        
        layout.addComponents(name, price, quantity, button, myCar);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
