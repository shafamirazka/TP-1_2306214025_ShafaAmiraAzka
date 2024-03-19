package assignments.assignment1;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderGenerator {
    // Inisialisasi objek Scanner untuk menerima input dari pengguna
    private static final Scanner input = new Scanner(System.in);
    // Deklarasi ArrayList untuk menyimpan Order ID yang telah dibuat
    private static ArrayList<String> orderIDs = new ArrayList<>();

    /* 
    Anda boleh membuat method baru sesuai kebutuhan Anda
    Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method yang sudah ada.
    */

    /*
     * Method  ini untuk menampilkan menu
     */

    // Method untuk menampilkan menu aplikasi
    public static void showMenu() {
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     * 
     * @return String Order ID dengan format sesuai pada dokumen soal
     */

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

    // Method utama untuk menjalankan program
    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan

        // Deklarasi variabel untuk menandai apakah program sedang berjalan atau tidak
        boolean running = true;
        // Memanggil method untuk menampilkan menu pada awal program
        showMenu();
        // Melakukan iterasi terus-menerus hingga pengguna memilih untuk keluar
        while (running) {
            System.out.println("--------------------------------------------------");
            System.out.print("Pilihan menu: ");
            // Membaca input menu dari pengguna
            int menu = input.nextInt();
            input.nextLine(); // Membaca newline setelah input angka

            // Menjalankan tindakan sesuai dengan pilihan menu yang dipilih pengguna
            switch (menu) {
                case 1:
                // Memulai proses validasi input dari pengguna untuk nama restoran, tanggal pemesanan, dan nomor telepon
                boolean validRestoran = false; // Inisialisasi variabel validRestoran sebagai false untuk memulai iterasi validasi
                while (!validRestoran) { // Melakukan iterasi selama validRestoran masih false, artinya input belum valid
                    System.out.print("Nama Restoran: ");
                    String namaRestoran = input.nextLine(); // Membaca input nama restoran dari pengguna
                    if (namaRestoran.length() < 4) { // Memeriksa apakah panjang nama restoran kurang dari 4 karakter
                        System.out.println("Nama Restoran tidak valid!"); // Menampilkan pesan kesalahan jika panjang nama restoran tidak memenuhi syarat
                        System.out.println(); // Mencetak baris kosong untuk pemisah
                    } else {
                        validRestoran = true; // Mengubah validRestoran menjadi true karena input nama restoran sudah valid
                        System.out.print("Tanggal Pemesanan: ");
                        String tanggalPemesanan = input.nextLine(); // Membaca input tanggal pemesanan dari pengguna
                        if (tanggalPemesanan.length() != 10 || !tanggalPemesanan.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            // Memeriksa apakah format tanggal pemesanan sesuai dengan format yang diharapkan (DD/MM/YYYY)
                            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!"); // Menampilkan pesan kesalahan jika format tanggal tidak sesuai
                            System.out.println(); // Mencetak baris kosong untuk pemisah
                            validRestoran = false; // Mengubah validRestoran menjadi false karena format tanggal pemesanan tidak valid
                        } else {
                            System.out.print("No. Telepon: ");
                            String noTelepon = input.nextLine(); // Membaca input nomor telepon dari pengguna
                            if (!noTelepon.matches("\\d+") || Long.parseLong(noTelepon) <= 0) {
                                // Memeriksa apakah nomor telepon berisi hanya digit positif
                                System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                                // Menampilkan pesan kesalahan jika nomor telepon tidak sesuai dengan format yang diharapkan
                                System.out.println(); // Mencetak baris kosong untuk pemisah
                                validRestoran = false; // Mengubah validRestoran menjadi false karena nomor telepon tidak valid
                            } else {
                                // Jika semua input sudah valid, menghasilkan Order ID dan menampilkan konfirmasi kepada pengguna
                                String orderID = generateOrderID(namaRestoran, tanggalPemesanan, noTelepon);
                                System.out.println("Order ID " + orderID + " diterima!");
                                System.out.println("--------------------------------------------------");
                                // Menampilkan kembali menu setelah Order ID berhasil dibuat
                                System.out.println("Pilih menu:");
                                System.out.println("1. Generate Order ID");
                                System.out.println("2. Generate Bill");
                                System.out.println("3. Keluar");
                            }
                        }
                    }
                }
                break;
                case 2:
                boolean validOrderID = false;
                while (!validOrderID) { // Perulangan selama Order ID tidak valid
                    // Meminta input Order ID dari pengguna
                    System.out.print("Order ID: ");
                    String orderIDInput = input.nextLine();
                    // Validasi panjang Order ID
                    if (orderIDInput.length() < 16) {
                        System.out.println("Order ID minimal 16 karakter");
                        System.out.println();
                    } else if (!orderIDs.contains(orderIDInput)) { // Validasi keberadaan Order ID dalam daftar Order IDs
                        System.out.println("Silahkan masukkan Order ID yang valid!");
                        System.out.println();
                    } else {
                        validOrderID = true; // Setel flag validOrderID menjadi true jika Order ID valid
                        // Meminta input lokasi pengiriman dari pengguna
                        System.out.print("Lokasi Pengiriman: ");
                        String lokasi = input.nextLine();
                        // Menghasilkan tagihan berdasarkan Order ID dan lokasi pengiriman
                        String bill = generateBill(orderIDInput, lokasi);
                        // Menampilkan tagihan kepada pengguna
                        System.out.println(bill);
                        // Menampilkan kembali menu setelah Bill berhasil dibuat
                        System.out.println("--------------------------------------------------");
                        System.out.println("Pilih menu:");
                        System.out.println("1. Generate Order ID");
                        System.out.println("2. Generate Bill");
                        System.out.println("3. Keluar");
                    }
                }
                break;            
                case 3:
                    // Mengubah variabel running menjadi false untuk keluar dari loop
                    running = false;
                    // Menampilkan pesan terima kasih kepada pengguna
                    System.out.println("Terima kasih telah menggunakan DepeFood!");
                    break;
                default:
                    // Menampilkan pesan kesalahan jika pilihan menu tidak valid
                    System.out.println("Pilihan menu tidak valid!");
            }
        }
        // Menutup objek Scanner setelah penggunaan selesai
        input.close();
    }
}