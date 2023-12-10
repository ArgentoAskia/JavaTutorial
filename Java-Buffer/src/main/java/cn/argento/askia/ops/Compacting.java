package cn.argento.askia.ops;

import java.nio.ByteBuffer;

/**
 * Compacting操作
 * 1. 令`p = 当前Position`，将当前`Position`位置的字节复制到`Buffer`为`0`的位置
 * 2. 将`Position + 1`位置的字节复制到`Buffer`为`1`的位置，以此类推
 * 3. 直到复制完`Limit - 1`处的字节到`Limit - 1 - p`的位置，令`n = Limit - 1 - p`
 * 4. 设置`position = n + 1`，设置`Limit`为`capacity`
 * 5. 如果有`Mark`标记，则清除`Mark`标记
 */
public class Compacting {
    public static void main(String[] args) {
        final ByteBuffer byteBuffer = (ByteBuffer) BufferUtility.initBuffer().position(2);
        System.out.println("before compacting...");
        BufferUtility.printAttributes(byteBuffer);
        byteBuffer.compact();
        System.out.println();
        System.out.println("after compacting...");
        BufferUtility.printAttributes(byteBuffer);
    }
}
