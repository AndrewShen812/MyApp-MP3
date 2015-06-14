package com.na517.lf.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 项目名称：LianfengApp
 * 类描述：Http请求操作
 * 创建人：lianfeng
 * 创建时间：2015/6/3 17:22
 * 修改人：lianfeng
 * 修改时间：2015/6/3 17:22
 * 修改备注：
 * 版本：V1.0
 */
public class Http {

    /**
     * Get方式请求数据
     * @param strUrl 请求链接
     * @return 返回数据
     * @throws IOException
     */
    public static String Get(String strUrl) throws IOException {
        String result = "";
        // 创建HttpClient实例
        HttpClient httpclient = new DefaultHttpClient();
        // 创建Get方法实例
        HttpGet httpGet = new HttpGet(strUrl);
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instreams = entity.getContent();
            result = convertStreamToString(instreams);
            httpGet.abort();
        }

        return result;

        /*String result = "";
        HttpGet httpRequest = new HttpGet(strUrl);// 建立http get联机
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);// 发出http请求
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            result = EntityUtils.toString(httpResponse.getEntity());// 获取相应的字符串
        }

        return result;*/

        /*
        String result = "";
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
        */
    }

    /**
     * Post方式请求数据
     * @param strUrl 请求链接
     * @return 返回数据
     */
    public static String Post(String strUrl) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            DataOutputStream dop = new DataOutputStream(
                    connection.getOutputStream());
//            dop.writeBytes("token=alexzhou");
            dop.flush();
            dop.close();

            result = convertStreamToString(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private static String convertStreamToString(InputStream inputStream) throws IOException {
        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(in);
        StringBuffer strBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            strBuffer.append(line);
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strBuffer.toString();
    }
}
