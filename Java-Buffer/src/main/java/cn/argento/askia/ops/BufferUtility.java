package cn.argento.askia.ops;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 提供打印Buffer属性和内容的简单方法
 */
public class BufferUtility {
    public static void main(String[] args) {
        final ByteBuffer allocate = ByteBuffer.allocate(10);
        printAttributes(allocate);
    }

    public static void printAttributes(Buffer buffer){
        System.out.println("******************************** Buffer ********************************");
        // 获取4大属性值
        final int capacity = buffer.capacity();
        final int limit = buffer.limit();
        final int position = buffer.position();
        int mark = -1;
        try {
            // className
            final String className = buffer.getClass().getName();
            // mark
            final Field markField = Buffer.class.getDeclaredField("mark");
            markField.setAccessible(true);
            mark = markField.getInt(buffer);
            System.out.println("Buffer Attributes:" + className
                    + "[capacity: " + capacity + ", "
                    + "limit: " + limit + ", "
                    + "position: " + position + ", "
                    + "mark: " + mark + "]" );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        final Object array = buffer.array();
        // 输出
        System.out.println("context: " + bufferToString(array, limit, position, mark));
        System.out.println("************************************************************************");
    }

    private static String bufferToString(Object array, int limit, int position, int mark){
        if (!array.getClass().isArray()){
            throw new RuntimeException("参数不是数组！");
        }
        final int length = Array.getLength(array);
        final Class<?> componentType = array.getClass().getComponentType();
        StringBuilder toStringResult = new StringBuilder(componentType.getSimpleName() + "[");
        for (int i = 0; i < length; i++) {
            final Object o = Array.get(array, i);
            toStringResult.append(o);
            if (i == limit){
                toStringResult.append("(L)");
            }
            if (i == position){
                toStringResult.append("(P)");
            }
            if (i == mark){
                toStringResult.append("(M)");
            }
            if (i != length - 1){
                toStringResult.append(", ");
            }
        }
        toStringResult.append("]");
        return toStringResult.toString();
    }

    public static ByteBuffer initBuffer(){
        byte[] bytes = new byte[]{'A', 'F', 'B', 'D', 'E'};
        final ByteBuffer allocate = ByteBuffer.allocate(14);
        allocate.limit(7);
        allocate.put(bytes[0]).put(bytes[1]).mark();
        allocate.put(bytes, 2, 3);
        return allocate;
    }
}
