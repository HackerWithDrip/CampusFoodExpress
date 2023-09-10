package com.example.campusfoodexpress.vendor;

public class PaymentOption {
    private String vendorUsername;
    private String isCardPayment;
    private String isCashPayment;

    public String getVendorUsername() {
        return vendorUsername;
    }

    public void setVendorUsername(String vendorUsername) {
        this.vendorUsername = vendorUsername;
    }

    public String getIsCardPayment() {
        return isCardPayment;
    }

    public void setIsCardPayment(String isCardPayment) {
        this.isCardPayment = isCardPayment;
    }

    public String getIsCashPayment() {
        return isCashPayment;
    }

    public void setIsCashPayment(String isCashPayment) {
        this.isCashPayment = isCashPayment;
    }

    public PaymentOption(String vendorUsername, String isCardPayment, String isCashPayment) {
        this.vendorUsername = vendorUsername;
        this.isCardPayment = isCardPayment;
        this.isCashPayment = isCashPayment;
    }
}
