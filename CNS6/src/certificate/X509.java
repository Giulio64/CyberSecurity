package certificate;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import java.math.BigInteger;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;

public class X509 {

	private String CERTPATH = "/Users/giulioserra/Documents/Universita/CNS6/certificate";

	private final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
	private final String END_CERT = "-----END CERTIFICATE-----";
	private final String LINE_SEPARATOR = System.getProperty("line.separator");

	/***
	 * Generate the X509 certificate
	 * @param keyPair to sign the certificate
	 * @return an X509 certificate
	 */
	public X509Certificate generateCert(KeyPair keyPair){

		try {

			X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
			X500Principal dnName = new X500Principal("cn=example");

			// add some options
			certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
			certGen.setSubjectDN(new X509Name("dc=name"));
			certGen.setIssuerDN(dnName); // use the same
			// yesterday
			certGen.setNotBefore(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
			// in 2 years
			certGen.setNotAfter(new Date(System.currentTimeMillis() + 2 * 365 * 24 * 60 * 60 * 1000));
			certGen.setPublicKey(keyPair.getPublic());
			certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
			certGen.addExtension(X509Extensions.ExtendedKeyUsage, true,
					new ExtendedKeyUsage(KeyPurposeId.id_kp_timeStamping));

			// finally, sign the certificate with the private key of the same KeyPair
			X509Certificate cert = certGen.generate(keyPair.getPrivate(), "BC");
			return cert;
		}catch(Exception e) {
			System.out.println("error creating X509 cert " + e);
			return null;
		}
	}

	/***
	 * Convert a certificate from 
	 * @param cert X509Certificate to convert
	 * @return a string to write as pem certificate
	 */
	private String convertToPem(X509Certificate cert){

		try {

			final Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());

			final byte[] rawCrtText = cert.getEncoded();
			final String encodedCertText = new String(encoder.encode(rawCrtText));
			final String prettified_cert = BEGIN_CERT + LINE_SEPARATOR + encodedCertText + LINE_SEPARATOR + END_CERT;
			return prettified_cert;
		
		}catch(Exception e) {
			System.out.println("Error converting X509 to pem:" + e);
			return null;
		}

	}

	/**
	 * Write a pem certificate in X509 format to directory
	 * @param keyPair pair of keys to aut
	 */
	public void writePemCertificate(KeyPair keyPair) {
		try {
			String toWrite = convertToPem(generateCert(keyPair));
			FileWriter fw = new FileWriter(CERTPATH + ".pem");
			fw.write(toWrite);
			fw.close();
		}catch(Exception e) {
			System.out.println("Cannot write pem certificate " + e);
		}
		
	}
	
	/**
	 * Write a pem certificate in X509 format to directory
	 * @param keyPair pair of keys to aut
	 */
	public void writeCerCertificate(KeyPair keyPair) {
		
		try {
		
			FileOutputStream os = new FileOutputStream(CERTPATH  + ".cer");
			os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.write(Base64.getEncoder().encode(generateCert(keyPair).getEncoded()));
			os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.close();
			
		}catch(Exception e) {
			System.out.println("Cannot write cer certificate " + e);
		}
		
	}

}
