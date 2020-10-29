package cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Scanner;


public abstract class AbstractAsymetric {
	
	

	/**
	 * Method to sign a plain text
	 * @param plainText data to sign
	 * @param publicKey to encrypt cipher
	 * @return
	 */
	public abstract String encrypt(String plainText, PublicKey publicKey);
	
	/***
	 * Method to verify the signature of 
	 * @param cipherText
	 * @param PrivateKey privateKey to decrypt a cipher
	 * @return
	 */
	public abstract String decrypt(String cipherText, PrivateKey privateKey);
	

	/***
	 * Generate a key pair to use with the signature
	 * @return a key pair
	 */
	public abstract KeyPair generateKeyPair();
	
	
}
