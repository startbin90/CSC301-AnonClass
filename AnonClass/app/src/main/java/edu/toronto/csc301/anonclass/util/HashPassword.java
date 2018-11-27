package edu.toronto.csc301.anonclass.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


// digest using md2
public class HashPassword {
    public static String hash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] md = messageDigest.digest();

            // change array of bytes to hex
            return new BigInteger(1, md).toString(16);

        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }
}
