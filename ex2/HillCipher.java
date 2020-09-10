import java.util.Scanner;

class HillCipher {
  String plainText = new String();
  String cipherText = new String();
  String key = new String();
  int det;
  int[][] key_mat = new int[3][3];
  int[][] plain_mat = new int[3][1];
  int[][] cipher_mat = new int[3][1];
  int[][] key_inv_mat = new int[3][3];
  int[][] adj = new int[3][3];

  void printPlainMat() {
    System.out.println("\nThe plain text vector is ");
    for (int i = 0; i < 3; i++) {
      plain_mat[i][0] = plainText.charAt(i) - 97;
      System.out.println(plain_mat[i][0]);
    }
  }

  void printCipherMat() {
    System.out.println("\nThe cipher text vector is ");
    for (int i = 0; i < 3; i++) {
      cipher_mat[i][0] = cipherText.charAt(i) - 97;
      System.out.println(cipher_mat[i][0]);
    }
  }

  void printKeyMat() {
    System.out.println("\nThe key matrix is\n ");
    int k = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        key_mat[i][j] = (int) key.charAt(k) - 97;
        System.out.print(key_mat[i][j] + " ");
        k++;
      }
      System.out.println();
    }
  }

  void printKeyInverseMat(int detInv) {
    System.out.println("\nThe inverse of the key matrix is\n ");
    int k = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        key_inv_mat[i][j] = (adj[i][j] * detInv) % 26;
        System.out.print(key_inv_mat[i][j] + " ");
        k++;
      }
      System.out.println();
    }
  }

  void encrypt() {
    System.out.println("\nThe cipher text vector is ");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 1; j++) {
        cipher_mat[i][j] = 0;

        for (int k = 0; k < 3; k++) {
          cipher_mat[i][j] += key_mat[i][k] * plain_mat[k][j];
        }

        cipher_mat[i][j] = cipher_mat[i][j] % 26;
        System.out.print(cipher_mat[i][j] + " ");
      }
      System.out.println();
    }
    // cipherText="";
    for (int i = 0; i < 3; i++) cipherText += (char) (cipher_mat[i][0] + 97);
    System.out.println("\nThe ciphertext is: " + cipherText);
  }

  void decrypt() {
    System.out.println("\nThe plain text vector is ");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 1; j++) {
        plain_mat[i][j] = 0;

        for (int k = 0; k < 3; k++) {
          plain_mat[i][j] += key_inv_mat[i][k] * cipher_mat[k][j];
        }

        plain_mat[i][j] = plain_mat[i][j] % 26;
        System.out.print(plain_mat[i][j] + " ");
      }
      System.out.println();
    }
    plainText = "";
    for (int i = 0; i < 3; i++) plainText += (char) (plain_mat[i][0] + 97);
    System.out.println("\nThe plaintext is: " + plainText);
  }

  public boolean isInvertible() {
    det = findDet(key_mat, 3);
    // System.out.println("det is "+det);
    if (det == 0 || det % 2 == 0 || det % 13 == 0) {
      return false;
    } else {
      return true;
    }
  }

  int modInverse(int a, int m) {
    a = a % m;
    for (int x = 1; x < m; x++) if ((a * x) % m == 1) return x;
    return 1;
  }

  void findAdjoint() {
    int sign = 1;
    int[][] temp = new int[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        getCofactor(key_mat, temp, i, j, 3);
        sign = ((i + j) % 2 == 0) ? 1 : -1;
        adj[j][i] = (sign) * (findDet(temp, 2));
        while (adj[j][i] < 0) {
          adj[j][i] += 26;
        }
      }
    }
  }

  void getCofactor(int mat[][], int temp[][], int p, int q, int n) {
    int i = 0, j = 0;
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < n; col++) {
        if (row != p && col != q) {
          temp[i][j++] = mat[row][col];
          if (j == n - 1) {
            j = 0;
            i++;
          }
        }
      }
    }
  }

  int findDet(int mat[][], int n) {
    int det = 0;
    if (n == 1) return mat[0][0];
    int temp[][] = new int[3][3];
    int sign = 1;
    for (int f = 0; f < n; f++) {
      getCofactor(mat, temp, 0, f, n);
      det += sign * mat[0][f] * findDet(temp, n - 1);
      sign = -sign;
    }
    while (det < 0) {
      det += 26;
    }
    return det % 26;
  }

  public static void main(String[] args) {
    HillCipher hc = new HillCipher();
    Scanner sc = new Scanner(System.in);
    System.out.println("HILL CIPHER");
    System.out.println("\nENCRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter plain text: ");
    hc.plainText = sc.next();
    System.out.print("\nEnter the key: ");
    hc.key = sc.next();
    hc.printKeyMat();
    hc.printPlainMat();
    hc.encrypt();

    System.out.println("\nDECRYPTION");
    System.out.println("**********");
    System.out.print("\nEnter cipher text: ");
    hc.cipherText = sc.next();
    System.out.print("\nEnter the key: ");
    hc.key = sc.next();
    hc.printKeyMat();
    if (!hc.isInvertible()) System.out.println("\nKey is not invertible"); else {
      int detInv = hc.modInverse(hc.det, 26);
      hc.findAdjoint();
      hc.printKeyInverseMat(detInv);
      hc.printCipherMat();
      hc.decrypt();
    }
    sc.close();
  }
}
