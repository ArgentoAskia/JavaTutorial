package cn.argento.askia.gson.parsing;

import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

/**
 * JsonStreamParser,采用流式Json解析
 *
 * @see GsonJsonParserDemo
 */
public class GsonJsonStreamParserDemo {
    public static void main(String[] args) {
        JsonStreamParser jsonStreamParser = new JsonStreamParser("");
        while (jsonStreamParser.hasNext()){
            JsonElement next = jsonStreamParser.next();
            if (next.isJsonArray()){

            }
            else if (next.isJsonNull()){

            }
            else if(next.isJsonObject()){

            }
            else if (next.isJsonPrimitive()){

            }
            else{
                // 未知
            }
        }
    }
}
