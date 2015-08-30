package org.ohuyo.rapid.base.security;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.ohuyo.commons.crypto.RsaChiper;
import org.springframework.stereotype.Service;

/**
 * RSA服务
 * 
 * @author rabbit
 * 
 */
@Service
public class RsaService {

	private RsaChiper rsaChiper;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException,
			NoSuchProviderException, InvalidKeySpecException, DecoderException {
		rsaChiper = new RsaChiper();
		String p = "22489fb2aa6aabc9b71bbf436cf8644199273d58d4c671d3d9c2dd7df79565196c22fe483b214cd6ac1c5e7104ec2f90ee5ceba3a8d219f545e361ae4b047ceaff1f06a88104254e7d58eaadc4cd8aadf97c199dd95529251d641b0404f7fc15bcc9e534df23d0c15cb44b7f24c76719440ab818f6b1f9ad6b34fe13a9a8e525";
		String m = "008dad38e3d567f17a6bd252cbe1642eb72c3a27087a061447892c1acf4069d6f282465289f97890e475e3360cef920bfede701783320da2fa6dae58744cd144646e9ca95f3c02e7cde67b62e74b68015875db7f7ddbf96060c3e081bb62ca2b229eafac0328aed4bc29bd95032fecad856c700169edc36c4653418dfbf0f66585";
		BigInteger bip = new BigInteger(Hex.decodeHex(p.toCharArray()));
		BigInteger bim = new BigInteger(Hex.decodeHex(m.toCharArray()));
		rsaChiper.setRsaPrivateKey(bim, bip);
		String e = "010001";
		BigInteger bie = new BigInteger(Hex.decodeHex(e.toCharArray()));
		rsaChiper.setRsaPublicKey(bim, bie);
	}

	public synchronized String decrypt(String hex) {
		try {
			byte[] buf = Hex.decodeHex(hex.toCharArray());
			byte[] src = rsaChiper.decryptByPrivateKey(buf);
			String str = new String(src);
			// return DigestUtil.getSHA(str);
			return null;
		} catch (Exception e) {
			throw new RuntimeException("decrypt meet error.", e);
		}
	}

	public synchronized String encrypt(String password) {
		try {
			byte[] buf = rsaChiper.encryptByPublicKey(password.getBytes());
			String ret = Hex.encodeHexString(buf);
			return ret;
		} catch (Exception e) {
			throw new RuntimeException("encrypt meet error", e);
		}
	}

	public synchronized byte[] decrypt(byte[] ciphertext) {
		try {
			return rsaChiper.decryptByPrivateKey(ciphertext);
		} catch (Exception e) {
			throw new RuntimeException("decrypt meet error", e);
		}
	}

}
