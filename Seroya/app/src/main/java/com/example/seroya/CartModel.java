package com.example.seroya;

public class CartModel {
    String image, name, subtotal,quantity;

    public CartModel() {
    }

    public CartModel(String image, String name, String subtotal, String quantity) {
        this.image = image;
        this.name = name;
        this.subtotal = subtotal;
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
