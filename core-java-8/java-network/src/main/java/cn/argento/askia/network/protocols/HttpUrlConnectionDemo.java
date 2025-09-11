package cn.argento.askia.network.protocols;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUrlConnectionDemo {
    public static void main(String[] args) {
        String websiteStr = "http://www.argentoaskia.cn";
        try {
            URL websiteURL = new URL(websiteStr);
            final URLConnection urlConnection = websiteURL.openConnection();
            // 设置请求属性
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
            urlConnection.connect();
            final InputStream inputStream = urlConnection.getInputStream();
            // 获取输入流
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            // 读取响应内容
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            // 关闭输入流
            in.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
