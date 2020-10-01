import java.util.*;

class DES {
  String plainText = new String();
  String cipherText = new String();
  String key = new String();
  String keys[] = new String[16];
  static Scanner sc = new Scanner(System.in);

  String leftCircularShift(String input, int numBits) {
    int n = input.length();
    String shifted = "";
    shifted += input.substring(numBits, n);
    shifted += input.substring(0, numBits);
    return shifted;
  }

  String permutation(int[] table, String input) {
    String output = "";
    for (int i = 0; i < table.length; i++) {
      output += input.charAt(table[i] - 1);
    }
    return output;
  }

  void generateKeys() {
    System.out.print("\nEnter the key of length 8 (in ASCII): ");
    //if(sc.hasNext() )
    key = sc.next();
    while (!validateKey()) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      key = sc.next();
    }

    key = Conversion.asciiToBinary(key);
    key = permutation(Constants.PC1, key);
    System.out.println(
      "\nOutput of PC1 (56-bit) in hex is " +
      Conversion.binaryToHex(key).toUpperCase()
    );
    String roundKey = key;
    System.out.println("\nThe round keys (48-bit) in hex are: ");
    for (int i = 0; i < 16; i++) {
      roundKey =
        leftCircularShift(roundKey.substring(0, 28), Constants.shiftBits[i]) +
        leftCircularShift(roundKey.substring(28, 56), Constants.shiftBits[i]);
      keys[i] = permutation(Constants.PC2, roundKey);
      System.out.println(
        "Key " + (i + 1) + ": " + Conversion.binaryToHex(keys[i]).toUpperCase()
      );
    }
  }

  String xor(String a, String b) {
    String ans = "";
    int n = a.length();
    for (int i = 0; i < n; i++) {
      if (a.charAt(i) == b.charAt(i)) ans += "0"; else ans += "1";
    }
    return ans;
  }

  String sBox(String input) {
    String output = "";
    String bin = "";
    for (int i = 0; i < 48; i += 6) {
      String temp = input.substring(i, i + 6);
      int num = i / 6;
      int row = Conversion.binaryToDecimal(
        temp.charAt(0) + "" + temp.charAt(5)
      );
      int col = Conversion.binaryToDecimal(temp.substring(1, 5));
      bin = Integer.toBinaryString(Constants.SBOX[num][row][col]);
      if (bin.length() < 4) {
        int l = 4 - bin.length();
        for (int j = 0; j < l; j++) bin = '0' + bin;
      }
      output += bin;
    }
    return output;
  }

  String round(String input, String key, int r_num) {
    String left = input.substring(0, 32);
    String right = input.substring(32, 64);
    String temp = right;
    // Expansion permutation 32 to 48 bit
    temp = permutation(Constants.EP, temp);
    // xor temp and round key
    temp = xor(temp, key);
    // lookup in s-box table
    temp = sBox(temp);
    // Straight D-box
    temp = permutation(Constants.P, temp);
    // xor
    left = xor(left, temp);
    // System.out.println(
    //   "Round " +
    //   (r_num + 1) +
    //   ": " +
    //   Conversion.binaryToHex(right).toUpperCase() +
    //   " " +
    //   Conversion.binaryToHex(left).toUpperCase()
    // );
    // swapping
    return right + left;
  }

  void encrypt() {
    System.out.println("\nENCRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the plainText of (in ASCII): ");
    plainText = sc.nextLine();
    plainText = Conversion.asciiToBinary(plainText);
    if (plainText.length() % Constants.PT_LENGTH != 0) {
      stuffPlainText();
      System.out.println(
        "\nPlain text after bit stuffing  (in hex) : " +
        Conversion.binaryToHex(plainText).toUpperCase() +
        "\n"
      );
    }
    generateKeys();
    cipherText = DesEncrypt(plainText);
    System.out.println(
      "\nThe cipher text is (in hex): " + cipherText.toUpperCase()
    );
  }

  String DesEncrypt(String plainText) {
    String cipher = "";
    int k = plainText.length() / Constants.PT_LENGTH;
    String cipherBlock, initial_perm = "";

    for (int i = 0; i < k; i = i + Constants.PT_LENGTH) {
      cipherBlock = plainText.substring(i, i + Constants.PT_LENGTH);
      // initial permutation
      cipherBlock = permutation(Constants.IP, cipherBlock);
      initial_perm += Conversion.binaryToHex(cipherBlock).toUpperCase();
      // 16 rounds
      for (int j = 0; j < 16; j++) {
        cipherBlock = round(cipherBlock, keys[j], j);
      }
      // 32-bit swap
      cipherBlock =
        cipherBlock.substring(32, 64) + cipherBlock.substring(0, 32);
      // final permutation
      cipherBlock = permutation(Constants.FP, cipherBlock);
      cipherBlock = Conversion.binaryToHex(cipherBlock);
      cipher += cipherBlock;
    }
    return cipher;
  }

  void decrypt() {
    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the cipherText (in hex): ");
    cipherText = sc.next();
    while (!validateCipherText(cipherText)) {
      System.out.println("\nInvalid cipher text length");
      System.out.print("\nEnter the cipherText (in hex): ");
      cipherText = sc.next();
    }
    generateKeys();
    plainText = DesDecrypt(cipherText);
    System.out.println(
      "\nThe plain text is (in ASCII): " + plainText.toUpperCase()
    );
  }

  String DesDecrypt(String cipherText) {
    String plain = "";
    int k = cipherText.length() / Constants.CT_LENGTH;
    String plainBlock, initial_perm = "";

    for (int i = 0; i < k; i = i + Constants.CT_LENGTH) {
      plainBlock = cipherText.substring(i, i + Constants.CT_LENGTH);

      plainBlock = Conversion.hexToBinary(plainBlock);
      // initial permutation
      plainBlock = permutation(Constants.IP, plainBlock);
      System.out.println(
        "\nOutput of initial permutation IP (in hex) : " +
        Conversion.binaryToHex(plainBlock).toUpperCase()
      );
      // 16-rounds
      for (int j = 15; j > -1; j--) {
        plainBlock = round(plainBlock, keys[j], 15 - j);
      }
      // 32-bit swap
      plainBlock = plainBlock.substring(32, 64) + plainBlock.substring(0, 32);
      plainBlock = permutation(Constants.FP, plainBlock);
      plainBlock = Conversion.binaryToAscii(plainBlock);
      plain += plainBlock;
    }
    return plain;
  }

  boolean validateKey() {
    return key.length() == Constants.KEY_LENGTH;
  }

  void stuffPlainText() {
    int l = plainText.length();
    if (l == Constants.PT_LENGTH) {
      return;
    } else if (l < Constants.PT_LENGTH) {
      for (int j = 0; j < Constants.PT_LENGTH - l; j++) plainText =
        plainText + '0';
    } else {
      for (
        int j = 0;
        j < Constants.PT_LENGTH - (l % Constants.PT_LENGTH);
        j++
      ) plainText = plainText + '0';
    }
  }

  boolean validateCipherText(String str) {
    return (str.length() % Constants.CT_LENGTH == 0);
  }

  public static void main(String args[]) {
    // Scanner sc = new Scanner(System.in);

    DES des = new DES();
    int choice = 0;
    while (true) {
      System.out.println("\nDATA ENCRYPTION STANDARD - DES");
      System.out.println("------------------------------");
      System.out.println("\n1.Key Generation");
      System.out.println("\n2.Encryption");
      System.out.println("\n3.Decryption");
      System.out.println("\n4.Double DES");
      System.out.println("\n5.Triple DES");
      System.out.println("\n6.Exit");
      System.out.print("\nEnter your choice(1/2/3/4/5/6): ");
      choice = DES.sc.nextInt();
      sc.nextLine();

      if (choice == 1) {
        System.out.println("\nKEY - GENERATION");
        System.out.println("*****************");
        des.generateKeys();
      } else if (choice == 2) {
        des.encrypt();
      } else if (choice == 3) {
        des.decrypt();
      } else if (choice == 4) {
        //double des
      } else if (choice == 5) {
        //triple des
      } else {
        break;
      }
    }
    sc.close();
  }
}
