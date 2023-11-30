package cn.argento.askia.tcp.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class AIOEchoKeepAliveClient {
    public static void main(String[] args) throws IOException {
        AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",9999);

        asynchronousSocketChannel.connect(inetSocketAddress, "当前时间：" + LocalDateTime.now() + ",连接成功！", new CompletionHandler<Void, String>() {

            @Override
            public void completed(Void result, String attachment) {
                System.out.println(LocalDateTime.now());
                System.out.println(attachment);
                System.out.println();
                ByteBuffer byteBuffer  = ByteBuffer.allocate(2048);
                // 让服务器回写Welcome()，我们先等待一下,然后再读！
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                asynchronousSocketChannel.read(byteBuffer, "当前时间：" + LocalDateTime.now(), new CompletionHandler<Integer, String>() {
                    @Override
                    public void completed(Integer result, String attachment) {
                        final byte[] array = byteBuffer.array();
                        System.out.println("读了" + result + "个字节，服务器回传内容：" + new String(array, 0, array.length));
                        final String msg = UUID.randomUUID().toString();
                        byteBuffer.clear();
                        asynchronousSocketChannel.write(ByteBuffer.wrap(msg.getBytes()), "当前时间：" + LocalDateTime.now(), new CompletionHandler<Integer, String>() {
                            @Override
                            public void completed(Integer result, String attachment) {

                            }

                            @Override
                            public void failed(Throwable exc, String attachment) {

                            }
                        });
                        // 写完之后等待一下服务器响应，像刚链接那时一样：
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        asynchronousSocketChannel.read(byteBuffer, "当前时间：" + LocalDateTime.now(), this);
                    }

                    @Override
                    public void failed(Throwable exc, String attachment) {

                    }
                });
            }

            @Override
            public void failed(Throwable exc, String attachment) {

            }
        });
        System.in.read();
    }

}
