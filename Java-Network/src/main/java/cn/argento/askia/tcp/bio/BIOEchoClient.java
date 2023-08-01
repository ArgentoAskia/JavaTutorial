package cn.argento.askia.tcp.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BIOEchoClient {
    public static void main(String[] args) throws IOException {
        // 1.创建一个IP地址
        InetAddress ip = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        // 2.通过指定服务器的IP地址、端口号来建立连接
        Socket socket = new Socket(ip, 9999);

        //3.使用客户端Socket对象中的方法getOutputStream，获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        //4.发送的内容
        String hello = "Hello Server!This is Client!";
        //5.发送给服务器
        os.write(hello.getBytes());
        // 6.发送结束，关闭发送流，只打开接收流等待接收！
        socket.shutdownOutput();

        //6.使用客户端Socket对象中的方法getInputStream，获取网络字节输入流InputStream对象
        InputStream is = socket.getInputStream();
        //7.使用网络字节输入流InputStream对象中的方法read，读取服务器回写的"上传成功!"
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes)) != -1){
            System.out.println(new String(bytes, 0, len));
        }

        //8.释放资源(FileInputStream对象, Socket)
        socket.close();

    }
}
