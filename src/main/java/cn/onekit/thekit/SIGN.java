package cn.onekit.thekit;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SIGN {

    public enum Method {
        SHA1,
        HMACSHA256
    }
    ////////////////////

    private final Method method;

    public SIGN(Method method) {
        this.method = method;
    }
    public String sign(String data) throws Exception {
        return sign(null,data);
    }
    public String sign(String key, String data) throws Exception {
        if(method==Method.SHA1){
            return DigestUtils.sha1Hex(data.getBytes(StandardCharsets.UTF_8));
        }
        Mac mac = Mac.getInstance(method.toString());
        byte[] secretByte = key.getBytes(StandardCharsets.UTF_8);
        SecretKey secret = new SecretKeySpec(secretByte, method.toString());
        mac.init(secret);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] doFinal = mac.doFinal(dataBytes);
        byte[] hexB = new Hex().encode(doFinal);
        return new String(hexB);
    }

    public boolean check(String signature, String key, String data) throws Exception {
        return sign( key, data).equals(signature);
    }
}
