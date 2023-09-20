package com.example.campusfoodexpress.vendor;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private int id;
    private Double price;
    private String foodItemName,vendorUsername;
    private boolean isAvailable;

    public FoodItem(String vendorUsername,int id, String foodItemName) {
        this.vendorUsername = vendorUsername;
        this.id = id;
        this.foodItemName = foodItemName;
        this.isAvailable = false;
    }

    public FoodItem(String vendorUsername,int id, String foodItemName, Double price) {
        this.vendorUsername = vendorUsername;
        this.id = id;
        this.foodItemName = foodItemName;
        this.price = price;
    }


    public FoodItem(String vendorUsername,int id, String foodItemName,String isAvailable) {
        this.vendorUsername = vendorUsername;
        this.id = id;
        this.foodItemName = foodItemName;
        if(isAvailable.equalsIgnoreCase("true"))
            this.isAvailable = true;
        else
            this.isAvailable = false;
    }

    public FoodItem(String vendorUsername, int foodId, String foodItemName, String isAvailable, Double price) {
        this.vendorUsername = vendorUsername;
        this.id = foodId;
        this.foodItemName = foodItemName;
        this.price = price;
        if(isAvailable.equalsIgnoreCase("true"))
            this.isAvailable = true;
        else
            this.isAvailable = false;
    }

    public int getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public String getVendorUsername(){return vendorUsername;}

    public Boolean isFoodItemAvailable(){return (isAvailable);}

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }
    @Override
    public String toString() {
        return id + "    " + foodItemName;
    }

    public boolean getSwitchState() {
        return isAvailable;
    }

    public void setSwitchState(boolean switchState) {
        this.isAvailable = switchState;
    }
}
