package cn.argento.askia.processors.source;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
@Deprecated
public class ABC {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "abcdefg我爱中国Hello World！除非你让我很开心！自发臧否！";
        final char[] chars = a.toCharArray();
        System.out.println(Arrays.toString(chars) + chars.length);
        final byte[] bytes = a.getBytes();
        final byte[] gbks = a.getBytes("GBK");
        final byte[] utf8 = a.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(bytes) + bytes.length);
        System.out.println(Arrays.toString(gbks) + gbks.length);
        System.out.println(Arrays.toString(utf8) + utf8.length);

    }
}
