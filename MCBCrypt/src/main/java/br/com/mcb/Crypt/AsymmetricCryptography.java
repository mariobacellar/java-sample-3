package br.com.mcb.Crypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * There is an error in this code, the decryption should be done with the "private key" and not with the public key and viceversa with the encryption: 
 * it should use the public key to encrypt instead of the private key.
 * 
 * =========================
 * Criptografía simétrica
 * =========================
 * Using the private key for encrypting and the public for decrypting — that’s the process of signing a message/verifying that a message is coming from a particular source.
 * Using the public key for encrypting and the private for decrypting — that’s secure communication.
 * 
 * 
 * 
 * =========================
 * FOR Signing Communication: (Criptografía asimétrica)
 * =========================
 * To encrypting: private key -> that’s the process for signing a message.
 * To decrypting: public  key -> that’s the process of verifying that a message is coming from a particular source./
 * 
 * =========================
 * FOR Secure Communication:
 * =========================
 * To encrypting: public  key -> that’s the process for encrypting 
 * To decrypting: private key -> that’s the process for decrypting
 *  
 * 
 * 
 * @author mario.bacellar
 *
 */
public class AsymmetricCryptography {

	private Cipher cipher;

	public AsymmetricCryptography() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.cipher = Cipher.getInstance("RSA");
	}

	// https://docs.oracle.com/javase/8/docs/api/java/security/spec/PKCS8EncodedKeySpec.html
	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	// https://docs.oracle.com/javase/8/docs/api/java/security/spec/X509EncodedKeySpec.html
	public PublicKey getPublic(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public void encryptFile(byte[] input, File output, PrivateKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		writeToFile(output, this.cipher.doFinal(input));
	}

	public void decryptFile(byte[] input, File output, PublicKey key)  throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		writeToFile(output, this.cipher.doFinal(input));
	}

	private void writeToFile(File output, byte[] toWrite) throws IllegalBlockSizeException, BadPaddingException, IOException {
		FileOutputStream fos = new FileOutputStream(output);
		fos.write(toWrite);
		fos.flush();
		fos.close();
	}

	public String encryptText(String msg, PrivateKey key)  throws	NoSuchAlgorithmException, 
																	NoSuchPaddingException, 
																	UnsupportedEncodingException, 
																	IllegalBlockSizeException, 
																	BadPaddingException, 
																	InvalidKeyException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
	}

	public String decryptText(String msg, PublicKey key) throws	InvalidKeyException, 
																UnsupportedEncodingException, 
																IllegalBlockSizeException, 
																BadPaddingException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
	}

	public byte[] getFileInBytes(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] fbytes = new byte[(int) f.length()];
		fis.read(fbytes);
		fis.close();
		return fbytes;
	}

	public static void main(String[] args) throws Exception {
		
		AsymmetricCryptography	ac			= new AsymmetricCryptography();
		
		PrivateKey privateKey = ac.getPrivate("KeyPair/privateKey");
		PublicKey  publicKey  = ac.getPublic ("KeyPair/publicKey");

		String original_msg = "Cryptography is fun!";
		System.out.println("-> Original Message....:["+original_msg+"]");

		String encrypted_msg = ac.encryptText(original_msg	, privateKey);
		System.out.println("-> Encrypted Message...:["+encrypted_msg+"]");
		
		String decrypted_msg = ac.decryptText(encrypted_msg	, publicKey);
		System.out.println("-> Decrypted Message...:["+decrypted_msg+"]");

		
		System.out.println("");
		System.out.println("=================================================");
		System.out.println("-> Decrypted Message From KeyPair/text.txt File");
		System.out.println("=================================================");
		System.out.println("");
		
		if (new File("KeyPair/text.txt").exists()) {
		
			File   file_in = new File("KeyPair/text_encrypted.txt");
			File   file_ou = new File("KeyPair/text_decrypted.txt");

			byte[] byte_file_in = ac.getFileInBytes(new File("KeyPair/text.txt")); 
			byte[] byte_file_ou = ac.getFileInBytes(new File("KeyPair/text_encrypted.txt"));
					
			ac.encryptFile( byte_file_in, file_in, privateKey);
			ac.decryptFile( byte_file_ou, file_ou , publicKey);

		} else {
		
			System.out.println("-> Create a file text.txt under folder KeyPair");
		}
	}
}
