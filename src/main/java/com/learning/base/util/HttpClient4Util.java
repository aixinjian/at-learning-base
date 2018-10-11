package com.learning.base.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class HttpClient4Util {

    private static CloseableHttpClient httpClient;

    private static RequestConfig defaultRequestConfig;

    /**
     * 最大连接数
     */
    private final static int MAX_TOTAL_CONNECTIONS = 60;

    /**
     * 从连接管理器中获取连接的最大等待时间
     * timeout in milliseconds used when requesting a connection from the connection manager.
     */
    private final static int WAIT_TIMEOUT = 100;

    /**
     * 每个路由最大连接数
     */
    private final static int MAX_ROUTE_CONNECTIONS = 10;

    /**
     * 连接超时时间
     * Determines the timeout in milliseconds until a connection is established.
     */
    private final static int CONNECT_TIMEOUT = 5000;

    /**
     * 读取超时时间
     * Defines the socket timeout in milliseconds,
     * which is the timeout for waiting for data  or, put differently,
     * a maximum period inactivity between two consecutive data packets).
     */
    private final static int READ_TIMEOUT = 15000;
    
    /**
     * UTF-8编码
     */
    private final static String ENCODING_UTF_8 = "UTF-8";

    public static boolean ubqsOverLoad = false;
    
    
    static {
        // SSL context for secure connections can be created either based on
        // system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();
        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        // Create global request configuration
        defaultRequestConfig = RequestConfig.custom().setConnectionRequestTimeout(WAIT_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(READ_TIMEOUT).build();
        // Create an HttpClient with the given custom dependencies and configuration.
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig).build();
    }

    public static CloseableHttpClient getHttpClient(){
        return httpClient;
    }

    /**
     * post获取数据
     * @param url 接口URL
     * @param requestMap NameValuePair参数
     * @param coding 编码
     * @return
     * @throws Exception
     */
    public static String Post(String url, Map<String, String> requestMap, String coding) throws Exception {
        Long startTimeMills = System.currentTimeMillis();
        String returnMsg = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Entry<String, String>> entrys = requestMap.entrySet();
            for (Entry<String, String> entry : entrys) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, coding));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader("Accept-Language", "zh-cn");
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpPost, HttpClientContext.create());
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    returnMsg = EntityUtils.toString(httpEntity);
                    EntityUtils.consume(httpEntity);
                }
                httpPost.abort();
            } catch (SocketTimeoutException e){
                throw new Exception("查询信息超时!");
            } finally {
                if(httpResponse != null){
                    httpResponse.close();
                }
            }
        } finally {
            Long endTimeMills = System.currentTimeMillis();
            Long dotimes = endTimeMills - startTimeMills;
            if (dotimes > 1000) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(dotimes)
                        .append(" Mills! httpclient call times more than 1000Millis, url = ")
                        .append(url)
                        .append(", param = ")
                        .append(requestMap.toString())
                ;
                log.warn(buffer.toString());
            }
        }
        return returnMsg;
    }
    
    public static String Get(String url, Map<String, String> requestMap, String coding) throws Exception {
        long startTimeMills = System.currentTimeMillis();
        String returnMsg = "";
        try{
            Set<Entry<String, String>> entrys = requestMap.entrySet();
            String params = "";
            boolean first = true;
            for (Entry<String, String> entry : entrys) {
                String key = entry.getKey();
                String value = entry.getValue();
                if(value == null) {
                    value="";
                }
                if(first) {
                    params = params + key + "=" + value ;
                    first = false;
                } else {
                    params = params + "&" + key + "=" + value ;
                }
            }
            if(StringUtils.isNotBlank(params)){
                url = url + "?" + params;
            }

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpGet, HttpClientContext.create());
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    returnMsg = EntityUtils.toString(httpEntity);
                    EntityUtils.consume(httpEntity);
                }
                httpGet.abort();
            } catch (SocketTimeoutException e){
                throw new Exception("查询信息超时!");
            } finally {
                if(httpResponse != null){
                    httpResponse.close();
                }
            }
        } finally {
            Long endTimeMills = System.currentTimeMillis();
            Long dotimes = endTimeMills-startTimeMills;
            if (dotimes > 1000) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(dotimes)
                      .append(" Mills! httpclient call times more than 1000Millis, url = ")
                      .append(url)
                      .append(", param = ")
                      .append(requestMap.toString())
                      ;
                log.warn(buffer.toString());
            }
        }
        return returnMsg;
    }

    public static String Get(String url) throws Exception {
        return Get(url, new HashMap<>(), ENCODING_UTF_8);
    }
    public static String Get(String url, Map<String, String> requestMap) throws Exception {
        return Get(url, requestMap, ENCODING_UTF_8);
    }

    public static String Post(String url, Map<String, String> requestMap) throws Exception {
        return Post(url, requestMap, ENCODING_UTF_8);
    }


    public static String Post(String url, String xml, String encodingRule) {
        String returnMsg = "";

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");

        CloseableHttpResponse httpResponse = null;
        try {

            httpPost.setEntity(new StringEntity(xml, encodingRule));
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                returnMsg = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            }
            httpPost.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if(httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return returnMsg;
    }

    /**
     * 获取图片字节流
     *
     * @param url
     * @param requestMap
     * @return
     * @throws Exception
     */

    public static byte[] GetImg(String url,Map<String, String> requestMap) throws Exception {
        CloseableHttpResponse httpResponse = null;
        Long startTimeMills = System.currentTimeMillis();
        try{
            Set<Entry<String, String>> entrys = requestMap.entrySet();
            String params = "";
            boolean first = true;
            for (Entry<String, String> entry : entrys) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value == null) {
                    value = "";
                }
                if (first) {
                    params = params + key + "=" + value;
                    first = false;
                } else {
                    params = params + "&" + key + "=" + value;
                }
            }
            if (StringUtils.isNotBlank(params)) {
                url = url + "?" + params;
            }

            HttpGet httpGet = new HttpGet(url);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            httpResponse = httpClient.execute(httpGet,HttpClientContext.create());
            if(httpResponse.getStatusLine().getStatusCode() != 200) {
                String returnMsg = httpResponse.getStatusLine().toString();
                log.error("请求失败，返回：" + returnMsg);
                throw new Exception("请求失败：" + url);
            }

            HttpEntity entity = httpResponse.getEntity();
            InputStream is = entity.getContent();
            byte[] buffer = new byte[1024];
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while( (len=is.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            is.close();

            //把outStream里的数据写入内存
            return outStream.toByteArray();
        } catch (SocketTimeoutException e){
            throw new Exception("查询信息超时!");
        } finally {
            if(httpResponse != null){
                httpResponse.close();
            }
            Long endTimeMills = System.currentTimeMillis();
            Long dotimes = endTimeMills-startTimeMills;
            if (dotimes > 1000) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(dotimes)
                        .append(" Mills! httpclient call times more than 1000Millis, url = ")
                        .append(url)
                ;
                log.warn(buffer.toString());
            }
        }
    }
}
