package com.vaadin.shoppingcart.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shoppingcart.component.ShoppingCart;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by keihell on 22/10/2016.
 */
@Theme("demo")
public class ShoppingCartAuxUI extends UI {

    ShoppingCart myCar;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        ShoppingCart myCart = ShoppingCart.recoverFromSession();
        if(myCart==null){
            //If there is no shoppingcart in my session, define a new component of type ShoppingCart
            this.showError();
        }

        myCart.setShoppingCartStyles(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_NO_VERTICAL_LINES);

        myCart.renderShoppingCart();

        setContent(myCart);
    }

    private void showError(){

    }
}
