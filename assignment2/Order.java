package java.assignments;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items;
    private boolean orderFinished;

    public Order(String orderID, String tanggalPemesanan, int biayaOngkosKirim, Restaurant restaurant, ArrayList<Menu> items) {
        this.orderID = orderID;
        this.tanggalPemesanan = tanggalPemesanan;
        this.biayaOngkosKirim = biayaOngkosKirim;
        this.restaurant = restaurant;
        this.items = items;
        this.orderFinished = false;
    }

    // Getter methods
    public String getOrderID() {
        return orderID;
    }

    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public int getBiayaOngkosKirim() {
        return biayaOngkosKirim;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public ArrayList<Menu> getItems() {
        return items;
    }

    public boolean isOrderFinished() {
        return orderFinished;
    }

    // Setter method to update order status
    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }
}
