package cn.argento.askia.awt.core.attrs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponentAt extends JFrame {
    public ComponentAt() {
        setTitle("getComponentAt 演示 - 点谁出谁");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 1. 底层容器（青色）
        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // 2. 放 3 个按钮（不同颜色）
        JButton btnA = new JButton("按钮 A");
        btnA.setBackground(Color.YELLOW);
        JButton btnB = new JButton("按钮 B");
        btnB.setBackground(Color.GREEN);
        JButton btnC = new JButton("按钮 C");
        btnC.setBackground(Color.MAGENTA);

        panel.add(btnA);
        panel.add(btnB);
        panel.add(btnC);

        // 3. 关键：给底层面板加鼠标监听
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 坐标是相对于 panel 的左上角
                Component c = panel.getComponentAt(e.getX(), e.getY());
                System.out.println("坐标 (" + e.getX() + "," + e.getY() + ")  →  组件: " +
                        c.getClass().getSimpleName() +
                        "  文本: " + (c instanceof JButton ? ((JButton) c).getText() : "无"));
            }
        });

        setContentPane(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComponentAt().setVisible(true));
    }
}
