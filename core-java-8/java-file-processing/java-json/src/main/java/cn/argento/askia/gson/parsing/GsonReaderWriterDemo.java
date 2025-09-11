package cn.argento.askia.gson.parsing;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * 原始Json字符串解析和创建支持, 在com.google.gson.stream.*包中提供了这一类支持！
 */
public class GsonReaderWriterDemo {
    public static void main(String[] args) throws IOException {
        jsonWriter();
        jsonReader();
    }
    private static void jsonWriter() throws IOException {
        JsonWriter writer = new JsonWriter(new PrintWriter(System.out));
        writer.beginObject().name("username").value("Askia").name("array")
                .beginArray().value("2").value("3").value("4").endArray().endObject().flush();
        System.out.println();
        // 关闭out？
//        writer.close();
    }
    private static void jsonReader() throws IOException {
        String str = "{\"username\":\"Askia\",\"array\":[2,3,4]}";
        JsonReader reader = new JsonReader(new StringReader(str));
        reader.beginObject();
        final String s = reader.nextName();
        final String s1 = reader.nextString();
        final String s2 = reader.nextName();
        reader.beginArray();
        final int i1 = reader.nextInt();
        final int i2 = reader.nextInt();
        final int i3 = reader.nextInt();
        reader.endArray();
        reader.endObject();
        reader.close();
        System.out.println(s + "=" + s1);
        System.out.println(s2 + "= [" + i1 + ", " + i2 +", " + i3 + "]");
    }
}
