package cn.onekit.thekit;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

@SuppressWarnings("unused")
public class CRYPTO {

    public enum Key {
        AES
    }

    public enum Mode {
        PKCS5("AES/CBC/PKCS5Padding"),
        PKCS7("AES/ECB/PKCS7Padding");

        private String mode;

        Mode(String mode) {
            this.mode = mode;
        }

        public String toString() {
            return mode;
        }
    }
    /////////////////////////

    private final Key key;
    private final Mode mode;

    public CRYPTO(Key key, Mode mode, int level) throws NoSuchAlgorithmException {
        this.key = key;
        this.mode = mode;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator.getInstance(key.toString()).init(level);
    }

    @SuppressWarnings("WeakerAccess")
    public AlgorithmParameters initIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance(key.toString());
        params.init(new IvParameterSpec(iv));
        return params;
    }

    public String generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(key.toString());
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    ///////////////////

    @SuppressWarnings("WeakerAccess")
    public byte[] encrypt(byte[] data, byte[] keyBytes, byte[] iv)
            throws Exception {
        Cipher cipher = Cipher.getInstance(mode.toString());
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, key.toString());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initIV(iv));
        return cipher.doFinal(data);
    }

    @SuppressWarnings("CharsetObjectCanBeUsed")
    public String encrypt(String data, String sKey, String iv) throws Exception {

        byte[] sSrc64 = data.getBytes("UTF-8");
        byte[] key64 = Base64.decodeBase64(sKey);
        byte[] iv64 = Base64.decodeBase64(iv);

        return Base64.encodeBase64String(encrypt(sSrc64, key64, iv64));
    }

    @SuppressWarnings("WeakerAccess")
    public byte[] decrypt(byte[] encryptedData, byte[] keyBytes, byte[] iv)
            throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, key.toString());
        Cipher cipher = Cipher.getInstance(mode.toString());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,initIV(iv));
        return cipher.doFinal(encryptedData);
    }

    @SuppressWarnings("CharsetObjectCanBeUsed")
    public String decrypt(String encryptedData, String iv, String key) throws Exception {

        byte[] encrypted64 = Base64.decodeBase64(encryptedData);
        byte[] key64 = Base64.decodeBase64(key);
        byte[] iv64 = Base64.decodeBase64(iv);

        byte[] data = decrypt(encrypted64, key64, iv64);
        return new String(data, "UTF-8");
    }
}