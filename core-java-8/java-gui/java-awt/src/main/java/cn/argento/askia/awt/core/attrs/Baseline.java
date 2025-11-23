package cn.argento.askia.awt.core.attrs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * AWT 的“组件基线”（baseline）就是 文字对齐的“虚拟水平线
 * 同一行里不同高/不同字体的组件，只要基线对齐，文字底部就刚好在一条直线上，看起来才整齐。
 * 下面的demo运行结果证明：按钮和标签高度不同，但文字底部在同一水平线——这就是 baseline 对齐的效果。
 */
public class Baseline {
    public static void main(String[] args) {
        Frame f = new Frame("Baseline Demo");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        JButton btn = new JButton("按钮");
        JLabel  lbl = new JLabel("标签文字");

        // 让二者基线对齐
        btn.setAlignmentY(Component.CENTER_ALIGNMENT);
        lbl.setAlignmentY(Component.CENTER_ALIGNMENT);
        lbl.setOpaque(true);
        lbl.setBackground(Color.GREEN);


        int h = btn.getHeight();
        int btnBase = btn.getBaseline(btn.getWidth(), h); // 例如 17
        int lblBase = lbl.getBaseline(lbl.getWidth(), h); // 例如 17
        System.out.println(btnBase);
        System.out.println(lblBase);
        // 同一行里 baseline 值相同 → 文字底部对齐。

        f.add(btn);
        f.add(lbl);
        f.pack();
        f.setVisible(true);
    }
}
