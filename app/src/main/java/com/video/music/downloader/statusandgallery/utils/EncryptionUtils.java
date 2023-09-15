package com.video.music.downloader.statusandgallery.utils;

import android.util.Base64;

import com.video.music.downloader.BuildConfig;
//import com.video.music.downloader.statusandgallery.AdsUtils.Utils.//MessageLogger;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {


    private final static String HEX = "0123456789ABCDEF";
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'I', 'd', 'e', '0', '0', '0', '0', '0', '1', '$', '1', '2', '3', '4', '5', '6'};
    public static SecretKey secret;
    public static String password;

    // ------------------------------------------------TODO Encryption type 1-------------------------------------------------
    public static Key publicKey = null;


    // ------------------------------------------------TODO Encryption type 2 (AES - symmetric)-------------------------------------------------
    public static Key privateKey = null;
    private SecretKey secretKey;

    public EncryptionUtils() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);

            secretKey = keyGenerator.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static SecretKey generateKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return secret = new SecretKeySpec(password.getBytes(), "AES");
    }

    public static byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        /* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }


    // ------------------------------------------------TODO Encryption type 2 (RSA - Asymmetric)-------------------------------------------------

    public static String decryptMsg(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }

    public static void RSAKeyPair() {

        // Generate key pair for 2048-bit RSA encryption and decryption
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            //MessageLogger.LogError("Crypto", "RSA key pair error");
        }

    }

    public static void GenerateEncodedString(String targetString) {
        // Encode the original data with the RSA private key
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(targetString.getBytes());
        } catch (Exception e) {
            //MessageLogger.LogError("Crypto", "RSA encryption error");
        }
        if (BuildConfig.DEBUG) {
            //MessageLogger.LogDebug("Encoded string: ", new String(Base64.encodeToString(encodedBytes, Base64.DEFAULT)));
        }
    }

    public static void GenerateDecodedString(byte[] encodedBytes) {
        // Encode the original data with the RSA private key
        // Decode the encoded data with the RSA public key
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            //MessageLogger.LogError("Crypto", "RSA decryption error");
        }

        if (BuildConfig.DEBUG) {
            //MessageLogger.LogDebug("Decoded string: ", new String(decodedBytes));
        }

    }

    public static String EncodeString64(String stringToEncode) {
        String base64 = null;
        try {
            byte[] data = stringToEncode.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return base64;
    }

// ------------------------------------------------TODO Encryption type 3 (base 64)-------------------------------------------------

    public static String DecodeString64(String stringToDecode) {
        String decodedString = "";
        try {
            byte[] data = Base64.decode(stringToDecode, Base64.DEFAULT);
            decodedString = new String(data, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedString;
    }

    public static String encryptSSL(String data) {
        String StrEncrypt = "";
        try {
            Key key = generateSSLKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            StrEncrypt = Base64.encodeToString(encVal, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return StrEncrypt;
    }

    // ------------------------------------------------TODO Encryption type 4 (base 64 + AES Cipher)-------------------------------------------------

    public static String decryptSSL(String encryptedData) {
        String decryptedValue = "";
        try {
            Key key = generateSSLKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.decode(encryptedData, Base64.DEFAULT);
            byte[] decValue = c.doFinal(decordedValue);
            decryptedValue = new String(decValue);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decryptedValue;
    }

    private static Key generateSSLKey() {
        Key key = null;
        try {
            key = new SecretKeySpec(keyValue, ALGO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }

    public static String Ecrp_Hex(String cleartext) {
        byte[] result = new byte[0];
        try {
            byte[] rawKey = getRawKey();
            result = encrypt(rawKey, cleartext.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(result);
    }

    // ------------------------------------------------TODO Encryption type 5 (HEX + AES Cipher)-------------------------------------------------

    public static String Dcrp_Hex(String encrypted) {
        byte[] result = new byte[0];
        try {
            byte[] enc = toByte(encrypted);
            result = decrypt(enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(result);
    }

    private static byte[] getRawKey() throws Exception {
        SecretKey key = new SecretKeySpec(keyValue, "AES");
        byte[] raw = key.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKey skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] encrypted)
            throws Exception {
        SecretKey skeySpec = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public byte[] makeAes(byte[] rawMessage, int cipherMode) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, this.secretKey);
            byte[] output = cipher.doFinal(rawMessage);
            return output;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
