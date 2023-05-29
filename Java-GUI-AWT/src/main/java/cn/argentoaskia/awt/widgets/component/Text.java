package cn.argentoaskia.awt.widgets.component;

import javax.swing.*;
import java.awt.*;

public class Text extends JFrame {
    public static final Text text = new Text();
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Hello！！！！", 100, 100);
    }
}
