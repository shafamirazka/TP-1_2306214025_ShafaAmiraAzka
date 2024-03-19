package assignments.assignment2;

public class Menu {
    private String namaMakanan;
    private double harga;
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    public Menu(String namaMakanan, double harga){
        // TODO: buat constructor untuk class ini
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getNamaMakanan() {
        return namaMakanan;
    }

    public double getHarga() {
        return harga;
    }

}