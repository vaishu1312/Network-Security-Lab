import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.math.BigInteger;
import java.util.*;
import java.security.NoSuchAlgorithmException;
  
public class SHA1 { 
    String message;

    public String hash()  throws NoSuchAlgorithmException
    { 
        String res="";

        MessageDigest md = MessageDigest.getInstance("SHA-1"); 
        byte[] messageDigest = md.digest(message.getBytes()); 

        //System.out.println("\nSHA1 Digest of "+ message + "  (in Base64): " +Base64.getEncoder().encodeToString(messageDigest));
        // BigInteger no = new BigInteger(1,messageDigest); 
        // res = no.toString(16); 

        for (byte b : messageDigest) {
           res+= String.format("%02X", b);
        }

        return res; 
    } 

    public static void main(String args[]) throws NoSuchAlgorithmException
    {   
        Scanner sc = new Scanner(System.in);
        System.out.println("\nSHA1 HASHING ALGORITHM");
        System.out.println("************************");
        SHA1 sha1 = new SHA1();

        System.out.print("\nEnter the message: ");
        sha1.message=sc.nextLine();
        String result=sha1.hash();
  
        System.out.println("\nSHA1 Digest of "+ sha1.message + " (in hex): " +result+"\nLength is "+result.length());          
    } 
}