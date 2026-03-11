package com.ps.common.utils;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
public class EncryptUtils {

    private EncryptUtils() {
    }

    public static String md5(String text) {
        if (text == null) {
            return null;
        }
        return DigestUtil.md5Hex(text);
    }

    public static String sha256(String text) {
        if (text == null) {
            return null;
        }
        return DigestUtil.sha256Hex(text);
    }

    public static String hash(String text, String algorithm) {
        if (text == null || algorithm == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("Hash algorithm not found: {}", algorithm, e);
            throw new RuntimeException("Hash algorithm not found: " + algorithm, e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                result.append('0');
            }
            result.append(hex);
        }
        return result.toString();
    }

    public static String bcrypt(String text) {
        if (text == null) {
            return null;
        }
        return BCrypt.hashpw(text, BCrypt.gensalt());
    }

    public static boolean bcryptCheck(String text, String hashedText) {
        if (text == null || hashedText == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(text, hashedText);
        } catch (Exception e) {
            log.error("BCrypt check failed: {}", e.getMessage(), e);
            return false;
        }
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateToken() {
        return generateUUID() + generateUUID();
    }

    public static String generateSalt() {
        return generateUUID().substring(0, 16);
    }

    public static String base64Encode(String text) {
        if (text == null) {
            return null;
        }
        return cn.hutool.core.codec.Base64.encode(text);
    }

    public static String base64Decode(String encodedText) {
        if (encodedText == null) {
            return null;
        }
        return cn.hutool.core.codec.Base64.decodeStr(encodedText);
    }

    public static String urlEncode(String text) {
        if (text == null) {
            return null;
        }
        return cn.hutool.core.net.URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    public static String urlDecode(String encodedText) {
        if (encodedText == null) {
            return null;
        }
        return cn.hutool.core.net.URLDecoder.decode(encodedText, StandardCharsets.UTF_8);
    }
}
