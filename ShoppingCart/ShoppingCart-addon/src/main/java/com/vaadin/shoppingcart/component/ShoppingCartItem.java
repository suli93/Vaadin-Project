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
    private float unitPrice;
    private float totalPrice;
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

    public ShoppingCartItem(long id, String name, float unitPrice){
        this(id, name, unitPrice, 1);
    }

    public ShoppingCartItem(long id, String name, float unitPrice, int quantity){
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = getUnitPrice() * getQuantity();
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
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

    public float getTotalPrice() {
        return totalPrice;
    }

    //Business Methods

    //Method to increase the quantity as much as indicated
    public void increaseQuantity(int quantity){
        this.setQuantity(getQuantity()+quantity);
    }

    //Method to increase the unitPrice as much as indicated
    public void increasePrice(float price){
        this.totalPrice += price;
    }

    //Method to decrease the quantity as much as indicated
    public void decreaseQuantity(int quantity){
        this.setQuantity(getQuantity()-quantity);
    }

    //Method to decrease the unitPrice as much as indicated
    public void decreasePrice(float price){
        this.totalPrice = this.totalPrice-price;
        if(this.totalPrice < 0.0F){
            this.totalPrice = (this.getUnitPrice()*this.getQuantity())-price;
        }
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
