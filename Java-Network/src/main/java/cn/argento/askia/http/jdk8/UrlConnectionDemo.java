package cn.argento.askia.http.jdk8;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * UrlConnection常用实现类：
 * FileURLConnection：支持File://协议
 * FtpURLConnection：支持Ftp://
 * JarURLConnection：支持Jar://
 * HttpsURLConnection：支持https://
 * HttpURLConnection:支持http://
 */
public class UrlConnectionDemo {
    public static void main(String[] args) throws IOException {
        String urlStr = "https://www.argentoaskia.cn";
        URL url = new URL(urlStr);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.connect();

        // http请求头信息

        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : headers.entrySet())
        {
            String key = entry.getKey();
            for (String value : entry.getValue())
                System.out.println(key + ": " + value);
        }

        // http请求头方便方法

        System.out.println("----------");
        System.out.println("getContentType: " + urlConnection.getContentType());
        System.out.println("getContentLength: " + urlConnection.getContentLength());
        System.out.println("getContentEncoding: " + urlConnection.getContentEncoding());
        System.out.println("getDate: " + urlConnection.getDate());
        System.out.println("getExpiration: " + urlConnection.getExpiration());
        System.out.println("getLastModifed: " + urlConnection.getLastModified());
        System.out.println("----------");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        // 请求体
        String line = "";
        while((line=br.readLine()) != null)
        {
            System.out.println(line);
        }
        br.close();
    }
}
