package cn.argento.askia.awt;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

public class CustomBorderExample extends JFrame {
    public CustomBorderExample() {
        setTitle("Custom Border Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建一个自定义边框
        Border customBorder = new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                // 绘制一个简单的矩形边框
                g.setColor(Color.BLUE);
                g.drawRect(x, y, width - 1, height - 1);

                // 绘制一个内部矩形边框
                g.setColor(Color.RED);
                g.drawRect(x + 5, y + 5, width - 11, height - 11);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(10, 10, 10, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        };

        // 创建一个 JPanel 并应用自定义边框
        JPanel panel = new JPanel();
        panel.setBorder(customBorder);
        panel.setPreferredSize(new Dimension(200, 100));
        panel.add(new JLabel("Custom Border"));

        // 将 JPanel 添加到 JFrame
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomBorderExample());
    }
}