// Kelas ini berada dalam paket assignments.assignment2
package assignments.assignment2;

// Mengimpor kelas ArrayList dan Scanner dari paket java.util yang diperlukan untuk penggunaan ArrayList dan input dari pengguna
import java.util.ArrayList;
import java.util.Scanner;

// Deklarasi kelas MainMenu yang akan menjadi tempat utama untuk logika aplikasi
public class MainMenu {
    // Inisialisasi objek Scanner untuk input dari pengguna
    private static final Scanner input = new Scanner(System.in);
    // Deklarasi ArrayList untuk menyimpan daftar restoran dan pengguna
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;


    public static void main(String[] args) {
        // Inisialisasi ArrayList untuk menyimpan daftar restoran dan pengguna
        restoList = new ArrayList<>();
        userList = new ArrayList<>();
        // Inisialisasi user awal
        initUser();

        // Variabel untuk menandai apakah program sedang berjalan
        boolean programRunning = true;
        // Menampilkan header
        printHeader();
        // Loop utama program
        while(programRunning){
            // Menampilkan menu awal
            startMenu();
            // Membaca input dari pengguna
            int command = input.nextInt();
            input.nextLine();
            // Variabel untuk menyimpan pengguna yang sedang login
            User userloggedIn = null;

            // Proses login
            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // TODO: Validasi input login
                // Memeriksa apakah pengguna ada dan mengembalikan pengguna yang sesuai
                userloggedIn = getUser(nama,  noTelp);
                // if(userloggedIn = getUser != null){
                boolean isLoggedIn = true;
                // Memproses login pengguna
                if(userloggedIn==null){
                    continue;
                }

                if(userloggedIn.getRole().equals("Customer")){
                    // Loop menu untuk pengguna tipe Customer
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userloggedIn);
                            case 2 -> handleCetakBill();
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    // Loop menu untuk pengguna tipe Admin
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
            }else if(command == 2){
                // Keluar dari program
                programRunning = false;
            }else{
                // Menampilkan pesan jika perintah tidak dikenali
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        // Menampilkan pesan saat program selesai
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    // Mendapatkan pengguna berdasarkan nama dan nomor telepon
    public static User getUser(String nama, String nomorTelepon){
        // TODO: Implementasi method untuk mendapat user dari userList
        // Melakukan iterasi melalui setiap objek User dalam userList menggunakan enhanced for loop
        for(User user : userList){
            // Memeriksa apakah nama dan nomor telepon dari user saat ini cocok dengan input yang diberikan
            if(user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)){
                // Jika cocok, mencetak pesan selamat datang ke layar dengan nama pengguna dan mengembalikan objek user
                System.out.println("Selamat Datang " + nama + "!");
                return user;
            }
        }
        // Menampilkan pesan jika pengguna tidak ditemukan
        System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
        return null;
    }

    public static void handleBuatPesanan(User userLoggedIn){
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        // Method untuk membuat pesanan oleh pengguna
        System.out.println("---------------Buat Pesanan---------------");
        // Meminta input nama restoran dari pengguna
        while(true){
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
    
        // Mencari restoran berdasarkan nama
        Restaurant restoran = null; // Inisialisasi variabel restoran sebagai null, yang akan digunakan untuk menyimpan hasil pencarian
        for (Restaurant resto : restoList) { // Iterasi melalui setiap objek Restaurant dalam restoList menggunakan enhanced for loop
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) { // Memeriksa apakah nama restoran saat ini cocok dengan namaRestoran yang diberikan (tanpa memperhatikan huruf besar/kecil)
                restoran = resto; // Jika cocok, variabel restoran diisi dengan objek restoran saat ini
                break; // Keluar dari loop karena restoran sudah ditemukan
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
            continue;
        }
    
        // Meminta input jumlah pesanan
        System.out.print("Jumlah Pesanan: ");
        int jumlahPesanan = input.nextInt();
        input.nextLine(); // Membuang newline
    
        // Membuat array untuk menyimpan item pesanan
        Menu[] items = new Menu[jumlahPesanan];
    
        // Meminta input setiap item pesanan
        for (int i = 0; i < jumlahPesanan; i++) {
            String namaItem = input.nextLine();
            // Mencari menu berdasarkan nama
            Menu menu = null; // Inisialisasi variabel menu sebagai null, yang akan digunakan untuk menyimpan hasil pencarian
            for (Menu m : restoran.getMenu()) { // Iterasi melalui setiap objek Menu dalam menu restoran menggunakan enhanced for loop
                if (m.getNamaMakanan().equalsIgnoreCase(namaItem)) { // Memeriksa apakah nama makanan dari menu saat ini cocok dengan namaItem yang diberikan (tanpa memperhatikan huruf besar/kecil)
                    menu = m; // Jika cocok, variabel menu diisi dengan objek menu saat ini
                    items[i] = m; // Memasukkan menu ke dalam array items pada indeks i
                    break; // Keluar dari loop karena menu sudah ditemukan
                }
            }

    
            // Memeriksa apakah menu ditemukan
            if (menu == null) {
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            }
    
            // Menyimpan item pesanan ke dalam array
        }
    
        // Membuat ID pesanan
        String orderID = Order.generateOrderID(namaRestoran, tanggalPemesanan, userLoggedIn.getNomorTelepon());
        // Membuat objek Order
        Order order = new Order(orderID, tanggalPemesanan, 0, restoran, items);
    
        // Menambahkan pesanan ke dalam history pengguna
        userLoggedIn.getOrderHistory().add(order);
    
        System.out.println("Pesanan dengan ID " + orderID + " diterima!");
        break;
    }}
    
    // Method untuk mencetak tagihan pesanan
    public static void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        // Meminta input Order ID dari pengguna
        System.out.print("Masukkan Order ID: ");
        String orderID = input.nextLine();
    
        // Mencari pesanan berdasarkan Order ID
        Order order = null; // Inisialisasi variabel order sebagai null, yang akan digunakan untuk menyimpan hasil pencarian pesanan
        User userLoggedIn = null; // Inisialisasi variabel userLoggedIn sebagai null, yang akan digunakan untuk menyimpan pengguna yang sedang masuk
        for (User user : userList) { // Iterasi melalui setiap objek User dalam userList menggunakan enhanced for loop
            for (Order o : user.getOrderHistory()) { // Iterasi melalui setiap objek Order dalam riwayat pesanan pengguna saat ini menggunakan enhanced for loop
                if (o.getOrderID().equalsIgnoreCase(orderID)) { // Memeriksa apakah ID pesanan saat ini cocok dengan orderID yang diberikan (tanpa memperhatikan huruf besar/kecil)
                    order = o; // Jika cocok, variabel order diisi dengan objek pesanan saat ini
                    userLoggedIn = user; // Variabel userLoggedIn diisi dengan pengguna yang sedang masuk
                    break; // Keluar dari loop karena pesanan sudah ditemukan
                }
            }
            if (order != null) { // Memeriksa apakah pesanan sudah ditemukan
                break; // Jika sudah, keluar dari loop utama
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
        System.out.println("Lokasi Pengiriman: " + userLoggedIn.getLokasi());
        System.out.println("Status Pengiriman: " + (order.isOrderFinished() ? "Finished" : "Not Finished"));
        System.out.println("Pesanan:");
        double totalBiaya = 0;
        // Iterasi melalui setiap objek Menu dalam daftar item pesanan menggunakan enhanced for loop
        for (Menu item : order.getItems()) {
            // Mencetak nama makanan dan harga makanan ke layar dengan format tertentu
            System.out.println("- " + item.getNamaMakanan() + " " + item.getHarga());
            // Menambahkan harga makanan ke totalBiaya
            totalBiaya += item.getHarga();
        }
        int biayaOngkir=0;
        switch (userLoggedIn.getLokasi().toUpperCase()) {
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
                System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
        }
        // Menambahkan biaya ongkir ke totalBiaya
        totalBiaya += biayaOngkir;
        // Mengatur biaya ongkir pesanan menggunakan nilai biayaOngkir
        order.setBiayaOngkir(biayaOngkir);
        // Mencetak biaya ongkir ke layar dengan format tertentu
        System.out.println("Biaya Ongkos Kirim : Rp " + biayaOngkir);
        // Mencetak baris baru
        System.out.println("Total Biaya: Rp " + (int) totalBiaya); // Ini mungkin merupakan kesalahan penulisan. Seharusnya ini seharusnya System.out.println();
    }

    // Method untuk melihat menu restoran
    public static void handleLihatMenu() {
        System.out.println("---------------Lihat Menu---------------");
        // Meminta input nama restoran dari pengguna
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
    
        // Mencari restoran berdasarkan nama
        Restaurant restoran = null; // Inisialisasi variabel restoran sebagai null, yang akan digunakan untuk menyimpan hasil pencarian
        for (Restaurant resto : restoList) { // Iterasi melalui setiap objek Restaurant dalam restoList menggunakan enhanced for loop
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) { // Memeriksa apakah nama restoran saat ini cocok dengan namaRestoran yang diberikan (tanpa memperhatikan huruf besar/kecil)
                restoran = resto; // Jika cocok, variabel restoran diisi dengan objek restoran saat ini
                break; // Keluar dari loop karena restoran sudah ditemukan
            }
        }

    
        // Memeriksa apakah restoran ditemukan
        if (restoran == null) {
            System.out.println("Restoran tidak terdaftar pada system.");
        }
    
        // Menampilkan menu restoran
        System.out.println("Menu Restoran " + restoran.getNama() + ":");
        for (Menu item : restoran.getMenu()) {
            System.out.println("- " + item.getNamaMakanan() + " Rp" + (int) item.getHarga());
        }
    }

    // Method untuk memperbarui status pesanan
    public static void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
        System.out.println("---------------Update Status Pesanan---------------");
    
        // Meminta input Order ID dari pengguna
        System.out.print("Masukkan Order ID: ");
        String orderID = input.nextLine();
        System.out.print("Status: ");
        String status = input.nextLine();
    
        // Mencari pesanan berdasarkan Order ID
        Order order = null;
        // Memeriksa apakah status pesanan adalah "selesai" (tanpa memperhatikan huruf besar/kecil)
        if(status.equalsIgnoreCase("selesai")){
            // Jika status pesanan adalah "selesai", melakukan pencarian pesanan berdasarkan orderID dalam riwayat pesanan setiap pengguna
            for (User user : userList) { // Iterasi melalui setiap objek User dalam userList menggunakan enhanced for loop
                for (Order o : user.getOrderHistory()) { // Iterasi melalui setiap objek Order dalam riwayat pesanan pengguna saat ini menggunakan enhanced for loop
                    if (o.getOrderID().equalsIgnoreCase(orderID)) { // Memeriksa apakah ID pesanan saat ini cocok dengan orderID yang diberikan (tanpa memperhatikan huruf besar/kecil)
                        order = o; // Jika cocok, variabel order diisi dengan objek pesanan saat ini
                        break; // Keluar dari loop karena pesanan sudah ditemukan
                    }
                }
                if (order != null) { // Memeriksa apakah pesanan sudah ditemukan
                    break; // Jika sudah, keluar dari loop utama
                }
            }
        }
    
        // Memeriksa apakah pesanan ditemukan
        if (order == null) {
            System.out.println("Order ID tidak dapat ditemukan.");
            return;
        }
    
        // Memperbarui status pesanan
        order.setOrderFinished(true);
        System.out.println("Status pesanan dengan Order ID " + orderID + " berhasil diupdate.");
    }

    // Method untuk menambahkan restoran baru oleh admin
    public static void handleTambahRestoran(){
    System.out.println("---------------Tambah restaurant---------------");
    System.out.print("Nama: ");
    String namaRestaurant = input.nextLine();

    // Periksa apakah restoran sudah terdaftar sebelumnya
    for (Restaurant restoran : restoList) {
        // Memeriksa apakah nama restoran dari objek restoran saat ini cocok dengan namaRestaurant yang diberikan (tanpa memperhatikan huruf besar/kecil)
        if (restoran.getNama().equalsIgnoreCase(namaRestaurant)) {
            System.out.println("Nama restoran \"" + namaRestaurant + "\" sudah pernah terdaftar. Mohon masukkan nama yang berbeda!");
            break;
        }
    }

    if (namaRestaurant.length() < 4) {
        System.out.println("Nama restaurant tidak valid.");
        return;
    }

    System.out.print("Jumlah Makanan: ");
    int jumlahMakanan = input.nextInt();
    input.nextLine(); // Membersihkan newline di buffer

    Restaurant restaurant = new Restaurant(namaRestaurant);

    if (jumlahMakanan <= 0) {
        System.out.println("Jumlah makanan harus lebih dari 0.");
        return;
    }

    for (int i = 0; i < jumlahMakanan; i++) {
        String makanan = input.nextLine();
        
        // Mencari indeks terakhir dari spasi
        int lastIndex = makanan.lastIndexOf(' ');
        
        // Memeriksa apakah lastIndex adalah -1 (artinya tidak ada spasi ditemukan) atau lastIndex adalah panjang string makanan dikurangi 1 (artinya spasi terakhir ditemukan di akhir string)
        if (lastIndex == -1 || lastIndex == makanan.length() - 1) {
            // Jika kondisi di atas terpenuhi, cetak pesan kesalahan ke layar dan kembalikan kontrol dari metode saat ini
            System.out.println("Format makanan tidak valid.");
            return;
        }

        
        // Mengambil nama makanan (dari awal hingga sebelum spasi terakhir)
        String namaMakanan = makanan.substring(0, lastIndex);
        
        // Mengambil harga makanan (setelah spasi terakhir)
        int hargaMakanan;
        try {
            // Mencoba untuk mengonversi string yang berisi harga makanan (yang berada setelah spasi terakhir) menjadi bilangan bulat
            hargaMakanan = Integer.parseInt(makanan.substring(lastIndex + 1));
        } catch (NumberFormatException e) {
                // Jika konversi gagal (misalnya, harga tidak dalam format bilangan bulat), tangkap NumberFormatException
            System.out.println("Harga makanan harus berupa bilangan bulat.");
            return;
        }
        
        // Membuat objek Menu dengan nama dan harga makanan yang sesuai
        Menu menu = new Menu(namaMakanan, hargaMakanan);
        
        // Menambahkan menu ke dalam restaurant
        restaurant.addMenu(menu);
    }

    // Menambah restaurant ke dalam daftar restaurant
    restoList.add(restaurant);
    System.out.println("Restaurant " + namaRestaurant + " berhasil terdaftar.");
}    

    // Method untuk menghapus restoran oleh admin
    public static void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("---------------Hapus Restoran---------------");
    
        // Meminta input nama restoran dari pengguna
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
    
        // Mencari restoran berdasarkan nama
        Restaurant restoran = null; // Inisialisasi variabel restoran sebagai null, yang akan digunakan untuk menyimpan hasil pencarian
        for (Restaurant resto : restoList) { // Iterasi melalui setiap objek Restaurant dalam restoList menggunakan enhanced for loop
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) { // Memeriksa apakah nama restoran saat ini cocok dengan namaRestoran yang diberikan (tanpa memperhatikan huruf besar/kecil)
                restoran = resto; // Jika cocok, variabel restoran diisi dengan objek restoran saat ini
                break; // Keluar dari loop karena restoran sudah ditemukan
            }
        }
    
        // Memeriksa apakah restoran ditemukan
        if (restoran == null) {
            System.out.println("Restoran tidak terdaftar pada system.");
        }
    
        // Menghapus restoran dari daftar restoran
        restoList.remove(restoran);
    
        System.out.println("Restoran berhasil dihapus.");
    }

    // Method untuk menginisialisasi pengguna awal
    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    // Method untuk menampilkan header program
    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    // Method untuk menampilkan menu awal
    public static void startMenu(){
        System.out.println();
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    // Method untuk menampilkan menu admin
    public static void menuAdmin(){
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    // Method untuk menampilkan menu customer
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
}