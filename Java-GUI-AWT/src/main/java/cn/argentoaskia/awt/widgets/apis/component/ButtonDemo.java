package cn.argentoaskia.awt.widgets.apis.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonDemo extends Frame {
    public static void main(String[] args) {
        new ButtonDemo();
    }

    private Button actionCommand;
    private Button background;
    private Button bounds;
    private Button cursors;
    private Button enabled;
    private Button font;
    private Button foreground;
    private Button label;
    private Button location;
    private Button maxSize;
    private Button minSize;
    private Button name;
    private Button preferredSize;
    private Button size;
    private Button visible;

    private Panel panel1;
    private Panel panel2;

    public void init(){
        panel1 = new Panel();
        JButton jButton;
        actionCommand = new Button("actionCommand");
        actionCommand.setActionCommand("this is a actionCommand, you can get this message in actionListener");
        background = new Button("background");
        background.setBackground(Color.cyan);
        cursors = new Button("cursors");
        cursors.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        enabled = new Button("enable");
        enabled.setEnabled(false);
        font = new Button("font");
        font.setFont(new Font("SansSerif", Font.BOLD, 30));
        foreground = new Button("foreground");
        foreground.setForeground(Color.green);
        label = new Button("label");
        label.setLabel("this is a button");
        preferredSize = new Button("preferredSize");
        preferredSize.setPreferredSize(new Dimension(100, 100));
        panel1.add(actionCommand);
        panel1.add(background);
        panel1.add(cursors);
        panel1.add(enabled);
        panel1.add(font);
        panel1.add(foreground);
        panel1.add(label);
        panel1.add(preferredSize);

        // 下面这些方法一般在非布局模式情况下使用！
        panel2 = new Panel();
        panel2.setLayout(null);
        panel2.setBounds(300, 300 , 200, 200);
        bounds = new Button("bounds");
        bounds.setBounds(0,0 ,100, 100);
        location = new Button("location");
        location.setLocation(100, 100);
        location.setSize(200, 200);
        maxSize = new Button("maxSize");
        maxSize.setMaximumSize(new Dimension(50, 50));
        minSize = new Button("minSize");
        minSize.setMinimumSize(new Dimension(200, 200));
        name = new Button("name");
        name.setName("this is button name");
        size = new Button("size");
        size.setSize(200, 200);
        visible = new Button("visible");
        panel2.add(bounds);
        panel2.add(location);
        panel2.add(maxSize);
        panel2.add(minSize);
        panel2.add(name);
        panel2.add(size);
        panel2.add(visible);

        add(panel1, BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);

    }

    public ButtonDemo(){
        init();
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }


//    private void init(){
//        button.setActionCommand();
//        button.setBackground();
//        button.setBounds();
//        button.setCursor();
//        button.setEnabled();
//        button.setFont();
//        button.setForeground();
//        button.setLabel();
//        button.setLocation();
//        button.setMaximumSize();
//        button.setMinimumSize();
//        button.setName();
//        button.setPreferredSize();
//        button.setSize();
//        button.setVisible();

//    }
}
