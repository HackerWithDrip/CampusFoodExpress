package com.example.campusfoodexpress.vendor;

import android.text.format.Time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private int orderID;
    private String orderStatus;
    private double orderTotal;
    private List<FoodItem> foodItems;

    public Order(int orderID, String orderStatus, double orderTotal) {
        this.orderID = orderID;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
        this.foodItems = new ArrayList<>();
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void addFoodItem(FoodItem foodItem) {
        foodItems.add(foodItem);
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }
}
