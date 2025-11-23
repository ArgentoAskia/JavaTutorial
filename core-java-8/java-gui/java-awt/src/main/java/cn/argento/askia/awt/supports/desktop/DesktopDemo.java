package cn.argento.askia.awt.supports.desktop;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DesktopDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        browseBaidu();
//        editFile();
//        openFile();
//        printFile();
//        mail();
        mail2();
    }

    /**
     * 使用默认浏览器打开www.baidu.com
     */
    private static void browseBaidu(){
        // 如果支持桌面环境
        if (Desktop.isDesktopSupported()) {
            // 获取桌面环境
            Desktop desktop = Desktop.getDesktop();
            // 支持默认浏览器打开
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    // 打开百度
                    desktop.browse(URI.create("https://www.baidu.com"));
                } catch (IOException e) {
                    // 打开失败
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 外部默认程序编辑文件
     */
    private static void editFile(){
        // 如果支持桌面环境
        if (Desktop.isDesktopSupported()) {
            // 获取桌面环境
            Desktop desktop = Desktop.getDesktop();
            // 支持默认浏览器打开
            if (desktop.isSupported(Desktop.Action.EDIT)) {
                try {
                    // 打开百度
                    File file = new File("core-java-8/java-gui/java-awt/src/main/resources/edit.txt");
                    desktop.edit(file);
                } catch (IOException e) {
                    // 打开失败
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 外部默认程序打开文件
     */
    private static void openFile(){
        // 如果支持桌面环境
        if (Desktop.isDesktopSupported()) {
            // 获取桌面环境
            Desktop desktop = Desktop.getDesktop();
            // 支持默认浏览器打开
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    // 打开百度
                    File file = new File("core-java-8/java-gui/java-awt/src/main/resources/picture.png");
                    desktop.open(file);
                } catch (IOException e) {
                    // 打开失败
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 外部默认程序打印文件
     */
    private static void printFile(){
        // 如果支持桌面环境
        if (Desktop.isDesktopSupported()) {
            // 获取桌面环境
            Desktop desktop = Desktop.getDesktop();
            // 支持默认浏览器打开
            if (desktop.isSupported(Desktop.Action.PRINT)) {
                try {
                    // 打开百度
                    File file = new File("core-java-8/java-gui/java-awt/src/main/resources/picture.png");
                    desktop.print(file);
                } catch (IOException e) {
                    // 打开失败
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打开默认邮件客户端
     */
    private static void mail(){
        // 如果支持桌面环境
        if (Desktop.isDesktopSupported()) {
            // 获取桌面环境
            Desktop desktop = Desktop.getDesktop();
            // 支持默认浏览器打开
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    desktop.mail();
                } catch (IOException e) {
                    // 打开失败
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 带正文标题的启动mail方式
     * @throws UnsupportedEncodingException
     */
    private static void mail2() throws UnsupportedEncodingException {
        String subject = URLEncoder.encode("Hello", String.valueOf(StandardCharsets.UTF_8)).replace("+", "%20");
        String body      = URLEncoder.encode("Line1\nLine2", String.valueOf(StandardCharsets.UTF_8)).replace("+", "%20");
        URI mailto = URI.create("mailto:someone@example.com?subject=" + subject + "&body=" + body);

        // 如果支持桌面环境
        if (Desktop.isDesktopSupported()) {
            // 获取桌面环境
            Desktop desktop = Desktop.getDesktop();
            // 支持默认浏览器打开
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    Desktop.getDesktop().mail(mailto);
                } catch (IOException e) {
                    // 打开失败
                    e.printStackTrace();
                }
            }
        }
    }
}
