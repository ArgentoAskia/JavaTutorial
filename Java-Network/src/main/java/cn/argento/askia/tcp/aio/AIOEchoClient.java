package cn.argento.askia.tcp.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

public class AIOEchoClient {
    public static void main(String[] args) throws IOException {
        // 1.创建管道！
        final AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",9999);
        System.out.println(inetSocketAddress);
        // 2.创建连接,该函数会立刻返回，因此需要循环，否则main线程完毕了，可能连回调都没来得及调用
        // 另外Connect方法和Accept方法一旦调用之后不可二次调用！
        // @是退出标志！
       while (true){
           channel.connect(inetSocketAddress, "Hello AIO Server!@", new CompletionHandler<Void, String>() {
               // 如果连接成功就调用
               @Override
               public void completed(Void result, String attachment) {
                   System.out.println(result);
                   // 3.写数据给服务端
                   final ByteBuffer writeToServer = ByteBuffer.wrap(attachment.getBytes());
                   channel.write(writeToServer, attachment, new CompletionHandler<Integer, String>() {
                       @Override
                       public void completed(Integer result, String attachment) {
                           System.out.println("发送给服务端的字节数：" + result + ",发送成功！");
                           System.out.println(attachment);
                           // 4.读数据！
                           ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                           channel.read(readBuffer, null, new CompletionHandler<Integer, Object>() {
                               @Override
                               public void completed(Integer result, Object attachment) {
                                   readBuffer.flip();
                                   byte[] bytes = new byte[readBuffer.remaining()];
                                   readBuffer.get(bytes);
                                   String body = new String(bytes, StandardCharsets.UTF_8);
                                   System.out.println("服务端发来信息：" + body);
                                   // 5.关闭连接！
                                   if (channel.isOpen()) {
                                       try {
                                           channel.close();
                                           System.exit(0);
                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }
                                   }
                               }

                               @Override
                               public void failed(Throwable exc, Object attachment) {

                               }
                           });
                       }

                       @Override
                       public void failed(Throwable exc, String attachment) {

                       }
                   });
               }

               // 如果连接失败就调用
               @Override
               public void failed(Throwable exc, String attachment) {
                   exc.printStackTrace();
                   System.out.println("连接失败！");
               }
           });
           // 如果连接失败，则我们先等一会再来
           // 不加这个会导致异常退出！
//           try {
//               Thread.sleep(10000);
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
           System.in.read();
       }
    }
}
