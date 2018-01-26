package com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFood;

/**
 * Created by surajk44437 on 8/12/2017.
 */

public class ViewFoodItem {
    String id, main_title, title, price, quantity, cheacked_value;

    public ViewFoodItem(String id, String main_title, String title, String price, String quantity, String cheacked_value) {
        this.id = id;
        this.main_title = main_title;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.cheacked_value = cheacked_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_title() {
        return main_title;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCheacked_value() {
        return cheacked_value;
    }

    public void setCheacked_value(String cheacked_value) {
        this.cheacked_value = cheacked_value;
    }
}
