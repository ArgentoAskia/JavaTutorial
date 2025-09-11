package cn.argento.askia.compiler;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.SimpleJavaFileObject;
import java.io.*;
import java.net.URI;
import java.util.Date;

/**
 * 代表一段Java代码的FileObject类
 */
public class StringSourceJavaFileObject extends SimpleJavaFileObject {

    private StringBuilder javaCode;
    private Date fileUpdateTime;

    public StringSourceJavaFileObject(StringBuilder javaCode, String className) {
        // string:///cn/argento/askia/HelloWorld.java
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.javaCode = javaCode;
        fileUpdateTime = new Date();
    }

    // 子实现类必须重新该方法，已获取代码性的内容（java代码，字节码等）
    // 其中ignoreEncodingErrors用于判断是否忽略代码中的编码性的错误等，常见字符乱码
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return javaCode.toString();
    }

    // 表示文件对象是否可以删除，如果代码的来源是外部文件，则需要实现该方法
    @Override
    public boolean delete() {
        System.out.println("123123");
        return super.delete();
    }

    // 表示最后一次修改时间！
    @Override
    public long getLastModified() {
        return fileUpdateTime.getTime();
    }

    // 如果Java代码是一个匿名性的代码，则判断是哪种匿名（本地变量、匿名类等等）
    // 需要自己定义该javaFileObject的具体匿名类型，参考NestingKind枚举量
    @Override
    public NestingKind getNestingKind() {
        return super.getNestingKind();
    }

    // 定义该类的访问级别
    // 子类需要实现该
    @Override
    public Modifier getAccessLevel() {
        return Modifier.PUBLIC;
    }

    // 打开输入流，如果存入的代码不是以文本的形式出入的，而是以字节的形式存入的则使用这两个方法！
    @Override
    public InputStream openInputStream() throws IOException {
        final String s = javaCode.toString();
        final byte[] bytes = s.getBytes();
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        final String s = javaCode.toString();
        final byte[] bytes = s.getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(bytes);
        return byteArrayOutputStream;
    }
}
