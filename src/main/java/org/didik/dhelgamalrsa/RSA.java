package org.didik.dhelgamalrsa;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * RSA implementation
 * @author didiksupriadi41
 *
 */
public class RSA {
	private final static BigInteger one      = new BigInteger("1");
	private final static SecureRandom random = new SecureRandom();
	private final static int N               = 1024; 
	
	private BigInteger modulus;
	public BigInteger privateKey;
	public BigInteger publicKey;
	
	RSA() {
		BigInteger p = BigInteger.probablePrime(N/2, random);
		BigInteger q = BigInteger.probablePrime(N/2, random);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

		modulus    = p.multiply(q);
		publicKey  = new BigInteger("65537");
		privateKey = publicKey.modInverse(phi);
		
		try {
			File pub = new File("rsa.pub");
			File pri = new File("rsa.pri");
			pub.createNewFile();
			pri.createNewFile();
			
			PrintWriter pw1 = new PrintWriter(pub);
			pw1.println(publicKey);
			pw1.flush();
			pw1.close();
			
			PrintWriter pw2 = new PrintWriter(pri);
			pw2.println(privateKey);
			pw2.flush();
			pw2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	BigInteger encrypt(BigInteger message) {
		long time = System.nanoTime();
		BigInteger encrypted = message.modPow(publicKey, modulus);
		long now = System.nanoTime() - time;
		System.out.println("  >Time: " + now + " ns<");
		
		try {
			File cipher = new File("cipher-rsa.txt");
			cipher.createNewFile();
			
			PrintWriter pw = new PrintWriter(cipher);
			pw.println(encrypted);
			pw.flush();
			pw.close();
			System.out.println("  >Size: " + cipher.length() + "<");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encrypted;
	}

	BigInteger decrypt(BigInteger encrypted) {
		long time = System.nanoTime();
		BigInteger message = encrypted.modPow(privateKey, modulus);
		long now = System.nanoTime() - time;
		System.out.println("  >Time: " + now + " ns<");
		
		return message;
	}
}
