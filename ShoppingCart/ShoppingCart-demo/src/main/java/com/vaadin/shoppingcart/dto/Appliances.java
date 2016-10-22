package com.vaadin.shoppingcart.dto;

import com.vaadin.shoppingcart.component.ShoppingCartItem;

/**
 * Created by keihell on 20/10/2016.
 */
public class Appliances extends ShoppingCartItem {
    private Brand brand;

    public enum Brand {
        TOSHIBA("Toshiba"), PANASONIC("Panasonic"), SONY("Sony"), CASIO("Casio"), LENOVO("Lenovo"),
        SAMSUNG("Samsung"), GENIUS("Genius"), BOSE("Bose");

        private String value;

        private Brand(String s){
            this.value = s;
        }

        public String getStringValue(){
            return this.value;
        }
    }

    public Appliances(String name, Float price, Brand brand){
        super();
        this.setName(name);
        this.setPrice(price);
        this.setBrand(brand);
    }

    public Appliances(){
        super();
    }

    public Appliances(Brand brand){
        super();
        this.brand = brand;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
