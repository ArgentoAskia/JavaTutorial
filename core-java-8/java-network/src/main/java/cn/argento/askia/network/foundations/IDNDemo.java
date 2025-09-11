package cn.argento.askia.network.foundations;

import java.net.IDN;

/**
 * 现代化的域名一般都支持中文, 比如：www.askia.网站。
 * 而因为许多网络协议和系统只支持 ASCII 字符。通过使用 IDN.toASCII，你可以将非 ASCII 域名转换为兼容的格式。而通过toUnicode()可以转回来
 */
public class IDNDemo {
    public static void main(String[] args) {
        String internationalizedDomainName = "www.例子.com";
        try {
            String asciiDomainName = IDN.toASCII(internationalizedDomainName, IDN.ALLOW_UNASSIGNED);
            System.out.println("ASCII Domain Name: " + asciiDomainName);
            String unicodeDomainName = IDN.toUnicode(asciiDomainName, IDN.ALLOW_UNASSIGNED);
            System.out.println("Unicode Domain Name: " + unicodeDomainName);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid domain name format.");
        }
    }
}
