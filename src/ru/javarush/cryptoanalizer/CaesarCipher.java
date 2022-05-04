package ru.javarush.cryptoanalizer;

public class CaesarCipher {
    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,\":-!? ";
    private String shiftedAlphabet;
    private static String space = " ";
    private static String serviceSymbols = ".,\":-!?";
    private final int key;

    public CaesarCipher(int key) {
        this.key = key;
        int offset = key;
        if (key >= alphabetLength()) {
            offset = key % alphabetLength();
        }
        shiftedAlphabet = alphabet.substring(offset) + alphabet.substring(0, offset);
    }

    public static int alphabetLength() {
        return alphabet.length();
    }

    public static boolean isSpaceSymbol(int symbol) {
        return space.indexOf(symbol) > -1;
    }

    public static boolean isServiceSymbol(int symbol) {
        return serviceSymbols.indexOf(symbol) > -1;
    }

    public int getKey() {
        return this.key;
    }

    private char crypt(int symbol, String origAlphabet, String shiftedAlphabet) {
        char c = (char) symbol;
        char newChar;
        int idx = origAlphabet.indexOf(Character.toUpperCase(c));
        if (idx != -1) {
            newChar = shiftedAlphabet.charAt(idx);
            if (Character.isLowerCase(c)) {
                newChar = Character.toLowerCase(newChar);
            }
        } else {
            newChar = c;
        }
        return newChar;
    }

    public char encrypt(int symbol) {
        return crypt(symbol, alphabet, shiftedAlphabet);
    }

    public char decrypt(int symbol) {
        return crypt(symbol, shiftedAlphabet, alphabet);
    }
}
