package cn.argento.askia.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

public class ByteCodeJavaFileObject extends SimpleJavaFileObject {

    private ByteArrayOutputStream byteCode;
    private String className;

    protected ByteCodeJavaFileObject(String className) {
        // string:///cn/argento/askia/HelloWorld.java
        super(URI.create("bytes:///" + className.replaceAll("\\.", "/") + Kind.CLASS.extension), Kind.CLASS);
        byteCode = new ByteArrayOutputStream();
        this.className = className;
    }

    public String getClassName(){
        return className;
    }

    // 打开输出流，编译器会将存储结果放在这个输出流里面
    @Override
    public OutputStream openOutputStream() throws IOException {
        return byteCode;
    }


    public byte[] getByteCodes(){
        return byteCode.toByteArray();
    }
}
