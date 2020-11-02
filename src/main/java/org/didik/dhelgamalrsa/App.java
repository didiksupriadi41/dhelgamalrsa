package org.didik.dhelgamalrsa;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

/**
 * App for simulation of RSA, Elgamal, & DiffieHellman
 * @author didiksupriadi41
 *
 */
public class App {
    public static void main( String[] args ) {
        // Pembangkitan kunci
    	RSA rsa = new RSA();
        Elgamal elgamal = new Elgamal();
        
        // Kunci
        System.out.println("RSA public key:");
        System.out.println(rsa.publicKey);       
        System.out.println();
        
        System.out.println("RSA private key:");
        System.out.println(rsa.privateKey);  
        System.out.println();
        
        System.out.println("Elgamal public key:");
        System.out.println(elgamal.y + "\n" + elgamal.g + "\n" + elgamal.p);
        System.out.println();
        
        System.out.println("Elgamal private key:");
        System.out.println(elgamal.x + "\n" + elgamal.p);      
        System.out.println();
        
        // Pesan
        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan pesan:");
        String s = sc.next();
        byte[] bytes = s.getBytes();
        BigInteger message = new BigInteger(bytes);
        sc.close();       
        System.out.println();
        
        // Enkripsi
        BigInteger rsaencrypt = rsa.encrypt(message);
        System.out.println("RSA cipher:");
        System.out.println(rsaencrypt);
        System.out.println();
        
        List<BigInteger> elgamalencrypt = elgamal.encrypt(message);
        System.out.println("Elgamal cipher:");
        System.out.println(elgamalencrypt);
        System.out.println();
        
        // Dekripsi
        BigInteger rsadecrypt = rsa.decrypt(rsaencrypt);
        System.out.println("RSA dekripsi:");
        System.out.println(rsadecrypt);
        System.out.println();
        
        BigInteger elgamaldecrypt = elgamal.decrypt(elgamalencrypt);
        System.out.println("Elgamal dekripsi:");
        System.out.println(elgamaldecrypt);
        System.out.println();
        
        // Pembangkitan kunci sesi
        DiffieHellman diffieHellman = new DiffieHellman();
        BigInteger aliceSecretNumber = new BigInteger("36");
        BigInteger bobSecretNumber = new BigInteger("58");
        
        BigInteger aliceMessage = diffieHellman.getAliceMessage(aliceSecretNumber);
        System.out.println("Alice message: " + aliceMessage);
        BigInteger bobMessage = diffieHellman.getBobMessage(bobSecretNumber);
        System.out.println("Bob message: " + bobMessage);
        System.out.println();
        
        System.out.println("Alice's key: " + diffieHellman.aliceCalculationOfKey(bobMessage, aliceSecretNumber));
        System.out.println("Bob's key: " + diffieHellman.bobCalculationOfKey(aliceMessage, bobSecretNumber));
    }
}
