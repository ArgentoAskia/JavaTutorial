package cn.argento.askia.ops;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class Remaining {
    public static void main(String[] args) {
        final ByteBuffer initBuffer = BufferUtility.initBuffer();
        System.out.println("remaining之前...");
        BufferUtility.printAttributes(initBuffer);
        System.out.println("remain = limit - position = " + (initBuffer.limit() - initBuffer.position()));
        System.out.println("remain = limit - position = " + initBuffer.remaining());
        System.out.println("hasRemaining?" + initBuffer.hasRemaining());
    }
}
