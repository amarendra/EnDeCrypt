package im.amar.apps.endecrypt;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by amar on 14/07/15.
 */
public class SimpleCrypto {

    private static byte[] mSecret;
    private static byte[] mKey;
    private static byte[] mOutput;

    public SimpleCrypto(String secret, String key) throws NoSuchAlgorithmException {
        mSecret = secret.getBytes();    //todo check
        byte[] keyStart = key.getBytes();   //todo check
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG"); //todo change to stronger secure random
        secureRandom.setSeed(keyStart);
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();   //todo: will be fifferent for en/de so change accorndingly (incorrect)
        mKey = secretKey.getEncoded();
    }

    public byte[] encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(mKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(mSecret);
        return encrypted;
    }

    public byte[] decrypt() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(mKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = cipher.doFinal(mSecret);
        return decrypted;
    }
}
