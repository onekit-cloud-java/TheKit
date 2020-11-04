package cn.onekit.thekit;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Crypto {
    public enum Method{
        HMACSHA256
    }
    private final Method method;

    public Crypto(Method method) {
        this.method = method;
    }

    public String encode(String key, String data, String iv) throws Exception {
        Mac mac = Mac.getInstance(method.toString());
        byte[] secretByte = key.getBytes(StandardCharsets.UTF_8);
        SecretKey secret = new SecretKeySpec(secretByte, method.toString());
        mac.init(secret);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] doFinal = mac.doFinal(dataBytes);
        byte[] hexB = new Hex().encode(doFinal);
        return new String(hexB);
    }

    public String encode(String key, String data) throws Exception {
        return encode(key, data, null);
    }
}
