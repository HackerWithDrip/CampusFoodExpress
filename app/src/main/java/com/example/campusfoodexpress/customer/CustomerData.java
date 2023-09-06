package com.example.campusfoodexpress.customer;

public class CustomerData {
    private String customerFirstName,customerLastName,customerContactNumber;

    public CustomerData(String customerFirstName, String customerLastName, String customerContactNumber) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerContactNumber = customerContactNumber;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerContactNumber() {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(String customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }

    @Override
    public  String toString(){
        return customerFirstName + " " +customerLastName + " " + customerContactNumber;
    }
}
