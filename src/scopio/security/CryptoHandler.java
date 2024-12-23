package scopio.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import scopio.log.LogLevel;
import scopio.log.Logger;

public class CryptoHandler {
    private KeyPair pair;

    public static String createAuthKey() {
        String key = "";
        for(int x = 0; x < 20; x++) {
            int rand = new SecureRandom().nextInt((int)'a')+(int)'&';
            key += (char) rand;
        }
        return key;
    }

    public static KeyPair generateAsyncKey(int keysize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keysize);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(byte[] data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static SecretKey generateSyncKey(int keysize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize);
        return keyGen.generateKey();
    }

    public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    public static PublicKey byteToKey(byte[] data) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static SecretKey byteToSecretKey(byte[] data) throws Exception {
        return new SecretKeySpec(data, "AES");
    }

    public CryptoHandler(int asyncKeysize) {
        try {
            this.pair = generateAsyncKey(asyncKeysize);
        } catch (Exception e) {
            new Logger().writeNewLogEntry(e.getMessage(), LogLevel.ERROR);
        }
    }

    public byte[] encryptRSA(byte[] data) {
        try {
            return encrypt(data, pair.getPublic());
        } catch (Exception e) {
            new Logger().writeNewLogEntry(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    public byte[] decryptRSA(byte[] data) {
        try {
            return decrypt(data, pair.getPrivate());
        } catch (Exception e) {
            new Logger().writeNewLogEntry(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }
}