package cn.argento.askia.network.httpclient.jdk8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlConnectionDemo {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        // 设置请求属性（可选）
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        urlConnection.setRequestProperty("Accept-Language", "en-US");
        urlConnection.connect();

        // 获取输入流并读取响应
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    }
}
