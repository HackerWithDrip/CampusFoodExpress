package com.example.campusfoodexpress.vendor;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private int id;
    private String foodItemName;
    private boolean switchState;

    public FoodItem(int id, String foodItemName) {
        this.id = id;
        this.foodItemName = foodItemName;
        this.switchState = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }
    @Override
    public String toString() {
        return id + "    " + foodItemName;
    }

    public boolean getSwitchState() {
        return switchState;
    }

    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }
}
