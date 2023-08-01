package cn.argento.askia.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorEventServer {

    public static void main(String[] args) throws IOException {
    	int port = 8001;
    	Selector selector = null;
    	ServerSocketChannel servChannel = null;
    	
    	try {
			selector = Selector.open();
			servChannel = ServerSocketChannel.open();
			servChannel.configureBlocking(false);
			servChannel.socket().bind(new InetSocketAddress(port), 1024);
			// 控制下一个兴趣的事件是什么，因此来控制通道读写
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("服务器在8001端口守候");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
    	
    	while(true)
    	{
    		try {
    			selector.select(1000);
    			Set<SelectionKey> selectedKeys = selector.selectedKeys();
    			Iterator<SelectionKey> it = selectedKeys.iterator();
    			SelectionKey key = null;
    			while (it.hasNext()) {
    				key = it.next();
    				it.remove();
    				try {
    					handleInput(selector,key);
    				} catch (Exception e) {
    					if (key != null) {
    						key.cancel();
    						if (key.channel() != null)
    							key.channel().close();
    					}
    				}
    			}
    		} 
    		catch(Exception ex)
    		{
    			ex.printStackTrace();    			
    		}
    		
    		try
    		{
    			Thread.sleep(500);
    		}
    		catch(Exception ex)
    		{
    			ex.printStackTrace();    			
    		}
    	}
    }
    
    public static void handleInput(Selector selector, SelectionKey key) throws IOException {

		if (key.isValid()) {
			// 处理新接入的请求消息
			if (key.isAcceptable()) {
				// Accept the new connection
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				// Add the new connection to the selector
				sc.register(selector, SelectionKey.OP_READ);
			}
			if (key.isReadable()) {
				// Read the data
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String request = new String(bytes, "UTF-8"); //接收到的输入
					System.out.println("client said: " + request);
					
					String response = request + " 666";
					doWrite(sc, response);
				} else if (readBytes < 0) {
					// 对端链路关闭
					key.cancel();
					sc.close();
				} else
					; // 读到0字节，忽略
			}
		}
	}

	public static void doWrite(SocketChannel channel, String response) throws IOException {
		if (response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}
}
