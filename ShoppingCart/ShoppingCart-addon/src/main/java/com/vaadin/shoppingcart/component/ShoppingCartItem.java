package com.vaadin.shoppingcart.component;

import java.util.Random;

/**
 * Created by keihell on 10/10/2016.
 */
public class ShoppingCartItem {
    //It's thought like a double-linked list, so we need pointers for previous and next item
    //for very item object we create.
    private ShoppingCartItem previousItem;
    private ShoppingCartItem nextItem;

    private long id;
    private String name;
    private float price;
    private int quantity;

    //Overloaded constructors
    public ShoppingCartItem(long id){
        this(id, "", 0.0F, 1);
    }

    public ShoppingCartItem(){
        this(generateRandomId());
    }

    public ShoppingCartItem(long id, String name, int quantity){
        this(id, name, 0.0F, quantity);
    }

    public ShoppingCartItem(long id, String name, float price){
        this(id, name, price, 1);
    }

    public ShoppingCartItem(long id, String name, float price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    //Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    //Business Methods

    //Method to increase the quantity as much as indicated
    public void increaseQuantity(int quantity){
        this.setQuantity(getQuantity()+quantity);
    }

    //Method to increase the price as much as indicated
    public void increasePrice(float price){
        this.setPrice(getPrice()+price);
    }

    //Method to increase the quantity as much as indicated
    public void decreaseQuantity(int quantity){
        this.setQuantity(getQuantity()-quantity);
    }

    //Method to increase the price as much as indicated
    public void decreasePrice(float price){
        this.setPrice(getPrice()-price);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(!(obj instanceof ShoppingCartItem)){
            return false;
        }
        ShoppingCartItem item = (ShoppingCartItem) obj;
        if(item.getId()==this.getId()){
            return true;
        }else{
            return false;
        }
    }

    public static long generateRandomId(){
        Random randomGenerator = new Random();
        return randomGenerator.nextLong();
    }
}
