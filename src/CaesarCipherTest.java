import java.io.*;
import java.nio.file.Path;
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
                case "1" -> encrypt();
                case "2" -> decrypt();
                case "3" -> decryptWithForce();
                default -> isExecuting = false;
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
                    char cryptChar = (crypt == CryptEnum.Encrypt) ? cipher.encrypt(symbol) : cipher.decrypt(symbol);
                    writer.write(cryptChar);
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

    private static void decryptWithForce() {
        System.out.println("Choose a file");
        String fileName = new Scanner(System.in).nextLine();

        String newFileName = fileName.substring(0, fileName.length() - 4) + "_decrypted.txt";
        // iterate over possible keys
        for (int i = 0; i < CaesarCipher.alphabetLength(); i++) {
            //calculate the total number of characters in the text and the number of spaces in the text
            int symbolsAmount = 0;
            int spacesAmount = 0;
            int serviceSymbolsAmount = 0;

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(Path.of(newFileName).toString()))) {
                // read encrypted file
                int symbol = bufferedReader.read();
                // create decryptor with a key
                CaesarCipher cipher = new CaesarCipher(i);
                while (symbol != -1) {
                    symbolsAmount++;
                    // find decrypted symbol and write into new file
                    char decryptChar = cipher.decrypt(symbol);
                    writer.write(decryptChar);
                    // check if this symbol is a space symbol
                    if (CaesarCipher.isSpaceSymbol(decryptChar)) {
                        spacesAmount++;
                    }
                    // check if this symbol is a service symbol
                    if (CaesarCipher.isServiceSymbol(decryptChar)) {
                        serviceSymbolsAmount++;
                    }
                    // read further
                    symbol = bufferedReader.read();
                }
                writer.flush();
                // calculate is it time to end the iteration
                int checkResult = 0;
                if (spacesAmount > 0) {
                    checkResult = (symbolsAmount - spacesAmount - serviceSymbolsAmount) / (spacesAmount + 1);
                }

                if (checkResult > 3 && checkResult < 7) {
                    System.out.println("Key = " + cipher.getKey());
                    break;
                }
            } catch (IOException e) {
                System.out.println("Error appears while trying to write into file");
            }
        }
    }
}
