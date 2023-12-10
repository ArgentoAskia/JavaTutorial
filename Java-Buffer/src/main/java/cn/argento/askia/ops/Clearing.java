package cn.argento.askia.ops;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * clearing操作：
 * 1. 移动`position`为`0`
 * 2. 让`limit = capacity`
 * 3. 清空`mark`标记
 *
 * @author asus
 */
public class Clearing {
    public static void main(String[] args) {
        final ByteBuffer initBuffer = BufferUtility.initBuffer();
        System.out.println("clearing之前...");
        BufferUtility.printAttributes(initBuffer);
        System.out.println();
        final Buffer clearBuffer = initBuffer.clear();
        System.out.println("clearing之后...");
        BufferUtility.printAttributes(clearBuffer);
    }
}
