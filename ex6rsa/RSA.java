import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class RSA {
  private BigInteger p;
  private BigInteger q;
  private BigInteger N;
  private BigInteger phi;
  private BigInteger e;
  private BigInteger d;
  private int bitlength = 1024;
  private Random r;

  public RSA() {
    r = new Random();
    p = BigInteger.probablePrime(bitlength, r);
    q = BigInteger.probablePrime(bitlength, r);
    N = p.multiply(q);
    phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    e = BigInteger.probablePrime(bitlength / 2, r);
    while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)    //gcd(e,phi)=1 and 1<e<phi
    {
        e.add(BigInteger.ONE);
    }
    d = e.modInverse(phi);
  }

  private static String bytesToString(byte[] message) {
    String str = "";
    for (byte b : message) {
      str += Byte.toString(b) + " ";
    }
    return str;
  }

  public byte[] encrypt(byte[] message) {
    BigInteger res = new BigInteger(message).modPow(e, N);
    System.out.println("\nThe cipher text is (in Big Integer) " + res);
    return res.toByteArray();
  }

  public byte[] decrypt(byte[] message) {
    BigInteger res = new BigInteger(message).modPow(d, N);
    System.out.println("\nThe plain text is (in Big Integer) " + res);
    return res.toByteArray();
  }

  public static void main(String[] args) throws IOException {
    RSA rsa = new RSA();
    Scanner sc = new Scanner(System.in);
    String plain, cipher;
    System.out.println("\nKey Generation");
    System.out.println("\nP is (in Big Integer) " + rsa.p);
    System.out.println("\nQ is (in Big Integer) " + rsa.q);
    System.out.println("\nN is (in Big Integer) " + rsa.N);
    System.out.println("\nPHI (N) is (in Big Integer) " + rsa.phi);
    System.out.println("\ne is (in Big Integer) " + rsa.e);
    System.out.println("\nThe private key 'd' is (in Big Integer) " + rsa.d);    

    System.out.println("\nEncryption");
    System.out.println("**********");
    System.out.print("\nEnter the plain text: ");
    plain = sc.nextLine();

    System.out.println(
      "\nThe plain text is (in Big Integer) " + new BigInteger(plain.getBytes())
    );
    System.out.println(
      "\nThe plain text is (in Base64) " +
      Base64.getEncoder().encodeToString(plain.getBytes())
    );

    byte[] cipherB = rsa.encrypt(plain.getBytes());

    System.out.println(
      "\nCipher Text is (in Base 64): " +
      Base64.getEncoder().encodeToString(cipherB)
    );

    System.out.println("\nDecryption");
    System.out.println("**********");
    System.out.print("\nEnter the cipher text (in Base64): ");
    cipher = sc.nextLine();

    cipherB = Base64.getDecoder().decode(cipher);
    System.out.println(
      "\nThe cipher text is (in Big Integer) " + new BigInteger(cipherB)
    );

    byte[] plainB = rsa.decrypt(cipherB);

    System.out.println(
      "\nPlain Text is (in Base 64): " +
      Base64.getEncoder().encodeToString(plainB)
    );

    System.out.println("\nPlain Text is: " + new String(plainB));
  }
}
