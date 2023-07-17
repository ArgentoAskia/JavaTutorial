package cn.argentoaskia.demo;

import java.io.StringBufferInputStream;
import java.util.UUID;

@Deprecated
public class StringBufferInputStreamDemo {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            stringBuilder.append(UUID.randomUUID().toString()).append(System.lineSeparator());
        }
        StringBufferInputStream stringBufferInputStream = new StringBufferInputStream(stringBuilder.toString());

        // 2.
        int read = stringBufferInputStream.read();

    }
}
