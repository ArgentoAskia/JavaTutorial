package cn.argento.askia;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

public class ByteCodeClassSample extends User implements Comparable<ByteCodeClassSample>, Serializable, Closeable {


    @Override
    public void close() throws IOException {
        System.out.println("close");
    }

    // bridge method
    @Override
    public int compareTo(ByteCodeClassSample o) {
        return 0;
    }

    public int field1;
    private double field2;
    protected float field3;

    transient String field4;

    private static final long serialVersionUID = 7581703094810404362L;

    private final double field5 = 5.2;



    public static void main(String[] args) {
        new ByteCodeClassSample();
    }
}

