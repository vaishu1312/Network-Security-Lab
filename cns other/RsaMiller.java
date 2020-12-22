import java.math.*;
import java.util.Random;
import java.util.Scanner;
class RsaEval{
	BigInteger p,q,n,phi,e,d;
	public RsaEval()
	{
		
		int flag=0;
		while(flag==0)
		{
			p=BigInteger.probablePrime(500,new Random());
			//System.out.println(p);
			flag=1;
			for(int i=0;i<4;i++)
				if(!isPrime(p))
					flag=0;
			//System.out.println(flag);
		}		
		flag=0;
		while(flag==0||p.compareTo(q)==0)
		{
			q=BigInteger.probablePrime(500,new Random());
			flag=1;
			for(int i=0;i<4;i++)
				if(!isPrime(q))
					flag=0;
		}
		n=p.multiply(q);
		phi=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e=BigInteger.probablePrime(100, new Random());
		while(e.gcd(phi).compareTo(BigInteger.ONE)>0 &&e.compareTo(phi)<0)
			e.add(BigInteger.ONE);
		d=e.modInverse(phi);
		System.out.println("d: "+d+"\ne: "+e+"\np: "+p+"\nq: "+q+"\nphi: "+phi);
	}
	private boolean isPrime(BigInteger p) {
		// TODO Auto-generated method stub
		BigInteger a=new BigInteger(400,new Random());
		while(a.compareTo(p)>0||a.compareTo(BigInteger.ZERO)==0)
			a=new BigInteger(400,new Random());
		//System.out.println(a);
		BigInteger pminus=p.subtract(BigInteger.ONE);
		
		int s=pminus.getLowestSetBit();
		BigInteger q=pminus.shiftRight(s);
		BigInteger x=a.modPow(q, p);
		if(x.compareTo(BigInteger.ONE)==0||x.compareTo(p.subtract(BigInteger.ONE))==0)
			return true;
		for(int i=0;i<s-1;i++)
		{
			x=x.multiply(x).mod(p);
			if(x.compareTo(BigInteger.ONE)==0)
				return false;
			if(x.compareTo(p.subtract(BigInteger.ONE))==0)
				return true;
			
		}
		return false;
	}
	
	
	
	byte[]encrypt(String msg)
	{
		byte[]msg_byte=msg.getBytes();
		BigInteger b=new BigInteger(msg_byte);
		return b.modPow(e,n).toByteArray();
	}
	byte[]decrypt(byte[] msg)
	{
		BigInteger b=new BigInteger(msg);
		return b.modPow(d, n).toByteArray();
	}
	String byteToString(byte[]arr)
	{
		String ans="";
		for(byte b:arr)
			ans+=Byte.toString(b);
		return ans;
	}
	
}
public class RsaMiller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter plain text: ");
		String text=sc.nextLine();
		RsaEval rsa=new RsaEval();
		byte enc[]=rsa.encrypt(text);
		byte dec[]=rsa.decrypt(enc);
		System.out.println(rsa.byteToString(enc));
		System.out.println(new String(dec));

	}

}
