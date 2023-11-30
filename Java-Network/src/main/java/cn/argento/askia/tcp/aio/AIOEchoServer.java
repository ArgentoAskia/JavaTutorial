package cn.argento.askia.tcp.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class AIOEchoServer {
    public static void main(String[] args) throws IOException {
        // 1.打开异步通道
        AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        // 2.绑定端口
        final InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",9999);
        asynchronousServerSocketChannel.bind(inetSocketAddress);

        // 3.监听来宾，因为监听是非阻塞的，所以会立刻返回，main线程会立刻结束，因此我们需要一个死循环（accept方法会被一直调用，但不是每次调用都有结果，只要有结果就会调用回调函数！）：
        while(true){
            // accept是异步函数，你可以传递一个任何的attachment对象当作参数给CompletionHandler接口作为参数使用，如一些必要的信息
            asynchronousServerSocketChannel.accept("当前时间是：" + LocalDateTime.now(), new CompletionHandler<AsynchronousSocketChannel, String>() {
                // 当加入成功则会触发该回调函数！
                @Override
                public void completed(AsynchronousSocketChannel socketChannel, String attachment) {
                    // result代表服务端和连接进来的客户端的一条连接！（注意这是从服务端角度出发的！）
                    // attachment是服务端预定义的信息！
                    // 如果成功则需要继续监听下一条连接,如果不加这行，则无法接收下一个连接！
//                    asynchronousServerSocketChannel.accept("当前时间是：" + LocalDateTime.now(), this);
                    try {
                        // 获取连接用户
                        final SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                        String welcomeStr = attachment + ",欢迎用户：" +remoteAddress;
                        System.out.println(welcomeStr);
                        String accpetStr = "收到来自用户" + remoteAddress + "您的信息：";
                        // 3.分配Buffer，读取信息
                        // 如果使用了小Buffer或者是网络原因等，需要读入多次，则怎么办呢？解决办法是需要手动调用read方法多次才能读完！
                        // 但是有个问题，那就是你无法知道调用了多少次read，才真正读完了所有数据！
                        // 因为当我们读完所有数据之后，再调用一次read，他会立刻返回，或者转到failed()！知道客户端再发信息过来则会再次read()
                        // 因此一种办法是采用标识符，当客户发送所有数据之后（调用了多次read之后，最后一次read读了这个标识符，然后知道要写了）
                        // 当然这个要靠开发者自己控制！AIO不负责确认read()是否读完了！见下面的注释！
                        // 另外一种方法是，当读入一次数据之后，立刻Write()：
                        // 如果读完数据之后，调用write()再立即调用read将会读到-1个字节，代表读完了！如果调用write之前还有字节，则会继续读！
                        ByteBuffer readBuffer = ByteBuffer.allocate(3);
//                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(readBuffer, "自定义的对象将会被传递到回调函数" + LocalDateTime.now(), new CompletionHandler<Integer, String>() {
                            @Override
                            public void completed(Integer result, String attachment) {
                                // result:代表成功读取的字节数量！
                                // attachment = "自定义的对象将会被传递到回调函数"
                                System.out.println("服务端读取了" + result + "个字节！");
                                System.out.println("attachment：" + attachment);
                                readBuffer.flip();
                                byte[] bytes = new byte[readBuffer.remaining()];
                                readBuffer.get(bytes);
                                String body = new String(bytes, StandardCharsets.UTF_8);
                                System.out.println(accpetStr + body);
                                // @是退出标志
//                                if (body.contains("@")){
                                // 或者只要读到了数据就立刻回写！，这样后续的read能读到-1！
                                if (body.contains("Se")){
                                     // 4.回写数据给客户端！
                                    System.out.println("回写数据！");
                                    final ByteBuffer writeBack = ByteBuffer.wrap((accpetStr + body).getBytes());
                                    socketChannel.write(writeBack, "回写数据成功，时间：" + LocalDateTime.now(), new CompletionHandler<Integer, String>() {
                                        @Override
                                        public void completed(Integer result, String attachment) {
                                            System.out.println("回写了" + result + "个字节!");
                                            System.out.println(attachment);
                                            System.out.println();

//                                             5.关闭服务器
                                            if (asynchronousServerSocketChannel.isOpen()){
                                                try {
                                                    asynchronousServerSocketChannel.close();
                                                    // 退出程序！
                                                    System.exit(0);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        @Override
                                        public void failed(Throwable exc, String attachment) {
                                            exc.printStackTrace();
                                            try {
                                                asynchronousServerSocketChannel.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                // !!!多次读写！
                                // 如果是小Buffer，则需要再次触发读！
                                // 清空Buffer，然后再读！
                                // 如果读完数据之后，调用write()再立即调用read将会读到-1个字节，代表读完了！如果调用write之前还有字节，则会继续读！
                                readBuffer.clear();
                                socketChannel.read(readBuffer, "自定义的对象将会被传递到回调函数" + LocalDateTime.now(), this);
                            }

                            @Override
                            public void failed(Throwable exc, String attachment) {
                                exc.printStackTrace();
//                                try {
//                                    asynchronousServerSocketChannel.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // 当加入失败则会触发该回调函数！
                @Override
                public void failed(Throwable exc, String attachment) {
                    exc.printStackTrace();
                    // 如果无法加入那就关闭服务端把！
//                   if (asynchronousServerSocketChannel.isOpen()){
//                       try {
//                           asynchronousServerSocketChannel.close();
//                       } catch (IOException e) {
//                           e.printStackTrace();
//                       }
//                   }
                }
            });
            System.in.read();
        }

    }
}
