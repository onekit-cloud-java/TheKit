package cn.onekit.thekit;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.jni.Error;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
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
    @SuppressWarnings("WeakerAccess")
    public String sign(String salt, String data) throws Exception {
        if(method==Method.SHA1){
            return DigestUtils.sha1Hex(data.getBytes(StandardCharsets.UTF_8));
        }
        if(salt==null){
            switch (method){
                case HMACSHA256:
                    return DigestUtils.sha256Hex(data);
                default:
                    throw new Exception(method.toString());
            }
        }
        Mac mac = Mac.getInstance(method.toString());
        byte[] secretByte = salt.getBytes(StandardCharsets.UTF_8);
        SecretKey secret = new SecretKeySpec(secretByte, method.toString());
        mac.init(secret);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] doFinal = mac.doFinal(dataBytes);
        byte[] hexB = new Hex().encode(doFinal);
        return new String(hexB);
    }

    public boolean check(String signature, String salt, String data) throws Exception {
        return sign( salt, data).equals(signature);
    }
}
