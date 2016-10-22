package com.vaadin.shoppingcart.dto;

import com.vaadin.shoppingcart.component.ShoppingCartItem;

/**
 * Created by keihell on 20/10/2016.
 */
public class Clothings extends ShoppingCartItem{
    private float size;
    private int yearsGuarantee;
    private Brand brand;

    public enum Brand {
        ADIDAS("Adidas"), H_AND_M("H&M"), LACOSTE("Lacoste"), ZAHRA("Zahra"), STADIUM("Stadium"), NIKE("Nike");

        private String value;

        private Brand(String s){
            this.value = s;
        }

        public String getStringValue(){
            return this.value;
        }
    }

    public Clothings(float size, int yearsGuarantee, Brand brand, String name, Float price){
        super();
        this.size = size;
        this.yearsGuarantee = yearsGuarantee;
        this.brand = brand;
        this.setName(name);
        this.setPrice(price);
    }

    public Clothings(){
        super();
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getYearsGuarantee() {
        return yearsGuarantee;
    }

    public void setYearsGuarantee(int yearsGuarantee) {
        this.yearsGuarantee = yearsGuarantee;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
