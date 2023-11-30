package cn.argento.askia.tcp.aio;

import sun.jvm.hotspot.interpreter.BytecodeRet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.time.LocalDateTime;

public class AIOEchoKeepAliveServer {
    public static void main(String[] args) {
        final AIOEchoKeepAliveServer aioEchoKeepAliveServer = new AIOEchoKeepAliveServer();
        aioEchoKeepAliveServer.startAccept();
    }

    public static void block(){
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final int SERVER_PORT = 9999;
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    private InetSocketAddress serverSocketAddress;
    private int count = 0;
    public AIOEchoKeepAliveServer(){
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocketAddress = new InetSocketAddress(SERVER_PORT);
        try {
            asynchronousServerSocketChannel.bind(serverSocketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAccept(){
        // 接收连接！
        asynchronousServerSocketChannel.accept("当前时间：" + LocalDateTime.now(), new CompletionHandler<AsynchronousSocketChannel, String>() {
            // 连接成功！
            @Override
            public void completed(AsynchronousSocketChannel result, String attachment) {
                String string = "当前时间：" + LocalDateTime.now();
                System.out.println(attachment + " | " + string);
                try {
                    // 再次监听下一次的连接！
                    asynchronousServerSocketChannel.accept("当前时间：" + LocalDateTime.now(), this);
                    // 回传欢迎语句！
                    welcome(result, attachment);
                    // 创建读写线程！
                    final Thread thread = new Thread(new ServerWaiterThread(result));
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, String attachment) {

            }
        });
        block();

    }

    private void welcome(AsynchronousSocketChannel result, String attachment) throws IOException {
        final InetSocketAddress remoteAddress = (InetSocketAddress) result.getRemoteAddress();
        System.out.println(attachment + "，欢迎用户：" + remoteAddress.getHostName() + "，IP地址：" + remoteAddress.getAddress().getHostAddress());
        // 回写建立连接成功！信息
        String welcomeBack = "当前时间："+ LocalDateTime.now() + "，服务器已与您建立连接！您是第" + (++count) + "位连接到服务器的用户！";
        result.write(ByteBuffer.wrap(welcomeBack.getBytes()), "服务器回写成功", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                System.out.println("当前时间："+ LocalDateTime.now() + "," + attachment + "，回写了" + result + "个字节！");
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.println("当前时间："+ LocalDateTime.now() + "," + "服务器回写失败！");
                exc.printStackTrace();
            }
        });
    }

}

class ServerWaiterThread implements Runnable{

    private AsynchronousSocketChannel asynchronousSocketChannel;
    private final String[] shutdownReading = {"shutdown reading", "exit reading", "exit read"};

    private boolean isKeepOnRead(String msg){
        for (String exitStr :
                shutdownReading) {
            if (exitStr.equalsIgnoreCase(msg)){
                return false;
            }
        }
        return true;
    }

    public ServerWaiterThread(AsynchronousSocketChannel asynchronousSocketChannel){
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // Read方法是异步的，他的执行和当前代码无关！
        asynchronousSocketChannel.read(byteBuffer, "接收时间：" + LocalDateTime.now(), new CompletionHandler<Integer, String>() {
            // 如果成功读入了内容，我们模拟服务端会进行ack并回写！
            @Override
            public void completed(Integer result, String attachment) {
                System.out.println(attachment + ",读了" + result + "个字节！");
                final byte[] array = byteBuffer.array();
                String context = new String(array, 0, array.length);
                System.out.println(attachment + ",内容是：" + context);
                // 清空ByteBuffer
                // 读完之后就进行回写！注意当前读的内容可能不是全部内容，所以读完之后还要继续写！
                byteBuffer.clear();
                // 读完数据了！
                if (result == -1){
                    asynchronousSocketChannel.write(ByteBuffer.wrap(("当前时间："+ LocalDateTime.now() + "," + "数据已读完！").getBytes()), "服务器回写成功", new CompletionHandler<Integer, String>() {
                        @Override
                        public void completed(Integer result, String attachment) {
                            System.out.println("当前时间："+ LocalDateTime.now() + "," + attachment + "，回写了" + result + "个字节！");
                        }

                        @Override
                        public void failed(Throwable exc, String attachment) {
                            System.out.println("当前时间："+ LocalDateTime.now() + "," + "服务器回写失败！");
                            exc.printStackTrace();
                        }
                    });
                }else{
                    // 数据还没读完，则回写读了的部分！
                    try {
                        String writeBack = "响应时间：" + LocalDateTime.now() + "服务器收到客户" + asynchronousSocketChannel.getRemoteAddress() + "的信息：" +  context;
                        asynchronousSocketChannel.write(ByteBuffer.wrap(writeBack.getBytes()), "服务器回写成功", new CompletionHandler<Integer, String>() {
                            @Override
                            public void completed(Integer result, String attachment) {
                                System.out.println("当前时间："+ LocalDateTime.now() + "," + attachment + "，回写了" + result + "个字节！");
                            }

                            @Override
                            public void failed(Throwable exc, String attachment) {
                                System.out.println("当前时间："+ LocalDateTime.now() + "," + "服务器回写失败！");
                                exc.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // 然后再继续读！！
                asynchronousSocketChannel.read(byteBuffer, "接收时间：" + LocalDateTime.now(), this);
            }

            @Override
            public void failed(Throwable exc, String attachment) {

            }
        });
        // 需要Block掉线程！
        AIOEchoKeepAliveServer.block();
    }
}
