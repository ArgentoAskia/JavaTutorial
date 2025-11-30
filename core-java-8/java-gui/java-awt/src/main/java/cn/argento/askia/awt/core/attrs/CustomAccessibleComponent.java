package cn.argento.askia.awt.core.attrs;

import javax.accessibility.*;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class CustomAccessibleComponent extends JPanel implements Accessible {
    private String displayText = "Custom Component";

    public CustomAccessibleComponent() {
        setPreferredSize(new Dimension(200, 100));
        setBackground(Color.LIGHT_GRAY);

        // 设置可访问性上下文
        getAccessibleContext().setAccessibleName("Custom Accessible Component");
        getAccessibleContext().setAccessibleDescription("A custom component with accessibility support");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString(displayText, 20, 50);
    }

    @Override
    public AccessibleContext getAccessibleContext() {
        return new CustomAccessibleContext();
    }

    // 自定义 AccessibleContext 实现
    private class CustomAccessibleContext extends AccessibleContext {
        @Override
        public String getAccessibleName() {
            return "Custom Accessible Component";
        }

        @Override
        public String getAccessibleDescription() {
            return "This is a custom component with full accessibility support";
        }

        @Override
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PANEL;
        }

        @Override
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = new AccessibleStateSet();
            states.add(AccessibleState.ENABLED);
            states.add(AccessibleState.VISIBLE);
            states.add(AccessibleState.SHOWING);
            return states;
        }

        @Override
        public int getAccessibleIndexInParent() {
            return 0;
        }

        @Override
        public int getAccessibleChildrenCount() {
            return 0;
        }

        @Override
        public Accessible getAccessibleChild(int i) {
            return null;
        }

        @Override
        public Locale getLocale() throws IllegalComponentStateException {
            return null;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Accessible Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CustomAccessibleComponent());
        frame.pack();
        frame.setVisible(true);
    }
}