import java.security.KeyPair; 
import java.security.KeyPairGenerator; 
import java.security.PrivateKey; 
import java.security.PublicKey; 
import java.security.SecureRandom; 
import java.security.Signature; 
import java.util.*;
  
//import javax.xml.bind.DatatypeConverter;

public class DSS { 
    String message,sign;
    private static final String SIGNING_ALGORITHM = "SHA256withRSA"; 
    private static final String RSA = "RSA"; 

    public KeyPair Generate_RSA_KeyPair() throws Exception 
    { 
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA); 
        keyPairGenerator.initialize(2048); 
        return keyPairGenerator.generateKeyPair(); 
    } 

    public byte[] Create_Digital_Signature(byte[] msg, PrivateKey prKey) throws Exception 
    { 
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM); 
        signature.initSign(prKey); 
        signature.update(msg); 
        return signature.sign(); 
    } 

    public boolean Verify_Digital_Signature(byte[] msg,byte[] signatureToVerify,PublicKey puKey) throws Exception 
    { 
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM); 
        signature.initVerify(puKey); 
        signature.update(msg); 
        return signature.verify(signatureToVerify); 
    } 

    public static void main(String args[]) throws Exception
    {   
        Scanner sc = new Scanner(System.in);
        System.out.println("\nDIGITAL SIGNATURE STANDARD");
        System.out.println("***************************");
        DSS dss = new DSS();

        System.out.println("\nGeneration of digital signature");
        System.out.println("-------------------------------");
        System.out.print("\nEnter the message: ");
        dss.message=sc.nextLine();

        KeyPair keyPair = dss.Generate_RSA_KeyPair(); 
        byte[] signature = dss.Create_Digital_Signature(dss.message.getBytes(),keyPair.getPrivate());  
        System.out.println("\nDigital Signature is (in Base-64 format):\n\n"+ Base64.getEncoder().encodeToString(signature)); 

        System.out.println("\nVerification of digital signature");
        System.out.println("---------------------------------");
        //sc.next();
        System.out.print("\nEnter the message: ");
        dss.message=sc.nextLine();
        //sc.nextLine();
        System.out.print("\nEnter the signature (in Base-64 format): ");
        dss.sign=sc.next();

        System.out.println("\nThe given digital signature is verified to be "+ dss.Verify_Digital_Signature(dss.message.getBytes(),Base64.getDecoder().decode(dss.sign), keyPair.getPublic()));   
    } 
}
