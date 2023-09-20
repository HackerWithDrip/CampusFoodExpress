package com.example.campusfoodexpress.vendor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDetails implements Serializable {
    int orderID;
    String orderStatus,customerName,customerSurname,customerPhone;
    LocalDateTime time;
    Double amountDue;
    List<Food> foodList;

    public OrderDetails(int orderID, String orderStatus, String customerName, String customerSurname,
                        String customerPhone, LocalDateTime time, Double amountDue,List<Food> foodList) {
        this.orderID = orderID;
        this.orderStatus = orderStatus;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerPhone = customerPhone;
        this.time = time;
        this.amountDue = amountDue;
        this.foodList = foodList;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public void addFood(Food food){
        foodList.add(food);
    }

    public List<Food> getFoodList(){
        return  this.foodList;
    }
}
