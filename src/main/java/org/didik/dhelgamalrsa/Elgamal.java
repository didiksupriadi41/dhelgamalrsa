package org.didik.dhelgamalrsa;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Elgamal implementation
 * @author didiksupriadi41
 *
 */
public class Elgamal {
	private static final SecureRandom random = new SecureRandom();
	
	public BigInteger p;
	public BigInteger g;
	public BigInteger x;
	public BigInteger y;
	
	Elgamal() {
		p = BigInteger.probablePrime(65, random);
		g = new BigInteger("3");
		x = new BigInteger("1234567890");
		y = g.modPow(x, p);
		
		try {
			File pub = new File("elgamal.pub");
			File pri = new File("elgamal.pri");
			pub.createNewFile();
			pri.createNewFile();
			
			PrintWriter pw1 = new PrintWriter(pub);
			pw1.println(y);
			pw1.println(g);
			pw1.println(p);
			pw1.flush();
			pw1.close();
			
			PrintWriter pw2 = new PrintWriter(pri);
			pw2.println(x);
			pw2.println(p);
			pw2.flush();
			pw2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	List<BigInteger> encrypt(BigInteger message) {
		BigInteger k = new BigInteger(64, random);
		
		long time = System.nanoTime();
		BigInteger a = g.modPow(k, p);
		BigInteger b = message.multiply(y.modPow(k, p)).mod(p);
		long now = System.nanoTime() - time;
		System.out.println("  >Time: " + now + " ns<");
		
		List<BigInteger> encrypted = new ArrayList<>();
		encrypted.add(a);
		encrypted.add(b);
		try {
			File cipher = new File("cipher-elgamal.txt");
			cipher.createNewFile();
			
			PrintWriter pw = new PrintWriter(cipher);
			pw.println(encrypted.get(0));
			pw.println(encrypted.get(1));
			pw.flush();
			pw.close();		
			System.out.println("  >Size: " + cipher.length() + "<");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encrypted;
	}
	
	BigInteger decrypt(List<BigInteger> encrypted) {
		BigInteger a = encrypted.get(0);
		BigInteger b = encrypted.get(1);
		
		long time = System.nanoTime();
		BigInteger crmodp = a.modPow(x, p);
		BigInteger d = crmodp.modInverse(p);
		BigInteger message = d.multiply(b).mod(p);
		long now = System.nanoTime() - time;
		System.out.println("  >Time: " + now + " ns<");
		
		return message;
	}
    
	
//	public static void main(String[] args) throws IOException {
//        BigInteger p, g, c, secretKey;
//        SecureRandom sc = new SecureRandom();
////        secretKey = new BigInteger("12345678901234567890");
//        secretKey = new BigInteger("1751");
//        //
//        // public key calculation
//        //
//        System.out.println("secretKey = " + secretKey);
////        p = BigInteger.probablePrime(64, sc);
////        g = new BigInteger("3");
////        c = g.modPow(secretKey, p);
//        p = new BigInteger("2357");
//        g = new BigInteger("2");
//        c = g.modPow(secretKey, p);
//        System.out.println("p = " + p);
//        System.out.println("b = " + g);
//        System.out.println("c = " + c);
//        //
//        // Encryption
//        //
//        System.out.print("Enter your Big Number message -->");
////        String s = "1234";
//        String s = "2035";
//        BigInteger X = new BigInteger(s);
////        BigInteger r = new BigInteger(64, sc);
//        BigInteger r = new BigInteger("1520");
//        BigInteger EC = X.multiply(c.modPow(r, p)).mod(p);
//        BigInteger brmodp = g.modPow(r, p);
//        System.out.println("Plaintext = " + X);
//        System.out.println("r = " + r);
//        System.out.println("EC = " + EC);
//        System.out.println("b^r mod p = " + brmodp);
//        //
//        // Decryption
//        //
//        BigInteger crmodp = brmodp.modPow(secretKey, p);
//        BigInteger d = crmodp.modInverse(p);
//        BigInteger ad = d.multiply(EC).mod(p);
//        System.out.println("\n\nc^r mod p = " + crmodp);
//        System.out.println("d = " + d);
//        System.out.println("Alice decodes: " + ad);
//
//    }
}