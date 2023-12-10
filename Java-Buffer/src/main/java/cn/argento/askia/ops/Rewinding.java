package cn.argento.askia.ops;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Rewinding操作
 * 1. 移动`position`为`0`
 * 2. 清除`Mark`
 */
public class Rewinding {
    public static void main(String[] args) {
        final ByteBuffer initBuffer = BufferUtility.initBuffer();
        System.out.println("rewinding之前...");
        BufferUtility.printAttributes(initBuffer);
        System.out.println();
        final Buffer rewindingBuffer = initBuffer.rewind();
        System.out.println("rewinding之后...");
        BufferUtility.printAttributes(rewindingBuffer);
    }
}
