package cn.argento.askia.tcp.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用服务端
 */
public class NIOSelectorServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannel3 = ServerSocketChannel.open();

        serverSocketChannel1.bind(new InetSocketAddress(7777));
        serverSocketChannel2.bind(new InetSocketAddress(8888));
        serverSocketChannel3.bind(new InetSocketAddress(9999));

        serverSocketChannel1.configureBlocking(false);
        serverSocketChannel2.configureBlocking(false);
        serverSocketChannel3.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel2.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel3.register(selector, SelectionKey.OP_ACCEPT);


        Set<SelectionKey> keys = selector.keys();
        System.out.println("已注册服务器通道的数量:" + keys.size());	//3
        System.out.println("通道：" + Arrays.toString(keys.toArray()));

        while(true){
            int select  = selector.selectNow();
//            int select  = selector.select();
            System.out.println("连接服务器的客户端数量：" + select);
            //Selector的selectedKeys()方法:获取当前已经连接的通道集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("已经到服务器的通道数量:" + selectionKeys.size());


            //处理Selector监听到客户端的请求事件:遍历Set集合,获取每一个SelectionKey对象
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()){
                SelectionKey selectionKey = it.next();
                int i = selectionKey.readyOps();
                System.out.println(i);
                //获取SelectionKey里边封装的服务器ServerSocketChannel对象
                ServerSocketChannel channel = (ServerSocketChannel)selectionKey.channel();
                System.out.println("获取当前通道ServerSocketChannel监听的端口号:" + channel.getLocalAddress() + channel);
                //处理监听的accept事件==>获取请求服务器的客户单SocketChannel对象
                SocketChannel socketChannel = channel.accept();
                System.out.println(socketChannel);
                //读取客户端SocketChannel发送的数据
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int len = socketChannel.read(buffer);
                System.out.println("服务器读取到客户端发送的数据:" + new String(buffer.array(), 0, len));

                //处理完SelectionKey监听到的事件,要在Set集合中移除已经处理完的SelectionKey对象
                it.remove();//使用迭代器对象移除集合中的元素,不会抛出并发修改异常(移除的就是it.next()方法取出的对象)
            }
            //获取完一个客户端的连接,睡眠2秒,在进行下一次轮询
            Thread.sleep(2000);
        }



    }
}
