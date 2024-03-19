package java.assignments;

import java.util.ArrayList;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu;

    public Restaurant(String nama) {
        this.nama = nama;
        this.menu = new ArrayList<>();
    }

    // Getter method
    public String getNama() {
        return nama;
    }

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    // Method to add menu to the restaurant
    public void addMenu(Menu item) {
        menu.add(item);
    }
}
