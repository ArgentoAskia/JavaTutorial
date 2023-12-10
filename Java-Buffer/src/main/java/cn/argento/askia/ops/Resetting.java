package cn.argento.askia.ops;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Marking and resetting
 * set position to Mark
 */
public class Resetting {
    public static void main(String[] args) {
        final ByteBuffer initBuffer = BufferUtility.initBuffer();
        System.out.println("reset之前...");
        BufferUtility.printAttributes(initBuffer);
        System.out.println();
        final Buffer resetBuffer = initBuffer.reset();
        System.out.println("reset之后...");
        BufferUtility.printAttributes(resetBuffer);
    }
}
