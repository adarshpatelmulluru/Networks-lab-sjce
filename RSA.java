import java.nio.charset.StandardCharsets;
import java.util.*;
import java.math.BigInteger;

public class RSA {
    static BigInteger p, q, n, phi, e, d;
    int bitlen = 1024;
    Random r;

    public RSA() {
        r = new Random();
        p = BigInteger.probablePrime(bitlen, r);
        q = BigInteger.probablePrime(bitlen, r);
        n = p.multiply(q);
        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlen / 2, r);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the data to encrypt : ");
        String text = in.nextLine().trim();
        byte []cypher = encrypt(text.getBytes());
        byte []decrypted = decrypt(cypher);
        System.out.println("The cypher text is" + new String(cypher));
        System.out.println("the decrypted text is "+new String(decrypted));

    }
    static byte[] encrypt(byte []message){
        return (new BigInteger(message)).modPow(e,n).toByteArray();
    }
    static byte[] decrypt(byte []message){
        return (new BigInteger(message)).modPow(d,n).toByteArray();
    }
}
