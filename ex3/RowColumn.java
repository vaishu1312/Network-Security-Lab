import java.util.Scanner;

public class RowColumn {
  String plainText = new String();
  String cipherText = new String();
  String key = new String();

  char[][] enc_mat;
  char[][] dec_mat;

  void encrypt() {
    int n_row, n_col,i, j,k=0;

    n_col = key.length();

    n_row = plainText.length() / n_col;
    if (plainText.length() % n_col>0)
        n_row += 1;

    enc_mat = new char[n_row][n_col];

    System.out.println("\nThe encryption matrix is \n");
    for ( j = 0; j < n_col; j++) {
    System.out.print(j+1+" ");
    }
    System.out.print("\n");

    for ( i = 0; i < n_row; i++) {
      for ( j = 0; j < n_col; j++) {
            if(k<plainText.length())
                enc_mat[i][j] = plainText.charAt(k++);
            else
                enc_mat[i][j] = '*'; 
        System.out.print(enc_mat[i][j] + " ");
      }
      System.out.print("\n");
    }

    for (j = 0; j < n_col; j++) {
      k = key.charAt(j) - 48 - 1;
      for (i = 0; i < n_row; i++) {
        cipherText += enc_mat[i][k];
      }
    }
    System.out.println("\nThe cipher text is " + cipherText);
  }


  void decrypt() {
    int len = cipherText.length();

    boolean dir_down = false;
    int row = 0, col = 0;

    dec_mat = new char[depth][len];

    for (int i = 0; i < len; i++) {
      dec_mat[row][col++] = '*';

      if (row == 0 || row == depth - 1) dir_down = !dir_down;
      if (dir_down) row++; else row--;
    }

    int index = 0;
    System.out.println("\nThe decryption matrix is \n");

    for (int i = 0; i < depth; i++) {
      for (int j = 0; j < len; j++) {
        if (dec_mat[i][j] == '*' && index < len) {
          dec_mat[i][j] = cipherText.charAt(index++);
          System.out.print(dec_mat[i][j] + " ");
        } else {
          System.out.print("  ");
        }
      }
      System.out.print("\n");
    }

    row = 0;
    col = 0;
    plainText = "";

    for (int i = 0; i < len; i++) {
      if (dec_mat[row][col] != '*') plainText += dec_mat[row][col++];

      if (row == 0 || row == depth - 1) dir_down = !dir_down;
      if (dir_down) row++; else row--;
    }

    System.out.println("\nThe plain text is " + plainText);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    RowColumn rc = new RowColumn();
    System.out.println("\nROW COLUMN CIPHER");
    System.out.println("\nENCRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the plainText: ");
    rc.plainText = sc.next();
    System.out.print("\nEnter the key: ");
    rc.key = sc.next();
    // while (rc.key<=0 || rc.key>=rc.plainText.length()) {
    //     System.out.println("\nInvalid key");
    //     System.out.print("\nEnter the key: ");
    //     rc.key = sc.next();
    // }
    rc.encrypt();

    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the cipherText: ");
    rc.cipherText = sc.next();
    System.out.print("\nEnter the key: ");
    rc.key = sc.next();
    // while (rc.key<=0 || rc.key>=rc.plainText.length()) {
    //     System.out.println("\nInvalid key");
    //     System.out.print("\nEnter the key: ");
    //     rc.key = sc.nextInt();
    // }
    //rc.decrypt();
    sc.close();
  }
}
