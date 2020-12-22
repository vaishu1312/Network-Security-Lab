import java.util.*;

class DesData {
  String plainText = new String();
  String cipherText = new String();
  String key = new String();
  String keys[] = new String[16];
}

class DoubleDes {
  String plainText = new String();
  String cipherText = new String();
  String key1 = new String();
  String key2 = new String();
  String keys1[] = new String[16];
  String keys2[] = new String[16];
}

class TripleDes {
  String plainText = new String();
  String cipherText = new String();
  String key1 = new String();
  String key2 = new String();
  String key3 = new String();
  String keys1[] = new String[16];
  String keys2[] = new String[16];
  String keys3[] = new String[16];
}

class DES {
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

  String[] generateKeys(String key) {
    String keys[] = new String[16];
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
    return keys;
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
    // swapping
    return right + left;
  }

  void tripleEncrypt() {
    System.out.println("\nTRIPLE DES ENCRYPTION");
    System.out.println("**********************");
    TripleDes des3Data = new TripleDes();
    System.out.print("\nEnter the plainText of (in ASCII): ");
    des3Data.plainText = sc.nextLine();
    des3Data.plainText = Conversion.asciiToBinary(des3Data.plainText);
    if (des3Data.plainText.length() % Constants.PT_LENGTH != 0) {
      des3Data.plainText = stuffPlainText(des3Data.plainText);
      System.out.println(
        "\nPlain text after bit stuffing  (in hex) : " +
        Conversion.binaryToHex(des3Data.plainText).toUpperCase() +
        "\n"
      );
    }
    System.out.print("\nEnter the first key of length 8 (in ASCII): ");
    des3Data.key1 = sc.next();
    sc.nextLine();
    while (!validateKey(des3Data.key1)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des3Data.key1 = sc.next();
      sc.nextLine();
    }
    des3Data.keys1 = generateKeys(des3Data.key1);
    des3Data.cipherText =
      Conversion.binaryToHex(DesEncrypt(des3Data.plainText, des3Data.keys1));
    System.out.println(
      "\nThe intermediate cipher text is (in hex): " +
      des3Data.cipherText.toUpperCase()
    );

    System.out.print("\nEnter the second key of length 8 (in ASCII): ");
    des3Data.key2 = sc.next();
    sc.nextLine();
    while (!validateKey(des3Data.key2)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des3Data.key2 = sc.next();
      sc.nextLine();
    }
    des3Data.keys2 = generateKeys(des3Data.key2);
    des3Data.cipherText = DesDecrypt(des3Data.cipherText, des3Data.keys2);
    System.out.println(
      "\nThe intermediate reverse cipher text is (in hex): " +
      Conversion.binaryToHex(des3Data.cipherText).toUpperCase()
    );

    System.out.print("\nEnter the third key of length 8 (in ASCII): ");
    des3Data.key3 = sc.next();
    while (!validateKey(des3Data.key3)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des3Data.key3 = sc.next();
    }
    des3Data.keys3 = generateKeys(des3Data.key3);

    des3Data.cipherText =
      Conversion.binaryToHex(DesEncrypt(des3Data.cipherText, des3Data.keys3));
    System.out.println(
      "\nThe cipher text is (in hex): " + des3Data.cipherText.toUpperCase()
    );
  }

  void tripleDecrypt() {
    System.out.println("\nTRIPLE DES DECRYPTION");
    System.out.println("**********************");
    TripleDes des3Data = new TripleDes();
    System.out.print("\nEnter the cipherText (in hex): ");
    des3Data.cipherText = sc.next();
    sc.nextLine();
    while (!validateCipherText(des3Data.cipherText)) {
      System.out.println("\nInvalid cipher text length");
      System.out.print("\nEnter the cipherText (in hex): ");
      des3Data.cipherText = sc.next();
      sc.nextLine();
    }
    System.out.print("\nEnter the key of length 8 (in ASCII): ");
    des3Data.key1 = sc.next();
    sc.nextLine();
    while (!validateKey(des3Data.key1)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des3Data.key1 = sc.next();
      sc.nextLine();
    }
    des3Data.keys1 = generateKeys(des3Data.key1);
    des3Data.plainText = DesDecrypt(des3Data.cipherText, des3Data.keys1);
    System.out.println(
      "\nThe intermediate plain text is (in hex): " +
      Conversion.binaryToHex(des3Data.plainText).toUpperCase()
    );

    System.out.print("\nEnter the second key of length 8 (in ASCII): ");
    des3Data.key2 = sc.next();
    sc.nextLine();
    while (!validateKey(des3Data.key2)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des3Data.key2 = sc.next();
      sc.nextLine();
    }
    des3Data.keys2 = generateKeys(des3Data.key2);
    des3Data.plainText =
      Conversion.binaryToHex(DesEncrypt(des3Data.plainText, des3Data.keys2));
    System.out.println(
      "\nThe intermediate reverse plain text is (in hex): " +
      des3Data.plainText.toUpperCase()
    );

    System.out.print("\nEnter the third key of length 8 (in ASCII): ");
    des3Data.key3 = sc.next();
    sc.nextLine();
    while (!validateKey(des3Data.key3)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des3Data.key3 = sc.next();
      sc.nextLine();
    }
    des3Data.keys3 = generateKeys(des3Data.key3);
    des3Data.plainText =
      Conversion.binaryToAscii(DesDecrypt(des3Data.plainText, des3Data.keys3));
    System.out.println(
      "\nThe plain text is (in ASCII): " + des3Data.plainText.toUpperCase()
    );
  }

  void doubleEncrypt() {
    System.out.println("\nDOUBLE DES ENCRYPTION");
    System.out.println("**********************");
    DoubleDes des2Data = new DoubleDes();
    System.out.print("\nEnter the plainText of (in ASCII): ");
    des2Data.plainText = sc.nextLine();
    des2Data.plainText = Conversion.asciiToBinary(des2Data.plainText);
    if (des2Data.plainText.length() % Constants.PT_LENGTH != 0) {
      des2Data.plainText = stuffPlainText(des2Data.plainText);
      System.out.println(
        "\nPlain text after bit stuffing  (in hex) : " +
        Conversion.binaryToHex(des2Data.plainText).toUpperCase() +
        "\n"
      );
    }
    System.out.print("\nEnter the first key of length 8 (in ASCII): ");
    des2Data.key1 = sc.next();
    sc.nextLine();
    while (!validateKey(des2Data.key1)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des2Data.key1 = sc.next();
      sc.nextLine();
    }
    des2Data.keys1 = generateKeys(des2Data.key1);
    des2Data.cipherText = DesEncrypt(des2Data.plainText, des2Data.keys1);
    System.out.println(
      "\nThe intermediate cipher text is (in hex): " +
      Conversion.binaryToHex(des2Data.cipherText).toUpperCase()
    );

    System.out.print("\nEnter the second key of length 8 (in ASCII): ");
    des2Data.key2 = sc.next();
    sc.nextLine();
    while (!validateKey(des2Data.key2)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des2Data.key2 = sc.next();
      sc.nextLine();
    }
    des2Data.keys2 = generateKeys(des2Data.key2);
    des2Data.cipherText =
      Conversion.binaryToHex(DesEncrypt(des2Data.cipherText, des2Data.keys2));
    System.out.println(
      "\nThe cipher text is (in hex): " + des2Data.cipherText.toUpperCase()
    );
  }

  void doubleDecrypt() {
    System.out.println("\nDOUBLE DES DECRYPTION");
    System.out.println("**********************");
    DoubleDes des2Data = new DoubleDes();
    System.out.print("\nEnter the cipherText (in hex): ");
    des2Data.cipherText = sc.next();
    sc.nextLine();
    while (!validateCipherText(des2Data.cipherText)) {
      System.out.println("\nInvalid cipher text length");
      System.out.print("\nEnter the cipherText (in hex): ");
      des2Data.cipherText = sc.next();
      sc.nextLine();
    }
    System.out.print("\nEnter the key of length 8 (in ASCII): ");
    des2Data.key1 = sc.next();
    sc.nextLine();
    while (!validateKey(des2Data.key1)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des2Data.key1 = sc.next();
      sc.nextLine();
    }
    des2Data.keys1 = generateKeys(des2Data.key1);
    des2Data.plainText =
      Conversion.binaryToHex(DesDecrypt(des2Data.cipherText, des2Data.keys1));
    System.out.println(
      "\nThe intermediate plain text is (in hex): " +
      des2Data.plainText.toUpperCase()
    );

    System.out.print("\nEnter the second key of length 8 (in ASCII): ");
    des2Data.key2 = sc.next();
    sc.nextLine();
    while (!validateKey(des2Data.key2)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      des2Data.key2 = sc.next();
      sc.nextLine();
    }
    des2Data.keys2 = generateKeys(des2Data.key2);
    des2Data.plainText =
      Conversion.binaryToAscii(DesDecrypt(des2Data.plainText, des2Data.keys2));
    System.out.println(
      "\nThe plain text is (in ASCII): " + des2Data.plainText.toUpperCase()
    );
  }

  void encrypt() {
    System.out.println("\nENCRYPTION");
    System.out.println("**********");
    DesData desData = new DesData();
    System.out.print("\nEnter the plainText of (in ASCII): ");
    desData.plainText = sc.nextLine();
    desData.plainText = Conversion.asciiToBinary(desData.plainText);
    if (desData.plainText.length() % Constants.PT_LENGTH != 0) {
      desData.plainText = stuffPlainText(desData.plainText);
      System.out.println(
        "\nPlain text after bit stuffing  (in hex) : " +
        Conversion.binaryToHex(desData.plainText).toUpperCase() +
        "\n"
      );
    }
    System.out.print("\nEnter the key of length 8 (in ASCII): ");
    desData.key = sc.next();
    sc.nextLine();
    while (!validateKey(desData.key)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      desData.key = sc.next();
      sc.nextLine();
    }
    desData.keys = generateKeys(desData.key);
    desData.cipherText =
      Conversion.binaryToHex(DesEncrypt(desData.plainText, desData.keys));
    System.out.println(
      "\nThe cipher text is (in hex): " + desData.cipherText.toUpperCase()
    );
  }

  String DesEncrypt(String plainText, String keys[]) { //pt,ct and keys in binary 
    String cipher = "";
    int k = plainText.length();
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
      //cipherBlock = Conversion.binaryToHex(cipherBlock);
      cipher += cipherBlock;
    }
    return cipher;
  }

  void decrypt() {
    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    DesData desData = new DesData();
    System.out.print("\nEnter the cipherText (in hex): ");
    desData.cipherText = sc.next();
    sc.nextLine();
    while (!validateCipherText(desData.cipherText)) {
      System.out.println("\nInvalid cipher text length");
      System.out.print("\nEnter the cipherText (in hex): ");
      desData.cipherText = sc.next();
      sc.nextLine();
    }
    System.out.print("\nEnter the key of length 8 (in ASCII): ");
    desData.key = sc.next();
    sc.nextLine();
    while (!validateKey(desData.key)) {
      System.out.println("\nInvalid key");
      System.out.print("\nEnter the key: ");
      desData.key = sc.next();
      sc.nextLine();
    }
    desData.keys = generateKeys(desData.key);
    desData.plainText =
      Conversion.binaryToAscii(DesDecrypt(desData.cipherText, desData.keys));
    System.out.println(
      "\nThe plain text is (in ASCII): " + desData.plainText.toUpperCase()
    );
  }

  String DesDecrypt(String cipherText, String keys[]) { //ct in hex, keys,pt  in binary  
    String plain = "";
    int k = cipherText.length();
    String plainBlock, initial_perm = "";

    for (int i = 0; i < k; i = i + Constants.CT_LENGTH) {
      plainBlock = cipherText.substring(i, i + Constants.CT_LENGTH);

      plainBlock = Conversion.hexToBinary(plainBlock);
      //initial permutation
      plainBlock = permutation(Constants.IP, plainBlock);
      // 16-rounds
      for (int j = 15; j > -1; j--) {
        plainBlock = round(plainBlock, keys[j], 15 - j);
      }
      // 32-bit swap
      plainBlock = plainBlock.substring(32, 64) + plainBlock.substring(0, 32);
      plainBlock = permutation(Constants.FP, plainBlock);
      //plainBlock = Conversion.binaryToAscii(plainBlock);
      plain += plainBlock;
    }
    return plain;
  }

  boolean validateKey(String key) {
    return key.length() == Constants.KEY_LENGTH;
  }

  String stuffPlainText(String plainText) {
    int l = plainText.length();
    if (l < Constants.PT_LENGTH) {
      for (int j = 0; j < Constants.PT_LENGTH - l; j++) plainText =
        plainText + '0';
    } else {
      for (
        int j = 0;
        j < Constants.PT_LENGTH - (l % Constants.PT_LENGTH);
        j++
      ) plainText = plainText + '0';
    }
    return plainText;
  }

  boolean validateCipherText(String str) {
    return (str.length() % Constants.CT_LENGTH == 0);
  }

  public static void main(String args[]) {

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
        System.out.print("\nEnter the key of length 8 (in ASCII): ");
        String key = sc.next();
        while (!des.validateKey(key)) {
          System.out.println("\nInvalid key");
          System.out.print("\nEnter the key: ");
          key = sc.next();
        }
        String keys[] = des.generateKeys(key);
      } else if (choice == 2) {
        des.encrypt();
      } else if (choice == 3) {
        des.decrypt();
      } else if (choice == 4) {
        System.out.println("\nDouble DES");
        System.out.println("------------");
        des.doubleEncrypt();
        des.doubleDecrypt();
      } else if (choice == 5) {
        System.out.println("\nTriple DES");
        System.out.println("------------");
        des.tripleEncrypt();
        des.tripleDecrypt();
      } else {
        break;
      }
    }
    sc.close();
  }
}
