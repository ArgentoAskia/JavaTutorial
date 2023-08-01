package cn.argento.askia.tcp.bio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 如果需要保持连接过程（聊天室等等），请使用available()方法来处理读！否则可能会卡死
 * 参考：https://blog.csdn.net/weixin_44398687/article/details/110007855
 */
public class BIOEchoKeepAliveServer {
    private final int SERVER_PORT = 9999;
    private ServerSocket serverSocket;
    private InetSocketAddress serverSocketAddress;

    public BIOEchoKeepAliveServer() throws IOException {
        serverSocket = new ServerSocket();
        serverSocketAddress = new InetSocketAddress(SERVER_PORT);
        serverSocket.bind(serverSocketAddress);
    }

    private void welcome(Socket accept, UUID uuid){
        try {
            OutputStream outputStream = accept.getOutputStream();
            InetAddress inetAddress = accept.getInetAddress();
            int port = accept.getPort();
            InetAddress localAddress = accept.getLocalAddress();
            int localPort = accept.getLocalPort();
            boolean keepAlive = accept.getKeepAlive();
            // 接收的buffer大小
            int receiveBufferSize = accept.getReceiveBufferSize();
            // 发送的buffer大小
            int sendBufferSize = accept.getSendBufferSize();
            // 获取连接超时时长
            int soTimeout = accept.getSoTimeout();
            boolean tcpNoDelay = accept.getTcpNoDelay();
            InputStream inputStream = accept.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // bug fix：卡死 https://blog.csdn.net/weixin_44398687/article/details/110007855
            // 使用available()来查看客户端发送了多少个字节
            int available = inputStream.available();
            byte[] buffer = new byte[available];
            int len = inputStream.read(buffer);
            // read方法返回读取到的字节的长度，也就是这次read，读了有多少个字节，因此需要这样解析！
            byteArrayOutputStream.write(buffer, 0, len);
            String context = byteArrayOutputStream.toString();
            String s = uuid.toString();
            String receive = "服务端已将您记录备案，您的UUID是" + s;
            String stringBuffer = "欢迎用户：" + inetAddress.getCanonicalHostName() + "访问主机：" +
                    localAddress.getHostAddress() + ":" + localPort + ", " +
                    "IP:Port为：" + inetAddress.getHostAddress() + ":" + port +
                    "\n" +
                    "客户端发送的内容是：" + context +
                    "\n" +
                    "是否KeepAlive：" + keepAlive + "\n" +
                    "是否TcpNoDelay：" + tcpNoDelay + "\n" +
                    "接收BufferSize: " + receiveBufferSize + "\n" +
                    "发送BufferSize: " + sendBufferSize + "\n" +
                    "连接建立之后保持连接的最大时间" + soTimeout +
                    "\n" +
                    "发送的内容是：" + receive;
            System.out.println(stringBuffer);
            outputStream.write(receive.getBytes());
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 标准BIO处理流程
    public void startConnecting() throws IOException {
        while(true){
            Socket accept = serverSocket.accept();
            System.out.println(accept + " | " + accept.hashCode());
            UUID uuid = UUID.randomUUID();
            new Thread(()->{
                welcome(accept, uuid);
                while(true){
                    try {
                        loop(accept,uuid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    private void loop(Socket accept, UUID uuid) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        while(accept.getInputStream().available() == 0){}
        int available = accept.getInputStream().available();
        byte[] buffer = new byte[available];
        int read = accept.getInputStream().read(buffer, 0, available);
        arrayOutputStream.write(buffer, 0, read);
        String saying = arrayOutputStream.toString();
        System.out.println(uuid.toString() + " said: " + saying);
        arrayOutputStream.close();
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write(("Server Recieve! what you said is: " + saying).getBytes());
    }

    public static void main(String[] args) throws IOException {
        BIOEchoKeepAliveServer bioEchoKeepAliveServer = new BIOEchoKeepAliveServer();
        bioEchoKeepAliveServer.startConnecting();
    }
}