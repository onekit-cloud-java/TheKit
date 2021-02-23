package cn.onekit.thekit;

import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("unused")
public class AJAX {
    @SuppressWarnings("WeakerAccess")
    public static Map<String,String> headers;
    private static void _setHeaders(HttpRequestBase requestBase) {
        if (headers == null) {
            return;
        }
        for (Entry<String, String> header : headers.entrySet()) {
            String key = header.getKey();
            if(requestBase.containsHeader(key)){
                requestBase.removeHeaders(key);
            }
            requestBase.addHeader(key, header.getValue());
        }
        headers = null;
    }
    @SuppressWarnings("WeakerAccess")
    public static String request(String url, String method, String data, String mchId, String sslp12_path)
            throws Exception {
        CloseableHttpClient httpClient;
        if (sslp12_path != null) {
            ////////////////////////////////

            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            FileInputStream instream = new FileInputStream(new File(sslp12_path));

            keyStore.load(instream, mchId.toCharArray());

            instream.close();

            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
//指定TLS版本
            @SuppressWarnings("deprecation")
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"},
                    null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } else {
            httpClient = HttpClients.createDefault();
        }

        ////////////////////////////////
        HttpRequestBase request = _initRequest(url,method,data);
        CloseableHttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    private static HttpRequestBase _initRequest(String url,String method,String data) throws Exception {
        HttpRequestBase requestBase;
        switch (method.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(url);
                StringEntity entity = new StringEntity(data, "utf-8");
                httpPost.setEntity(entity);
                httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=\"utf-8\"");
                requestBase = httpPost;
                break;
            case "GET":
                if (data!=null && data.length() > 0) {
                    url += "?" + data;
                }
                requestBase = new HttpGet(url);
                break;
            default:
                throw new Exception(method);
        }
        _setHeaders(requestBase);
        return requestBase;
    }

    public static String request(String url, String method, String data) throws Exception {
        return request(url, method, data, null, null);
    }

    public static String request(String url) throws Exception {
        return request(url, "GET", null, null, null);
    }

    public static String request(String url, String method, Map<String, String> map) throws Exception {

        StringBuilder data = new StringBuilder();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                data.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            if (data.length() > 0) {
                data.delete(0, 1);
            }
        }
        CloseableHttpClient
                httpClient = HttpClients.createDefault();

        HttpRequestBase request=_initRequest(url,method,data.toString());
        CloseableHttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    public static String upload(String url,  byte[] data) throws Exception {

        CloseableHttpClient
                httpClient = HttpClients.createDefault();

                HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(data));
        httpPost.addHeader("Content-Type", "application/octet-stream");
        _setHeaders(httpPost);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
    public static String upload(String url,  Map<String,byte[]> files) throws Exception {

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for(Map.Entry<String,byte[]> entry : files.entrySet()){
            multipartEntityBuilder.addBinaryBody(entry.getKey(),entry.getValue());
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(multipartEntityBuilder.build());
        httpPost.addHeader("Content-Type", "application/octet-stream");
        _setHeaders(httpPost);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
    public static byte[] download(String url, String method, String data) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();


        ////////////////////////////////
        HttpRequestBase request=_initRequest(url,method,data);
        CloseableHttpResponse response = httpClient.execute(request);
        return EntityUtils.toByteArray(response.getEntity());
    }
}
