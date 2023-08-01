package cn.argento.askia.tcp.bio;


import java.io.*;
import java.net.*;
import java.util.UUID;

/**
 * Java TCP Socket编程的两大核心就是下面这两个类：
 * ServerSocket:
 *      public ServerSocket()
 *      public ServerSocket(int port)
 *      public ServerSocket(int port, int backlog):backlog代表最大链接数
 *      public ServerSocket(int port, int backlog, InetAddress bindAddr)
 * Socket:
 */
public class BIOEchoServer {
    /**
     * 创建服务器的核心就是先创建一个ServerSocket，然后再调用accept()等待连接进来.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // 1.创建一个ServerSocket对象
        ServerSocket serverSocket = new ServerSocket();
        // 2.也可以使用InetSocketAddress辅助创建服务器，这个类类似于客户端的InetAddress
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);
        serverSocket.bind(inetSocketAddress);
        // 2.监听连接进来,需要使用一个死循环
        while(true){
            // accept方法将会阻塞，直到有链接进来
            // close 方法将关闭ServerSocket
            Socket accept = serverSocket.accept();

            // 如果有链接进来，我们就分配一个线程来处理它，
            // 为了提高效率，后期将使用线程池
            new Thread(() ->{
                // 3.获取socket的输入输出流，输入流是传内容给服务器的，输出流是传东西给客户端的
                // 这也是网络编程的核心
                try {
                    InputStream inputStream = accept.getInputStream();
                    OutputStream outputStream = accept.getOutputStream();

                    // Socket端的IP+端口
                    InetAddress inetAddress = accept.getInetAddress();
                    int port = accept.getPort();
                    // ServerSocket端的IP+端口
                    InetAddress localAddress = accept.getLocalAddress();
                    int localPort = accept.getLocalPort();
                    // 下面的这个API等于getLocalPort() + getLocalAddress()
                    // SocketAddress localSocketAddress = accept.getLocalSocketAddress();
                    // 下面的这个API等于getInetAddress() + getPort
                    // SocketAddress remoteSocketAddress = accept.getRemoteSocketAddress();

                    // 一些于TCP相关联的头
                    // 是否保持活跃
                    boolean keepAlive = accept.getKeepAlive();
                    // 接收的buffer大小
                    int receiveBufferSize = accept.getReceiveBufferSize();
                    // 发送的buffer大小
                    int sendBufferSize = accept.getSendBufferSize();
                    // 获取连接超时时长
                    int soTimeout = accept.getSoTimeout();
                    boolean tcpNoDelay = accept.getTcpNoDelay();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    // read方法返回读取到的字节的长度，也就是这次read，读了有多少个字节，因此需要这样解析！
                    while((len = inputStream.read(buffer)) != -1){
                        byteArrayOutputStream.write(buffer,0, len);
                    }
                    String context = byteArrayOutputStream.toString();
                    String s = UUID.randomUUID().toString();
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
                    accept.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }



    }
}
