package cn.argento.askia.network.tcp.async;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class AIOClient {
    public static void main(String[] args) {
        try {
            AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

            // 连接服务器（异步）
            CountDownLatch latch = new CountDownLatch(1);

            client.connect(new InetSocketAddress("localhost", 8080), null,
                    new CompletionHandler<Void, Void>() {
                        @Override
                        public void completed(Void result, Void attachment) {
                            System.out.println("成功连接到服务器");

                            final boolean[] loop = {true};
                            while(loop[0]){
                                Scanner scanner = new Scanner(System.in);
                                if (scanner.hasNextLine()) {
                                    String message = scanner.nextLine();
                                    ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                                    // 异步写入
                                    client.write(buffer, null, new CompletionHandler<Integer, Void>() {
                                        @Override
                                        public void completed(Integer bytesWritten, Void attachment) {
                                            System.out.println("发送数据成功，字节数: " + bytesWritten);

                                            // 准备接收响应
                                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                                            // 异步读取
                                            client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                                @Override
                                                public void completed(Integer bytesRead, ByteBuffer buffer) {
                                                    buffer.flip();
                                                    byte[] data = new byte[buffer.remaining()];
                                                    buffer.get(data);
                                                    System.out.println("收到服务器响应: " + new String(data));
                                                }

                                                @Override
                                                public void failed(Throwable exc, ByteBuffer buffer) {
                                                    exc.printStackTrace();
                                                    latch.countDown();
                                                    loop[0] = false;
                                                }
                                            });
                                        }

                                        @Override
                                        public void failed(Throwable exc, Void attachment) {
                                            exc.printStackTrace();
                                            latch.countDown();
                                            loop[0] = false;
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void failed(Throwable exc, Void attachment) {
                            System.err.println("连接失败:");
                            exc.printStackTrace();
                            latch.countDown();
                        }
                    });

            // 等待操作完成
            latch.await();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}