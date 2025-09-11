package cn.argento.askia.processors.bytecode;

import org.objectweb.asm.*;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JuLoggerAnnotationProcessor extends ClassVisitor {

    // 运行此处会得到Item.class经过JuLoggerAnnotationProcessor之后的字节码文件item_test.classs
//    public static void main(String[] args) throws IOException {
//        final byte[] bytes = Files.readAllBytes(Paths.get("D:\\OpenSourceProject\\java-project\\core-java-8\\java-annotation\\target\\classes\\cn\\argento\\askia\\processors\\bytecode\\Item.class"));
//        final byte[] process = process("cn.argento.askia.processors.bytecode.Item", bytes);
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\OpenSourceProject\\java-project\\core-java-8\\java-annotation\\target\\classes\\cn\\argento\\askia\\processors\\bytecode\\item_test.class"));
//        fileOutputStream.write(process);
//        fileOutputStream.close();
//    }

    private String className;

    public static byte[] process(String className, byte[] bytecode){
        final ClassReader classReader = new ClassReader(bytecode);
        final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        CheckClassAdapter checkClassAdapter = new CheckClassAdapter(classWriter);
        final JuLoggerAnnotationProcessor juLoggerAnnotationProcessor = new JuLoggerAnnotationProcessor(checkClassAdapter, className);
        classReader.accept(juLoggerAnnotationProcessor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    public JuLoggerAnnotationProcessor(CheckClassAdapter writer, String className){
        super(Opcodes.ASM8, writer);
        this.className = className;
    }


    private boolean modifiedLoggerField = false;
    private boolean modifiedStaticLogger = false;
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals("Lcn/argento/askia/processors/bytecode/JuLogger;") && !visible){
            modifiedLoggerField = true;
        }
        return null;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (modifiedLoggerField && "logger".equals(name)){
            access = access | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC;
            modifiedStaticLogger = true;
            modifiedLoggerField = false;
            return cv.visitField(access, name, descriptor, signature, value);
        }
        return super.visitField(access, name, descriptor, signature, value);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println(name);
        if (modifiedStaticLogger){
            modifiedStaticLogger = false;
            // 获取静态代码块方法
            final MethodVisitor methodVisitor = cv.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            // 插入代码
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLdcInsn(className);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/logging/Logger", "getLogger", "(Ljava/lang/String;)Ljava/util/logging/Logger;", false);
            methodVisitor.visitFieldInsn(Opcodes.PUTSTATIC, "cn/argento/askia/processors/bytecode/Item", "logger", "Ljava/util/logging/Logger;");
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "cn/argento/askia/processors/bytecode/Item", "logger", "Ljava/util/logging/Logger;");
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/util/logging/Level", "ALL", "Ljava/util/logging/Level;");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/logging/Logger", "setLevel", "(Ljava/util/logging/Level;)V", false);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/logging/ConsoleHandler");
            methodVisitor.visitInsn(Opcodes.DUP);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/logging/ConsoleHandler", "<init>", "()V", false);
            methodVisitor.visitVarInsn(Opcodes.ASTORE, 0);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/util/logging/Level", "ALL", "Ljava/util/logging/Level;");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/logging/ConsoleHandler", "setLevel", "(Ljava/util/logging/Level;)V", false);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "cn/argento/askia/processors/bytecode/Item", "logger", "Ljava/util/logging/Logger;");
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/logging/Logger", "addHandler", "(Ljava/util/logging/Handler;)V", false);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLocalVariable("consoleHandler", "Ljava/util/logging/ConsoleHandler;", null, label3, label5, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
