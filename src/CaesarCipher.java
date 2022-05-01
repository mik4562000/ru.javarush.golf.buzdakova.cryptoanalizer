public class CaesarCipher {
    private String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,\":-!? ";
    private String shiftedAlphabet;
    private final int key;

    public CaesarCipher(int key) {
        this.key = key;
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
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
