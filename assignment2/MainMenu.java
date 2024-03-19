package java.assignments;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        restoList = new ArrayList<>();
        userList = new ArrayList<>();
        initUser();

        boolean programRunning = true;
        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // Validasi input login
                User userLoggedIn = getUser(nama, noTelp);
                boolean isLoggedIn = true;

                if(userLoggedIn != null){
                    if(userLoggedIn.getRole().equals("Customer")){
                        while (isLoggedIn){
                            menuCustomer();
                            int commandCust = input.nextInt();
                            input.nextLine();

                            switch(commandCust){
                                case 1 -> handleBuatPesanan(userLoggedIn);
                                case 2 -> handleCetakBill();
                                case 3 -> handleLihatMenu();
                                case 4 -> handleUpdateStatusPesanan();
                                case 5 -> isLoggedIn = false;
                                default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                            }
                        }
                    } else if(userLoggedIn.getRole().equals("Admin")){
                        while (isLoggedIn){
                            menuAdmin();
                            int commandAdmin = input.nextInt();
                            input.nextLine();

                            switch(commandAdmin){
                                case 1 -> handleTambahRestoran();
                                case 2 -> handleHapusRestoran();
                                case 3 -> isLoggedIn = false;
                                default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                            }
                        }
                    }
                } else {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                }
            } else if(command == 2){
                programRunning = false;
            } else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static User getUser(String nama, String nomorTelepon){
        for(User user : userList){
            if(user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)){
                return user;
            }
        }
        return null;
    }

    public static void handleBuatPesanan(User userLoggedIn) {
        System.out.println("\nMembuat pesanan:");
        // Menampilkan daftar restoran
        System.out.println("Daftar restoran:");
        for (Restaurant resto : restoList) {
            System.out.println(resto.getNama());
        }
    
        // Meminta input nama restoran dari pengguna
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
    
        // Mencari restoran berdasarkan nama
        Restaurant restoran = null;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                restoran = resto;
                break;
            }
        }
    
        // Memeriksa apakah restoran ditemukan
        if (restoran == null) {
            System.out.println("Restoran tidak terdaftar pada system.");
            return;
        }
    
        // Meminta input tanggal pemesanan
        System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
        String tanggalPemesanan = input.nextLine();
    
        // Memeriksa format tanggal yang dimasukkan
        if (!tanggalPemesanan.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!");
            return;
        }
    
        // Meminta input jumlah pesanan
        System.out.print("Jumlah Pesanan: ");
        int jumlahPesanan = input.nextInt();
        input.nextLine(); // Membuang newline
    
        // Membuat array untuk menyimpan item pesanan
        Menu[] items = new Menu[jumlahPesanan];
    
        // Meminta input setiap item pesanan
        for (int i = 0; i < jumlahPesanan; i++) {
            System.out.print("Item pesanan ke-" + (i + 1) + ": ");
            String namaItem = input.nextLine();
    
            // Mencari menu berdasarkan nama
            Menu menu = null;
            for (Menu m : restoran.getMenu()) {
                if (m.getNamaMakanan().equalsIgnoreCase(namaItem)) {
                    menu = m;
                    break;
                }
            }
    
            // Memeriksa apakah menu ditemukan
            if (menu == null) {
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                return;
            }
    
            // Menyimpan item pesanan ke dalam array
            items[i] = menu;
        }
    
        // Membuat ID pesanan
        String orderID = restoran.getNama().toUpperCase().substring(0, 4) + tanggalPemesanan.replaceAll("/", "") + userLoggedIn.getNomorTelepon().substring(6);
    
        // Membuat objek Order
        Order order = new Order(orderID, tanggalPemesanan, 0, restoran, items);
    
        // Menambahkan pesanan ke dalam history pengguna
        userLoggedIn.getOrderHistory().add(order);
    
        System.out.println("Pesanan dengan ID " + orderID + " diterima!");
    }
    

    public static void handleCetakBill() {
        System.out.println("\nMencetak bill:");
    
        // Meminta input Order ID dari pengguna
        System.out.print("Masukkan Order ID: ");
        String orderID = input.nextLine();
    
        // Mencari pesanan berdasarkan Order ID
        Order order = null;
        for (User user : userList) {
            for (Order o : user.getOrderHistory()) {
                if (o.getOrderID().equalsIgnoreCase(orderID)) {
                    order = o;
                    break;
                }
            }
            if (order != null) {
                break;
            }
        }
    
        // Memeriksa apakah pesanan ditemukan
        if (order == null) {
            System.out.println("Order ID tidak dapat ditemukan.");
            return;
        }
    
        // Menampilkan informasi bill
        System.out.println("Bill:");
        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Tanggal Pemesanan: " + order.getTanggalPemesanan());
        System.out.println("Restaurant: " + order.getRestaurant().getNama());
        System.out.println("Lokasi Pengiriman: " + order.getRestaurant().getLokasi());
        System.out.println("Status Pengiriman: " + (order.isOrderFinished() ? "Finished" : "Not Finished"));
        System.out.println("Pesanan:");
        double totalBiaya = 0;
        for (Menu item : order.getItems()) {
            System.out.println("- " + item.getNamaMakanan() + " " + item.getHarga());
            totalBiaya += item.getHarga();
        }
        System.out.println("Total Biaya: Rp " + totalBiaya);
    }
    

    public static void handleLihatMenu() {
        System.out.println("\nMelihat menu:");
    
        // Meminta input nama restoran dari pengguna
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
    
        // Mencari restoran berdasarkan nama
        Restaurant restoran = null;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                restoran = resto;
                break;
            }
        }
    
        // Memeriksa apakah restoran ditemukan
        if (restoran == null) {
            System.out.println("Restoran tidak terdaftar pada system.");
            return;
        }
    
        // Menampilkan menu restoran
        System.out.println("Menu Restoran " + restoran.getNama() + ":");
        for (Menu item : restoran.getMenu()) {
            System.out.println("- " + item.getNamaMakanan() + " Rp" + item.getHarga());
        }
    }
    

    public static void handleUpdateStatusPesanan() {
        System.out.println("\nMemperbarui status pesanan:");
    
        // Meminta input Order ID dari pengguna
        System.out.print("Masukkan Order ID: ");
        String orderID = input.nextLine();
    
        // Mencari pesanan berdasarkan Order ID
        Order order = null;
        for (User user : userList) {
            for (Order o : user.getOrderHistory()) {
                if (o.getOrderID().equalsIgnoreCase(orderID)) {
                    order = o;
                    break;
                }
            }
            if (order != null) {
                break;
            }
        }
    
        // Memeriksa apakah pesanan ditemukan
        if (order == null) {
            System.out.println("Order ID tidak dapat ditemukan.");
            return;
        }
    
        // Memperbarui status pesanan
        order.setOrderFinished(true);
        System.out.println("Status pesanan dengan Order ID " + orderID + " berhasil diperbarui.");
    }
    

    public static void handleTambahRestoran() {
        System.out.println("\nMenambah restoran:");
    
        // Meminta input nama restoran dari pengguna
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
    
        // Meminta input lokasi restoran dari pengguna
        System.out.print("Lokasi Restoran: ");
        String lokasiRestoran = input.nextLine();
    
        // Membuat objek restoran baru dan menambahkannya ke daftar restoran
        Restaurant newRestoran = new Restaurant(namaRestoran, lokasiRestoran);
        restoList.add(newRestoran);
    
        System.out.println("Restoran " + namaRestoran + " berhasil ditambahkan.");
    }
    

    public static void handleHapusRestoran() {
        System.out.println("\nMenghapus restoran:");
    
        // Meminta input nama restoran dari pengguna
        System.out.print("Nama Restoran yang akan dihapus: ");
        String namaRestoran = input.nextLine();
    
        // Mencari restoran berdasarkan nama
        Restaurant restoran = null;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                restoran = resto;
                break;
            }
        }
    
        // Memeriksa apakah restoran ditemukan
        if (restoran == null) {
            System.out.println("Restoran tidak terdaftar pada system.");
            return;
        }
    
        // Menghapus restoran dari daftar restoran
        restoList.remove(restoran);
    
        System.out.println("Restoran " + namaRestoran + " berhasil dihapus.");
    }
    

    public static void initUser(){
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }


    // Method untuk menghasilkan Order ID berdasarkan nama restoran, tanggal pemesanan, dan nomor telepon
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
        orderIDs.add(generatedOrderID);
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

/*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     *          Bill:
     *          Order ID: [Order ID]
     *          Tanggal Pemesanan: [Tanggal Pemesanan]
     *          Lokasi Pengiriman: [Kode Lokasi]
     *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */

    // Method untuk menghasilkan tagihan berdasarkan Order ID dan lokasi pengiriman
    public static String generateBill(String orderID, String lokasi) {
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan

        // Validasi apakah Order ID sudah ada sebelumnya
        if (!orderIDs.contains(orderID)) {
            return "Order ID tidak ditemukan!";
        }
        // Menghitung biaya pengiriman berdasarkan lokasi pengiriman
        int biayaOngkir = 0;
        switch (lokasi.toUpperCase()) {
            case "P":
                biayaOngkir = 10000;
                break;
            case "U":
                biayaOngkir = 20000;
                break;
            case "T":
                biayaOngkir = 35000;
                break;
            case "S":
                biayaOngkir = 40000;
                break;
            case "B":
                biayaOngkir = 60000;
                break;
            default:
                return "Harap masukkan lokasi pengiriman yang ada pada jangkauan!";
        }

        // Ekstrak informasi dari tanggal pemesanan pada Order ID
        // String namaRestoran = orderID.substring(0, 4);
        String tanggalPemesanan = orderID.substring(4, 12);
        // String noTelepon = orderID.substring(12, 14);

        // Menghasilkan tagihan dengan informasi Order ID, tanggal pemesanan, lokasi pengiriman, dan biaya pengiriman
        return "Bill:\n" +
                "Order ID: " + orderID + "\n" +
                "Tanggal Pemesanan: " + tanggalPemesanan.substring(0, 2) + "/" + tanggalPemesanan.substring(2, 4) + "/" + tanggalPemesanan.substring(4) + "\n" +
                "Lokasi Pengiriman: " + lokasi.toUpperCase() + "\n" +
                "Biaya Ongkos Kirim: Rp " + biayaOngkir;
    }
}