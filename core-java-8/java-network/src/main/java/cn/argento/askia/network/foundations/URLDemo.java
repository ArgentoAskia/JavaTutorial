package cn.argento.askia.network.foundations;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.URI;
import java.net.URL;

public class URLDemo {
    public static void main(String[] args) throws IOException {
        URI uri1 = URI.create("https://Askia:Askia@www.baidu.com:80/baidu?ie=utf-8&wd=我的OGNL#123");
        URL url = uri1.toURL();
        boolean b = url.sameFile(uri1.toURL());
        System.out.println(b);
        String authority = url.getAuthority();
        int defaultPort = url.getDefaultPort();
        String file = url.getFile();
        String host = url.getHost();
        String path = url.getPath();
        int port = url.getPort();
        String protocol = url.getProtocol();
        String query = url.getQuery();
        String ref = url.getRef();
        String userInfo = url.getUserInfo();
        System.out.println(authority);
        System.out.println(defaultPort);
        System.out.println(file);
        System.out.println(host);
        System.out.println(path);
        System.out.println(port);
        System.out.println(protocol);
        System.out.println(query);
        System.out.println(ref);
        System.out.println(userInfo);

    }
}
