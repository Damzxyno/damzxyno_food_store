package com.damzxyno.foodstore.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This is a utility class for hashing passwords using SHA-256 algorithm.
 */

public class PasswordHashingUtil {
    private final String salt;
    private final static String ALGORITHM ="SHA-256";

    public PasswordHashingUtil(String salt) {
        this.salt = salt;
    }

    /**
     * This method hashes a given arguement using SHA-256
     * @param string to be hashed
     * @return a hashed resule
     */
    public String hash(String string){
        try {
            var messageDigest = MessageDigest.getInstance(ALGORITHM);
            var wordAcidBytes = String.format("%s%s", salt, string).getBytes();
            byte[] hashedBytes = messageDigest.digest(wordAcidBytes);
            var sb = new StringBuilder();
            for (var b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password.", e);
        }
    }

    /**
     * This method checks if a hashed string and a raw string contain the same message
     * @param rawPassword the un-hashed string
     * @param hashedPassword the hashed string
     * @return a boolean, whether they are both the same.
     */
    public boolean matches(String rawPassword, String hashedPassword) {
        return hashedPassword.equals(hash(rawPassword));
    }
}
