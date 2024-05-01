package assignments.assignment3.payment;

public class CreditCardPayment {
    //TODO: implementasikan class yang implement interface di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02; // 2% transaction fee
    private long saldo;

    public CreditCardPayment(long saldo) {
        this.saldo = saldo;
    }

    public long getSaldo() {
        return saldo;
    }

    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    @Override
    public void processPayment(long amount) {
        // Hitung total biaya pembayaran dengan memperhitungkan transaction fee
        double totalAmount = amount + (amount * TRANSACTION_FEE_PERCENTAGE);
        if (totalAmount > saldo) {
            System.out.println("Saldo tidak mencukupi untuk melakukan pembayaran.");
            return;
        }
        // Proses pembayaran
        saldo -= totalAmount;
        System.out.println("Pembayaran sebesar Rp " + amount + " dengan kartu kredit berhasil diproses.");
    }

    // Method untuk menghitung transaction fee
    public long countTransactionFee(long amount) {
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    }
}