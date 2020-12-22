import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;

public class CaesarCipher {

    public static String encrypt(String plaintext, int key) {
        String result = "";
        for (int i = 0; i < plaintext.length(); i++) {
            if (Character.isUpperCase(plaintext.charAt(i))) {
                char ch = (char) (( plaintext.charAt(i) - 65 + key) % 26 + 65);
                result += ch;
            } else {
                char ch = (char) (( plaintext.charAt(i) - 97 + key) % 26 + 97);
                result += ch;
            }
        }
        return result;
    }

    public static String decrypt(String ciphertext, int key) {
        String result = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            if (Character.isUpperCase(ciphertext.charAt(i))) {
                char ch = (char) (( ciphertext.charAt(i) - 65 - key + 26) % 26 + 65);
                result += ch;
            } else {
                char ch = (char) (( ciphertext.charAt(i) - 97 - key + 26) % 26 + 97);
                result += ch;
            }
        }
        return result;
    }

    public static boolean validateString(String str) {
        str = str.toLowerCase();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateKey(int key) {
        return (key >= 0 && key <= 25);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] dictionary = { "hello", "zebra", "giraffe","lion","tiger","pen", "jug" };
        HashSet<String> dict = new HashSet<String>(Arrays.asList(dictionary));

        System.out.println("\nENCRYPTION");
        System.out.println("**********");

        System.out.print("\nEnter the Plain Text: ");
        String plaintext = sc.next();
        while (!validateString(plaintext)) {
            System.out.println("\nPlain text can contain only alphabets");
            System.out.print("\nEnter the Plain Text: ");
            plaintext = sc.next();
        }
        int key = -1;
        System.out.print("\nEnter encryption key: ");
        String k = sc.next();

        while (!(validateKey(Integer.parseInt(k)))) {
            System.out.println("\nInvalid key");
            System.out.print("\nEnter encryption  key: ");
            k = sc.next();
        }

        key = Integer.parseInt(k);

        System.out.println("\nCipher text is : " + encrypt(plaintext, key));

        System.out.println("\nDECRYPTION");
        System.out.println("**********");

        System.out.print("\nEnter the Cipher Text: ");
        String ciphertext = sc.next();
        while (!validateString(ciphertext)) {
            System.out.println("\nCipher text can only contain  alphabets");
            System.out.print("\nEnter the Cipher Text: ");
            ciphertext = sc.next();
        }
        key = -1;
        System.out.print("\nEnter decryption key: ");
        k = sc.next();

        while (!(validateKey(Integer.parseInt(k)))) {
            System.out.println("\nInvalid key");
            System.out.print("\nEnter decryption  key: ");
            k = sc.next();
        }

        key = Integer.parseInt(k);

        System.out.println("\nPlain text is : " + decrypt(ciphertext, key));

        System.out.println("\nCRYPT-ANALYSIS");
        System.out.println("**************");

        System.out.print("\nEnter the Cipher Text for crypt analysis: ");
        String crypt = sc.next();
        while (!validateString(crypt)) {
            System.out.println("\nCipher text can only contain alphabets");
            System.out.print("\nEnter the Cipher Text for crypt analysis: ");
            crypt = sc.next();
        }
        System.out.print("Key  PlainText");
        System.out.print("\n***  *********");
        String values = "";
        for (int i = 0; i < 26; i++) {
            String res = decrypt(crypt, i);
            System.out.printf("\n%-3d  %s", i, res);
            if (dict.contains(res.toLowerCase())) {
                values += "Key = " + i + " : " + res + "\n";
                break;
            }
        }
        System.out.println("\nThe possible plain text value is: " + values);
        sc.close();
    }
}