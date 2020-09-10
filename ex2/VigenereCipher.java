import java.util.Scanner;

public class VigenereCipher {

  String plainText = new String();
  String cipherText = new String();
  String key = new String();

  void generateKey() {      
    int diff;
    if(key.length()== plainText.length())
        return;
    else if(key.length()> plainText.length())
    {   key=key.substring(0,plainText.length());
        System.out.println("\nThe modified key is "+key);
        return;
    }
    while (key.length() != plainText.length()) {
      diff = plainText.length() - key.length();
      if (diff >= key.length())
        key += key;
     else 
        key = key + key.substring(0, diff);
    }
    System.out.println("\nThe modified key is "+key);
  }

  void encrypt() {
    for (int i = 0; i < plainText.length(); i++) {
      int x = (plainText.charAt(i)-97 + key.charAt(i)-97) % 26;
      x += 'a';
      cipherText += (char) (x);
    }
  }

  void decrypt() {
    for (int i = 0; i < cipherText.length() && i < key.length(); i++) {
      int x = (cipherText.charAt(i) - key.charAt(i) + 26) % 26;
      //System.out.println("x is "+x);
      x += 'a';
      plainText += (char) (x);
    }
  }

  public static void main(String[] args) {

    VigenereCipher vc = new VigenereCipher();
    Scanner sc = new Scanner(System.in);
    System.out.println("\nVIGENERE CIPHER");
    System.out.println("\nENCRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter plain text: ");
    vc.plainText = sc.next();
    System.out.print("\nEnter the key: ");
    vc.key = sc.next();
    vc.generateKey();
    vc.encrypt();
    System.out.println("\nCipher text is: " + vc.cipherText);

    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter cipher text: ");
    vc.cipherText = sc.next();
    System.out.print("\nEnter the key: ");
    vc.key = sc.next();
    vc.generateKey();
    vc.plainText = "";
    vc.decrypt();
    System.out.println("\nPlain text is: " + vc.plainText);
    sc.close();
  }
}
