package cn.onekit.thekit;

import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.Map.Entry;

public class AJAX {
    public static Map<String,String> headers;
    private static void setHeaders(HttpRequestBase request) {
        if (headers == null) {
            return;
        }
        for (Entry<String, String> header : headers.entrySet()) {
            request.addHeader(header.getKey(), header.getValue());
        }
        headers = null;
    }
    /*
        public static String requestString(HttpServletRequest request) throws Exception {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        }

        public static Map<String, String> requestMap(HttpServletRequest request) throws Exception {
            Map<String, String> result = new HashMap<String, String>();
            Map<String, String[]> map = request.getParameterMap();
            for (Entry<String, String[]> entry : map.entrySet()) {
                result.put(entry.getKey(), entry.getValue()[0]);
            }
            return result;
        }
    */
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
        HttpRequestBase request;
        switch (method.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(url);
                if (data != null) {
                    StringEntity entity = new StringEntity(data, "utf-8");
                    httpPost.setEntity(entity);
                    httpPost.addHeader("Content-Type", "application/json; charset=\"utf-8\"");
                }
                request = httpPost;
                break;
            case "GET":
                HttpGet httpGet = new HttpGet(url);
                request = httpGet;
                break;
            default:
                throw new Exception(method);
        }
        setHeaders(request);
        CloseableHttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), "utf-8");
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
        HttpRequestBase request;
        switch (method.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(url);
                StringEntity entity = new StringEntity(data.toString(), "utf-8");
                httpPost.setEntity(entity);
                httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=\"utf-8\"");
                request = httpPost;
                break;
            case "GET":
                if (data.length() > 0) {
                    url += "?" + data.toString();
                }
                HttpGet httpGet = new HttpGet(url);
                request = httpGet;
                break;
            default:
                throw new Exception(method);
        }
        setHeaders(request);
        CloseableHttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    public static String upload(String url,  byte[] data,String contentType) throws Exception {

        CloseableHttpClient
                httpClient = HttpClients.createDefault();

                HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(data, ContentType.create(contentType)));

        setHeaders(httpPost);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
    public static byte[] download(String url, String method, String data) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();


        ////////////////////////////////
        HttpRequestBase request;
        switch (method.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(url);
                if (data != null) {
                    StringEntity entity = new StringEntity(data, "utf-8");
                    httpPost.setEntity(entity);
                    httpPost.addHeader("Content-Type", "application/json; charset=\"utf-8\"");
                }
                request = httpPost;
                break;
            case "GET":
                HttpGet httpGet = new HttpGet(url);
                request = httpGet;
                break;
            default:
                throw new Exception(method);
        }
        setHeaders(request);
        CloseableHttpResponse response = httpClient.execute(request);
        return EntityUtils.toByteArray(response.getEntity());
    }
}
