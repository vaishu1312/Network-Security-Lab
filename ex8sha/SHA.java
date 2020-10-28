public class SHA{
    String message;
    byte[] msgArr;
    String A = "67452301";
    String B = "efcdab89";
    String C = "98badcfe";
    String D = "10325476";
    String E = "c3d2e1f0";   

    String kArr[]={"5A827999","6ED9EBA1","8F1BBCDC","CA62C1D6"};

    SHA(){
        //A=Integer.toBinaryString(Integer.parseInt(A,16));
        A=hexToBin(A);
        B=hexToBin(B);
        C=hexToBin(C);
        D=hexToBin(D);
        E=hexToBin(E);     
    }

    void compression(){
        A=E+func(t)+(A<< 5)+Wt+K t;
        B=A;
        C=B<<30;
        D=C;
        E=D;
    }

    String func(int t){
        String res="";
        if(t <= 19){
            //(B AND C) OR ((NOT B) AND D)
            for(int i=0;i<32;i++){
                if(B.charAt(i)=='1' && C.charAt(i)=='1'){
                    res+="1";
                }
                else if(D.charAt(i)=='1' && B.charAt(i)=='0'){
                    res+="1";
                }
                else
                    res+="0";
            }
        }
        else if ((t>=20 && t<= 39) || (t>=60 && t<= 79)){
           // B XOR C XOR D
           for(int i=0;i<32;i++){
            if(B.charAt(i)!=C.charAt(i)){
                if(D.charAt(i)==1)
                    res+="0";
                else
                    res+="1";
            }
            else{
                if(D.charAt(i)==0)
                    res+="0";
                else
                    res+="1";
            }
        }
        }
        else if (t>=40 && t<= 59){
            //(B AND C) OR (B AND D) OR (C AND D)
            for(int i=0;i<32;i++){
                if(B.charAt(i)=='1' && C.charAt(i)=='1'){
                    res+="1";
                }
                else if(B.charAt(i)=='1' && D.charAt(i)=='1'){
                    res+="1";
                }
                else if(C.charAt(i)=='1' && D.charAt(i)=='1'){
                    res+="1";
                }
                else
                    res+="0";
            }
        }   
        return res;     
    }

    public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.println("\nSHA1 HASHING ALGORITHM");
    System.out.println("************************");
    SHA sha = new SHA();

    System.out.print("\nEnter the message: ");
    message=sc.next();
    msgArr = message.getBytes();
    int len =msgArr.size();
    int bitLen=len*8;

    int noOfBlocks=len/64;
    if(len%64!=0){
        noOfBlocks++;
    }

    message=Integer.toBinaryString(Integer.parseInt(message,2));

    if(bitLen%512!=448){
        int diff=448-bitLen%512;
        message=message+"1";
        for(int i=0;i<diff-1;i++){
            message=message+"0";
        }
    }
    else{
        message=message+"1";
        for(int i=0;i<512;i++){
            message=message+"0";
        } 
    }

    String lenStr=Integer.toBinaryString(bitLen);
    if(lenStr.length()<64){
        int diff=64-lenStr.length();
        for(int i=0;i<diff;i++){
            message="0"+message;
        }        
    }    

    sha.compression();
    System.out.println("\nA's private key is (in Big Integer)");
    System.out.println("---------------------\n" + sha.Xa);
    System.out.println("\nA's public key is (in Big Integer)");
    System.out.println("---------------------\n" + sha.Ya);

   
    }
}