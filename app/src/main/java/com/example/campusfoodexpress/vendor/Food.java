package com.example.campusfoodexpress.vendor;

import java.io.Serializable;

public class Food implements Serializable {
    String foodName;
    int qty;
    Double amountToPay;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Food(String foodName, int qty,Double amountToPay) {
        this.foodName = foodName;
        this.qty = qty;
        this.amountToPay = amountToPay;
    }
}
