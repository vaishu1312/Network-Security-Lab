public final class Conversion {

    private String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    public static final String hexToBinary(String hex) {
        String bin = "", temp = "";
    
        int n = Integer.parseInt(hex.substring(0, 7), 16);
        bin = Integer.toBinaryString(n);
        if (bin.length() < 28) {
          int l = 28 - bin.length();
          for (int j = 0; j < l; j++) bin = '0' + bin;
        }
    
        n = Integer.parseInt(hex.substring(7, 14), 16);
        temp = Integer.toBinaryString(n);
        if (temp.length() < 28) {
          int l = 28 - temp.length();
          for (int j = 0; j < l; j++) temp = '0' + temp;
        }
        bin += temp;
    
        n = Integer.parseInt(hex.substring(14, 16), 16);
        temp = Integer.toBinaryString(n);
        if (temp.length() < 8) {
          int l = 8 - temp.length();
          for (int j = 0; j < l; j++) temp = '0' + temp;
        }
        bin += temp;
        return bin;
      }
    

    public static final String asciiToBinary(String ascii) {
      String bin_T = "";
      int n = ascii.length();
  
      for (int i = 0; i < n; i++) {
        int val = Integer.valueOf(ascii.charAt(i));
        String bin = "";
        while (val > 0) {
          if (val % 2 == 1) bin += '1'; else bin += '0';
          val /= 2;
        }
        bin = reverse(bin);
        if (bin.length() < 8) {
          int l = 8 - bin.length();
          for (int j = 0; j < l; j++) bin = '0' + bin;
        }
        bin_T += bin + "";
      }
      return bin_T;
    }
  
    public static final String reverse(String input) {
      char[] a = input.toCharArray();
      int l, r = 0;
      r = a.length - 1;
  
      for (l = 0; l < r; l++, r--) {
        char temp = a[l];
        a[l] = a[r];
        a[r] = temp;
      }
      return String.valueOf(a);
    }
  
    public static final int binaryToDecimal(String binaryStr) {
      return Integer.parseInt(binaryStr,2);
    }
  
    public static final String binaryToHex(String binary) {
      String hexStr = "";
      for (int i = 0; i < binary.length(); i = i + 4) {
        int decimal = Integer.parseInt(binary.substring(i, i + 4), 2);
        hexStr = hexStr + Integer.toString(decimal, 16);
        //Integer.toHexString()
      }
      return hexStr;
    }
  
    public static final String binaryToAscii(String binary) {
      String res = "";
      for (int i = 0; i < binary.length(); i = i + 8) {
        char ch = (char) binaryToDecimal(binary.substring(i, i + 8));
        res += ch;
      }
      return res;
    }
  }
  