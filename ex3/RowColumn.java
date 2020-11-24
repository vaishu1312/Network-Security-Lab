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
    for ( j = 0; j < n_col; j++) {
        System.out.print("--");
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

    System.out.println("\nAfter rearranging the columns using the key, the encryption matrix  is \n");

    for ( j = 0; j < n_col; j++) {
      System.out.print(key.charAt(j)+" ");
      }
    System.out.print("\n");
    for ( j = 0; j < n_col; j++) {
        System.out.print("--");
        }
    System.out.print("\n");
  

    for ( i = 0; i < n_row; i++) {
      for ( j = 0; j < n_col; j++) { 
          k = key.charAt(j) - 48 - 1;
          System.out.print(enc_mat[i][k] + " "); 
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

    int n_row, n_col,i, j,k=0;

    n_row = key.length();

    n_col = cipherText.length() / n_row;
    if (cipherText.length() % n_row>0)
        n_col += 1;

    dec_mat = new char[n_row][n_col];

    System.out.println("\nThe decryption matrix is \n");

    for ( i = 0; i < n_row; i++) {
      System.out.print(key.charAt(i) + "---> ");
      for ( j = 0; j < n_col; j++) {      
        dec_mat[i][j]=cipherText.charAt(k++);  
          System.out.print(dec_mat[i][j] + " "); 
      }
      System.out.print("\n");
    }

    int row;
    k=0;

    System.out.println("\nAfter rearranging the rows using the key, the decryption matrix  is \n");

    for( i=0;i<cipherText.length();i=i+n_col){
        row=key.charAt(k++)-48-1;
        for( j=0;j<n_col;j++)
        {
            dec_mat[row][j]=cipherText.charAt(i+j);            
        }
    }

    for ( i = 0; i < n_row; i++) {
      for ( j = 0; j < n_col; j++) { 
          System.out.print(dec_mat[i][j] + " "); 
      }
      System.out.print("\n");
    }

    plainText = "";

    for ( i = 0; i < n_col; i++) {
        for ( j = 0; j < n_row; j++) { 
            plainText += dec_mat[j][i]; 
        }
      }    

    System.out.println("\nThe plain text is " + plainText+"\n");
  }


  void decrypt2() {

    int n_row, n_col,i, j,k=0;

    n_col = key.length();

    n_row = cipherText.length() / n_col;
    if (cipherText.length() % n_col>0)
        n_row += 1;

    dec_mat = new char[n_row][n_col];
    int col;

    for( i=0;i<cipherText.length();i=i+n_row){
        col=key.charAt(k++)-48-1;
        for( j=0;j<n_row;j++)
        {
            dec_mat[j][col]=cipherText.charAt(i+j);
        }
    }

    plainText = "";
    System.out.println("\nThe decryption matrix is \n");

    for ( i = 0; i < n_row; i++) {
      for ( j = 0; j < n_col; j++) {        
          System.out.print(dec_mat[i][j] + " ");      
          plainText += dec_mat[i][j];  
      }
      System.out.print("\n");
    }

    System.out.println("\nThe plain text is " + plainText+"\n");
  }

  boolean validateKey(){
    int len=key.length();
    if(len<=1 || len>=plainText.length() || !key.matches("[1-9]+"))
      return false;
    for(int i=0;i<len;i++){
      if(!key.contains(Integer.toString(i+1)))
        return false;
    }
    return true;
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
    while (!rc.validateKey()) {
        System.out.println("\nInvalid key");
        System.out.print("\nEnter the key: ");
        rc.key = sc.next();
    }
    rc.encrypt();

    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the cipherText: ");
    rc.cipherText = sc.next();
    System.out.print("\nEnter the key: ");
    rc.key = sc.next();
    while (!rc.validateKey()) {
        System.out.println("\nInvalid key");
        System.out.print("\nEnter the key: ");
        rc.key = sc.next();
    }
    rc.decrypt();
    sc.close();
  }
}
