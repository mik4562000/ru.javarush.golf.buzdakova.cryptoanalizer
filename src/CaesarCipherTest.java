import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CaesarCipherTest {
    public static void main(String[] args) {
        boolean isExecuting = true;
        Scanner scanner = new Scanner(System.in);
        while (isExecuting) {
            System.out.println("1 - Encrypt data using the key");
            System.out.println("2 - Decrypt data using the key");
            System.out.println("3 - Decrypt data with the unknown key");
            System.out.println("any other key - Exit");
            String userChoice = scanner.nextLine();
            switch (userChoice) {
                case "1":
                    encrypt();
                    break;
                case "2":
                    decrypt();
                    break;
                case "3":
                    break;
                default:
                    isExecuting = false;
            }
        }
    }

    private static void encrypt() {
        crypt(CryptEnum.Encrypt, "_encrypted.txt");
    }

    private static void decrypt() {
        crypt(CryptEnum.Decrypt, "_decrypted.txt");
    }

    private static void crypt(CryptEnum crypt, String newFileEnding) {
        System.out.println("Choose a file");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            int key = scanner.nextInt();
            String newFileName = fileName.substring(0, fileName.length() - 4) + newFileEnding;

            int symbol = bufferedReader.read();
            CaesarCipher cipher = new CaesarCipher(key);


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Path.of(newFileName).toString()))) {
                while (symbol != -1) {
                    char encryptChar = crypt == CryptEnum.Encrypt? cipher.encrypt(symbol) : cipher.decrypt(symbol);
                    writer.write(encryptChar);
                    symbol = bufferedReader.read();
                }
                writer.flush();
            } catch (IOException ioException) {
                System.out.println("Error appears while trying to write into file");
            }
        } catch (IOException e) {
            System.out.println("Input file is not detected.");
        }
    }
}
