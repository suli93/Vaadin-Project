package com.vaadin.shoppingcart.dto;

import com.vaadin.shoppingcart.component.ShoppingCartItem;

import java.util.Date;

/**
 * Created by keihell on 20/10/2016.
 */
public class Books extends ShoppingCartItem {
    private Date publishDate;
    private Type bookType;

    public enum Type {
        BIOGRAPHY("Biography"), FABLE("Fable"), FANTASY("Fantasy"), FOLK_TALE("Folk"),
        LEGEND("Legend"), MYTH("Myth"), SCIENCE_FICTION("Science Fiction");

        private String value;

        private Type(String s){
            this.value = s;
        }

        public String getStringValue(){
            return this.value;
        }


    }

    public Books(Date publishDate, Type bookType, String name, Float price){
        super();
        this.publishDate = publishDate;
        this.bookType = bookType;
        this.setName(name);
        this.setPrice(price);
    }

    public Books(){
        super();
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Type getBookType() {
        return bookType;
    }

    public void setBookType(Type bookType) {
        this.bookType = bookType;
    }
}
