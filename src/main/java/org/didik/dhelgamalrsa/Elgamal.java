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
		p = BigInteger.probablePrime(1024, random);
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
}
