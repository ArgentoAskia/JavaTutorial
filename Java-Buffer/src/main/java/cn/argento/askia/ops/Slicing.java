package cn.argento.askia.ops;

import java.nio.ByteBuffer;

/**
 * Slicing操作
 *
 */
public class Slicing {
    public static void main(String[] args) {
        final ByteBuffer byteBuffer = (ByteBuffer) BufferUtility.initBuffer().position(2);
        final ByteBuffer sliceBuffer = (ByteBuffer) byteBuffer.slice();
        System.out.println("after Slicing...");
        BufferUtility.printAttributes(byteBuffer);
        System.out.println();
        BufferUtility.printAttributes(sliceBuffer);
        System.out.println("sliceBuffer's arrayOffset:" + sliceBuffer.arrayOffset());
        System.out.println("byteBuffer's arrayOffset:" + byteBuffer.arrayOffset());
        System.out.println("可以看到子Buffer的底层数组和父Buffer的是一致的，通过arrayOffset来控制子Buffer的第一个访问元素！");
    }
}
