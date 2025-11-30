package cn.argento.askia.awt;

import javax.swing.*;
import java.awt.*;

public class NestedFrameExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Main Frame");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600);

            JDesktopPane desktopPane = new JDesktopPane();
            mainFrame.add(desktopPane);

            // 创建一个 JInternalFrame
            JInternalFrame internalFrame = new JInternalFrame("Nested Frame", true, true, true, true);
            internalFrame.setSize(400, 300);
            internalFrame.setLocation(50, 50);

            // 在 JInternalFrame 中添加内容
            internalFrame.add(new JLabel("This is a nested frame", SwingConstants.CENTER), BorderLayout.CENTER);

            // 将 JInternalFrame 添加到 JDesktopPane
            desktopPane.add(internalFrame);
            internalFrame.setVisible(true);

            mainFrame.setVisible(true);
        });
    }
}
