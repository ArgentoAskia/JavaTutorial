package cn.argento.askia.tcp.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 如果需要保持连接过程（聊天室等等），请使用available()方法来处理读！否则可能会卡死
 */
public class BIOEchoKeepAliveClient {
    public static void main(String[] args) throws Exception {
        // 1.创建一个IP地址
        InetAddress ip = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        // 2.通过指定服务器的IP地址、端口号来建立连接
        Socket socket = new Socket(ip, 9999);
        System.out.println(socket + " | " + socket.hashCode());

        //3.使用客户端Socket对象中的方法getOutputStream，获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        //4.发送的内容
        String hello = "Hello Server!This is Client!";
        //5.发送给服务器
        os.write(hello.getBytes());

        //6.使用客户端Socket对象中的方法getInputStream，获取网络字节输入流InputStream对象
        InputStream is = socket.getInputStream();
        //7.使用网络字节输入流InputStream对象中的方法read，读取服务器回写的"上传成功!"
        int available2;
        while((available2 = is.available()) == 0){}
        byte[] bytes = new byte[available2];
        int len = is.read(bytes);
        System.out.println(new String(bytes, 0, len));


        Scanner scanner = new Scanner(System.in);
        lastLoop:
        while(true){
            while(scanner.hasNextLine()){
                String s = scanner.nextLine();
                if (s.equalsIgnoreCase("exit")){
                    break lastLoop;
                }else{
                    os.write(s.getBytes());
                }
                // 如果有内容回写回来，则读入
                int available = is.available();
                while((available = is.available()) == 0){}
                byte[] bytes1 = new byte[available];
                int read = is.read(bytes1);
                System.out.println(new String(bytes1, 0, read));
            }
        }
        //8.释放资源(FileInputStream对象, Socket)
        socket.close();
    }
}
