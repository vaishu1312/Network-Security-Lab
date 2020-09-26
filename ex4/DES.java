import java.util.*; 
  
 class DES { 

    String plainText = new String();
  String cipherText = new String();
  String key = new String();
        String keys[] = new String[16];
  
        // xor 2 hexadecimal strings 
        String xor(String a, String b) 
        { 
            // hexadecimal to decimal(base 10) 
            long t_a = Long.parseUnsignedLong(a, 16); 
            // hexadecimal to decimal(base 10) 
            long t_b = Long.parseUnsignedLong(b, 16); 
            // xor 
            t_a = t_a ^ t_b; 
            // decimal to hexadecimal 
            a = Long.toHexString(t_a); 
            // prepend 0's to maintain length 
            while (a.length() < b.length()) 
                a = "0" + a; 
            return a; 
        } 
  

        String permutation(int[] sequence, String input) 
        { 
            String output = ""; 
            input = hextoBin(input); 
            for (int i = 0; i < sequence.length; i++) 
                output += input.charAt(sequence[i] - 1); 
            output = binToHex(output); 
            return output; 
        } 
    
        // s-box lookup 
        String sBox(String input) 
        { 
            String output = ""; 
            input = hextoBin(input); 
            for (int i = 0; i < 48; i += 6) { 
                String temp = input.substring(i, i + 6); 
                int num = i / 6; 
                int row = Integer.parseInt( 
                    temp.charAt(0) + "" + temp.charAt(5), 2); 
                int col = Integer.parseInt( 
                    temp.substring(1, 5), 2); 
                output += Integer.toHexString( 
                    sbox[num][row][col]); 
            } 
            return output; 
        } 
  
        String round(String input, String key, int num) 
        { 
            // fk 
            String left = input.substring(0, 8); 
            String temp = input.substring(8, 16); 
            String right = temp; 
            // Expansion permutation 
            temp = permutation(EP, temp); 
            // xor temp and round key 
            temp = xor(temp, key); 
            // lookup in s-box table 
            temp = sBox(temp); 
            // Straight D-box 
            temp = permutation(P, temp); 
            // xor 
            left = xor(left, temp); 
            System.out.println("Round "
                               + (num + 1) + " "
                               + right.toUpperCase() 
                               + " " + left.toUpperCase() + " "
                               + key.toUpperCase()); 
  
            // swapper 
            return right + left; 
        } 
  
        String encrypt() 
        { 
            int i; 
            // get round keys 
            String keys[] = getKeys(key); 
  
            // initial permutation 
            plainText = permutation(IP, plainText); 
            System.out.println( 
                "After initial permutation: "
                + plainText.toUpperCase()); 
            System.out.println( 
                "After splitting: L0="
                + plainText.substring(0, 8).toUpperCase() 
                + " R0="
                + plainText.substring(8, 16).toUpperCase() + "\n"); 
  
            // 16 rounds 
            for (i = 0; i < 16; i++) { 
                plainText = round(plainText, keys[i], i); 
            } 
  
            // 32-bit swap 
            plainText = plainText.substring(8, 16) 
                        + plainText.substring(0, 8); 
  
            // final permutation 
            plainText = permutation(IP1, plainText); 
            return plainText; 
        } 
  
        String decrypt(String plainText, String key) 
        { 
            int i; 
            // get round keys 
            String keys[] = getKeys(key); 
  
            // initial permutation 
            plainText = permutation(IP, plainText); 
            System.out.println( 
                "After initial permutation: "
                + plainText.toUpperCase()); 
            System.out.println( 
                "After splitting: L0="
                + plainText.substring(0, 8).toUpperCase() 
                + " R0=" + plainText.substring(8, 16).toUpperCase() 
                + "\n"); 
  
            // 16-rounds 
            for (i = 15; i > -1; i--) { 
                plainText = round(plainText, keys[i], 15 - i); 
            } 
  
            // 32-bit swap 
            plainText = plainText.substring(8, 16) 
                        + plainText.substring(0, 8); 
            plainText = permutation(IP1, plainText); 
            return plainText; 
        } 
    
    public static void main(String args[]) 
    { 
               Scanner sc = new Scanner(System.in);
    DES des = new DES();
    System.out.println("\nDATA ENCRYPTION STANDARD - DES");
    System.out.println("\nENCRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the plainText: ");
    des.plainText = sc.next();
    System.out.print("\nEnter the key: ");
    des.key = sc.next();
    // while (!des.validateKey()) {
    //     System.out.println("\nInvalid key");
    //     System.out.print("\nEnter the key: ");
    //     des.key = sc.next();
    // }
    des.encrypt();

    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the cipherText: ");
    des.cipherText = sc.next();
    System.out.print("\nEnter the key: ");
    des.key = sc.next();
    // while (!des.validateKey()) {
    //     System.out.println("\nInvalid key");
    //     System.out.print("\nEnter the key: ");
    //     des.key = sc.next();
    // }
    des.decrypt();      
    } 
} 