package cn.argento.askia;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLCoderDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "https://www.baidu.com/s?ie=utf-8&wd=%E9%98%BB%E5%A1%9E%E5%92%8C%E9%9D%9E%E9%98%BB%E5%A1%9E%E5%8C%BA%E5%88%AB";
        // 解码，第一个参数传递要解码的url，第二个指定编码！
        String decode = URLDecoder.decode(url, "utf-8");
        System.out.println(decode);
        // 编码
        String encode = URLEncoder.encode(decode, "utf-8");
        System.out.println(encode);
        String gbk = URLEncoder.encode(decode, "GBK");
        System.out.println(gbk);

    }
}
