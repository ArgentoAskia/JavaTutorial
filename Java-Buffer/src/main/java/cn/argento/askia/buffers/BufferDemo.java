package cn.argento.askia.buffers;

import java.nio.*;

public class BufferDemo {
    public static void main(String[] args) {
        // 1.几乎所有的Buffer类的对象创建都需要使用wrap和allocate方法
        final ByteBuffer allocate = ByteBuffer.allocate(1024);
        final CharBuffer allocate1 = CharBuffer.allocate(1024);
        final DoubleBuffer allocate2 = DoubleBuffer.allocate(1024);
        final FloatBuffer allocate3 = FloatBuffer.allocate(1024);
        final IntBuffer allocate4 = IntBuffer.allocate(1024);
        final LongBuffer allocate5 = LongBuffer.allocate(1024);
        final ShortBuffer allocate6 = ShortBuffer.allocate(1024);
        final byte[] bytes = new byte[1024];
        final ByteBuffer wrap = ByteBuffer.wrap(bytes);

    }
}
