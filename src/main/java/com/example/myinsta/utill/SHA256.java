package com.example.myinsta.utill;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256
 * Encryption class that implements java.security SHA256
 */
public class SHA256 {
    public static String encrypt(String originalString){
        String hash;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(originalString.getBytes(StandardCharsets.UTF_8));

            byte [] bytes = digest.digest();
            StringBuilder builder = new StringBuilder();

            for(byte b: bytes){
                builder.append(String.format("%02x", b));
            }
            hash = builder.toString();

        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Encryption error", e);
        }
        return hash;
    }
}
