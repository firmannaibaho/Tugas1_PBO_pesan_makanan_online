import java.util.Scanner;

public class LoginProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String adminUsername = "admin";
        String adminPassword = "admin123";
        String customerUsername = "customer";
        String customerPassword = "customer123";

        System.out.println("Selamat datang di program login!");
        System.out.print("Masukkan username Anda: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password Anda: ");
        String password = scanner.nextLine();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Selamat datang, admin!");
            // Tambahkan kode spesifik untuk admin di sini
        } else if (username.equals(customerUsername) && password.equals(customerPassword)) {
            System.out.println("Selamat datang, customer!");
            // Tambahkan kode spesifik untuk customer di sini
        } else {
            System.out.println("Username atau password tidak valid.");
        }

        scanner.close();
    }
}
