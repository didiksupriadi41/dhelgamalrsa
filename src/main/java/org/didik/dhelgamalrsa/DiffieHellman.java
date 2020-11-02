package org.didik.dhelgamalrsa;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * DiffieHellman implementation
 * @author didiksupriadi41
 *
 */
public class DiffieHellman {
	private static final SecureRandom random = new SecureRandom();
	
	private BigInteger n;
	private BigInteger g;
	
	public DiffieHellman() {
		n = BigInteger.probablePrime(65, random);
		g = BigInteger.probablePrime(64, random);
	}
	
	public BigInteger getAliceMessage(BigInteger aliceSecretNumber){
		return g.modPow(aliceSecretNumber, n);	
	}

	public BigInteger getBobMessage(BigInteger bobSecretNumber){
		return g.modPow(bobSecretNumber, n);
	}

	public BigInteger aliceCalculationOfKey
	(BigInteger bobMessage, BigInteger aliceSecretNumber){
		return bobMessage.modPow(aliceSecretNumber, n);
	}

	public BigInteger bobCalculationOfKey
	(BigInteger aliceMessage, BigInteger bobSecretNumber){
		return aliceMessage.modPow(bobSecretNumber, n);
	}
}
