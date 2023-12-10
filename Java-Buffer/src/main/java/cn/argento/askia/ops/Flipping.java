package cn.argento.askia.ops;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Flipping操作
 * 1. 设置`limit = position`
 * 2. 移动`position`为`0`
 * 3. 清除`Mark`
 */
public class Flipping {
    public static void main(String[] args) {
        final ByteBuffer initBuffer = BufferUtility.initBuffer();
        System.out.println("flipping之前...");
        BufferUtility.printAttributes(initBuffer);
        System.out.println();
        final Buffer flipBuffer = initBuffer.flip();
        System.out.println("flipping之后...");
        BufferUtility.printAttributes(flipBuffer);
    }
}
