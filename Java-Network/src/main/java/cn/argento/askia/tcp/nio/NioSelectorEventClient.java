package cn.argento.askia.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class NioSelectorEventClient {

	public static void main(String[] args) {

		String host = "127.0.0.1";
		int port = 8001;

		Selector selector = null;
		SocketChannel socketChannel = null;

		try 
		{
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false); // 非阻塞

			// 如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
			if (socketChannel.connect(new InetSocketAddress(host, port))) 
			{
				socketChannel.register(selector, SelectionKey.OP_READ);
				doWrite(socketChannel);
			} 
			else 
			{
				// 控制这个selector上的该channel下一次的操作是CONNECT
				socketChannel.register(selector, SelectionKey.OP_CONNECT);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		while (true) 
		{
			try 
			{
				// 基于Selector和SelectionKey动作的方式来指定当前channel是哪一个操作，因此来量化操作Channel
				// selectNow()、select()用来进行选择操作！，选中的key将会在下一轮的select()中被枚举出来！
				int i = selector.selectNow();
				System.out.println(i);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) 
				{
					key = it.next();
					it.remove();
					try 
					{
						//处理每一个channel
						handleInput(selector, key);
					} 
					catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null)
								key.channel().close();
						}
					}
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	

		// 多路复用器关闭后，所有注册在上面的Channel资源都会被自动去注册并关闭
//		if (selector != null)
//			try {
//				selector.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
	}

	public static void doWrite(SocketChannel sc) throws IOException {
		byte[] str = UUID.randomUUID().toString().getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(str.length);
		writeBuffer.put(str);
		writeBuffer.flip();
		sc.write(writeBuffer);
	}

	public static void handleInput(Selector selector, SelectionKey key) throws Exception {

		if (key.isValid()) {
			// 判断是否连接成功
			SocketChannel sc = (SocketChannel) key.channel();
			if (key.isConnectable()) {
				if (sc.finishConnect()) {
					// 控制这个selector上的该channel下一次的操作是WRITE操作
					// 可以使用key.isXXX()来判断下一次的操作是什么，从而进行相关操作！
					// 使用register()方法来注册操作键
					sc.register(selector, SelectionKey.OP_WRITE);
				} 				
			}
			if (key.isReadable()) {
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("Server said : " + body);
				} else if (readBytes < 0) {
					// 对端链路关闭
					key.cancel();
					sc.close();
				} else
					; // 读到0字节，忽略

				sc.register(selector, SelectionKey.OP_WRITE);
			}

			if (key.isWritable()){
				doWrite(sc);
				sc.register(selector, SelectionKey.OP_READ);
			}
			Thread.sleep(3000);
		}
	}
}
