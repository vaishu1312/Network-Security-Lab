import java.util.Scanner;

public class RailFence {

  String plainText = new String();
  String cipherText = new String();
  int depth;

  char[][] enc_mat;
  char[][] dec_mat;

  void encrypt() {

    int len = plainText.length();
    boolean dir_down = false;
    int row = 0, col = 0;

    enc_mat = new char[depth][len];

    for (int i = 0; i < depth; i++)
        for (int j = 0;j < len;j++) 
            enc_mat[i][j] = '\0';

    for (int i = 0; i < len; i++) {

      enc_mat[row][col++] = plainText.charAt(i);
      if (row == 0 || row == depth - 1) 
        dir_down = !dir_down;
      if (dir_down)
       row++;
      else
       row--;

    }

    System.out.println("\nThe encryption matrix is \n");
    for (int i = 0; i < depth; i++){
        for (int j = 0;j < len;j++) {
            if(enc_mat[i][j] == '\0')
                System.out.print("  ");
            else{
            System.out.print(enc_mat[i][j]+" ");
            cipherText+=enc_mat[i][j];
            }
        }
        System.out.print("\n");
    }

    System.out.println("\nThe cipher text is "+cipherText);
} 

void decrypt() 
{ 
    int len = cipherText.length();

    boolean dir_down = false;
    int row = 0, col = 0;

    dec_mat = new char[depth][len];        

    for (int i=0; i < len; i++) 
    { 
        dec_mat[row][col++] = '*'; 

        if (row == 0 || row == depth - 1) 
                  dir_down = !dir_down;
        if (dir_down)
            row++;
        else
            row--;
    } 
  
    int index = 0; 
    System.out.println("\nThe decryption matrix is \n");

    for (int i=0; i<depth; i++) {
        for (int j=0; j<len; j++) {
            if (dec_mat[i][j] == '*' && index<len) {
                dec_mat[i][j] = cipherText.charAt(index++); 
                System.out.print(dec_mat[i][j]+" ");
            }
            else{
                System.out.print("  ");
            }
        }
        System.out.print("\n");
    }
    
    row = 0;
    col = 0; 
    plainText="";

    for (int i=0; i< len; i++) 
    { 
        if (dec_mat[row][col] != '*') 
           plainText+=dec_mat[row][col++];
        
        if (row == 0 || row == depth - 1) 
           dir_down = !dir_down;
        if (dir_down)
          row++;
        else
          row--;
    } 

    System.out.println("\nThe plain text is "+plainText);
    
} 

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    RailFence rf = new RailFence();
    System.out.println("\nRAIL FENCE CIPHER");
    System.out.println("\nENCRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the plainText: ");
    rf.plainText = sc.next();
    System.out.print("\nEnter the depth(key): ");
    rf.depth = sc.nextInt();
    while (rf.depth<=0 || rf.depth>=rf.plainText.length()) {
        System.out.println("\nInvalid key");
        System.out.print("\nEnter the depth(key): ");
        rf.depth = sc.nextInt();
    }
    rf.encrypt();

    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter the cipherText: ");
    rf.cipherText = sc.next();
    System.out.print("\nEnter the depth(key): ");
    rf.depth = sc.nextInt();
    while (rf.depth<=0 || rf.depth>=rf.plainText.length()) {
        System.out.println("\nInvalid key");
        System.out.print("\nEnter the depth(key): ");
        rf.depth = sc.nextInt();
    }
    rf.decrypt();
    sc.close();
  }
}
