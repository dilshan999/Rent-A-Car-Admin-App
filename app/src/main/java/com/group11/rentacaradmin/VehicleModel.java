package com.group11.rentacaradmin;

public class VehicleModel {

    private String ImageUrl;
    private double Price;
    private String Brand, VehicleID;
    private int Passengers;
    private String Transmission;

    public VehicleModel() {
    }

    public VehicleModel(String imageUrl, double price, String brand, int passengers, String transmission) {

        if (brand.trim().equals("")) {
            brand = "No Name";
        }

        ImageUrl = imageUrl;
        Price = price;
        Brand = brand;
        Passengers = passengers;
        Transmission = transmission;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getVehicleID() {
        return VehicleID;
    }

    public void setVehicleID(String vehicleID) {
        VehicleID = vehicleID;
    }

    public int getPassengers() {
        return Passengers;
    }

    public void setPassengers(int passengers) {
        Passengers = passengers;
    }

    public String getTransmission() {
        return Transmission;
    }

    public void setTransmission(String transmission) {
        Transmission = transmission;
    }
}
