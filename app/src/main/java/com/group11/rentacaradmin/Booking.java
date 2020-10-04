package com.group11.rentacaradmin;


public class Booking {

    private String model;
    private String price;
    private String cusName;
    private String cusPhone;
    private String cusEmail;
    private String cusDate;
    private String cusNoOfDays;

    public Booking() {

    }

    public Booking(String model, String price, String cusName, String cusPhone, String cusEmail, String cusDate, String cusNoOfDays) {
        this.model = model;
        this.price = price;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusEmail = cusEmail;
        this.cusDate = cusDate;
        this.cusNoOfDays = cusNoOfDays;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusDate() {
        return cusDate;
    }

    public void setCusDate(String cusDate) {
        this.cusDate = cusDate;
    }

    public String getCusNoOfDays() {
        return cusNoOfDays;
    }

    public void setCusNoOfDays(String cusNoOfDays) {
        this.cusNoOfDays = cusNoOfDays;
    }
}
