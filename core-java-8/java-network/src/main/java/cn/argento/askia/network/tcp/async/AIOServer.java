package cn.argento.askia.network.tcp.async;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AIOServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 创建异步服务器通道并绑定端口
            AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(PORT));
            System.out.println("AIO服务器已启动，监听端口：" + PORT);

            // 使用CountDownLatch保持主线程不退出
            CountDownLatch latch = new CountDownLatch(1);

            // 开始接受连接（异步）
            serverChannel.accept(null, new AcceptHandler(serverChannel));

            // 等待
            latch.await();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 连接接受处理器
    static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
        private AsynchronousServerSocketChannel serverChannel;

        public AcceptHandler(AsynchronousServerSocketChannel serverChannel) {
            this.serverChannel = serverChannel;
        }

        @Override
        public void completed(AsynchronousSocketChannel client, Void attachment) {
            // 1. 继续接受下一个连接
            serverChannel.accept(null, this);

            // 2. 处理当前连接
            System.out.println("新客户端连接: " + client);

            // 创建读缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 异步读取数据
            client.read(buffer, buffer, new ReadHandler(client));
        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            System.err.println("接受连接失败:");
            exc.printStackTrace();
        }
    }

    // 数据读取处理器
    static class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
        private AsynchronousSocketChannel client;

        public ReadHandler(AsynchronousSocketChannel client) {
            this.client = client;
        }

        @Override
        public void completed(Integer bytesRead, ByteBuffer buffer) {
            System.out.println(bytesRead);
            if (bytesRead > 0) {
                // 准备读取缓冲区数据
                buffer.flip();

                // Echo回显：将读取的数据写回客户端
                client.write(buffer, buffer, new WriteHandler(client));

            }
            else if (bytesRead == -1) {

            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            System.err.println("读取数据失败:");
            exc.printStackTrace();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 数据写入处理器
    static class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {
        private AsynchronousSocketChannel client;

        public WriteHandler(AsynchronousSocketChannel client) {
            this.client = client;
        }

        @Override
        public void completed(Integer bytesWritten, ByteBuffer buffer) {
            // 清空缓冲区，准备下一次读取
            buffer.clear();

            // 继续异步读取
            client.read(buffer, buffer, new ReadHandler(client));
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            System.err.println("写入数据失败:");
            exc.printStackTrace();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}