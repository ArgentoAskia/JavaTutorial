package cn.argento.askia.network.foundations;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class NetworkInterfaceDemo {
    public static void main(String[] args) throws SocketException {
        // 1.使用静态方法获取所有软件硬件网卡接口
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        // 2.进行遍历
        while(networkInterfaces.hasMoreElements()){
            System.out.println("============================================");
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            // 获取接口上的网卡显示名称
            String displayName = networkInterface.getDisplayName();
            // 获取接口名称
            String name = networkInterface.getName();
            // 获取MTU（Max Transmission Unit 最大传输单元）
            int mtu = networkInterface.getMTU();
            // 获取当前网卡接口的接口下标
            int index = networkInterface.getIndex();
            // 获取硬件地址，没有硬件地址的一般都是软接口
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            System.out.println("第" + (index) + "个网卡信息：");
            System.out.println("网卡显示名：" + displayName);
            System.out.println("接口名称：" + name);
            System.out.println("数据最大传输单元" + mtu);
            if (hardwareAddress != null){
                /**
                 * @apiNote 如何将一个十进制的字符串里面所有表示数字的内容都转成16进制字符串！！
                 */
//                MessageFormat messageFormat = new MessageFormat(
//                        "{0,number,##} - {1,number,##} - {2,number,##} - {3,number,##} - {4,number,##} - {5,number,##}"
//                );
                // Object[] Integer[] ？？
                Integer[] addresss = {
                        Byte.toUnsignedInt(hardwareAddress[0]),
                        Byte.toUnsignedInt(hardwareAddress[1]),
                        Byte.toUnsignedInt(hardwareAddress[2]),
                        Byte.toUnsignedInt(hardwareAddress[3]),
                        Byte.toUnsignedInt(hardwareAddress[4]),
                        Byte.toUnsignedInt(hardwareAddress[5])
                };
                String hardwareAddressFormat = String.format("%02X-%02X-%02X-%02X-%02X-%02X", addresss[0], addresss[1], addresss[2], addresss[3], addresss[4], addresss[5]);
                System.out.println("硬件地址：" + hardwareAddressFormat);
            }else {
                System.out.println("硬件地址：该网络接口没有硬件地址！");
            }

            // 获取网络地址
            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
            if (interfaceAddresses == null || interfaceAddresses.size() == 0){
                System.out.println("网络地址池：" + "没有网络地址");
            }else{
                System.out.println("网络地址池：" + Arrays.toString(interfaceAddresses.toArray()));
            }

            // 差不多的方法
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

            // 父接口
            NetworkInterface parent = networkInterface.getParent();
            // 子接口
            Enumeration<NetworkInterface> subInterfaces = networkInterface.getSubInterfaces();
            System.out.println("============================================");
            System.out.println();
        }
    }
}
