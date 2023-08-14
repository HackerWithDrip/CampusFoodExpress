package com.example.campusfoodexpress.vendor;

public class VendorData {
    private int businessID;
    private String username;
    private String password;
    private String businessName;
    private String businessContactNumber;
    private String businessHours;
    private String businessLocation;
    private String businessDescription;

    public VendorData(String businessName, String businessContactNumber, String businessHours, String businessLocation, String businessDescription) {
        this.businessID = businessID;
        this.username = username;
        this.password = password;
        this.businessName = businessName;
        this.businessContactNumber = businessContactNumber;
        this.businessHours = businessHours;
        this.businessLocation = businessLocation;
        this.businessDescription = businessDescription;
    }

    public VendorData() {
        // Default constructor
    }


    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessContactNumber() {
        return businessContactNumber;
    }

    public void setBusinessContactNumber(String businessContactNumber) {
        this.businessContactNumber = businessContactNumber;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getBusinessBio() {
        return businessDescription;
    }

    public void setBusinessBio(String businessBio) {
        this.businessDescription = businessBio;
    }

    @Override
    public  String toString(){
        return businessName + " " +businessContactNumber + " " + businessDescription;
    }
}
