package cn.argento.askia.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOSelectorClient {
    public static void main(String[] args) {
        new Thread(() -> {
            //创建客户端对象,轮询连接服务器
            while (true) {
                try (SocketChannel socketChannel = SocketChannel.open();) {
                    System.out.println("客户端1开始连接7777端口...");
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 7777));

                    System.out.println("客户端1连接7777端口成功,给服务器发送数据");
                    socketChannel.write(ByteBuffer.wrap("你好服务器,我是连接7777端口号的客户端1对象!".getBytes()));

                    System.out.println("客户端1 7777发送数据完毕,结束轮询...");
                    break;
                } catch (IOException e) {
                    System.out.println("客户端1 连接7777端口异常");
                }
            }
        }).start();

        new Thread(() -> {
            //创建客户端对象,轮询连接服务器
            while (true) {
                try (SocketChannel socketChannel = SocketChannel.open();) {
                    System.out.println("客户端2开始连接7777端口...");
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 7777));

                    System.out.println("客户端2连接7777端口成功,给服务器发送数据");
                    socketChannel.write(ByteBuffer.wrap("你好服务器,我是连接7777端口号的客户端2对象!".getBytes()));

                    System.out.println("客户端2 7777发送数据完毕,结束轮询...");
                    break;
                } catch (IOException e) {
                    System.out.println("客户端2 连接7777端口异常");
                }
            }
        }).start();

        new Thread(() -> {
            //创建客户端对象,轮询连接服务器
            while (true) {
                try (SocketChannel socketChannel = SocketChannel.open();) {
                    System.out.println("客户端3开始连接8888端口...");
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 8888));

                    System.out.println("客户端3连接8888端口成功,给服务器发送数据");
                    socketChannel.write(ByteBuffer.wrap("你好服务器,我是连接8888端口号的客户端3对象!".getBytes()));

                    System.out.println("客户端3 8888发送数据完毕,结束轮询...");
                    break;
                } catch (IOException e) {
                    System.out.println("客户端3 连接8888端口异常");
                }
            }
        }).start();

        new Thread(() -> {
            //创建客户端对象,轮询连接服务器
            while (true) {
                try (SocketChannel socketChannel = SocketChannel.open();) {
                    System.out.println("客户端4 开始连接9999端口...");
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

                    System.out.println("客户端4 连接9999端口成功,给服务器发送数据");
                    socketChannel.write(ByteBuffer.wrap("你好服务器,我是连接9999端口号的客户端4对象!".getBytes()));

                    System.out.println("客户端4 9999发送数据完毕,结束轮询...");
                    break;
                } catch (IOException e) {
                    System.out.println("客户端4 连接9999端口异常");
                }
            }
        }).start();
    }
}
