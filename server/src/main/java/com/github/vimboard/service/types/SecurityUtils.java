package com.github.vimboard.service.types;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

    public static String sha1(String value) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new NullPointerException(e.getMessage());
        }
        digest.reset();
        digest.update(value.getBytes(StandardCharsets.UTF_8));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }
}
