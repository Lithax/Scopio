package scopio.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingHandler {
    public static byte[] hash(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}