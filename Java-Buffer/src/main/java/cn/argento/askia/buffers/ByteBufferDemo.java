package cn.argento.askia.buffers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ByteBufferDemo {
    public static void main(String[] args) throws IOException {
        // Path默认使用后项目根路径作为路径，和File一样！
        final Path path = Paths.get("Java-Beans/src/main/resources/readerText.txt");
        System.out.println(path.toAbsolutePath());
        final byte[] readerTextBytes = Files.readAllBytes(path);

        ByteBuffer allocBuffer = ByteBuffer.allocate(1024);
        final ByteBuffer readerTextBuffer = ByteBuffer.wrap(readerTextBytes);
        CharBuffer charBuffer;

    }
}
