package assignments.assignment2;

public class Order {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private Menu[] items;
    private boolean orderFinished;
        
        public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[]items){
        // TODO: buat constructor untuk class ini
        this.orderID = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;
        this.items = items;
        this.orderFinished = false;
    }
    
    // TODO: tambahkan methods yang diperlukan untuk class ini
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

    public void setBiayaOngkir(int newBiayaOngkir){
        this.biayaOngkosKirim=newBiayaOngkir;
    }
    
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Menu[] getItems() {
        return items;
    }

    public boolean isOrderFinished() {
        return orderFinished;
    }

    // Setter method to update order status
    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        // TODO:Lengkapi method ini sehingga dapat mengenerate Order ID sesuai ketentuan

        // Ambil 4 huruf pertama dari nama restoran dan konversi ke huruf kapital
        String namaRestoranID = namaRestoran.substring(0, 4).toUpperCase();

        // Ambil 8 karakter dari tanggal pemesanan dan hapus tanda '/'
        String tanggalOrderID = tanggalOrder.replaceAll("/", "");

        // Ambil 2 digit terakhir dari nomor telepon dan format dengan 2 digit
        int sum = 0;
        for (char c : noTelepon.toCharArray()) { // Melakukan iterasi melalui setiap karakter dalam nomor telepon
            if (Character.isDigit(c)) { // Memeriksa apakah karakter saat ini adalah digit
                sum += Character.getNumericValue(c); // Jika ya, tambahkan nilai digit tersebut ke variabel sum
            }
        }
        // Format nilai sum menjadi dua digit dengan String.format(), menggunakan %02d
        String noTeleponID = String.format("%02d", sum % 100);

        // Menghitung checksum untuk menambahkan keamanan pada Order ID
        String orderIDWithoutChecksum = namaRestoranID + tanggalOrderID + noTeleponID;
        int checksum1 = 0; // Inisialisasi variabel checksum pertama
        int checksum2 = 0; // Inisialisasi variabel checksum kedua
        // Melakukan iterasi melalui setiap karakter dalam Order ID yang belum memiliki checksum
        for (int i = 0; i < orderIDWithoutChecksum.length(); i++) {
            // Mendapatkan nilai numerik dari karakter saat ini
            int numericValue = getNumericValue(orderIDWithoutChecksum.charAt(i));
            // Menambahkan nilai numerik ke checksum pertama jika indeks karakter genap, atau ke checksum kedua jika indeks ganjil
            if (i % 2 == 0) {
                checksum1 += numericValue;
            } else {
                checksum2 += numericValue;
            }
        }
        // Menggunakan operasi modulus (%) untuk memastikan bahwa checksum1 dan checksum2 berada dalam rentang 0-35
        checksum1 %= 36;
        checksum2 %= 36;
        // Mengonversi nilai checksum menjadi karakter dengan menggunakan method getCharacter()
        char checksum1Char = getCharacter(checksum1);
        char checksum2Char = getCharacter(checksum2);

        // Menghasilkan Order ID dengan menambahkan checksum
        String generatedOrderID = namaRestoranID + tanggalOrderID + noTeleponID + checksum1Char + checksum2Char;
        // Menyimpan Order ID yang telah digenerate ke dalam ArrayList orderIDs
        return generatedOrderID; // Mengembalikan Order ID yang telah digenerate
    }

// Method untuk mendapatkan nilai numerik dari karakter
private static int getNumericValue(char c) {
    if (Character.isDigit(c)) { // Memeriksa apakah karakter adalah angka
        return Character.getNumericValue(c); // Mengembalikan nilai numerik karakter jika karakter adalah angka
    } else {
        // Jika karakter bukan angka, mengembalikan nilai numerik yang sesuai dengan representasi angka heksadesimal dari karakter tersebut
        // Misalnya, 'A' akan dianggap sebagai 10, 'B' sebagai 11, dan seterusnya.
        return c - 'A' + 10;
    }
}

// Method untuk mendapatkan karakter dari nilai numerik
private static char getCharacter(int value) {
    if (value < 10) {
        // Jika nilai numerik kurang dari 10, mengembalikan karakter yang sesuai dengan nilai numerik tersebut
        // Misalnya, untuk nilai 0-9, karakter yang dikembalikan adalah '0' - '9'.
        return (char) ('0' + value);
    } else {
        // Jika nilai numerik lebih dari atau sama dengan 10, mengembalikan karakter yang sesuai dengan nilai numerik tersebut
        // Misalnya, untuk nilai 10-35, karakter yang dikembalikan adalah 'A' - 'Z'.
        // Karakter 'A' direpresentasikan oleh nilai numerik 10, 'B' oleh 11, dan seterusnya.
        return (char) ('A' + value - 10);
    }
}
}