package cn.argento.askia.awt.core.attrs;

import javax.swing.*;
import java.awt.*;

public class AlignmentExample extends JFrame {
    public AlignmentExample() {
        setTitle("Alignment Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 创建组件并设置 AlignmentX 和 AlignmentY
        JButton leftAlignedButton = new JButton("Left Aligned");
        leftAlignedButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton centerAlignedButton = new JButton("Center Aligned");
        centerAlignedButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton rightAlignedButton = new JButton("Right Aligned");
        rightAlignedButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // 添加组件到 JFrame
        add(leftAlignedButton);
        add(centerAlignedButton);
        add(rightAlignedButton);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AlignmentExample::new);
    }
}
