package cn.argento.askia;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URIDemo {
    public static void main(String[] args) throws MalformedURLException {

        // getter
        // 用户获取URI各个部分的信息
        // 有getRawXXX()、getXXX()之分
        // 其中getXXX()会对URI的部分进行Decode之后才返回
        // getRawXXX()才返回原始类型
        URI uri1 = URI.create("https://Askia:Askia@www.baidu.com:80/baidu?ie=utf-8&wd=我的OGNL#123");
        System.out.println("======================= 1.URI节解析 =======================");
        System.out.println("打印URI：" + uri1.toString());
        System.out.println("打印URI（中文等字符将使用UTF-8编码点代替）：" + uri1.toASCIIString());
        System.out.println("转换成URL：" + uri1.toURL());
        String authority = uri1.getAuthority();
        String fragment = uri1.getFragment();
        String host = uri1.getHost();
        String path = uri1.getPath();
        int port = uri1.getPort();
        String query = uri1.getQuery();
        String scheme = uri1.getScheme();
        String schemeSpecificPart = uri1.getSchemeSpecificPart();
        String userInfo = uri1.getUserInfo();
        System.out.println("URI authority部分：" + authority);
        System.out.println("URI fragment部分：" + fragment);
        System.out.println("URI host部分：" + host);
        System.out.println("URI path部分：" + path);
        System.out.println("URI port部分：" + port);
        System.out.println("URI query部分：" + query);
        System.out.println("URI scheme部分：" + scheme);
        System.out.println("URI schemeSpecificPart部分：" + schemeSpecificPart);
        System.out.println("URI userInfo部分：" + userInfo);
        System.out.println("==========================================================");
        System.out.println();

        System.out.println("================== 2.判别方法 ===================");
        URI uri2 = URI.create("file:///D:/Sample/doc/afc/.././ppt_com.html");
        URI uri = URI.create("/helloServlet");
        URI uri4 = URI.create("mailto:java-net@java.sun.com#ABC");

        boolean absolute = uri2.isAbsolute();
        boolean absolute1 = uri.isAbsolute();
        System.out.println(uri2 + "是否是绝对URI：" + absolute);

        System.out.println(uri + "是否是绝对URI：" + absolute1);

        // path == null则是透明的URI，也就是说没有路径
        boolean opaque = uri2.isOpaque();
        boolean opaque1 = uri4.isOpaque();
        System.out.println(uri2 + "是否是Opaque URI：" + opaque);
        System.out.println(uri4 + "是否是Opaque URI：" + opaque1);
        System.out.println("==========================================================");
        System.out.println();

        System.out.println("==================== 3.处理方法 =====================");
        URI uri6 = URI.create("https://www.argentoaskia.cn/2012/12/index.html");
        URI uri5 = URI.create("https://www.argentoaskia.cn");
        System.out.println(uri2 + "标准化：" + uri2.normalize());
        System.out.println(uri4 + "标准化：" + uri4.normalize());
        System.out.println();
        // 求两个路径的相对路径
        URI relativize = uri5.relativize(uri6);
        // 使用URI替换Path部分, 用于拼接处理Uri1和uri
        URI resolve = uri1.resolve(uri);
        System.out.println(uri5 + "和" + uri6 + "的相对关系：" + relativize);
        System.out.println(uri1 + "拼接上" + uri + "的Path部分：" +resolve);
        System.out.println();
    }
}
