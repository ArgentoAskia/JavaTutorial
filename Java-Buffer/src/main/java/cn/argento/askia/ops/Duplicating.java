package cn.argento.askia.ops;

import java.nio.ByteBuffer;

/**
 * 复制操作
 * 对复制出来的`Buffer`进行`Put`操作将会影响原来的`Buffer`，反之亦然，
 * 但他们的`Limit`、`Position`、`Mark`都是相对独立的！
 */
public class Duplicating {
    public static void main(String[] args) {
        final ByteBuffer byteBuffer = (ByteBuffer) BufferUtility.initBuffer().position(2);
        final ByteBuffer duplicateBuffer = (ByteBuffer) byteBuffer.duplicate().position(5);
        System.out.println("after duplicating...");
        BufferUtility.printAttributes(byteBuffer);
        System.out.println();
        BufferUtility.printAttributes(duplicateBuffer);
    }
}
